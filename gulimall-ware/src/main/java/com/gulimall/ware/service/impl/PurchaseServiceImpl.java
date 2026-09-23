package com.gulimall.ware.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.spring.service.impl.ServiceImpl;
import com.gulimall.common.constant.WareConstant;
import com.gulimall.common.core.page.PageDomain;
import com.gulimall.common.core.page.TableSupport;
import com.gulimall.ware.domain.PurchaseDetail;
import com.gulimall.ware.service.IPurchaseDetailService;
import com.gulimall.ware.service.IWareSkuService;
import com.gulimall.ware.vo.MergeVo;
import com.gulimall.ware.vo.PurchaseDoneVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gulimall.ware.mapper.PurchaseMapper;
import com.gulimall.ware.domain.Purchase;
import com.gulimall.ware.service.IPurchaseService;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 【请填写功能名称】Service业务层处理
 *
 * @author jdjdjd
 * @date 2026-09-22
 */
@Service
public class PurchaseServiceImpl extends ServiceImpl<PurchaseMapper, Purchase> implements IPurchaseService {

    @Autowired
    IPurchaseDetailService purchaseDetailService;

    @Autowired
    IWareSkuService wareSkuService;
    @Override
    public IPage<Purchase> unreceiveList() {
        PageDomain pageDomain = TableSupport.buildPageRequest();
        Page<Purchase> page = new Page<>(pageDomain.getPageNum(), pageDomain.getPageSize());
        QueryWrapper<Purchase> wrapper = new QueryWrapper<>();

        wrapper.eq("status", WareConstant.PurchaseStatusEnum.CREATED.getCode())
                .or().eq("status",WareConstant.PurchaseStatusEnum.ASSIGNED.getCode());
        return this.page(page, wrapper);
    }

    @Transactional
    @Override
    public int merge(MergeVo vo) {
        Long purchaseId = vo.getPurchaseId();
        if(purchaseId == null){
            //新建采购单
            Purchase purchase = new Purchase();
            purchase.setCreateTime(LocalDateTime.now());
            purchase.setUpdateTime(LocalDateTime.now());
            purchase.setStatus((long) WareConstant.PurchaseStatusEnum.CREATED.getCode());
            this.save(purchase);
            purchaseId = purchase.getId();
        }
        Long finalPurchaseId = purchaseId;
        List<PurchaseDetail> list = vo.getItems().stream().map(id -> {
            PurchaseDetail purchaseDetail = purchaseDetailService.getById(id);

            if (purchaseDetail.getStatus() != null &&
                    (purchaseDetail.getStatus() == WareConstant.PurchaseDetailStatusEnum.ASSIGNED.getCode() ||
                     purchaseDetail.getStatus() == WareConstant.PurchaseDetailStatusEnum.CREATED.getCode())){
                purchaseDetail.setId(id);
                purchaseDetail.setPurchaseId(finalPurchaseId);
                purchaseDetail.setStatus((long) WareConstant.PurchaseDetailStatusEnum.ASSIGNED.getCode());
                return purchaseDetail;
            }else{
                return null;
            }
        }).toList();
        purchaseDetailService.updateBatchById(list);

        Purchase byId = this.getById(purchaseId);
        byId.setUpdateTime(LocalDateTime.now());
        this.updateById(byId);

        return 1;
    }

    @Override
    public int received(List<Long> purchaseIds) {
        //确认采购单状态为新建或已分配
        List<Purchase> purchases = this.listByIds(purchaseIds);
        List<Purchase> purchaseList = purchases.stream().filter(p -> {
            if (p.getStatus() != null &&
                    (p.getStatus() == WareConstant.PurchaseStatusEnum.ASSIGNED.getCode() ||
                    p.getStatus() == WareConstant.PurchaseStatusEnum.CREATED.getCode())){
                return true;
            } else {
                return false;
            }
        }).map(purchase -> {
            //修改采购单的状态与时间
            purchase.setStatus((long) WareConstant.PurchaseStatusEnum.RECEIVE.getCode());
            purchase.setUpdateTime(LocalDateTime.now());
            return purchase;
        }).toList();
        this.updateBatchById(purchaseList);
        //修改采购需求的状态的时间
        List<PurchaseDetail> detailByPurchase = purchaseDetailService.getDetailByPurchaseIds(purchaseIds);
        List<PurchaseDetail> list = detailByPurchase.stream().map(purchaseDetail -> {
            purchaseDetail.setStatus((long) WareConstant.PurchaseDetailStatusEnum.BUYING.getCode());
            return purchaseDetail;
        }).toList();
        purchaseDetailService.updateBatchById(list);
        return 1;
    }

    @Override
    public int done(PurchaseDoneVo purchaseDoneVo) {
        Long purchaseId = purchaseDoneVo.getId();
        //修改采购项状态
        //采购项是否完全的判断
        AtomicBoolean flag = new AtomicBoolean(true);
        List<PurchaseDetail> detailList = purchaseDoneVo.getItems().stream().map(item -> {
            PurchaseDetail purchaseDetail = new PurchaseDetail();

            //TODO PurchaseDetail缺少reason、应采购、实采购字段 && 当不小心少写一个后也成功了，应该失败
            if (WareConstant.PurchaseDetailStatusEnum.FINISH.getCode() != item.getStatus()) {
                flag.set(false);
            }else{
                PurchaseDetail detail = purchaseDetailService.getById(item.getItemId());
                //将成功的入库 ware_sku
                wareSkuService.updateFinishedStock(detail.getSkuId(),detail.getWareId(),detail.getSkuNum());
            }
            purchaseDetail.setId(item.getItemId());
            purchaseDetail.setPurchaseId(purchaseId);
            purchaseDetail.setStatus(Long.valueOf(item.getStatus()));

            return purchaseDetail;
        }).toList();
        purchaseDetailService.updateBatchById(detailList);
        //修改采购单状态---需要根据采购项状态决定，全部成功才是完成，有失败的为异常
        Purchase purchase = new Purchase();
        if (flag.get()) {
            purchase.setStatus((long) WareConstant.PurchaseStatusEnum.FINISH.getCode());
        }else{
            purchase.setStatus((long) WareConstant.PurchaseStatusEnum.HASERROR.getCode());
        }
        purchase.setId(purchaseId);
        this.updateById(purchase);

        return 1;
    }
    // 单表 CRUD 由 ServiceImpl 提供，如有自定义业务方法可在此处扩展
}