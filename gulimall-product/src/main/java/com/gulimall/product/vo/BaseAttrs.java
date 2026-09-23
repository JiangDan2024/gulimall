package com.gulimall.product.vo;

import lombok.Data;
import java.io.Serializable;
/**
 * 基础属性
 */
@Data
public class BaseAttrs implements Serializable {
    private static final long serialVersionUID = 1L;
    /** 属性 ID */
    private Long attrId;
    /** 属性值 */
    private String attrValues;
    /** 是否展示描述 */
    private Integer showDesc;
}
