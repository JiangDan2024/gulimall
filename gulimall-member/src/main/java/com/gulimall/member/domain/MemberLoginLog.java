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
 * 【请填写功能名称】对象 ums_member_login_log
 *
 * @author jdjdjd
 * @date 2026-09-20
 */
        @Data
        @EqualsAndHashCode(callSuper = true)
        @TableName("ums_member_login_log")
        public class MemberLoginLog extends BaseEntity
        {
        private static final long serialVersionUID = 1L;

            /** id */
        @TableId(value = "id", type = IdType.AUTO)
    private Long id;

            /** member_id */
            @Excel(name = "member_id")
    private Long memberId;

            /** 创建时间 */
    private Date createTime;

            /** ip */
            @Excel(name = "ip")
    private String ip;

            /** city */
            @Excel(name = "city")
    private String city;

            /** 登录类型[1-web，2-app] */
            @Excel(name = "登录类型[1-web，2-app]")
    private Integer loginType;

        }