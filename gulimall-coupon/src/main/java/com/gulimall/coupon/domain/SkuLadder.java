package com.gulimall.coupon.domain;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.gulimall.common.annotation.Excel;
import com.gulimall.common.core.domain.BaseEntity;

/**
 * 【请填写功能名称】对象 sms_sku_ladder
 *
 * @author jdjdjd
 * @date 2026-09-22
 */
        @Data
        @EqualsAndHashCode(callSuper = true)
        @TableName("sms_sku_ladder")
        public class SkuLadder extends BaseEntity
        {
        private static final long serialVersionUID = 1L;

            /** id */
        @TableId(value = "id", type = IdType.AUTO)
    private Long id;

            /** spu_id */
            @Excel(name = "spu_id")
    private Long skuId;

            /** 满几件 */
            @Excel(name = "满几件")
    private Long fullCount;

            /** 打几折 */
            @Excel(name = "打几折")
    private BigDecimal discount;

            /** 折后价 */
            @Excel(name = "折后价")
    private BigDecimal price;

            /** 是否叠加其他优惠[0-不可叠加，1-可叠加] */
            @Excel(name = "是否叠加其他优惠[0-不可叠加，1-可叠加]")
    private Integer addOther;

        }