package com.gulimall.order.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.gulimall.common.annotation.Excel;
import com.gulimall.common.core.domain.BaseEntity;

/**
 * 【请填写功能名称】对象 oms_order
 *
 * @author jdjdjd
 * @date 2026-09-22
 */
        @Data
        @EqualsAndHashCode(callSuper = true)
        @TableName("oms_order")
        public class Order extends BaseEntity
        {
        private static final long serialVersionUID = 1L;

            /** id */
        @TableId(value = "id", type = IdType.AUTO)
    private Long id;

            /** member_id */
            @Excel(name = "member_id")
    private Long memberId;

            /** 订单号 */
            @Excel(name = "订单号")
    private String orderSn;

            /** 使用的优惠券 */
            @Excel(name = "使用的优惠券")
    private Long couponId;

            /** create_time */
    private Date createTime;

            /** 用户名 */
            @Excel(name = "用户名")
    private String memberUsername;

            /** 订单总额 */
            @Excel(name = "订单总额")
    private BigDecimal totalAmount;

            /** 应付总额 */
            @Excel(name = "应付总额")
    private BigDecimal payAmount;

            /** 运费金额 */
            @Excel(name = "运费金额")
    private BigDecimal freightAmount;

            /** 促销优化金额（促销价、满减、阶梯价） */
            @Excel(name = "促销优化金额", readConverterExp = "促=销价、满减、阶梯价")
    private BigDecimal promotionAmount;

            /** 积分抵扣金额 */
            @Excel(name = "积分抵扣金额")
    private BigDecimal integrationAmount;

            /** 优惠券抵扣金额 */
            @Excel(name = "优惠券抵扣金额")
    private BigDecimal couponAmount;

            /** 后台调整订单使用的折扣金额 */
            @Excel(name = "后台调整订单使用的折扣金额")
    private BigDecimal discountAmount;

            /** 支付方式【1-&gt;支付宝；2-&gt;微信；3-&gt;银联； 4-&gt;货到付款；】 */
            @Excel(name = "支付方式【1-&gt;支付宝；2-&gt;微信；3-&gt;银联； 4-&gt;货到付款；】")
    private Long payType;

            /** 订单来源[0-&gt;PC订单；1-&gt;app订单] */
            @Excel(name = "订单来源[0-&gt;PC订单；1-&gt;app订单]")
    private Long sourceType;

            /** 订单状态【0-&gt;待付款；1-&gt;待发货；2-&gt;已发货；3-&gt;已完成；4-&gt;已关闭；5-&gt;无效订单】 */
            @Excel(name = "订单状态【0-&gt;待付款；1-&gt;待发货；2-&gt;已发货；3-&gt;已完成；4-&gt;已关闭；5-&gt;无效订单】")
    private Long status;

            /** 物流公司(配送方式) */
            @Excel(name = "物流公司(配送方式)")
    private String deliveryCompany;

            /** 物流单号 */
            @Excel(name = "物流单号")
    private String deliverySn;

            /** 自动确认时间（天） */
            @Excel(name = "自动确认时间", readConverterExp = "天=")
    private Long autoConfirmDay;

            /** 可以获得的积分 */
            @Excel(name = "可以获得的积分")
    private Long integration;

            /** 可以获得的成长值 */
            @Excel(name = "可以获得的成长值")
    private Long growth;

            /** 发票类型[0-&gt;不开发票；1-&gt;电子发票；2-&gt;纸质发票] */
            @Excel(name = "发票类型[0-&gt;不开发票；1-&gt;电子发票；2-&gt;纸质发票]")
    private Long billType;

            /** 发票抬头 */
            @Excel(name = "发票抬头")
    private String billHeader;

            /** 发票内容 */
            @Excel(name = "发票内容")
    private String billContent;

            /** 收票人电话 */
            @Excel(name = "收票人电话")
    private String billReceiverPhone;

            /** 收票人邮箱 */
            @Excel(name = "收票人邮箱")
    private String billReceiverEmail;

            /** 收货人姓名 */
            @Excel(name = "收货人姓名")
    private String receiverName;

            /** 收货人电话 */
            @Excel(name = "收货人电话")
    private String receiverPhone;

            /** 收货人邮编 */
            @Excel(name = "收货人邮编")
    private String receiverPostCode;

            /** 省份/直辖市 */
            @Excel(name = "省份/直辖市")
    private String receiverProvince;

            /** 城市 */
            @Excel(name = "城市")
    private String receiverCity;

            /** 区 */
            @Excel(name = "区")
    private String receiverRegion;

            /** 详细地址 */
            @Excel(name = "详细地址")
    private String receiverDetailAddress;

            /** 订单备注 */
            @Excel(name = "订单备注")
    private String note;

            /** 确认收货状态[0-&gt;未确认；1-&gt;已确认] */
            @Excel(name = "确认收货状态[0-&gt;未确认；1-&gt;已确认]")
    private Long confirmStatus;

            /** 删除状态【0-&gt;未删除；1-&gt;已删除】 */
            @Excel(name = "删除状态【0-&gt;未删除；1-&gt;已删除】")
    private Long deleteStatus;

            /** 下单时使用的积分 */
            @Excel(name = "下单时使用的积分")
    private Long useIntegration;

            /** 支付时间 */
            @JsonFormat(pattern = "yyyy-MM-dd")
            @Excel(name = "支付时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date paymentTime;

            /** 发货时间 */
            @JsonFormat(pattern = "yyyy-MM-dd")
            @Excel(name = "发货时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date deliveryTime;

            /** 确认收货时间 */
            @JsonFormat(pattern = "yyyy-MM-dd")
            @Excel(name = "确认收货时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date receiveTime;

            /** 评价时间 */
            @JsonFormat(pattern = "yyyy-MM-dd")
            @Excel(name = "评价时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date commentTime;

            /** 修改时间 */
            @JsonFormat(pattern = "yyyy-MM-dd")
            @Excel(name = "修改时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date modifyTime;

        }