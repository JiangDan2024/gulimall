package com.gulimall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.spring.service.impl.ServiceImpl;
import com.gulimall.product.domain.Brand;
import com.gulimall.product.domain.Category;
import com.gulimall.product.mapper.BrandMapper;
import com.gulimall.product.mapper.CategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gulimall.product.mapper.CategoryBrandRelationMapper;
import com.gulimall.product.domain.CategoryBrandRelation;
import com.gulimall.product.service.ICategoryBrandRelationService;

import java.util.List;

/**
 * 【请填写功能名称】Service业务层处理
 *
 * @author jdjdjd
 * @date 2026-09-19
 */
@Service
public class CategoryBrandRelationServiceImpl extends ServiceImpl<CategoryBrandRelationMapper, CategoryBrandRelation> implements ICategoryBrandRelationService {
    @Autowired
    BrandMapper brandMapper;

    @Autowired
    CategoryMapper categoryMapper;

    @Autowired
    CategoryBrandRelationMapper categoryBrandRelationMapper;

    @Override
    public List<CategoryBrandRelation> categoryList(Long brandId) {
        QueryWrapper<CategoryBrandRelation> wrapper = new QueryWrapper<>();
        wrapper.eq("brand_id",brandId);
        List<CategoryBrandRelation> list = baseMapper.selectList(wrapper);
        return list;
    }

    @Override
    public boolean cascaderSave(CategoryBrandRelation categoryBrandRelation) {
        Brand brand = brandMapper.selectById(categoryBrandRelation.getBrandId());
        Category category = categoryMapper.selectById(categoryBrandRelation.getCatelogId());
        categoryBrandRelation.setBrandName(brand.getName());
        categoryBrandRelation.setCatelogName(category.getName());

        return this.save(categoryBrandRelation);
    }

    @Override
    public void updateBrand(Long brandId, String brandName) {
        UpdateWrapper<CategoryBrandRelation> wrapper = new UpdateWrapper<>();
        wrapper.eq("brand_id",brandId).set("brand_name",brandName);
        this.update(wrapper);
    }

    @Override
    public void updateCategory(Long catId, String name) {
        categoryBrandRelationMapper.updateCategory(catId,name);
    }
    // 单表 CRUD 由 ServiceImpl 提供，如有自定义业务方法可在此处扩展
}