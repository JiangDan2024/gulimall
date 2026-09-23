package com.gulimall.product.vo;

import com.gulimall.common.annotation.Excel;
import com.gulimall.product.domain.Attr;
import lombok.Data;

import java.util.List;

@Data
public class AttrGroupWithAttrVo {
    /** 分组id */
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

    private Long[] catelogPath;

    private List<Attr> attrs;
}
