package com.gulimall.product.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.gulimall.common.annotation.Excel;
import com.gulimall.common.core.domain.BaseEntity;

/**
 * 【请填写功能名称】对象 pms_attr_group
 *
 * @author jdjdjd
 * @date 2026-09-16
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("pms_attr_group")
public class AttrGroup extends BaseEntity
{
private static final long serialVersionUID = 1L;

    /** 分组id */
    @TableId(value = "attr_group_id", type = IdType.AUTO)
    private Long attrGroupId;

    /** 组名 */
    @Excel(name = "组名")
    private String attrGroupName;

    /** 排序 */
    @Excel(name = "排序")
    private Long sort;

    /** 描述 */
    @Excel(name = "描述")
    private String descript;

    /** 组图标 */
    @Excel(name = "组图标")
    private String icon;

    /** 所属分类id */
    @Excel(name = "所属分类id")
    private Long catelogId;

}