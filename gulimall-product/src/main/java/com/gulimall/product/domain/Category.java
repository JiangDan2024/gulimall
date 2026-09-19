package com.gulimall.product.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.gulimall.common.annotation.Excel;
import lombok.Getter;
import lombok.Setter;
import com.gulimall.common.core.domain.BaseEntity;

import java.util.List;

/**
 * 【请填写功能名称】对象 pms_category
 *
 * @author jiangdan
 * @date 2026-09-12
 */
@Getter
@Setter
@TableName("pms_category")
public class Category extends BaseEntity
{
    private static final long serialVersionUID = 1L;

        /** 分类id */
    @TableId(value = "cat_id", type = IdType.AUTO)
    private Long catId;

        /** 分类名称 */
        @Excel(name = "分类名称")
    private String name;

        /** 父分类id */
        @Excel(name = "父分类id")
    private Long parentCid;

        /** 层级 */
        @Excel(name = "层级")
    private Long catLevel;

        /** 是否显示[0-不显示，1显示] */
        @Excel(name = "是否显示[0-不显示，1显示]")
        @TableLogic(value = "1",delval = "0")
    private Long showStatus;

        /** 排序 */
        @Excel(name = "排序")
    private Long sort;

        /** 图标地址 */
        @Excel(name = "图标地址")
    private String icon;

        /** 计量单位 */
        @Excel(name = "计量单位")
    private String productUnit;

        /** 商品数量 */
        @Excel(name = "商品数量")
    private Long productCount;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
        @TableField(exist = false)
    private List<Category> children;
}