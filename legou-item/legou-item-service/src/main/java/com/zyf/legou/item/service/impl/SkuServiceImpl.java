package com.zyf.legou.item.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.zyf.legou.core.service.impl.CrudServiceImpl;
import com.zyf.legou.item.dao.SkuDao;
import com.zyf.legou.item.po.Sku;
import com.zyf.legou.item.service.ISkuService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SkuServiceImpl extends CrudServiceImpl<Sku> implements ISkuService {

    @Override
    public List<Sku> list(Sku entity) {
        QueryWrapper<Sku> queryWrapper = Wrappers.<Sku>query();
        if (null != entity.getSpuId()){
            queryWrapper.eq("spu_id_",entity.getSpuId());
        }
        return getBaseMapper().selectList(queryWrapper);
    }

    @Override
    public void decrCount(Integer num, Long skuId) {
        Sku sku = getBaseMapper().selectById(skuId);

        if (sku.getStock() >= num){
            sku.setStock(sku.getStock() - num);
            getBaseMapper().updateById(sku);
        }
        ((SkuDao)getBaseMapper()).decrCount(num, skuId);
    }
}
