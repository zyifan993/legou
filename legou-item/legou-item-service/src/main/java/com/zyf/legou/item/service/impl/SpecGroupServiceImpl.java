package com.zyf.legou.item.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.zyf.legou.core.service.impl.CrudServiceImpl;
import com.zyf.legou.item.dao.SpecGroupDao;
import com.zyf.legou.item.dao.SpecParamDao;
import com.zyf.legou.item.po.SpecGroup;
import com.zyf.legou.item.po.SpecParam;
import com.zyf.legou.item.service.ISpecGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class SpecGroupServiceImpl extends CrudServiceImpl<SpecGroup> implements ISpecGroupService {
    @Autowired
    private SpecParamDao specParamDao;

    @Override
    public List<SpecGroup> list(SpecGroup entity) {
        return ((SpecGroupDao)getBaseMapper()).selectList(entity);

    }

    @Override
    public void saveGroup(Long cid, List<SpecGroup> groups) {
        //根据cid删除所有规格参数的分组和规格参数
        getBaseMapper().delete(Wrappers.<SpecGroup>query().eq("cid_",cid));
        specParamDao.delete(Wrappers.<SpecParam>query().eq("cid_",cid));

        //逐个添加规格参数分组和规格参数
        for (SpecGroup group : groups) {
            getBaseMapper().insert(group);
            for (SpecParam param : group.getParams()) {
                param.setGroupId(group.getId());//页面groupId是防止重复自动+1，保存时要保存数据库中groupId
                specParamDao.insert(param);
            }
        }
    }
}
