package com.gulimall.product.vo;

import com.gulimall.product.domain.SkuImages;
import com.gulimall.product.domain.SkuInfo;
import com.gulimall.product.domain.SpuInfoDesc;
import lombok.Data;

import java.util.List;

@Data
public class SkuItemVo {

    //1.查询skuinfo
    SkuInfo info;

    //是否有货
    Boolean hasStock=true;

    //2.查询sku_image
    List<SkuImages> images;

    //3.spu的销售属性组合
    List<SaleAttrVo> saleAttrs;

    //4.获取spu介绍
    SpuInfoDesc desc;

    //5.获取spu规格参数
    List<SpuItemAttrGroupVo> attrs;
}
