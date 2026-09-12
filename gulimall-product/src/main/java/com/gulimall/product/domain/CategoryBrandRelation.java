package com.gulimall.product.domain;

import com.gulimall.common.annotation.Excel;
import lombok.Getter;
import lombok.Setter;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.IdType;
import com.gulimall.common.core.domain.BaseEntity;

/**
 * 【请填写功能名称】对象 pms_category_brand_relation
 *
 * @author jiangdan
 * @date 2026-09-12
 */
@Getter
@Setter
@TableName("pms_category_brand_relation")
public class CategoryBrandRelation extends BaseEntity
{
    private static final long serialVersionUID = 1L;

        /** $column.columnComment */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

        /** 品牌id */
        @Excel(name = "品牌id")
    private Long brandId;

        /** 分类id */
        @Excel(name = "分类id")
    private Long catelogId;

        /** $column.columnComment */
        @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String brandName;

        /** $column.columnComment */
        @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String catelogName;

}