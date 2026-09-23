package com.gulimall.product.vo;

import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
/**
 * SKU 信息
 */
@Data
public class Skus implements Serializable {
    private static final long serialVersionUID = 1L;
    /** 销售属性列表 */
    private List<Attr> attr;
    /** SKU 名称 */
    private String skuName;
    /** 价格（强烈建议使用 BigDecimal） */
    private BigDecimal price;
    /** SKU 标题 */
    private String skuTitle;
    /** SKU 副标题 */
    private String skuSubtitle;
    /** SKU 图片列表 */
    private List<Images> images;
    /** SKU 描述（原代码为 descar，保持与前端一致，但建议改为 desc） */
    private List<String> descar;
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
