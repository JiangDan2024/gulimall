package com.gulimall.common.to;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class SkuReductionTo {
    /** sku_id */
    private Long skuId;
    /** 满减数量 */
    private BigDecimal fullCount;
    /** 折扣（建议使用 BigDecimal） */
    private BigDecimal discount;
    /** 计数状态 */
    private Integer countStatus;
    /** 满减价格 */
    private BigDecimal fullPrice;
    /** 减免价格 */
    private BigDecimal reducePrice;
    /** 价格状态 */
    private Integer priceStatus;
    /** 会员价格列表 */
    private List<MemberPrice> memberPrice;
}
