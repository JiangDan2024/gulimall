package com.gulimall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.spring.service.impl.ServiceImpl;
import com.gulimall.common.core.page.PageDomain;
import com.gulimall.common.core.page.TableSupport;
import com.gulimall.common.utils.StringUtils;
import org.springframework.stereotype.Service;
import com.gulimall.product.mapper.SkuInfoMapper;
import com.gulimall.product.domain.SkuInfo;
import com.gulimall.product.service.ISkuInfoService;

import java.math.BigDecimal;
import java.util.Map;

/**
 * 【请填写功能名称】Service业务层处理
 *
 * @author jdjdjd
 * @date 2026-09-19
 */
@Service
public class SkuInfoServiceImpl extends ServiceImpl<SkuInfoMapper, SkuInfo> implements ISkuInfoService {
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
    // 单表 CRUD 由 ServiceImpl 提供，如有自定义业务方法可在此处扩展
}