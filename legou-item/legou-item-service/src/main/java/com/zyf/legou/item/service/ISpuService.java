package com.zyf.legou.item.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zyf.legou.core.service.ICrudService;
import com.zyf.legou.item.po.Spu;

public interface ISpuService extends IService<Spu>, ICrudService<Spu> {

    /**
     * 保存spu，包括如下表的数据
     *  spu
     *  spuDetail
     *  skus
     * @param spu
     */
    public void saveSpu(Spu spu);
}
