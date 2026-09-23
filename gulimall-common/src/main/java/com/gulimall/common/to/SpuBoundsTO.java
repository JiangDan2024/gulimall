package com.gulimall.common.to;

import com.gulimall.common.annotation.Excel;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class SpuBoundsTO {
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
