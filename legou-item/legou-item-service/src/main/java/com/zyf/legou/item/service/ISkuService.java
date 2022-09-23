package com.zyf.legou.item.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zyf.legou.core.service.ICrudService;
import com.zyf.legou.item.po.Sku;

public interface ISkuService extends IService<Sku>, ICrudService<Sku> {

    /**
     * 减库存
     * @param num 数量
     * @param skuId
     */
    public void decrCount(Integer num,Long skuId);

}
