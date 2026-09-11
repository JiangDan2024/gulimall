package com.gulimall.product.domain;

import com.gulimall.common.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import java.util.List;
import com.gulimall.common.core.domain.BaseEntity;

/**
 * 【请填写功能名称】对象 pms_attr_attrgroup_relation
 *
 * @author jiangdan
 * @date 2026-09-12
 */
@Getter
@Setter
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