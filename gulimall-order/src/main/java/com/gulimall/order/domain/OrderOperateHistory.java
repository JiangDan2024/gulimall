package com.gulimall.order.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.gulimall.common.annotation.Excel;
import com.gulimall.common.core.domain.BaseEntity;

import java.util.Date;

/**
 * 【请填写功能名称】对象 oms_order_operate_history
 *
 * @author jdjdjd
 * @date 2026-09-22
 */
        @Data
        @EqualsAndHashCode(callSuper = true)
        @TableName("oms_order_operate_history")
        public class OrderOperateHistory extends BaseEntity
        {
        private static final long serialVersionUID = 1L;

            /** id */
        @TableId(value = "id", type = IdType.AUTO)
    private Long id;

            /** 订单id */
            @Excel(name = "订单id")
    private Long orderId;

            /** 操作人[用户；系统；后台管理员] */
            @Excel(name = "操作人[用户；系统；后台管理员]")
    private String operateMan;

            /** 操作时间 */
    private Date createTime;

            /** 订单状态【0-&gt;待付款；1-&gt;待发货；2-&gt;已发货；3-&gt;已完成；4-&gt;已关闭；5-&gt;无效订单】 */
            @Excel(name = "订单状态【0-&gt;待付款；1-&gt;待发货；2-&gt;已发货；3-&gt;已完成；4-&gt;已关闭；5-&gt;无效订单】")
    private Long orderStatus;

            /** 备注 */
            @Excel(name = "备注")
    private String note;

        }