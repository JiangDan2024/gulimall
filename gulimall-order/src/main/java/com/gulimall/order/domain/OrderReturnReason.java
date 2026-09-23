package com.gulimall.order.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.gulimall.common.annotation.Excel;
import com.gulimall.common.core.domain.BaseEntity;

/**
 * 【请填写功能名称】对象 oms_order_return_reason
 *
 * @author jdjdjd
 * @date 2026-09-22
 */
        @Data
        @EqualsAndHashCode(callSuper = true)
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