package com.gulimall.member.domain;

import com.gulimall.common.annotation.Excel;
import lombok.Getter;
import lombok.Setter;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.IdType;
import com.gulimall.common.core.domain.BaseEntity;

/**
 * 【请填写功能名称】对象 ums_member_receive_address
 *
 * @author jiangdan
 * @date 2026-09-12
 */
@Getter
@Setter
@TableName("ums_member_receive_address")
public class MemberReceiveAddress extends BaseEntity
{
    private static final long serialVersionUID = 1L;

        /** id */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

        /** member_id */
        @Excel(name = "member_id")
    private Long memberId;

        /** 收货人姓名 */
        @Excel(name = "收货人姓名")
    private String name;

        /** 电话 */
        @Excel(name = "电话")
    private String phone;

        /** 邮政编码 */
        @Excel(name = "邮政编码")
    private String postCode;

        /** 省份/直辖市 */
        @Excel(name = "省份/直辖市")
    private String province;

        /** 城市 */
        @Excel(name = "城市")
    private String city;

        /** 区 */
        @Excel(name = "区")
    private String region;

        /** 详细地址(街道) */
        @Excel(name = "详细地址(街道)")
    private String detailAddress;

        /** 省市区代码 */
        @Excel(name = "省市区代码")
    private String areacode;

        /** 是否默认 */
        @Excel(name = "是否默认")
    private Integer defaultStatus;

}