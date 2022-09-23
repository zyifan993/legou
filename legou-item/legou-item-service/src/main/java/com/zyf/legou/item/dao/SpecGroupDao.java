package com.zyf.legou.item.dao;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.zyf.legou.core.dao.ICrudDao;
import com.zyf.legou.item.po.SpecGroup;

import java.util.List;

public interface SpecGroupDao extends ICrudDao<SpecGroup> {

    /**
     * 根据实体条件动态查询分组
     * @param specGroup
     * @return
     */
    List<SpecGroup> selectList(SpecGroup specGroup);
}
