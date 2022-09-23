package com.zyf.legou.page.service.impl;

import com.zyf.legou.common.utils.JsonUtils;
import com.zyf.legou.item.po.Category;
import com.zyf.legou.item.po.Sku;
import com.zyf.legou.item.po.Spu;
import com.zyf.legou.item.po.SpuDetail;
import com.zyf.legou.page.client.CategoryClient;
import com.zyf.legou.page.client.SkuClient;
import com.zyf.legou.page.client.SpuClient;
import com.zyf.legou.page.client.SpuDetailClient;
import com.zyf.legou.page.service.PageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PageServiceImpl implements PageService {

    @Autowired
    private SkuClient skuClient;
    @Autowired
    private SpuClient spuClient;
    @Autowired
    private CategoryClient categoryClient;
    @Autowired
    private SpuDetailClient spuDetailClient;
    @Autowired
    private TemplateEngine templateEngine;

    //生成静态文件路径
    @Value("${pagepath}")
    private String pagepath;

    /**
     * 构建数据模型
     * @return
     */
    private Map<String,Object> buildDataModel(Long spuId){
        //构建数据模型
        Map<String,Object> dateMap = new HashMap<>();
        //获取spu 和SKU列表
        Spu spu = spuClient.edit(spuId);
        Category c1 = categoryClient.edit(spu.getId());
        //获取分类列表
        dateMap.put("category1",categoryClient.edit(spu.getCid1()));
        dateMap.put("category2",categoryClient.edit(spu.getCid2()));
        dateMap.put("category3",categoryClient.edit(spu.getCid3()));

        List<Sku> skus = skuClient.selectSkusBySpuId(spu.getId());
        List<String> images = new ArrayList<>();
        for (Sku sku : skus) {
            images.add(sku.getImages());
        }
        dateMap.put("imageList",images);
        SpuDetail spuDetail = spuDetailClient.edit(spu.getId());
        Map<String, Object> genericMap = JsonUtils.parseMap(spuDetail.getSpecialSpec(), String.class, Object.class);
        dateMap.put("specificationList",genericMap);
        dateMap.put("spu",spu);
        dateMap.put("spuDetail",spuDetail);

        //根据spuId查询Sku集合
        dateMap.put("skuList", skus);
        return dateMap;
    }


    @Override
    public void createPageHtml(Long spuId) {
        // 1.上下文 模板 + 数据集 =html
        Context context = new Context();
        Map<String, Object> dataModel = buildDataModel(spuId);
        context.setVariables(dataModel);
        // 2.准备文件
        File dir = new File(pagepath);
        if (!dir.exists()){
            dir.mkdir();
        }
        File dest = new File(dir, spuId + ".html");
        // 3.生成页面
        try (PrintWriter writer = new PrintWriter(dest, "UTF-8")) {
            //模板的文件
            templateEngine.process("item", context, writer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
