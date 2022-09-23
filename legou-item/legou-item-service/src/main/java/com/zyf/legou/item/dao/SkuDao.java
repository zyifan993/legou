package com.zyf.legou.item.dao;

import com.zyf.legou.core.dao.ICrudDao;
import com.zyf.legou.item.po.Sku;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface SkuDao extends ICrudDao<Sku> {

    @Select("select * from sku_ where spu_id_ = #{skuId}")
    public List<Sku> findBySkuId(Integer skuId);

    @Update(value="update sku_ set stock_ = stock_ - #{num} where id_ =#{skuId} and stock_ >= #{num}")
    public int decrCount(@Param("num") Integer num,@Param("skuId")Long skuId);
}
