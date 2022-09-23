package com.zyf.legou.item.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.zyf.legou.core.service.impl.CrudServiceImpl;
import com.zyf.legou.item.dao.BrandDao;
import com.zyf.legou.item.po.Brand;
import com.zyf.legou.item.po.Category;
import com.zyf.legou.item.service.IBrandService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BrandServiceImpl extends CrudServiceImpl<Brand> implements IBrandService {

    @Override
    public List<Category> selectCategoryByBrand(Long id) {
        return ((BrandDao)getBaseMapper()).selectCategoryByBrand(id);
    }

    @Override
    public List<Brand> selectBrandByIds(List<Long> ids) {
        QueryWrapper<Brand> queryWrapper = Wrappers.<Brand>query().in("id_",ids);
        return baseMapper.selectList(queryWrapper);
    }

    @Override
    @Transactional(readOnly = false)
    public boolean saveOrUpdate(Brand entity) {
        boolean result = super.saveOrUpdate(entity);
        //删除商品的关联
        ((BrandDao)getBaseMapper()).deleteCategoryByBrand(entity.getId());
        //新增商品与分类
        Long[] categoryIds = entity.getCategoryIds();
        if (categoryIds != null){
            for (Long categoryId : categoryIds) {
                ((BrandDao) getBaseMapper()).insertCategoryAndBrand(categoryId,entity.getId());
            }
        }
        return result;
    }
}
