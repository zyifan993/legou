package com.zyf.legou.item.service;

import com.zyf.legou.core.service.ICrudService;
import com.zyf.legou.item.po.Category;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface CategoryService extends ICrudService<Category> {

    public List<String> selectNameByIds(List<Long> ids);
}
