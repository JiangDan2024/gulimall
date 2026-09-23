package com.gulimall.product.service;

import com.baomidou.mybatisplus.spring.service.IService;
import com.gulimall.product.domain.SpuInfoDesc;

/**
 * 【请填写功能名称】Service接口
 *
 * @author jdjdjd
 * @date 2026-09-19
 */
public interface ISpuInfoDescService extends IService<SpuInfoDesc> {
    void saveSpuInfoDesc(SpuInfoDesc spuInfoDesc);
    // 单表 CRUD 由 IService 提供，如有自定义业务方法可在此处扩展
}