package com.gulimall.member.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.gulimall.common.annotation.Excel;
import com.gulimall.common.core.domain.BaseEntity;

import java.util.Date;

/**
 * 【请填写功能名称】对象 ums_integration_change_history
 *
 * @author jdjdjd
 * @date 2026-09-20
 */
        @Data
        @EqualsAndHashCode(callSuper = true)
        @TableName("ums_integration_change_history")
        public class IntegrationChangeHistory extends BaseEntity
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

            /** 变化的值 */
            @Excel(name = "变化的值")
    private Long changeCount;

            /** 备注 */
            @Excel(name = "备注")
    private String note;

            /** 来源[0-&gt;购物；1-&gt;管理员修改;2-&gt;活动] */
            @Excel(name = "来源[0-&gt;购物；1-&gt;管理员修改;2-&gt;活动]")
    private Long sourceTyoe;

        }