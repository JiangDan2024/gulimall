package com.gulimall.product.vo;

import lombok.Data;

@Data
public class AttrGroupCateVo extends AttrVo{

    private String groupName;

    private String categoryName;

    private Long[] catelogPath;
}
