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
 * 【请填写功能名称】对象 sms_seckill_session
 *
 * @author jdjdjd
 * @date 2026-09-22
 */
        @Data
        @EqualsAndHashCode(callSuper = true)
        @TableName("sms_seckill_session")
        public class SeckillSession extends BaseEntity
        {
        private static final long serialVersionUID = 1L;

            /** id */
        @TableId(value = "id", type = IdType.AUTO)
    private Long id;

            /** 场次名称 */
            @Excel(name = "场次名称")
    private String name;

            /** 每日开始时间 */
            @JsonFormat(pattern = "yyyy-MM-dd")
            @Excel(name = "每日开始时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date startTime;

            /** 每日结束时间 */
            @JsonFormat(pattern = "yyyy-MM-dd")
            @Excel(name = "每日结束时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date endTime;

            /** 启用状态 */
            @Excel(name = "启用状态")
    private Integer status;

            /** 创建时间 */
    private Date createTime;

        }