package com.zyf.legou.search.service;

import com.sun.org.apache.xpath.internal.operations.Bool;
import com.zyf.legou.item.po.Brand;
import com.zyf.legou.item.po.Category;
import com.zyf.legou.item.po.SpecParam;
import com.zyf.legou.search.client.BrandClient;
import com.zyf.legou.search.client.CategoryClient;
import com.zyf.legou.search.client.SpecParamClient;
import com.zyf.legou.search.dao.GoodsDao;
import com.zyf.legou.search.po.Goods;
import com.zyf.legou.search.po.SearchRequest;
import com.zyf.legou.search.po.SearchResult;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.LongTerms;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilter;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SearchService {

    @Autowired
    private BrandClient brandClient;

    @Autowired
    private CategoryClient categoryClient;

    @Autowired
    private SpecParamClient specParamClient;

    @Autowired
    private GoodsDao goodsDao;


    public SearchResult search(SearchRequest searchRequest) {
        String key = searchRequest.getKey();
        if (key == null) {
            return null;
        }

        //????????????????????????
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        //???????????????????????????
        queryBuilder.withSourceFilter(new FetchSourceFilter(new String[]{"id", "subTitle", "skus"}, null));

        //??????????????????
        BoolQueryBuilder boolQueryBuilder = buildBasicQueryWithFilter(searchRequest);

        queryBuilder.withQuery(boolQueryBuilder);

        //??????
        Integer page = searchRequest.getPage() - 1; //es?????????0??????
        Integer size = searchRequest.getSize();
        queryBuilder.withPageable(PageRequest.of(page, size));

        //??????
        String sortBy = searchRequest.getSortBy();
        Boolean desc = searchRequest.getDescending();
        if (StringUtils.isNotBlank(sortBy)) {
            queryBuilder.withSort(SortBuilders.fieldSort(sortBy).order(desc ? SortOrder.DESC: SortOrder.ASC));
        }

        //??????????????????
        String brandAggsName = "brands";
        String categoryAggsName = "categories";
        queryBuilder.addAggregation(AggregationBuilders.terms(brandAggsName).field("brandId"));
        queryBuilder.addAggregation(AggregationBuilders.terms(categoryAggsName).field("cid3"));

        //????????????
        AggregatedPage<Goods> goodsResult = (AggregatedPage<Goods>) goodsDao.search(queryBuilder.build());

        //????????????
        List<Brand> brands = getBrandAgg(brandAggsName, goodsResult);
        List<Category> categories = getCategoryAgg(categoryAggsName, goodsResult);

        //??????????????????
        /*
            - ??????????????????1????????????????????????
            - ????????????????????????????????????????????????????????????
            - ??????NativeQueryBuilder,???????????????????????????key????????????
            - ?????????????????????????????????????????????query????????????
            - ????????????????????????????????????k?????????????????????,options????????????????????????
         */
        List<Map<String, Object>> specs = null;
        if (categories.size() == 1) {
            specs = getSpecs(categories.get(0).getId(), boolQueryBuilder);
        }


        //????????????
        long total = goodsResult.getTotalElements(); //?????????
        long totalPages = goodsResult.getTotalPages(); //?????????

        return new SearchResult(total, totalPages,goodsResult.getContent(), categories, brands, specs);
    }

    /**
     * ??????????????????
     * @param cid
     * @param query
     * @return
     */
    private List<Map<String, Object>> getSpecs(Long cid, QueryBuilder query) {
        List<Map<String,Object>> specs = new ArrayList<>();

        SpecParam sp = new SpecParam();
        sp.setCid(cid);
        sp.setSearching(true);
        List<SpecParam> specParams = this.specParamClient.selectSpecParamApi(sp);

        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();

        //???????????????????????????????????????????????????????????????????????????????????????
        queryBuilder.withQuery(query);

        for (SpecParam specParam : specParams) {
            String name = specParam.getName();//???????????????cpu??????
            queryBuilder.addAggregation(AggregationBuilders.terms(name).field("specs."+name+".keyword"));
        }

        AggregatedPage<Goods> aggs = (AggregatedPage<Goods>) this.goodsDao.search(queryBuilder.build());
        Map<String, Aggregation> stringAggregationMap = aggs.getAggregations().asMap();

        for (SpecParam specParam : specParams) {
            Map<String,Object> spec = new HashMap<>();
            String name = specParam.getName();
            if (stringAggregationMap.get(name) instanceof  StringTerms) {
                StringTerms stringTerms = (StringTerms) stringAggregationMap.get(name);
                List<String> val = new ArrayList<>();
                for (StringTerms.Bucket bucket : stringTerms.getBuckets()) {
                    val.add(bucket.getKeyAsString());
                }
                spec.put("k",name);//????????????????????????????????????
                spec.put("options",val);

                specs.add(spec);
            }
        }

        return specs;
    }


    /**
     * ??????????????????
     * @param searchRequest
     * @return
     */
    private BoolQueryBuilder buildBasicQueryWithFilter(SearchRequest searchRequest) {
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        boolQueryBuilder.must(QueryBuilders.matchQuery("all", searchRequest.getKey()));

        //????????????
        BoolQueryBuilder filterQueryBuilder = QueryBuilders.boolQuery();
        for (Map.Entry<String, String> entry : searchRequest.getFilter().entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            //?????????????????????????????????.keywords??????"spec."??????,??????????????????
            if (!"cid3".equals(key) && !"brandId".equals(key)) {
                key = "specs." + key + ".keyword";
            }
            //??????termQuery????????????
            filterQueryBuilder.must(QueryBuilders.termQuery(key, value));

        }

        return boolQueryBuilder.filter(filterQueryBuilder);
    }

    /**
     * ????????????????????????
     * @param brandAggsName
     * @param goodsResult
     * @return
     */
    private List<Brand> getBrandAgg(String brandAggsName, AggregatedPage<Goods> goodsResult) {
        LongTerms longTerms = (LongTerms) goodsResult.getAggregation(brandAggsName);
        List<Long> brandIds = new ArrayList<>();
        for (LongTerms.Bucket bucket : longTerms.getBuckets()) {
            brandIds.add(bucket.getKeyAsNumber().longValue());
        }
        return brandClient.selectBrandByIds(brandIds);
    }

    /**
     * ???????????????????????????
     * @param categoryAggsName
     * @param goodsResult
     * @return
     */
    private List<Category> getCategoryAgg(String categoryAggsName, AggregatedPage<Goods> goodsResult) {
        LongTerms longTerms = (LongTerms) goodsResult.getAggregation(categoryAggsName);
        List<Long> categoryIds = new ArrayList<>();
        for (LongTerms.Bucket bucket : longTerms.getBuckets()) {
            categoryIds.add(bucket.getKeyAsNumber().longValue());
        }
        List<String> names = this.categoryClient.queryNameByIds(categoryIds);

        List<Category> categories = new ArrayList<>();
        for (int i = 0; i < names.size(); i++) {
            Category category =new Category();
            category.setId(categoryIds.get(i));
            category.setTitle(names.get(i));
            categories.add(category);
        }
        return categories;
    }


}
