package com.gulimall.search.vo;

import lombok.Data;

import java.util.List;

@Data
public class SearchParam {
    private String keyword; //页面传来的全文匹配关键字
    private Long catalog3Id;//三级分类
    private String sort;//排序条件 拼接为字符串
    private Integer hasStock;//是否只显示有货
    private String skuPrice;//价格区间查询
    private List<Long> brandId;//按照品牌进行查询，可以多选，传list
    private List<String> attrs;//按照属性进行筛选
    private Integer pageNum;//页码
    private String _queryString;//原始查询条件
}
