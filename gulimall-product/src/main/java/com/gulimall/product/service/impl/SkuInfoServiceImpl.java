package com.gulimall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.spring.service.impl.ServiceImpl;
import com.gulimall.common.core.page.PageDomain;
import com.gulimall.common.core.page.TableSupport;
import com.gulimall.common.utils.StringUtils;
import com.gulimall.product.domain.SkuImages;
import com.gulimall.product.domain.SpuInfoDesc;
import com.gulimall.product.service.*;
import com.gulimall.product.vo.SaleAttrVo;
import com.gulimall.product.vo.SkuItemVo;
import com.gulimall.product.vo.SpuItemAttrGroupVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gulimall.product.mapper.SkuInfoMapper;
import com.gulimall.product.domain.SkuInfo;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 【请填写功能名称】Service业务层处理
 *
 * @author jdjdjd
 * @date 2026-09-19
 */
@Service
public class SkuInfoServiceImpl extends ServiceImpl<SkuInfoMapper, SkuInfo> implements ISkuInfoService {

    @Autowired
    ISkuImagesService skuImagesServicer;

    @Autowired
    ISpuInfoDescService spuInfoDescServicer;

    @Autowired
    IAttrGroupService attrGroupService;

    @Autowired
    ISkuSaleAttrValueService skuSaleAttrValueService;

    @Override
    public void savSkuInfo(SkuInfo skuInfo) {
        this.baseMapper.insert(skuInfo);
    }

    @Override
    public IPage<SkuInfo> pageQuery(Map<String, Object> params) {
        PageDomain pageDomain = TableSupport.buildPageRequest();
        Page<SkuInfo> page = new Page<>(pageDomain.getPageNum(), pageDomain.getPageSize());
        QueryWrapper<SkuInfo> wrapper = new QueryWrapper<>();
        /***
         *  pageNum: this.pageIndex,
         *  pageSize: this.pageSize,
         *  key: this.dataForm.key,
         *  catelogId: this.dataForm.catelogId==0?null:this.dataForm.catelogId,
         *  brandId: this.dataForm.brandId==0?null:this.dataForm.brandId,
         *  min: this.dataForm.price.min,
         *  max: this.dataForm.price.max
         */
        String key = params.getOrDefault("key", "").toString();
        if(StringUtils.isNotBlank(key)){
            wrapper.and(w->{
                w.eq("sku_id",key)
                        .or().like("sku_name",key)
                        .or().like("sku_desc",key);
            });
        }
        String brandId = params.getOrDefault("brandId","0").toString();
        if(StringUtils.isNotBlank(brandId) &&!"0".equalsIgnoreCase(brandId)){
            wrapper.eq("brand_id",brandId);
        }
        String catelogId = params.getOrDefault("catelogId","0").toString();
        if(StringUtils.isNotBlank(catelogId) &&!"0".equalsIgnoreCase(catelogId)){
            wrapper.eq("catalog_id",catelogId);
        }
        String minStr = params.getOrDefault("min", "0").toString();
        if (StringUtils.isNotBlank(minStr)) {
            BigDecimal min = new BigDecimal(minStr);
            wrapper.ge("price",min);
        }
        String maxStr = params.getOrDefault("max", "").toString();
        if (StringUtils.isNotBlank(maxStr)) {
            try{
                BigDecimal max = new BigDecimal(maxStr);
                if(max.compareTo(BigDecimal.ZERO)>0){
                    wrapper.le("price",max);
                }
            }catch (Exception e){
            }
        }
        return this.page(page,wrapper);
    }

    @Override
    public List<SkuInfo> getBySpuId(Long spuId) {
        return this.list(new QueryWrapper<SkuInfo>().eq("spu_id",spuId));
    }

    @Autowired
    ThreadPoolExecutor executor;
    
    @Override
    public SkuItemVo item(String skuId) throws ExecutionException, InterruptedException {
        //TODO商品快速介绍，是否有货
        SkuItemVo itemVo = new SkuItemVo();
        CompletableFuture<SkuInfo> infoFuture = CompletableFuture.supplyAsync(() -> {
            //1.查询skuinfo
            SkuInfo info = this.getById(skuId);
            itemVo.setInfo(info);
            return info;
        }, executor);
        //2.查询sku_image
        CompletableFuture<Void> imageFuture = CompletableFuture.runAsync(() -> {
            List<SkuImages> images = skuImagesServicer.getImagesBySkuid(skuId);
            itemVo.setImages(images);
        }, executor);

        //3.spu的销售属性组合
        CompletableFuture<Void> saleAttrFuture = infoFuture.thenAcceptAsync(info -> {
            List<SaleAttrVo> saleAttrVos = skuSaleAttrValueService.getSaleAttrsBySpuId(info.getSpuId());
            itemVo.setSaleAttrs(saleAttrVos);
        }, executor);

        //4.获取spu介绍
        CompletableFuture<Void> descFuture = infoFuture.thenAcceptAsync(info -> {
            SpuInfoDesc desc = spuInfoDescServicer.getById(info.getSpuId());
            itemVo.setDesc(desc);
        }, executor);

        //5.获取spu规格参数
        CompletableFuture<Void> attrGroupFuture = infoFuture.thenAcceptAsync(info -> {
            List<SpuItemAttrGroupVo> attrs = attrGroupService.getAttrGroupWithAttrBySpuId(info.getSpuId(), info.getCatalogId());
            itemVo.setAttrs(attrs);
        }, executor);

        //等待全部执行
        CompletableFuture.allOf(infoFuture,imageFuture,saleAttrFuture,descFuture,attrGroupFuture).get();

        return itemVo;
    }
    // 单表 CRUD 由 ServiceImpl 提供，如有自定义业务方法可在此处扩展
}