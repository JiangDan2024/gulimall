package com.gulimall.product.vo;

import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;
/**
 * 会员价格
 */
@Data
public class MemberPrice implements Serializable {
    private static final long serialVersionUID = 1L;
    /** 会员等级 ID */
    private Long id;
    /** 会员等级名称 */
    private String name;
    /** 会员价格 */
    private BigDecimal price;
}