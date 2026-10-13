package com.gulimall.product.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.spring.service.IService;
import com.gulimall.product.domain.SkuInfo;
import com.gulimall.product.vo.SkuItemVo;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * 【请填写功能名称】Service接口
 *
 * @author jdjdjd
 * @date 2026-09-19
 */
public interface ISkuInfoService extends IService<SkuInfo> {
    void savSkuInfo(SkuInfo skuInfo);

    IPage<SkuInfo> pageQuery(Map<String, Object> params);

    List<SkuInfo> getBySpuId(Long spuId);

    SkuItemVo item(String skuId) throws ExecutionException, InterruptedException;
    // 单表 CRUD 由 IService 提供，如有自定义业务方法可在此处扩展
}