package com.gulimall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.spring.service.impl.ServiceImpl;
import com.gulimall.common.constant.ProductConstant;
import com.gulimall.common.core.page.PageDomain;
import com.gulimall.common.utils.StringUtils;
import com.gulimall.product.domain.Attr;
import com.gulimall.product.domain.AttrAttrgroupRelation;
import com.gulimall.product.mapper.AttrAttrgroupRelationMapper;
import com.gulimall.product.mapper.AttrMapper;
import com.gulimall.product.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gulimall.product.mapper.AttrGroupMapper;
import com.gulimall.product.domain.AttrGroup;
import com.gulimall.product.service.IAttrGroupService;

import java.util.List;

/**
 * 【请填写功能名称】Service业务层处理
 *
 * @author jdjdjd
 * @date 2026-09-16
 */
@Service
public class AttrGroupServiceImpl extends ServiceImpl<AttrGroupMapper, AttrGroup> implements IAttrGroupService {

    @Autowired
    private ICategoryService iCategoryService;
    @Autowired
    private AttrAttrgroupRelationMapper attrAttrgroupRelationMapper;
    @Autowired
    private AttrMapper attrMapper;

//    @Override
//    public IPage<AttrGroup> pageAddPath(Page<AttrGroup> page, QueryWrapper<AttrGroup> wrapper) {
//        Page<AttrGroup> attrGroupPage = baseMapper.selectPage(page, wrapper);
//        List<AttrGroup> records = attrGroupPage.getRecords();
//        records.forEach(attrGroup -> {
//            Long[] path = iCategoryService.findCategoryPath(attrGroup.getCatelogId());
//            attrGroup.setCatelogPath(path);
//        });
//        return attrGroupPage.setRecords(records);
//    }

    @Override
    public AttrGroup getInfo(Long attrGroupId) {
        AttrGroup byId = this.getById(attrGroupId);
        Long[] path = iCategoryService.findCategoryPath(byId.getCatelogId());
        byId.setCatelogPath(path);
        return byId;
    }

    @Override
    public IPage<AttrGroup> queryPage(PageDomain pageDomain, AttrGroup attrGroup) {
        Page<AttrGroup> page = new Page<>(pageDomain.getPageNum(), pageDomain.getPageSize());
        QueryWrapper<AttrGroup> wrapper = new QueryWrapper<>();
        if(StringUtils.isNotEmpty(attrGroup.getKey())){
            wrapper.eq("attr_group_id",attrGroup.getKey())
                    .or().like("attr_group_name",attrGroup.getKey())
                    .or().like("descript",attrGroup.getKey());
        }
        if(attrGroup.getCatelogId()!=null){
            wrapper.eq("catelog_id",attrGroup.getCatelogId());
        }

        return this.page(page, wrapper);
    }

    @Override
    public IPage<Attr> noAttrRelation(PageDomain pageDomain, AttrGroup attrGroup, Long groupId) {
        //找到当前分组的分类
        AttrGroup group = this.getById(groupId);
        //找到当前分类的其他分组
        List<AttrGroup> otherGroup = this.list(new QueryWrapper<AttrGroup>().eq("catelog_id", group.getCatelogId()));
        List<Long> otherGroupList = otherGroup.stream().map(AttrGroup::getAttrGroupId).toList();
        //剔除与其他分组关联的属性
        List<AttrAttrgroupRelation> attrs = attrAttrgroupRelationMapper.selectList(new QueryWrapper<AttrAttrgroupRelation>().in("attr_group_id", otherGroupList));
        List<Long> attrIdList = attrs.stream().map(AttrAttrgroupRelation::getAttrId).toList();
        //构造分页对象
        Page<Attr> page = new Page<>(pageDomain.getPageNum(), pageDomain.getPageSize());
        QueryWrapper<Attr> wrapper = new QueryWrapper<Attr>()
                .eq("attr_type", ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode())
                .eq("catelog_id",group.getCatelogId());
        //实现模糊查询
        if(!attrIdList.isEmpty()){
            wrapper.notIn("attr_id",attrIdList);
        }
        if(StringUtils.isNotEmpty(attrGroup.getKey())){
            wrapper.and(w->{
                w.eq("attr_id",attrGroup.getKey())
                        .or().like("attr_name",attrGroup.getKey());
            });
        }
        return attrMapper.selectPage(page, wrapper);
    }
    // 单表 CRUD 由 ServiceImpl 提供，如有自定义业务方法可在此处扩展
}