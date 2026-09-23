package com.gulimall.member.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.gulimall.common.annotation.Excel;
import com.gulimall.common.core.domain.BaseEntity;

/**
 * 【请填写功能名称】对象 ums_member_collect_subject
 *
 * @author jdjdjd
 * @date 2026-09-20
 */
        @Data
        @EqualsAndHashCode(callSuper = true)
        @TableName("ums_member_collect_subject")
        public class MemberCollectSubject extends BaseEntity
        {
        private static final long serialVersionUID = 1L;

            /** id */
        @TableId(value = "id", type = IdType.AUTO)
    private Long id;

            /** subject_id */
            @Excel(name = "subject_id")
    private Long subjectId;

            /** subject_name */
            @Excel(name = "subject_name")
    private String subjectName;

            /** subject_img */
            @Excel(name = "subject_img")
    private String subjectImg;

            /** 活动url */
            @Excel(name = "活动url")
    private String subjectUrll;

        }