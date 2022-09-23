package com.zyf.legou.admin.controller;

import com.zyf.legou.admin.po.Dept;
import com.zyf.legou.admin.service.IDeptService;
import com.zyf.legou.core.controller.BaseController;
import com.zyf.legou.core.controller.BaseController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/dept")
public class DeptController extends BaseController<IDeptService, Dept> {

}
