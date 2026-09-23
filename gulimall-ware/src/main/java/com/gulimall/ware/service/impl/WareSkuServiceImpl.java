package com.gulimall.ware.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.spring.service.impl.ServiceImpl;
import com.gulimall.common.core.domain.AjaxResult;
import com.gulimall.ware.feign.ProductFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gulimall.ware.mapper.WareSkuMapper;
import com.gulimall.ware.domain.WareSku;
import com.gulimall.ware.service.IWareSkuService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 【请填写功能名称】Service业务层处理
 *
 * @author jdjdjd
 * @date 2026-09-22
 */
@Service
public class WareSkuServiceImpl extends ServiceImpl<WareSkuMapper, WareSku> implements IWareSkuService {
    @Autowired
    WareSkuMapper  wareSkuMapper;

    @Autowired
    ProductFeign productFeign;
    @Transactional
    @Override
    public void updateFinishedStock(Long skuId, Long wareId, Long skuNum) {
        //如果skuid+wareid对应的商品库存已存在，则更新操作、否则新增
        QueryWrapper<WareSku> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("sku_id",skuId).eq("ware_id",wareId);
        List<WareSku> skuList = this.list(queryWrapper);
        if(skuList==null||skuList.size()==0){
            //新增操作
            //获取sku_name等信息 远程
            WareSku wareSku = new WareSku();
            AjaxResult skuInfo = productFeign.getSkuInfo(skuId);
            if(skuInfo.isSuccess()){
                try{
                    Map<String,Object> data = (skuInfo.get("data")!=null)?(Map<String,Object>)skuInfo.get("data"):null;
                    wareSku.setSkuName(data.get("skuName").toString());
                }catch (Exception e){}

            }
            wareSku.setSkuId(skuId);
            wareSku.setWareId(wareId);
            wareSku.setStock(skuNum);
            wareSku.setStockLocked(0L);
            this.save(wareSku);
        }else{
            //更新操作
            wareSkuMapper.updateFinishedStock(skuId,wareId,skuNum);
        }
    }
    // 单表 CRUD 由 ServiceImpl 提供，如有自定义业务方法可在此处扩展
}