package com.gulimall.product.service.impl;

import com.baomidou.mybatisplus.spring.service.impl.ServiceImpl;
import com.gulimall.product.vo.SaleAttrVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gulimall.product.mapper.SkuSaleAttrValueMapper;
import com.gulimall.product.domain.SkuSaleAttrValue;
import com.gulimall.product.service.ISkuSaleAttrValueService;

import java.util.List;

/**
 * 【请填写功能名称】Service业务层处理
 *
 * @author jdjdjd
 * @date 2026-09-19
 */
@Service
public class SkuSaleAttrValueServiceImpl extends ServiceImpl<SkuSaleAttrValueMapper, SkuSaleAttrValue> implements ISkuSaleAttrValueService {

    @Autowired
    SkuSaleAttrValueMapper skuSaleAttrValueMapper;

    @Override
    public void saveSkuSaleAttrs(List<SkuSaleAttrValue> skuSaleAttrList) {
        if(skuSaleAttrList!=null&&skuSaleAttrList.size()>0){
            this.baseMapper.insert(skuSaleAttrList);
        }
    }

    @Override
    public List<SaleAttrVo> getSaleAttrsBySpuId(Long spuId) {
        List<SaleAttrVo> saleAttrVos = skuSaleAttrValueMapper.getSaleAttrsBySpuId(spuId);
        return saleAttrVos;
    }

    @Override
    public List<String> getSkuAttrValues(Long skuId) {
        List<String> values = skuSaleAttrValueMapper.getSkuAttrValues(skuId);
        return values;
    }
    // 单表 CRUD 由 ServiceImpl 提供，如有自定义业务方法可在此处扩展
}