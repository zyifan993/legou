package com.zyf.legou.item.dao;

import com.zyf.legou.core.dao.ICrudDao;
import com.zyf.legou.item.po.Brand;
import com.zyf.legou.item.po.Category;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BrandDao extends ICrudDao<Brand> {

    /**
     * 删除商品和分类关联
     * @param id
     * @return
     */
    public int deleteCategoryByBrand(Long id);

    /**
     * 查询商品的分类
     * @param id
     * @return
     */
    public List<Category> selectCategoryByBrand(Long id);

    /**
     * 关联商品和分类
     * @param categoryId
     * @param brandId
     * @return
     */
    public int insertCategoryAndBrand(@Param("categoryId") Long categoryId, @Param("brandId") Long brandId);
}
