package com.zyf.legou.page.controller;

import com.zyf.legou.page.service.PageService;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/page")
public class PageController {

    @Autowired
    private PageService pageService;

    @RequestMapping("/createHtml/{id}")
    public ResponseEntity<String> createHtml(@PathVariable(name = "id") Long id){
        pageService.createPageHtml(id);
        return ResponseEntity.ok("生成成功");
    }
}
