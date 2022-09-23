package com.zyf.legou.security.service;

import com.zyf.legou.core.service.ICrudService;
import com.zyf.legou.security.po.Role;
import com.zyf.legou.security.po.User;

import java.util.List;

/**
 * @Title:
 * @Description: 
 *
 * @Copyright 2019 lxs - Powered By 雪松
 * @Author: lxs
 * @Date:  2019/10/9
 * @Version V1.0
 */
public interface IRoleService extends ICrudService<Role> {

    /**
     * 查询角色的用户
     * @param id
     * @return
     */
    public List<User> selectUserByRole(Long id);

}
