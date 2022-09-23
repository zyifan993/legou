package com.zyf.legou.search.dao;

import com.zyf.legou.search.po.Goods;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface GoodsDao extends ElasticsearchRepository<Goods, Long> {

}
