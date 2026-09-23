package com.gulimall.product.vo;

import lombok.Data;
import java.io.Serializable;
/**
 * 销售属性
 */
@Data
public class Attr implements Serializable {
    private static final long serialVersionUID = 1L;
    /** 属性 ID */
    private Long attrId;
    /** 属性名称 */
    private String attrName;
    /** 属性值 */
    private String attrValue;
}
