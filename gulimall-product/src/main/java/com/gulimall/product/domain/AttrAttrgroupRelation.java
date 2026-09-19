package com.gulimall.product.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.gulimall.common.annotation.Excel;
import com.gulimall.common.core.domain.BaseEntity;

/**
 * 【请填写功能名称】对象 pms_attr_attrgroup_relation
 *
 * @author jdjdjd
 * @date 2026-09-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("pms_attr_attrgroup_relation")
public class AttrAttrgroupRelation extends BaseEntity
{
private static final long serialVersionUID = 1L;

    /** id */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /** 属性id */
    @Excel(name = "属性id")
    private Long attrId;

    /** 属性分组id */
    @Excel(name = "属性分组id")
    private Long attrGroupId;

    /** 属性组内排序 */
    @Excel(name = "属性组内排序")
    private Long attrSort;

}