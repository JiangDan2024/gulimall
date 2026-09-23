package com.gulimall.product.service;

import com.baomidou.mybatisplus.spring.service.IService;
import com.gulimall.product.domain.Brand;
import com.gulimall.product.domain.CategoryBrandRelation;
import com.gulimall.product.vo.BrandVo;

import java.util.List;

/**
 * 【请填写功能名称】Service接口
 *
 * @author jdjdjd
 * @date 2026-09-19
 */
public interface ICategoryBrandRelationService extends IService<CategoryBrandRelation> {
    List<CategoryBrandRelation> categoryList(Long brandId);

    boolean cascaderSave(CategoryBrandRelation categoryBrandRelation);

    void updateBrand(Long brandId,String brandName);

    void updateCategory(Long catId, String name);

    List<BrandVo> brandList(Long catId);
    // 单表 CRUD 由 IService 提供，如有自定义业务方法可在此处扩展
}