package com.zyf.legou.item.controller;

import com.zyf.legou.core.controller.BaseController;
import com.zyf.legou.item.po.Sku;
import com.zyf.legou.item.service.ISkuService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sku")
public class SkuController extends BaseController<ISkuService, Sku> {

    @ApiOperation(value = "查询spu对应的sku",notes = "根据spuid查询sku")
    @GetMapping("/select-skus-by-spuid/{id}")
    public List<Sku> selectSkusBySpuId(@PathVariable Long id){
        Sku sku = new Sku();
        sku.setSpuId(id);
        return service.list(sku);
    }

    /**
     * 减库存
     * @param num
     * @param skuId
     */
    @PostMapping("/decr-count")
    public void decrCount(@RequestParam("num") Integer num,@RequestParam("skuId") Long skuId){
        service.decrCount(num, skuId);
    }
}
