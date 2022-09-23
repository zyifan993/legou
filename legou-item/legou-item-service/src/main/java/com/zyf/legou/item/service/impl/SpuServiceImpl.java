package com.zyf.legou.item.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.zyf.legou.core.service.impl.CrudServiceImpl;
import com.zyf.legou.item.po.Sku;
import com.zyf.legou.item.po.Spu;
import com.zyf.legou.item.service.ISkuService;
import com.zyf.legou.item.service.ISpuDetailService;
import com.zyf.legou.item.service.ISpuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SpuServiceImpl extends CrudServiceImpl<Spu> implements ISpuService {

    @Autowired
    private ISpuDetailService spuDetailService;
    @Autowired
    private ISkuService skuService;


    @Override
    public void saveSpu(Spu spu) {
        //保存spu
        this.saveOrUpdate(spu);
        //保存spuDetail
        if (null == spu.getSpuDetail().getId()){
            //新增
            //保存主键
            spu.getSpuDetail().setId(spu.getId());
            spuDetailService.save(spu.getSpuDetail());
        }else {
            //修改
            spuDetailService.updateById(spu.getSpuDetail());
        }
        //保存sku
        //删除原来的sku列表
        skuService.remove(Wrappers.<Sku>query().eq("spu_id_",spu.getId()));
        //添加新的sku
        for (Sku sku : spu.getSkus()) {
            sku.setSpuId(spu.getId());
            skuService.save(sku);
        }
    }
}
