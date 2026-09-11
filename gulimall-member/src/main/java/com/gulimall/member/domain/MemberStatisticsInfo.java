package com.gulimall.member.domain;

import java.math.BigDecimal;
import com.gulimall.common.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import java.util.List;
import com.gulimall.common.core.domain.BaseEntity;

/**
 * 【请填写功能名称】对象 ums_member_statistics_info
 *
 * @author jiangdan
 * @date 2026-09-11
 */
@Getter
@Setter
@TableName("ums_member_statistics_info")
public class MemberStatisticsInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

        /** id */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

        /** 会员id */
        @Excel(name = "会员id")
    private Long memberId;

        /** 累计消费金额 */
        @Excel(name = "累计消费金额")
    private BigDecimal consumeAmount;

        /** 累计优惠金额 */
        @Excel(name = "累计优惠金额")
    private BigDecimal couponAmount;

        /** 订单数量 */
        @Excel(name = "订单数量")
    private Long orderCount;

        /** 优惠券数量 */
        @Excel(name = "优惠券数量")
    private Long couponCount;

        /** 评价数 */
        @Excel(name = "评价数")
    private Long commentCount;

        /** 退货数量 */
        @Excel(name = "退货数量")
    private Long returnOrderCount;

        /** 登录次数 */
        @Excel(name = "登录次数")
    private Long loginCount;

        /** 关注数量 */
        @Excel(name = "关注数量")
    private Long attendCount;

        /** 粉丝数量 */
        @Excel(name = "粉丝数量")
    private Long fansCount;

        /** 收藏的商品数量 */
        @Excel(name = "收藏的商品数量")
    private Long collectProductCount;

        /** 收藏的专题活动数量 */
        @Excel(name = "收藏的专题活动数量")
    private Long collectSubjectCount;

        /** 收藏的评论数量 */
        @Excel(name = "收藏的评论数量")
    private Long collectCommentCount;

        /** 邀请的朋友数量 */
        @Excel(name = "邀请的朋友数量")
    private Long inviteFriendCount;

}