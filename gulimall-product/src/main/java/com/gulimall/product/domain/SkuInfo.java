package com.gulimall.product.domain;

import java.math.BigDecimal;
import com.gulimall.common.annotation.Excel;
import lombok.Getter;
import lombok.Setter;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.IdType;
import com.gulimall.common.core.domain.BaseEntity;

/**
 * 【请填写功能名称】对象 pms_sku_info
 *
 * @author jiangdan
 * @date 2026-09-12
 */
@Getter
@Setter
@TableName("pms_sku_info")
public class SkuInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

        /** skuId */
    @TableId(value = "sku_id", type = IdType.AUTO)
    private Long skuId;

        /** spuId */
        @Excel(name = "spuId")
    private Long spuId;

        /** sku名称 */
        @Excel(name = "sku名称")
    private String skuName;

        /** sku介绍描述 */
        @Excel(name = "sku介绍描述")
    private String skuDesc;

        /** 所属分类id */
        @Excel(name = "所属分类id")
    private Long catalogId;

        /** 品牌id */
        @Excel(name = "品牌id")
    private Long brandId;

        /** 默认图片 */
        @Excel(name = "默认图片")
    private String skuDefaultImg;

        /** 标题 */
        @Excel(name = "标题")
    private String skuTitle;

        /** 副标题 */
        @Excel(name = "副标题")
    private String skuSubtitle;

        /** 价格 */
        @Excel(name = "价格")
    private BigDecimal price;

        /** 销量 */
        @Excel(name = "销量")
    private Long saleCount;

}