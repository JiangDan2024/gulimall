package com.gulimall.coupon.domain;

import java.math.BigDecimal;
import com.gulimall.common.annotation.Excel;
import lombok.Getter;
import lombok.Setter;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.IdType;
import com.gulimall.common.core.domain.BaseEntity;

/**
 * 【请填写功能名称】对象 sms_sku_full_reduction
 *
 * @author jiangdan
 * @date 2026-09-12
 */
@Getter
@Setter
@TableName("sms_sku_full_reduction")
public class SkuFullReduction extends BaseEntity
{
    private static final long serialVersionUID = 1L;

        /** id */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

        /** spu_id */
        @Excel(name = "spu_id")
    private Long skuId;

        /** 满多少 */
        @Excel(name = "满多少")
    private BigDecimal fullPrice;

        /** 减多少 */
        @Excel(name = "减多少")
    private BigDecimal reducePrice;

        /** 是否参与其他优惠 */
        @Excel(name = "是否参与其他优惠")
    private Integer addOther;

}