package com.gulimall.product.vo;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@ToString
@Data
public class SaleAttrVo {
    Long attrId;
    String attrName;
//    String attrValues;
    //返回属性对应的所有skuid
    List<AttrValueWithSkuIdVo> attrValues;
}
