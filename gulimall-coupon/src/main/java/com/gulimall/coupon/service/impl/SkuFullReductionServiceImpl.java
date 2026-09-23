package com.gulimall.coupon.service.impl;

import com.baomidou.mybatisplus.spring.service.impl.ServiceImpl;
import com.gulimall.common.to.SkuReductionTo;
import com.gulimall.common.utils.bean.BeanUtils;
import com.gulimall.coupon.domain.MemberPrice;
import com.gulimall.coupon.domain.SkuLadder;
import com.gulimall.coupon.service.IMemberPriceService;
import com.gulimall.coupon.service.ISkuLadderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gulimall.coupon.mapper.SkuFullReductionMapper;
import com.gulimall.coupon.domain.SkuFullReduction;
import com.gulimall.coupon.service.ISkuFullReductionService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 【请填写功能名称】Service业务层处理
 *
 * @author jdjdjd
 * @date 2026-09-22
 */
@Service
public class SkuFullReductionServiceImpl extends ServiceImpl<SkuFullReductionMapper, SkuFullReduction> implements ISkuFullReductionService {
    @Autowired
    ISkuLadderService skuLadderService;
    @Autowired
    IMemberPriceService memberPriceService;

    @Override
    public int addSkuCoupon(SkuReductionTo skuReductionTo) {
        //sku_full_reduction 满减信息
        SkuFullReduction skuFullReduction = new SkuFullReduction();
        BigDecimal fullCount = skuReductionTo.getFullCount();
        BigDecimal reducePrice = skuReductionTo.getReducePrice();

        // 先判空，再判断是否大于0
        boolean hasFullCount = fullCount != null && fullCount.compareTo(BigDecimal.ZERO) > 0;
        boolean hasReducePrice = reducePrice != null && reducePrice.compareTo(BigDecimal.ZERO) > 0;

        if (hasFullCount || hasReducePrice) {
            BeanUtils.copyProperties(skuReductionTo, skuFullReduction);
            this.save(skuFullReduction);
        }

        //sku_ladder 阶梯价格
        SkuLadder skuLadder = new SkuLadder();
        BigDecimal discount = skuReductionTo.getDiscount();
        boolean hasDiscount = discount != null && discount.compareTo(BigDecimal.ZERO)>0;
        if(hasDiscount){
            BeanUtils.copyProperties(skuReductionTo, skuLadder);
            //TODO 下订单时计算折后价Price
            skuLadder.setAddOther(skuReductionTo.getCountStatus());
            skuLadderService.save(skuLadder);
        }
        //member_price 会员价
        List<com.gulimall.common.to.MemberPrice> memberPrices = skuReductionTo.getMemberPrice();
        List<MemberPrice> memberPriceList = memberPrices.stream()
                .filter(obj -> obj.getPrice() != null && obj.getPrice().compareTo(BigDecimal.ZERO) > 0)
                .map(obj -> {
            MemberPrice memberPrice = new MemberPrice();
            memberPrice.setSkuId(skuReductionTo.getSkuId());
            memberPrice.setMemberLevelId(obj.getId());
            memberPrice.setMemberLevelName(obj.getName());
            memberPrice.setMemberPrice(obj.getPrice());
            return memberPrice;
        }).toList();
        memberPriceService.saveBatch(memberPriceList);
        return 1;
    }
    // 单表 CRUD 由 ServiceImpl 提供，如有自定义业务方法可在此处扩展
}