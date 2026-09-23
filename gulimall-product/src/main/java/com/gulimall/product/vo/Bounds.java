package com.gulimall.product.vo;

import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 积分信息
 */
@Data
public class Bounds implements Serializable {
    private static final long serialVersionUID = 1L;
    /** 购物积分 */
    private BigDecimal buyBounds;
    /** 成长积分 */
    private BigDecimal growBounds;
}