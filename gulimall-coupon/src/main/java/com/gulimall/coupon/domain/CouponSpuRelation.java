package com.gulimall.coupon.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.gulimall.common.annotation.Excel;
import com.gulimall.common.core.domain.BaseEntity;

/**
 * 【请填写功能名称】对象 sms_coupon_spu_relation
 *
 * @author jdjdjd
 * @date 2026-09-22
 */
        @Data
        @EqualsAndHashCode(callSuper = true)
        @TableName("sms_coupon_spu_relation")
        public class CouponSpuRelation extends BaseEntity
        {
        private static final long serialVersionUID = 1L;

            /** id */
        @TableId(value = "id", type = IdType.AUTO)
    private Long id;

            /** 优惠券id */
            @Excel(name = "优惠券id")
    private Long couponId;

            /** spu_id */
            @Excel(name = "spu_id")
    private Long spuId;

            /** spu_name */
            @Excel(name = "spu_name")
    private String spuName;

        }