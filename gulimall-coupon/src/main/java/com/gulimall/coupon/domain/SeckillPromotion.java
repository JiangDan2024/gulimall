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
 * 【请填写功能名称】对象 sms_seckill_promotion
 *
 * @author jdjdjd
 * @date 2026-09-22
 */
        @Data
        @EqualsAndHashCode(callSuper = true)
        @TableName("sms_seckill_promotion")
        public class SeckillPromotion extends BaseEntity
        {
        private static final long serialVersionUID = 1L;

            /** id */
        @TableId(value = "id", type = IdType.AUTO)
    private Long id;

            /** 活动标题 */
            @Excel(name = "活动标题")
    private String title;

            /** 开始日期 */
            @JsonFormat(pattern = "yyyy-MM-dd")
            @Excel(name = "开始日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date startTime;

            /** 结束日期 */
            @JsonFormat(pattern = "yyyy-MM-dd")
            @Excel(name = "结束日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date endTime;

            /** 上下线状态 */
            @Excel(name = "上下线状态")
    private Long status;

            /** 创建时间 */
    private Date createTime;

            /** 创建人 */
            @Excel(name = "创建人")
    private Long userId;

        }