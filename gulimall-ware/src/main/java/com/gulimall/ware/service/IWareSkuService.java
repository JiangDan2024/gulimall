package com.gulimall.ware.service;

import com.baomidou.mybatisplus.spring.service.IService;
import com.gulimall.common.to.SkuHasstockVo;
import com.gulimall.ware.domain.WareSku;

import java.util.List;

/**
 * 【请填写功能名称】Service接口
 *
 * @author jdjdjd
 * @date 2026-09-22
 */
public interface IWareSkuService extends IService<WareSku> {
    void updateFinishedStock(Long skuId, Long wareId, Long skuNum);

    List<SkuHasstockVo> hasStock(List<Long> skuId);
    // 单表 CRUD 由 IService 提供，如有自定义业务方法可在此处扩展
}