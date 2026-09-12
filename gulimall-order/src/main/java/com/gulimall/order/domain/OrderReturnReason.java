package com.gulimall.order.domain;

import java.util.Date;
import com.gulimall.common.annotation.Excel;
import lombok.Getter;
import lombok.Setter;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.IdType;
import com.gulimall.common.core.domain.BaseEntity;

/**
 * 【请填写功能名称】对象 oms_order_return_reason
 *
 * @author jiangdan
 * @date 2026-09-12
 */
@Getter
@Setter
@TableName("oms_order_return_reason")
public class OrderReturnReason extends BaseEntity
{
    private static final long serialVersionUID = 1L;

        /** id */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

        /** 退货原因名 */
        @Excel(name = "退货原因名")
    private String name;

        /** 排序 */
        @Excel(name = "排序")
    private Long sort;

        /** 启用状态 */
        @Excel(name = "启用状态")
    private Integer status;

        /** create_time */
    private Date createTime;

}