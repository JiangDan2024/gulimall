package com.gulimall.coupon.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.gulimall.common.annotation.Excel;
import com.gulimall.common.core.domain.BaseEntity;

/**
 * 【请填写功能名称】对象 sms_seckill_sku_relation
 *
 * @author jdjdjd
 * @date 2026-09-22
 */
        @Data
        @EqualsAndHashCode(callSuper = true)
        @TableName("sms_seckill_sku_relation")
        public class SeckillSkuRelation extends BaseEntity
        {
        private static final long serialVersionUID = 1L;

            /** id */
        @TableId(value = "id", type = IdType.AUTO)
    private Long id;

            /** 活动id */
            @Excel(name = "活动id")
    private Long promotionId;

            /** 活动场次id */
            @Excel(name = "活动场次id")
    private Long promotionSessionId;

            /** 商品id */
            @Excel(name = "商品id")
    private Long skuId;

            /** 秒杀价格 */
            @Excel(name = "秒杀价格")
    private Long seckillPrice;

            /** 秒杀总量 */
            @Excel(name = "秒杀总量")
    private Long seckillCount;

            /** 每人限购数量 */
            @Excel(name = "每人限购数量")
    private Long seckillLimit;

            /** 排序 */
            @Excel(name = "排序")
    private Long seckillSort;

        }