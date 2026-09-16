package com.gulimall.ware.domain;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.gulimall.common.annotation.Excel;
import com.gulimall.common.core.domain.BaseEntity;

/**
 * 【请填写功能名称】对象 wms_purchase_detail
 *
 * @author jdjdjd
 * @date 2026-09-16
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("wms_purchase_detail")
public class PurchaseDetail extends BaseEntity
{
private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /** 采购单id */
    @Excel(name = "采购单id")
    private Long purchaseId;

    /** 采购商品id */
    @Excel(name = "采购商品id")
    private Long skuId;

    /** 采购数量 */
    @Excel(name = "采购数量")
    private Long skuNum;

    /** 采购金额 */
    @Excel(name = "采购金额")
    private BigDecimal skuPrice;

    /** 仓库id */
    @Excel(name = "仓库id")
    private Long wareId;

    /** 状态[0新建，1已分配，2正在采购，3已完成，4采购失败] */
    @Excel(name = "状态[0新建，1已分配，2正在采购，3已完成，4采购失败]")
    private Long status;

}