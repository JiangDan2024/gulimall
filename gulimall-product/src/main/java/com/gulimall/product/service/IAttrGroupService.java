package com.gulimall.product.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.spring.service.IService;
import com.gulimall.common.core.page.PageDomain;
import com.gulimall.product.domain.Attr;
import com.gulimall.product.domain.AttrGroup;

/**
 * 【请填写功能名称】Service接口
 *
 * @author jdjdjd
 * @date 2026-09-16
 */
public interface IAttrGroupService extends IService<AttrGroup> {

    AttrGroup getInfo(Long attrGroupId);

    IPage<AttrGroup> queryPage(PageDomain pageDomain, AttrGroup attrGroup);

    IPage<Attr> noAttrRelation(PageDomain pageDomain, AttrGroup attrGroup, Long groupId);
    // 单表 CRUD 由 IService 提供，如有自定义业务方法可在此处扩展
}