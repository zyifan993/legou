package com.zyf.legou.item.controller;

import com.zyf.legou.core.controller.BaseController;
import com.zyf.legou.item.po.Brand;
import com.zyf.legou.item.po.Category;
import com.zyf.legou.item.service.IBrandService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/brand")
public class BrandController extends BaseController<IBrandService, Brand> {

    @Override
    public void afterEdit(Brand entity) {
        //得到品牌的所属id
        List<Category> categories = service.selectCategoryByBrand(entity.getId());
        Long[] ids = new Long[categories.size()];
        for (int i=0;i<categories.size();i++){
            ids[i] = categories.get(i).getId();
        }
        entity.setCategoryIds(ids);
    }

    @ApiOperation(value = "根据id查询品牌",notes = "根据id查询品牌")
    @GetMapping("/list-by-ids")
    public List<Brand> selectBrandByIds(@RequestParam("ids") List<Long> ids){
        return service.selectBrandByIds(ids);
    }
}
