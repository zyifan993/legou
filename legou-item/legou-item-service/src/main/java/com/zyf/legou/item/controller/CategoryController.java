package com.zyf.legou.item.controller;

import com.zyf.legou.core.controller.BaseController;
import com.zyf.legou.core.po.ResponseBean;
import com.zyf.legou.item.po.Category;
import com.zyf.legou.item.service.impl.CategoryServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController extends BaseController<CategoryServiceImpl, Category> {

    public ResponseEntity<List<String>> queryNameByIds(@RequestParam("ids") List<Long> ids){
        List<String> names = service.selectNameByIds(ids);

        if (names == null || names.size() == 0){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(names);
    }
}
