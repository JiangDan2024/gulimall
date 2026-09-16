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
 * 【请填写功能名称】对象 wms_purchase
 *
 * @author jdjdjd
 * @date 2026-09-16
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("wms_purchase")
public class Purchase extends BaseEntity
{
private static final long serialVersionUID = 1L;

    /** 采购单id */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /** 采购人id */
    @Excel(name = "采购人id")
    private Long assigneeId;

    /** 采购人名 */
    @Excel(name = "采购人名")
    private String assigneeName;

    /** 联系方式 */
    @Excel(name = "联系方式")
    private String phone;

    /** 优先级 */
    @Excel(name = "优先级")
    private Long priority;

    /** 状态 */
    @Excel(name = "状态")
    private Long status;

    /** 仓库id */
    @Excel(name = "仓库id")
    private Long wareId;

    /** 总金额 */
    @Excel(name = "总金额")
    private BigDecimal amount;

}