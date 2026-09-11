package com.gulimall.product.domain;

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
 * 【请填写功能名称】对象 pms_sku_sale_attr_value
 *
 * @author jiangdan
 * @date 2026-09-12
 */
@Getter
@Setter
@TableName("pms_sku_sale_attr_value")
public class SkuSaleAttrValue extends BaseEntity
{
    private static final long serialVersionUID = 1L;

        /** id */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

        /** sku_id */
        @Excel(name = "sku_id")
    private Long skuId;

        /** attr_id */
        @Excel(name = "attr_id")
    private Long attrId;

        /** 销售属性名 */
        @Excel(name = "销售属性名")
    private String attrName;

        /** 销售属性值 */
        @Excel(name = "销售属性值")
    private String attrValue;

        /** 顺序 */
        @Excel(name = "顺序")
    private Long attrSort;

}