package com.zyf.legou.item.api;


import com.zyf.legou.item.po.Category;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/category")
public interface CategoryApi {

    @ApiOperation(value="根据ids查询names", notes = "根据分类id查询名称列表")
    @GetMapping("/names")
    public List<String> queryNameByIds(@RequestParam("ids") List<Long> ids);

    @ApiOperation(value="查询", notes="根据实体条件查询")
    @RequestMapping(value = "/list")
    public List<Category> list(Category category);

    @ApiOperation(value="加载", notes="根据ID加载")
    @GetMapping("/edit/{id}")
    public Category edit(@PathVariable Long id);

}
