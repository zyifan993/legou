package com.zyf.legou.item.dao;

import com.zyf.legou.core.dao.ICrudDao;
import com.zyf.legou.item.po.SpecGroup;
import com.zyf.legou.item.po.SpecParam;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface SpecParamDao extends ICrudDao<SpecParam> {

    @Select("select * from spec_param_ where group_id_ = #{groupId}")
    public List<SpecGroup> findByGroupId(Integer groupId);
}
