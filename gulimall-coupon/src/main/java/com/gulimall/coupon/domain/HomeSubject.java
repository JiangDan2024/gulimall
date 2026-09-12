package com.gulimall.coupon.domain;

import com.gulimall.common.annotation.Excel;
import lombok.Getter;
import lombok.Setter;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.IdType;
import com.gulimall.common.core.domain.BaseEntity;

/**
 * 【请填写功能名称】对象 sms_home_subject
 *
 * @author jiangdan
 * @date 2026-09-12
 */
@Getter
@Setter
@TableName("sms_home_subject")
public class HomeSubject extends BaseEntity
{
    private static final long serialVersionUID = 1L;

        /** id */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

        /** 专题名字 */
        @Excel(name = "专题名字")
    private String name;

        /** 专题标题 */
        @Excel(name = "专题标题")
    private String title;

        /** 专题副标题 */
        @Excel(name = "专题副标题")
    private String subTitle;

        /** 显示状态 */
        @Excel(name = "显示状态")
    private Integer status;

        /** 详情连接 */
        @Excel(name = "详情连接")
    private String url;

        /** 排序 */
        @Excel(name = "排序")
    private Long sort;

        /** 专题图片地址 */
        @Excel(name = "专题图片地址")
    private String img;

}