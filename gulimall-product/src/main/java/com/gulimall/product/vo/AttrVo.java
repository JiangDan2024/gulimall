package com.gulimall.product.vo;

import com.gulimall.common.annotation.Excel;
import lombok.Data;

@Data
public class AttrVo {
    /** 属性id */
    private Long attrId;

    /** 属性名 */
    @Excel(name = "属性名")
    private String attrName;

    /** 是否需要检索[0-不需要，1-需要] */
    @Excel(name = "是否需要检索[0-不需要，1-需要]")
    private Long searchType;

    /** 值类型[0-为单个值，1-可以选择多个值] */
    @Excel(name = "值类型[0-为单个值，1-可以选择多个值]")
    private Long valueType;

    /** 属性图标 */
    @Excel(name = "属性图标")
    private String icon;

    /** 可选值列表[用逗号分隔] */
    @Excel(name = "可选值列表[用逗号分隔]")
    private String valueSelect;

    /** 属性类型[0-销售属性，1-基本属性，2-既是销售属性又是基本属性] */
    @Excel(name = "属性类型[0-销售属性，1-基本属性，2-既是销售属性又是基本属性]")
    private Long attrType;

    /** 启用状态[0 - 禁用，1 - 启用] */
    @Excel(name = "启用状态[0 - 禁用，1 - 启用]")
    private Long enable;

    /** 所属分类 */
    @Excel(name = "所属分类")
    private Long catelogId;

    /** 快速展示【是否展示在介绍上；0-否 1-是】，在sku中仍然可以调整 */
    @Excel(name = "快速展示【是否展示在介绍上；0-否 1-是】，在sku中仍然可以调整")
    private Long showDesc;

    private Long attrGroupId;
}
