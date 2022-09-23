package com.zyf.legou.item.service;

import com.zyf.legou.core.service.ICrudService;
import com.zyf.legou.item.po.Brand;
import com.zyf.legou.item.po.Category;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface IBrandService extends ICrudService<Brand> {
    /**
     * 根据商品id查询分类
     * @param id
     * @return
     */

    public List<Category> selectCategoryByBrand(Long id);

    public List<Brand> selectBrandByIds(@RequestParam("ids")List<Long> ids);
}
