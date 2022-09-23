package com.zyf.legou.security.dao;

import com.zyf.legou.core.dao.ICrudDao;
import com.zyf.legou.security.po.Role;
import com.zyf.legou.security.po.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.web.bind.annotation.RequestParam;

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
public interface UserDao extends ICrudDao<User> {

	/**
	 * 删除用户角色关联
	 * @param id
	 * @return
	 */
	public int deleteRoleByUser(Long id);

	/**
	 * 关联用户角色
	 * @param roleId
	 * @param userId
	 * @return
	 */
	public int insertRoleAndUser(@Param("roleId") Long roleId, @Param("userId") Long userId);

	/**
	 * 查询用户的角色
	 * @param id
	 * @return
	 */
	public List<Role> selectRoleByUser(Long id);

	@Update(value="update user_ set point_ = point_ + #{point} where user_name_ =#{userName}")
	public int addPoint(@Param(value = "point") Long point,@Param(value = "userName") String userName);

}
