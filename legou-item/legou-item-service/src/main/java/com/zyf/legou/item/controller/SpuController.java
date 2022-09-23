package com.zyf.legou.item.controller;

import com.zyf.legou.core.controller.BaseController;
import com.zyf.legou.core.po.ResponseBean;
import com.zyf.legou.item.po.Spu;
import com.zyf.legou.item.service.ISpuService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/spu")
public class SpuController extends BaseController<ISpuService, Spu> {

    @ApiOperation(value="保存商品信息", notes="保存商品信息")
    @PostMapping(value = "/save-spu")
    public ResponseBean saveSpu(@RequestBody Spu spu){
        ResponseBean rm = new ResponseBean();
        //保存
        try {
            service.saveSpu(spu);
        } catch (Exception e) {
            e.printStackTrace();
            rm.setSuccess(false);
            rm.setMsg("保存失败");
        }
        return rm;
    }
    @ApiOperation(value="查询所有", notes="查询所有spu")
    @GetMapping("/list-all")
    public List<Spu> selectAll() {
         return service.list(new Spu());
    }
}
