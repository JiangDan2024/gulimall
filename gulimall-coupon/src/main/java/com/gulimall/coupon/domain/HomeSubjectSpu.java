package com.gulimall.coupon.domain;

import com.gulimall.common.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import java.util.List;
import com.gulimall.common.core.domain.BaseEntity;

/**
 * 【请填写功能名称】对象 sms_home_subject_spu
 *
 * @author jiangdan
 * @date 2026-09-11
 */
@Getter
@Setter
@TableName("sms_home_subject_spu")
public class HomeSubjectSpu extends BaseEntity
{
    private static final long serialVersionUID = 1L;

        /** id */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

        /** 专题名字 */
        @Excel(name = "专题名字")
    private String name;

        /** 专题id */
        @Excel(name = "专题id")
    private Long subjectId;

        /** spu_id */
        @Excel(name = "spu_id")
    private Long spuId;

        /** 排序 */
        @Excel(name = "排序")
    private Long sort;

}