package com.gulimall.coupon.domain;

import java.math.BigDecimal;
import com.gulimall.common.annotation.Excel;
import lombok.Getter;
import lombok.Setter;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.IdType;
import com.gulimall.common.core.domain.BaseEntity;

/**
 * 【请填写功能名称】对象 sms_spu_bounds
 *
 * @author jiangdan
 * @date 2026-09-12
 */
@Getter
@Setter
@TableName("sms_spu_bounds")
public class SpuBounds extends BaseEntity
{
    private static final long serialVersionUID = 1L;

        /** id */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

        /** $column.columnComment */
        @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Long spuId;

        /** 成长积分 */
        @Excel(name = "成长积分")
    private BigDecimal growBounds;

        /** 购物积分 */
        @Excel(name = "购物积分")
    private BigDecimal buyBounds;

        /** 优惠生效情况[1111（四个状态位，从右到左）;0 - 无优惠，成长积分是否赠送;1 - 无优惠，购物积分是否赠送;2 - 有优惠，成长积分是否赠送;3 - 有优惠，购物积分是否赠送【状态位0：不赠送，1：赠送】] */
        @Excel(name = "优惠生效情况[1111", readConverterExp = "四=个状态位，从右到左")
    private Integer work;

}