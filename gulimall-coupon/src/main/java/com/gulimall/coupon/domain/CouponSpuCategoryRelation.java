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
 * 【请填写功能名称】对象 sms_coupon_spu_category_relation
 *
 * @author jiangdan
 * @date 2026-09-11
 */
@Getter
@Setter
@TableName("sms_coupon_spu_category_relation")
public class CouponSpuCategoryRelation extends BaseEntity
{
    private static final long serialVersionUID = 1L;

        /** id */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

        /** 优惠券id */
        @Excel(name = "优惠券id")
    private Long couponId;

        /** 产品分类id */
        @Excel(name = "产品分类id")
    private Long categoryId;

        /** 产品分类名称 */
        @Excel(name = "产品分类名称")
    private String categoryName;

}