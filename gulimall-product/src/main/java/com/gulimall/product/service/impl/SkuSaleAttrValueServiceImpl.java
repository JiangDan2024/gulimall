package com.gulimall.product.service.impl;

import com.baomidou.mybatisplus.spring.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.gulimall.product.mapper.SkuSaleAttrValueMapper;
import com.gulimall.product.domain.SkuSaleAttrValue;
import com.gulimall.product.service.ISkuSaleAttrValueService;

/**
 * 【请填写功能名称】Service业务层处理
 *
 * @author jdjdjd
 * @date 2026-09-19
 */
@Service
public class SkuSaleAttrValueServiceImpl extends ServiceImpl<SkuSaleAttrValueMapper, SkuSaleAttrValue> implements ISkuSaleAttrValueService {
    // 单表 CRUD 由 ServiceImpl 提供，如有自定义业务方法可在此处扩展
}