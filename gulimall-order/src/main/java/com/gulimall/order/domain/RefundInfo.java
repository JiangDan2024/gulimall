package com.gulimall.order.domain;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.gulimall.common.annotation.Excel;
import com.gulimall.common.core.domain.BaseEntity;

/**
 * 【请填写功能名称】对象 oms_refund_info
 *
 * @author jdjdjd
 * @date 2026-09-22
 */
        @Data
        @EqualsAndHashCode(callSuper = true)
        @TableName("oms_refund_info")
        public class RefundInfo extends BaseEntity
        {
        private static final long serialVersionUID = 1L;

            /** id */
        @TableId(value = "id", type = IdType.AUTO)
    private Long id;

            /** 退款的订单 */
            @Excel(name = "退款的订单")
    private Long orderReturnId;

            /** 退款金额 */
            @Excel(name = "退款金额")
    private BigDecimal refund;

            /** 退款交易流水号 */
            @Excel(name = "退款交易流水号")
    private String refundSn;

            /** 退款状态 */
            @Excel(name = "退款状态")
    private Integer refundStatus;

            /** 退款渠道[1-支付宝，2-微信，3-银联，4-汇款] */
            @Excel(name = "退款渠道[1-支付宝，2-微信，3-银联，4-汇款]")
    private Long refundChannel;

            /** $column.columnComment */
            @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String refundContent;

        }