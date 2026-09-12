package com.gulimall.ware.domain;

import com.gulimall.common.annotation.Excel;
import lombok.Getter;
import lombok.Setter;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.IdType;
import com.gulimall.common.core.domain.BaseEntity;

/**
 * 【请填写功能名称】对象 wms_ware_info
 *
 * @author jiangdan
 * @date 2026-09-12
 */
@Getter
@Setter
@TableName("wms_ware_info")
public class WareInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

        /** id */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

        /** 仓库名 */
        @Excel(name = "仓库名")
    private String name;

        /** 仓库地址 */
        @Excel(name = "仓库地址")
    private String address;

        /** 区域编码 */
        @Excel(name = "区域编码")
    private String areacode;

}