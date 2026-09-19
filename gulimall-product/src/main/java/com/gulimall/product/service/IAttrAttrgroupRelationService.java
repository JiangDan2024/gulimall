package com.gulimall.product.service;

import com.baomidou.mybatisplus.spring.service.IService;
import com.gulimall.product.domain.AttrAttrgroupRelation;

import java.util.List;

/**
 * 【请填写功能名称】Service接口
 *
 * @author jdjdjd
 * @date 2026-09-19
 */
public interface IAttrAttrgroupRelationService extends IService<AttrAttrgroupRelation> {
    int relationDelete(List<AttrAttrgroupRelation> list);

    boolean addRelation(List<AttrAttrgroupRelation> relationList);
    // 单表 CRUD 由 IService 提供，如有自定义业务方法可在此处扩展
}