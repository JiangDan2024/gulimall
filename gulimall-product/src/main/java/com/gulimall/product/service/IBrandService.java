package com.gulimall.product.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.spring.service.IService;
import com.gulimall.common.core.page.PageDomain;
import com.gulimall.product.domain.Brand;

/**
 * 【请填写功能名称】Service接口
 *
 * @author jdjdjd
 * @date 2026-09-16
 */
public interface IBrandService extends IService<Brand> {
    IPage<Brand> queryPage(PageDomain pageDomain, Brand brand);

    int updateCascader(Brand brand);
    // 单表 CRUD 由 IService 提供，如有自定义业务方法可在此处扩展
}