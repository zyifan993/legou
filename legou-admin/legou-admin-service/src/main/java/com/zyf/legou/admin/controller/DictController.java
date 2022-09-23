package com.zyf.legou.admin.controller;

import com.zyf.legou.admin.po.Dict;
import com.zyf.legou.admin.service.IDictService;
import com.zyf.legou.core.controller.BaseController;
import com.zyf.legou.core.controller.BaseController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dict")
public class DictController extends BaseController<IDictService, Dict> {


}
