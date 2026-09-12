package com.gulimall.ware.domain;

import com.gulimall.common.annotation.Excel;
import lombok.Getter;
import lombok.Setter;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.IdType;
import com.gulimall.common.core.domain.BaseEntity;

/**
 * 【请填写功能名称】对象 wms_ware_sku
 *
 * @author jiangdan
 * @date 2026-09-12
 */
@Getter
@Setter
@TableName("wms_ware_sku")
public class WareSku extends BaseEntity
{
    private static final long serialVersionUID = 1L;

        /** id */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

        /** sku_id */
        @Excel(name = "sku_id")
    private Long skuId;

        /** 仓库id */
        @Excel(name = "仓库id")
    private Long wareId;

        /** 库存数 */
        @Excel(name = "库存数")
    private Long stock;

        /** sku_name */
        @Excel(name = "sku_name")
    private String skuName;

        /** 锁定库存 */
        @Excel(name = "锁定库存")
    private Long stockLocked;

}