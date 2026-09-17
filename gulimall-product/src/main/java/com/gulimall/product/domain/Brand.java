package com.gulimall.product.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gulimall.common.valid.AddGroup;
import com.gulimall.common.valid.ListValue;
import com.gulimall.common.valid.UpdateGroup;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.gulimall.common.annotation.Excel;
import com.gulimall.common.core.domain.BaseEntity;
import org.hibernate.validator.constraints.URL;

/**
 * 【请填写功能名称】对象 pms_brand
 *
 * @author jdjdjd
 * @date 2026-09-16
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("pms_brand")
public class Brand extends BaseEntity
{
private static final long serialVersionUID = 1L;

    /** 品牌id */
    @TableId(value = "brand_id", type = IdType.AUTO)
    @NotNull(message = "更新时品牌ID不能为空",groups = {UpdateGroup.class})
    @Null(message = "新增时品牌ID必须为空",groups = {AddGroup.class})
    private Long brandId;

    /** 品牌名 */
    @Excel(name = "品牌名")
    @NotBlank(message = "品牌名不能为空",groups = {AddGroup.class, UpdateGroup.class})
    private String name;

    /** 品牌logo地址 */
    @Excel(name = "品牌logo地址")
    @NotBlank(message = "logo不能为空",groups = {AddGroup.class})
    @URL(message = "logo必须是有效的url地址",groups = {AddGroup.class, UpdateGroup.class})
    private String logo;

    /** 介绍 */
    @Excel(name = "介绍")
    private String descript;

    /** 显示状态[0-不显示；1-显示] */
    @Excel(name = "显示状态[0-不显示；1-显示]")
    @ListValue(val={1,0},groups = {AddGroup.class, UpdateGroup.class})
    private Integer showStatus;

    /** 检索首字母 */
    @Excel(name = "检索首字母")
    @NotBlank(message = "检索首字母不能为空",groups = {AddGroup.class})
    @Pattern(regexp  = "^[a-zA-Z]$",message = "检索首字母必须为一个字母",groups = {AddGroup.class, UpdateGroup.class})
    private String firstLetter;

    /** 排序 */
    @Excel(name = "排序")
    @NotNull(message = "排序不能为空",groups = {AddGroup.class})
    @Min(value = 0,message = "排序为大于0的整数",groups = {AddGroup.class, UpdateGroup.class})
    private Long sort;

}