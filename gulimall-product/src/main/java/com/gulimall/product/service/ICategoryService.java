package com.gulimall.product.service;

import com.gulimall.product.domain.Category;
import com.baomidou.mybatisplus.spring.service.IService;

import java.util.List;

/**
 * 【请填写功能名称】Service接口
 * 
 * @author jiangdan
 * @date 2026-09-12
 */
public interface ICategoryService extends IService<Category>
{

    List<Category> listWithTree();

    int removeMenusByIds(List<Long> ids);

    Long[] findCategoryPath(Long catelogId);

    boolean updateCascader(Category category);
}
