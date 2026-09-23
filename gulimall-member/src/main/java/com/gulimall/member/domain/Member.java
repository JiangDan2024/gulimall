package com.gulimall.member.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.gulimall.common.annotation.Excel;
import com.gulimall.common.core.domain.BaseEntity;

/**
 * 【请填写功能名称】对象 ums_member
 *
 * @author jdjdjd
 * @date 2026-09-20
 */
        @Data
        @EqualsAndHashCode(callSuper = true)
        @TableName("ums_member")
        public class Member extends BaseEntity
        {
        private static final long serialVersionUID = 1L;

            /** id */
        @TableId(value = "id", type = IdType.AUTO)
    private Long id;

            /** 会员等级id */
            @Excel(name = "会员等级id")
    private Long levelId;

            /** 用户名 */
            @Excel(name = "用户名")
    private String username;

            /** 密码 */
            @Excel(name = "密码")
    private String password;

            /** 昵称 */
            @Excel(name = "昵称")
    private String nickname;

            /** 手机号码 */
            @Excel(name = "手机号码")
    private String mobile;

            /** 邮箱 */
            @Excel(name = "邮箱")
    private String email;

            /** 头像 */
            @Excel(name = "头像")
    private String header;

            /** 性别 */
            @Excel(name = "性别")
    private Long gender;

            /** 生日 */
            @JsonFormat(pattern = "yyyy-MM-dd")
            @Excel(name = "生日", width = 30, dateFormat = "yyyy-MM-dd")
    private Date birth;

            /** 所在城市 */
            @Excel(name = "所在城市")
    private String city;

            /** 职业 */
            @Excel(name = "职业")
    private String job;

            /** 个性签名 */
            @Excel(name = "个性签名")
    private String sign;

            /** 用户来源 */
            @Excel(name = "用户来源")
    private Long sourceType;

            /** 积分 */
            @Excel(name = "积分")
    private Long integration;

            /** 成长值 */
            @Excel(name = "成长值")
    private Long growth;

            /** 启用状态 */
            @Excel(name = "启用状态")
    private Long status;

            /** 注册时间 */
    private Date createTime;

        }