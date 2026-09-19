package com.gulimall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.spring.service.impl.ServiceImpl;
import com.gulimall.common.core.page.PageDomain;
import com.gulimall.common.utils.StringUtils;
import com.gulimall.product.service.ICategoryBrandRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gulimall.product.mapper.BrandMapper;
import com.gulimall.product.domain.Brand;
import com.gulimall.product.service.IBrandService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 【请填写功能名称】Service业务层处理
 *
 * @author jdjdjd
 * @date 2026-09-16
 */
@Service
public class BrandServiceImpl extends ServiceImpl<BrandMapper, Brand> implements IBrandService {

    @Autowired
    ICategoryBrandRelationService categoryBrandRelationService;

    @Override
    public IPage<Brand> queryPage(PageDomain pageDomain, Brand brand) {
        Page<Brand> page = new Page<>(pageDomain.getPageNum(), pageDomain.getPageSize());
        QueryWrapper<Brand> wrapper = new QueryWrapper<>();
        if(StringUtils.isNotEmpty(brand.getKey())){
            wrapper.eq("brand_id",brand.getKey())
                    .or().eq("first_letter",brand.getKey())
                    .or().like("name",brand.getKey())
                    .or().like("descript",brand.getKey());
        }

        return this.page(page,wrapper);
    }
    @Transactional
    @Override
    public int updateCascader(Brand brand) {
        //更新品牌表
        this.updateById(brand);
        //更新级联表
        categoryBrandRelationService.updateBrand(brand.getBrandId(),brand.getName());
        return 1;
    }
    // 单表 CRUD 由 ServiceImpl 提供，如有自定义业务方法可在此处扩展
}