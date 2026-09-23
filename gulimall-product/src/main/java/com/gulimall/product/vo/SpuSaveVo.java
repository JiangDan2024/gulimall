package com.gulimall.product.vo;

import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
/**
 * SPU 保存传输对象 (View Object)
 * 用于接收前端发布商品时提交的 SPU 数据
 */
@Data
public class SpuSaveVo implements Serializable {
    private static final long serialVersionUID = 1L;
    /** SPU 名称 */
    private String spuName;
    /** SPU 描述 */
    private String spuDescription;
    /** 分类 ID */
    private Long catalogId;
    /** 品牌 ID */
    private Long brandId;
    /** 重量（建议使用 BigDecimal 保证精度） */
    private BigDecimal weight;
    /** 发布状态（0-下架，1-上架） */
    private Integer publishStatus;
    /** 商品详情描述（图片列表） */
    private List<String> decript;
    /** 商品图集（图片列表） */
    private List<String> images;
    /** 积分信息 */
    private Bounds bounds;
    /** 基础属性 */
    private List<BaseAttrs> baseAttrs;
    /** SKU 信息列表 */
    private List<Skus> skus;
}
