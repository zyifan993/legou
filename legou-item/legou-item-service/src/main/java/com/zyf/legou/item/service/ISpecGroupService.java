package com.zyf.legou.item.service;

import com.zyf.legou.core.service.ICrudService;
import com.zyf.legou.item.po.SpecGroup;

import java.util.List;

public interface ISpecGroupService extends ICrudService<SpecGroup> {

    /**
     *根据前台传递的参数列表，保存规格参数
     * @param id
     * @param groups
     */
    public void saveGroup(Long id, List<SpecGroup> groups);
}
