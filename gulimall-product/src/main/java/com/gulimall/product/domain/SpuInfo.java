package com.gulimall.product.domain;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.gulimall.common.annotation.Excel;
import com.gulimall.common.core.domain.BaseEntity;

/**
 * 【请填写功能名称】对象 pms_spu_info
 *
 * @author jdjdjd
 * @date 2026-09-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("pms_spu_info")
public class SpuInfo extends BaseEntity
{
private static final long serialVersionUID = 1L;

    /** 商品id */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /** 商品名称 */
    @Excel(name = "商品名称")
    private String spuName;

    /** 商品描述 */
    @Excel(name = "商品描述")
    private String spuDescription;

    /** 所属分类id */
    @Excel(name = "所属分类id")
    private Long catalogId;

    /** 品牌id */
    @Excel(name = "品牌id")
    private Long brandId;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private BigDecimal weight;

    /** 上架状态[0 - 下架，1 - 上架] */
    @Excel(name = "上架状态[0 - 下架，1 - 上架]")
    private Long publishStatus;

}