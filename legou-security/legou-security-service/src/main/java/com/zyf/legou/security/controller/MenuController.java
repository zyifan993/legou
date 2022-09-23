package com.zyf.legou.security.controller;

import com.zyf.legou.core.controller.BaseController;
import com.zyf.legou.core.po.ResponseBean;
import com.zyf.legou.security.po.Menu;
import com.zyf.legou.security.service.IMenuService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

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
@RestController
@RequestMapping("/menu")
public class MenuController extends BaseController<IMenuService, Menu> {

    /**
     * 查询菜单，选中角色已有的菜单
     * @param roleId
     * @return
     */
    @ApiOperation(value = "查询菜单，选中角色已有的菜单", notes = "查询菜单，选中角色已有的菜单")
    @RequestMapping(value = "/list-role/{roleId}", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public List<Menu> listMenuByRole(@PathVariable Long roleId) {
        return this.service.listChecked(roleId);
    }

    /**
     * 查询当前用户的菜单
     * @return
     */
    @ApiOperation("查询当前用户的菜单")
    @GetMapping("/list-current-user")
    @ResponseBody
    public List<Menu> ListMenuByCurrentUser() {
        //// TODO: 2019/10/9 暂时mock，userId=15admin用户，后期整合权限框架
        return this.service.listByUser(15l);
    }

    /**
     * 给角色分配菜单
     * @param roleId
     * @param ids
     * @return
     */
    @ApiOperation("给角色分配菜单")
    @PostMapping("/assign/{roleId}")
    @ResponseBody
    public ResponseBean assignMenu(@PathVariable Long roleId, @RequestParam Long[] ids) {
        ResponseBean rb = new ResponseBean();
        try {
            service.doAssignMenu2Role(roleId, ids);
        } catch (Exception e) {
            e.printStackTrace();
            rb.setSuccess(false);
            rb.setMsg("保存失败");
        }
        return rb;
    }

}
