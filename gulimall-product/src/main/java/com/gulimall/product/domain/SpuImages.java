package com.gulimall.product.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.gulimall.common.annotation.Excel;
import com.gulimall.common.core.domain.BaseEntity;

/**
 * 【请填写功能名称】对象 pms_spu_images
 *
 * @author jdjdjd
 * @date 2026-09-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("pms_spu_images")
public class SpuImages extends BaseEntity
{
private static final long serialVersionUID = 1L;

    /** id */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /** spu_id */
    @Excel(name = "spu_id")
    private Long spuId;

    /** 图片名 */
    @Excel(name = "图片名")
    private String imgName;

    /** 图片地址 */
    @Excel(name = "图片地址")
    private String imgUrl;

    /** 顺序 */
    @Excel(name = "顺序")
    private Long imgSort;

    /** 是否默认图 */
    @Excel(name = "是否默认图")
    private Long defaultImg;

}