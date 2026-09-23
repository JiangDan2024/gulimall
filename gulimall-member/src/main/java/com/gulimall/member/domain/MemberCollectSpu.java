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
 * 【请填写功能名称】对象 ums_member_collect_spu
 *
 * @author jdjdjd
 * @date 2026-09-20
 */
        @Data
        @EqualsAndHashCode(callSuper = true)
        @TableName("ums_member_collect_spu")
        public class MemberCollectSpu extends BaseEntity
        {
        private static final long serialVersionUID = 1L;

            /** id */
        @TableId(value = "id", type = IdType.AUTO)
    private Long id;

            /** 会员id */
            @Excel(name = "会员id")
    private Long memberId;

            /** spu_id */
            @Excel(name = "spu_id")
    private Long spuId;

            /** spu_name */
            @Excel(name = "spu_name")
    private String spuName;

            /** spu_img */
            @Excel(name = "spu_img")
    private String spuImg;

            /** create_time */
    private Date createTime;

        }