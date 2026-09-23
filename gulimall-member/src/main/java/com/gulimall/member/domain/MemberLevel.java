package com.gulimall.member.domain;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.gulimall.common.annotation.Excel;
import com.gulimall.common.core.domain.BaseEntity;

/**
 * 【请填写功能名称】对象 ums_member_level
 *
 * @author jdjdjd
 * @date 2026-09-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("ums_member_level")
public class MemberLevel extends BaseEntity
{
private static final long serialVersionUID = 1L;

    /** id */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /** 等级名称 */
    @Excel(name = "等级名称")
    private String name;

    /** 等级需要的成长值 */
    @Excel(name = "等级需要的成长值")
    private Long growthPoint;

    /** 是否为默认等级[0-&gt;不是；1-&gt;是] */
    @Excel(name = "是否为默认等级[0-&gt;不是；1-&gt;是]")
    private Long defaultStatus;

    /** 免运费标准 */
    @Excel(name = "免运费标准")
    private BigDecimal freeFreightPoint;

    /** 每次评价获取的成长值 */
    @Excel(name = "每次评价获取的成长值")
    private Long commentGrowthPoint;

    /** 是否有免邮特权 */
    @Excel(name = "是否有免邮特权")
    private Long priviledgeFreeFreight;

    /** 是否有会员价格特权 */
    @Excel(name = "是否有会员价格特权")
    private Long priviledgeMemberPrice;

    /** 是否有生日特权 */
    @Excel(name = "是否有生日特权")
    private Long priviledgeBirthday;

    /** 备注 */
    @Excel(name = "备注")
    private String note;

}