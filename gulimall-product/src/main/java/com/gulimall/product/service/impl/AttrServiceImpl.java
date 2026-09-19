package com.gulimall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.spring.service.impl.ServiceImpl;
import com.gulimall.common.constant.ProductConstant;
import com.gulimall.common.core.page.PageDomain;
import com.gulimall.common.utils.StringUtils;
import com.gulimall.common.utils.bean.BeanUtils;
import com.gulimall.product.domain.AttrAttrgroupRelation;
import com.gulimall.product.domain.AttrGroup;
import com.gulimall.product.domain.Category;
import com.gulimall.product.mapper.AttrAttrgroupRelationMapper;
import com.gulimall.product.mapper.AttrGroupMapper;
import com.gulimall.product.mapper.CategoryMapper;
import com.gulimall.product.service.ICategoryService;
import com.gulimall.product.vo.AttrGroupCateVo;
import com.gulimall.product.vo.AttrVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gulimall.product.mapper.AttrMapper;
import com.gulimall.product.domain.Attr;
import com.gulimall.product.service.IAttrService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 【请填写功能名称】Service业务层处理
 *
 * @author jdjdjd
 * @date 2026-09-19
 */
@Service
public class AttrServiceImpl extends ServiceImpl<AttrMapper, Attr> implements IAttrService {

    @Autowired
    AttrAttrgroupRelationMapper  attrAttrgroupRelationMapper;

    @Autowired
    AttrGroupMapper attrGroupMapper;

    @Autowired
    CategoryMapper categoryMapper;

    @Autowired
    ICategoryService  categoryService;
    @Transactional
    @Override
    public int saveAttr(AttrVo attrVo) {
        Attr attr = new Attr();
        BeanUtils.copyProperties(attrVo, attr);
        this.save(attr);
        //保存关联关系
        if(attr.getAttrType() == ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode()){
            AttrAttrgroupRelation attrAttrgroupRelation = new AttrAttrgroupRelation();
            attrAttrgroupRelation.setAttrGroupId(attrVo.getAttrGroupId());
            attrAttrgroupRelation.setAttrId(attr.getAttrId());
            attrAttrgroupRelationMapper.insert(attrAttrgroupRelation);
        }
        return 1;
    }

    @Override
    public IPage<AttrGroupCateVo> baseList(Attr attr, PageDomain pageDomain, String type) {
        Page<Attr> page = new Page<>(pageDomain.getPageNum(), pageDomain.getPageSize());
        QueryWrapper<Attr> wrapper = new QueryWrapper<Attr>().eq("attr_type",type.equals("base")?ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode():ProductConstant.AttrEnum.ATTR_TYPE_SALE.getCode());
        if(attr.getCatelogId()!=null){
            wrapper.eq("catelog_id",attr.getCatelogId());
        }
        String key = attr.getKey();
        if(StringUtils.isNotEmpty(key)){
            wrapper.and(w->{
                w.eq("attr_id",key).or().like("attr_name",key);
                    });
        }
        Page<Attr> pageVo = this.page(page, wrapper);

        List<AttrGroupCateVo> attrVos = pageVo.getRecords().stream().map(o -> {
            AttrGroupCateVo vo = new AttrGroupCateVo();
            BeanUtils.copyProperties(o, vo);
            QueryWrapper<AttrAttrgroupRelation> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("attr_id", o.getAttrId());
            //查询分组
            if(type.equals("base")){
                AttrAttrgroupRelation attrAttrgroupRelation = attrAttrgroupRelationMapper.selectOne(queryWrapper);
                if(attrAttrgroupRelation!=null){
                    AttrGroup attrGroup = attrGroupMapper.selectById(attrAttrgroupRelation.getAttrGroupId());
                    vo.setGroupName(attrGroup.getAttrGroupName());
                }
            }
            //查询分类
            Category category = categoryMapper.selectById(o.getCatelogId());
            vo.setCategoryName(category.getName());
            return vo;
        }).collect(Collectors.toList());
        Page<AttrGroupCateVo> pageRes = new Page<>(pageVo.getCurrent(), pageVo.getSize(),pageVo.getTotal());
        pageRes.setRecords(attrVos);
        return pageRes;
    }

    @Transactional
    @Override
    public AttrGroupCateVo getAttr(Long attrId) {
        AttrGroupCateVo vo = new AttrGroupCateVo();
        Attr attr = this.getById(attrId);
        BeanUtils.copyProperties(attr, vo);
        //查询分组信息
        if(attr.getAttrType() == ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode()){
            AttrAttrgroupRelation relation = attrAttrgroupRelationMapper.selectOne(new QueryWrapper<AttrAttrgroupRelation>().eq("attr_id", attrId));
            if(relation!=null){
                AttrGroup attrGroup = attrGroupMapper.selectById(relation.getAttrGroupId());
                vo.setGroupName(attrGroup.getAttrGroupName());
                vo.setAttrGroupId(attrGroup.getAttrGroupId());
            }
        }

        //查询分类信息
        Category category = categoryMapper.selectById(vo.getCatelogId());
        if (category!=null){
            vo.setCategoryName(category.getName());
            vo.setCatelogPath(categoryService.findCategoryPath(vo.getCatelogId()));
        }
//        vo.setGroupName();
//        vo.setCategoryName();
//        vo.setCagelogPath();
        return vo;
    }

    @Transactional
    @Override
    public boolean updateAttr(AttrVo attrVo) {
        Attr attr = new Attr();
        BeanUtils.copyProperties(attrVo, attr);
        this.updateById(attr);
        //更新分组关联表
        if(attr.getAttrType() == ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode()){
            Long count = attrAttrgroupRelationMapper.selectCount(new QueryWrapper<AttrAttrgroupRelation>().eq("attr_id", attr.getAttrId()));
            AttrAttrgroupRelation attrAttrgroupRelation = new AttrAttrgroupRelation();
            attrAttrgroupRelation.setAttrId(attr.getAttrId());
            attrAttrgroupRelation.setAttrGroupId(attrVo.getAttrGroupId());
            if(count>0){
                attrAttrgroupRelationMapper.update(attrAttrgroupRelation,new UpdateWrapper<AttrAttrgroupRelation>().eq("attr_id", attr.getAttrId()));
            }else{
                attrAttrgroupRelationMapper.insert(attrAttrgroupRelation);
            }
        }
        return true;
    }

    @Override
    public List<Attr> queryGroupRelation(Long groupId) {

        List<AttrAttrgroupRelation> attrList = attrAttrgroupRelationMapper.selectList(new QueryWrapper<AttrAttrgroupRelation>().eq("attr_group_id", groupId));
        if(attrList!=null&& !attrList.isEmpty()){
            List<Long> attrIds = attrList.stream().map(AttrAttrgroupRelation::getAttrId).toList();
            return this.listByIds(attrIds);
        }
        return null;
    }
    // 单表 CRUD 由 ServiceImpl 提供，如有自定义业务方法可在此处扩展
}