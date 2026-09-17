package com.gulimall.product.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.gulimall.common.annotation.Excel;
import com.gulimall.common.core.domain.BaseEntity;

/**
 * 【请填写功能名称】对象 pms_product_attr_value
 *
 * @author jdjdjd
 * @date 2026-09-16
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("pms_product_attr_value")
public class ProductAttrValue extends BaseEntity
{
private static final long serialVersionUID = 1L;

    /** id */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /** 商品id */
    @Excel(name = "商品id")
    private Long spuId;

    /** 属性id */
    @Excel(name = "属性id")
    private Long attrId;

    /** 属性名 */
    @Excel(name = "属性名")
    private String attrName;

    /** 属性值 */
    @Excel(name = "属性值")
    private String attrValue;

    /** 顺序 */
    @Excel(name = "顺序")
    private Long attrSort;

    /** 快速展示【是否展示在介绍上；0-否 1-是】 */
    @Excel(name = "快速展示【是否展示在介绍上；0-否 1-是】")
    private Long quickShow;

}