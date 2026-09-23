package com.gulimall.coupon.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.gulimall.common.annotation.Excel;
import com.gulimall.common.core.domain.BaseEntity;

/**
 * 【请填写功能名称】对象 sms_seckill_sku_notice
 *
 * @author jdjdjd
 * @date 2026-09-22
 */
        @Data
        @EqualsAndHashCode(callSuper = true)
        @TableName("sms_seckill_sku_notice")
        public class SeckillSkuNotice extends BaseEntity
        {
        private static final long serialVersionUID = 1L;

            /** id */
        @TableId(value = "id", type = IdType.AUTO)
    private Long id;

            /** member_id */
            @Excel(name = "member_id")
    private Long memberId;

            /** sku_id */
            @Excel(name = "sku_id")
    private Long skuId;

            /** 活动场次id */
            @Excel(name = "活动场次id")
    private Long sessionId;

            /** 订阅时间 */
            @JsonFormat(pattern = "yyyy-MM-dd")
            @Excel(name = "订阅时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date subcribeTime;

            /** 发送时间 */
            @JsonFormat(pattern = "yyyy-MM-dd")
            @Excel(name = "发送时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date sendTime;

            /** 通知方式[0-短信，1-邮件] */
            @Excel(name = "通知方式[0-短信，1-邮件]")
    private Integer noticeType;

        }