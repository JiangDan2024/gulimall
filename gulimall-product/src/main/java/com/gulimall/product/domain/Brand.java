package com.gulimall.product.domain;

import com.gulimall.common.annotation.Excel;
import lombok.Getter;
import lombok.Setter;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.IdType;
import com.gulimall.common.core.domain.BaseEntity;

/**
 * 【请填写功能名称】对象 pms_brand
 *
 * @author jiangdan
 * @date 2026-09-12
 */
@Getter
@Setter
@TableName("pms_brand")
public class Brand extends BaseEntity
{
    private static final long serialVersionUID = 1L;

        /** 品牌id */
    @TableId(value = "brand_id", type = IdType.AUTO)
    private Long brandId;

        /** 品牌名 */
        @Excel(name = "品牌名")
    private String name;

        /** 品牌logo地址 */
        @Excel(name = "品牌logo地址")
    private String logo;

        /** 介绍 */
        @Excel(name = "介绍")
    private String descript;

        /** 显示状态[0-不显示；1-显示] */
        @Excel(name = "显示状态[0-不显示；1-显示]")
    private Long showStatus;

        /** 检索首字母 */
        @Excel(name = "检索首字母")
    private String firstLetter;

        /** 排序 */
        @Excel(name = "排序")
    private Long sort;

}