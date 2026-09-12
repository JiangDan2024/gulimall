package com.gulimall.coupon.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.gulimall.common.annotation.Excel;
import lombok.Getter;
import lombok.Setter;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.IdType;
import com.gulimall.common.core.domain.BaseEntity;

/**
 * 【请填写功能名称】对象 sms_home_adv
 *
 * @author jiangdan
 * @date 2026-09-12
 */
@Getter
@Setter
@TableName("sms_home_adv")
public class HomeAdv extends BaseEntity
{
    private static final long serialVersionUID = 1L;

        /** id */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

        /** 名字 */
        @Excel(name = "名字")
    private String name;

        /** 图片地址 */
        @Excel(name = "图片地址")
    private String pic;

        /** 开始时间 */
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
        @Excel(name = "开始时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

        /** 结束时间 */
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
        @Excel(name = "结束时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

        /** 状态 */
        @Excel(name = "状态")
    private Integer status;

        /** 点击数 */
        @Excel(name = "点击数")
    private Long clickCount;

        /** 广告详情连接地址 */
        @Excel(name = "广告详情连接地址")
    private String url;

        /** 备注 */
        @Excel(name = "备注")
    private String note;

        /** 排序 */
        @Excel(name = "排序")
    private Long sort;

        /** 发布者 */
        @Excel(name = "发布者")
    private Long publisherId;

        /** 审核者 */
        @Excel(name = "审核者")
    private Long authId;

}