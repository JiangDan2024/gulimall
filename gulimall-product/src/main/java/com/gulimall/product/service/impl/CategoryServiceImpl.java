package com.gulimall.product.service.impl;

import com.baomidou.mybatisplus.spring.service.impl.ServiceImpl;

import com.gulimall.product.service.ICategoryBrandRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gulimall.product.mapper.CategoryMapper;
import com.gulimall.product.domain.Category;
import com.gulimall.product.service.ICategoryService;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 【请填写功能名称】Service业务层处理
 * 
 * @author jiangdan
 * @date 2026-09-12
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper,Category> implements ICategoryService
{

    @Autowired
    ICategoryBrandRelationService categoryBrandRelationService;
//    @Override
//    public List<Category> listWithTree() {
//        //查询所有数据
//        List<Category> entites = baseMapper.selectList(null);
//        //找出一级目录
//        List<Category> collect = entites.stream().filter(category ->
//                //父目录为0找出一级目录
//                category.getParentCid() == 0
//        ).map((menu) -> {
//            menu.setChildren(getChildren(menu, entites));
//            return menu;
//        }).sorted((menu1, menu2) -> {
//            return Math.toIntExact((menu1.getSort() == null ? 0 : menu1.getSort()) - (menu2.getSort() == null ? 0 : menu2.getSort()));
//        }).collect(Collectors.toList());
//        return collect;
//    }
//
//    private List<Category> getChildren(Category root,List<Category> all){
//        List<Category> children = all.stream().filter(category ->{
//            return category.getParentCid() == root.getCatId();
//        }).map(category -> {
//            category.setChildren(getChildren(category,all));
//            return category;
//        }).sorted((menu1,menu2)->{
//            return Math.toIntExact((menu1.getSort() == null ? 0 : menu1.getSort()) - (menu2.getSort() == null ? 0 : menu2.getSort()));
//        }).collect(Collectors.toList());
//
//        return children;
//    }
    @Override
    public List<Category> listWithTree() {
        // 查询所有数据
        List<Category> entities = baseMapper.selectList(null);

        // 找出一级目录
        return entities.stream()
                .filter(category -> category.getParentCid() != null && category.getParentCid() == 0L)
                .map(category -> {
                    category.setChildren(getChildren(category, entities));
                    return category;
                })
                .sorted((m1, m2) -> {
                    long s1 = m1.getSort() == null ? 0 : m1.getSort();
                    long s2 = m2.getSort() == null ? 0 : m2.getSort();
                    return Long.compare(s1, s2);
                })
                .collect(Collectors.toList());
    }

    @Override
    public int removeMenusByIds(List<Long> ids) {
        //todo 删除前先检查id是否有其他正在使用的地方
        return baseMapper.deleteByIds(ids);
    }

    @Override
    public Long[] findCategoryPath(Long catelogId) {
        List<Long> paths = new ArrayList<>();
        //递归查找父id
        List<Long> parentPath = findParentPath(catelogId, paths);
        Collections.reverse(parentPath);
        return parentPath.toArray(new Long[parentPath.size()]);
    }

    @Transactional
    @Override
    public boolean updateCascader(Category category) {
        this.updateById(category);
        categoryBrandRelationService.updateCategory(category.getCatId(),category.getName());
        return true;
    }

    private List<Long> findParentPath(Long categoryId,List<Long> paths) {
        paths.add(categoryId);
        Category byId = this.getById(categoryId);
        if (byId == null) {
            return paths;
        }
        if(byId.getParentCid()!=0){
            findParentPath(byId.getParentCid(),paths);
        }
        return paths;
    }

    private List<Category> getChildren(Category root, List<Category> all) {
        return all.stream()
                .filter(category -> category.getParentCid() != null
                        && category.getParentCid().equals(root.getCatId()))
                .map(category -> {
                    category.setChildren(getChildren(category, all));
                    return category;
                })
                .sorted((m1, m2) -> {
                    long s1 = m1.getSort() == null ? 0 : m1.getSort();
                    long s2 = m2.getSort() == null ? 0 : m2.getSort();
                    return Long.compare(s1, s2);
                })
                .collect(Collectors.toList());
    }
}
