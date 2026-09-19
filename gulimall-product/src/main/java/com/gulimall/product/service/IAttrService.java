package com.gulimall.product.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.spring.service.IService;
import com.gulimall.common.core.page.PageDomain;
import com.gulimall.product.domain.Attr;
import com.gulimall.product.vo.AttrGroupCateVo;
import com.gulimall.product.vo.AttrVo;

import java.util.List;

/**
 * 【请填写功能名称】Service接口
 *
 * @author jdjdjd
 * @date 2026-09-19
 */
public interface IAttrService extends IService<Attr> {
    int saveAttr(AttrVo attr);

    IPage<AttrGroupCateVo> baseList(Attr attr, PageDomain pageDomain, String type);

    AttrGroupCateVo getAttr(Long attrId);

    boolean updateAttr(AttrVo attr);

    List<Attr> queryGroupRelation(Long groupId);
    // 单表 CRUD 由 IService 提供，如有自定义业务方法可在此处扩展
}