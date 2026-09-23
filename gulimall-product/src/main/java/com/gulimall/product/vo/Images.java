package com.gulimall.product.vo;

import lombok.Data;
import java.io.Serializable;
/**
 * 图片信息
 */
@Data
public class Images implements Serializable {
    private static final long serialVersionUID = 1L;
    /** 图片地址 */
    private String imgUrl;
    /** 是否默认图 */
    private Integer defaultImg;
}