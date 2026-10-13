package com.gulimall.product.vo;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@ToString
@Data
public class SpuItemAttrGroupVo {
    String groupName;
    List<Attr> attrs;
}
