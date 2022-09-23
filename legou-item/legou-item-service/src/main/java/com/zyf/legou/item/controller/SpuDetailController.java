package com.zyf.legou.item.controller;

import com.zyf.legou.core.controller.BaseController;
import com.zyf.legou.item.po.SpuDetail;
import com.zyf.legou.item.service.ISpuDetailService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/spu-detail")
public class SpuDetailController extends BaseController<ISpuDetailService, SpuDetail> {
}
