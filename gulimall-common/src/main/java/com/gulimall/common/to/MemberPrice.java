package com.gulimall.common.to;

import com.gulimall.common.core.domain.BaseEntity;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 【请填写功能名称】对象 sms_member_price
 *
 * @author jdjdjd
 * @date 2026-09-22
 */
@Data
public class MemberPrice implements Serializable {
    private static final long serialVersionUID = 1L;
    /** 会员等级 ID */
    private Long id;
    /** 会员等级名称 */
    private String name;
    /** 会员价格 */
    private BigDecimal price;
}