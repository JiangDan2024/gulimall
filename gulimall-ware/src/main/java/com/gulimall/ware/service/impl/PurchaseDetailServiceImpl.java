package com.gulimall.ware.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.spring.service.impl.ServiceImpl;
import com.gulimall.common.core.page.PageDomain;
import com.gulimall.common.core.page.TableSupport;
import com.gulimall.common.utils.StringUtils;
import org.springframework.stereotype.Service;
import com.gulimall.ware.mapper.PurchaseDetailMapper;
import com.gulimall.ware.domain.PurchaseDetail;
import com.gulimall.ware.service.IPurchaseDetailService;

import java.util.ArrayList;
import java.util.List;

/**
 * 【请填写功能名称】Service业务层处理
 *
 * @author jdjdjd
 * @date 2026-09-22
 */
@Service
public class PurchaseDetailServiceImpl extends ServiceImpl<PurchaseDetailMapper, PurchaseDetail> implements IPurchaseDetailService {
    @Override
    public IPage<PurchaseDetail> pageQuery(PurchaseDetail purchaseDetail) {
        PageDomain pageDomain = TableSupport.buildPageRequest();
        Page<PurchaseDetail> page = new Page<>(pageDomain.getPageNum(), pageDomain.getPageSize());
        QueryWrapper<PurchaseDetail> wrapper = new QueryWrapper<>();
        String key = purchaseDetail.getKey();
        if(StringUtils.isNotEmpty(key)){
            wrapper.and(w->{
               w.eq("id",key)
                       .or().eq("purchase_id",key)
                       .or().eq("sku_id",key);
            });
        }
        if(purchaseDetail.getStatus()!=null){
            wrapper.eq("status",purchaseDetail.getStatus());
        }
        if(purchaseDetail.getWareId()!=null){
            wrapper.eq("ware_id",purchaseDetail.getWareId());
        }
        return this.page(page, wrapper);
    }

    @Override
    public List<PurchaseDetail> getDetailByPurchaseIds(List<Long> purchaseIds) {
        List<PurchaseDetail> purchaseDetails = new ArrayList<>();
        purchaseIds.forEach(id -> {
            QueryWrapper<PurchaseDetail> wrapper = new QueryWrapper<>();
            wrapper.eq("purchase_id", id);
            purchaseDetails.addAll(this.list(wrapper));
        });
        return purchaseDetails;
    }
    // 单表 CRUD 由 ServiceImpl 提供，如有自定义业务方法可在此处扩展
}