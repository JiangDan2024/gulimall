package com.gulimall.product.domain;

import com.gulimall.common.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import java.util.List;
import com.gulimall.common.core.domain.BaseEntity;

/**
 * 【请填写功能名称】对象 pms_sku_images
 *
 * @author jiangdan
 * @date 2026-09-12
 */
@Getter
@Setter
@TableName("pms_sku_images")
public class SkuImages extends BaseEntity
{
    private static final long serialVersionUID = 1L;

        /** id */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

        /** sku_id */
        @Excel(name = "sku_id")
    private Long skuId;

        /** 图片地址 */
        @Excel(name = "图片地址")
    private String imgUrl;

        /** 排序 */
        @Excel(name = "排序")
    private Long imgSort;

        /** 默认图[0 - 不是默认图，1 - 是默认图] */
        @Excel(name = "默认图[0 - 不是默认图，1 - 是默认图]")
    private Long defaultImg;

}