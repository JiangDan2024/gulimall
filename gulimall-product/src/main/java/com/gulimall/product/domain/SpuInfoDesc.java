package com.gulimall.product.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.gulimall.common.annotation.Excel;
import com.gulimall.common.core.domain.BaseEntity;

/**
 * 【请填写功能名称】对象 pms_spu_info_desc
 *
 * @author jdjdjd
 * @date 2026-09-16
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("pms_spu_info_desc")
public class SpuInfoDesc extends BaseEntity
{
private static final long serialVersionUID = 1L;

    /** 商品id */
    @TableId(value = "spu_id", type = IdType.AUTO)
    private Long spuId;

    /** 商品介绍 */
    @Excel(name = "商品介绍")
    private String decript;

}