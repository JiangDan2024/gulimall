package com.gulimall.product.service.impl;

import com.baomidou.mybatisplus.spring.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gulimall.product.mapper.AttrAttrgroupRelationMapper;
import com.gulimall.product.domain.AttrAttrgroupRelation;
import com.gulimall.product.service.IAttrAttrgroupRelationService;

import java.util.List;

/**
 * 【请填写功能名称】Service业务层处理
 *
 * @author jdjdjd
 * @date 2026-09-19
 */
@Service
public class AttrAttrgroupRelationServiceImpl extends ServiceImpl<AttrAttrgroupRelationMapper, AttrAttrgroupRelation> implements IAttrAttrgroupRelationService {

    @Autowired
    AttrAttrgroupRelationMapper attrAttrgroupRelationMapper;
    @Override
    public int relationDelete(List<AttrAttrgroupRelation> list) {
        return attrAttrgroupRelationMapper.relationDelete(list);
    }

    public boolean addRelation(List<AttrAttrgroupRelation> relationList) {
        return this.saveBatch(relationList);
    }
    // 单表 CRUD 由 ServiceImpl 提供，如有自定义业务方法可在此处扩展
}