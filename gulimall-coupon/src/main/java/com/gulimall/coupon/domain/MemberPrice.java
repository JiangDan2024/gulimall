package com.gulimall.coupon.domain;

import java.math.BigDecimal;
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
 * 【请填写功能名称】对象 sms_member_price
 *
 * @author jiangdan
 * @date 2026-09-11
 */
@Getter
@Setter
@TableName("sms_member_price")
public class MemberPrice extends BaseEntity
{
    private static final long serialVersionUID = 1L;

        /** id */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

        /** sku_id */
        @Excel(name = "sku_id")
    private Long skuId;

        /** 会员等级id */
        @Excel(name = "会员等级id")
    private Long memberLevelId;

        /** 会员等级名 */
        @Excel(name = "会员等级名")
    private String memberLevelName;

        /** 会员对应价格 */
        @Excel(name = "会员对应价格")
    private BigDecimal memberPrice;

        /** 可否叠加其他优惠[0-不可叠加优惠，1-可叠加] */
        @Excel(name = "可否叠加其他优惠[0-不可叠加优惠，1-可叠加]")
    private Integer addOther;

}