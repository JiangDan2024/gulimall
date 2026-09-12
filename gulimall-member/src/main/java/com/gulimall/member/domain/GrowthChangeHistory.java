package com.gulimall.member.domain;

import java.util.Date;
import com.gulimall.common.annotation.Excel;
import lombok.Getter;
import lombok.Setter;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.IdType;
import com.gulimall.common.core.domain.BaseEntity;

/**
 * 【请填写功能名称】对象 ums_growth_change_history
 *
 * @author jiangdan
 * @date 2026-09-12
 */
@Getter
@Setter
@TableName("ums_growth_change_history")
public class GrowthChangeHistory extends BaseEntity
{
    private static final long serialVersionUID = 1L;

        /** id */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

        /** member_id */
        @Excel(name = "member_id")
    private Long memberId;

        /** create_time */
    private Date createTime;

        /** 改变的值（正负计数） */
        @Excel(name = "改变的值", readConverterExp = "正=负计数")
    private Long changeCount;

        /** 备注 */
        @Excel(name = "备注")
    private String note;

        /** 积分来源[0-购物，1-管理员修改] */
        @Excel(name = "积分来源[0-购物，1-管理员修改]")
    private Long sourceType;

}