package com.zyf.legou.item.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zyf.legou.core.po.BaseEntity;
import lombok.Data;

@Data
@TableName("spec_param_")
public class SpecParam extends BaseEntity {
    @TableField("cid_")
    private Long cid;
    @TableField("groupId_")
    private Long groupId;
    @TableField("name_")
    private String name;
    //是否为数字
    @TableField("numeric_")
    private Boolean numeric;
    ///单位
    @TableField("unit_")
    private String unit;
    //是否为sku参数
    @TableField("generic_")
    private Boolean generic;
    //是否为搜索项
    @TableField("searching_")
    private Boolean searching;
    //排序
    @TableField("segments_")
    private String segments;

}
