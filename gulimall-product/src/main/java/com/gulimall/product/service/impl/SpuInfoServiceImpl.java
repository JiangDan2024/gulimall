package com.gulimall.product.service.impl;

import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.spring.service.impl.ServiceImpl;
import com.gulimall.common.constant.ProductConstant;
import com.gulimall.common.core.page.PageDomain;
import com.gulimall.common.core.page.TableSupport;
import com.gulimall.common.to.MemberPrice;
import com.gulimall.common.to.SkuHasstockVo;
import com.gulimall.common.to.SkuReductionTo;
import com.gulimall.common.to.SpuBoundsTO;
import com.gulimall.common.core.domain.AjaxResult;
import com.gulimall.common.to.es.SkuEsModel;
import com.gulimall.common.utils.StringUtils;
import com.gulimall.product.domain.*;
import com.gulimall.product.domain.Attr;
import com.gulimall.product.feign.SearchFeign;
import com.gulimall.product.feign.SkuWareFeign;
import com.gulimall.product.feign.SpuCouponFeign;
import com.gulimall.product.service.*;
import com.gulimall.product.vo.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gulimall.product.mapper.SpuInfoMapper;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 【请填写功能名称】Service业务层处理
 *
 * @author jdjdjd
 * @date 2026-09-19
 */
@Service
public class SpuInfoServiceImpl extends ServiceImpl<SpuInfoMapper, SpuInfo> implements ISpuInfoService {

    @Autowired
    private ISpuInfoDescService iSpuInfoDescService;
    @Autowired
    private ISpuImagesService iSpuImagesService;
    @Autowired
    private IAttrService iAttrService;
    @Autowired
    private IProductAttrValueService iProductAttrValueService;
    @Autowired
    private ISkuInfoService iSkuInfoService;
    @Autowired
    private ISkuImagesService iSkuImagesService;
    @Autowired
    private ISkuSaleAttrValueService iSkuSaleAttrValueService;
    @Autowired
    private SpuCouponFeign spuCouponFeign;
    @Autowired
    private IBrandService iBrandService;
    @Autowired
    private ICategoryService iCategoryService;
    @Autowired
    private SkuWareFeign skuWareFeign;
    @Autowired
    private SearchFeign searchFeign;
    @Autowired
    private SpuInfoMapper spuInfoMapper;

    public SpuInfoServiceImpl(ISpuInfoDescService iSpuInfoDescService, ISpuImagesService iSpuImagesService, IAttrService iAttrService, IProductAttrValueService iProductAttrValueService, ISkuInfoService iSkuInfoService, ISkuImagesService iSkuImagesService, ISkuSaleAttrValueService iSkuSaleAttrValueService) {
        this.iSpuInfoDescService = iSpuInfoDescService;
        this.iSpuImagesService = iSpuImagesService;
        this.iAttrService = iAttrService;
        this.iProductAttrValueService = iProductAttrValueService;
        this.iSkuInfoService = iSkuInfoService;
        this.iSkuImagesService = iSkuImagesService;
        this.iSkuSaleAttrValueService = iSkuSaleAttrValueService;
    }

    @Transactional
    @Override
    public int saveSpuInfo(SpuSaveVo spuSaveVo) {
        //1.保存spu基本信息（pms_spu_info）
        SpuInfo spuInfo = new SpuInfo();
        BeanUtils.copyProperties(spuSaveVo, spuInfo);
        spuInfo.setUpdateTime(LocalDateTime.now());
        spuInfo.setCreateTime(LocalDateTime.now());
        this.saveBaseSpuInfo(spuInfo);
        //2.保存spu描述图片（pms_spu_info_desc）
        List<String> decript = spuSaveVo.getDecript();
        SpuInfoDesc spuInfoDesc = new SpuInfoDesc();
        spuInfoDesc.setSpuId(spuInfo.getId());
        spuInfoDesc.setDecript(String.join(",", decript));
        iSpuInfoDescService.saveSpuInfoDesc(spuInfoDesc);
        //3.保存spu图片集（pms_spu_images）
        List<String> images = spuSaveVo.getImages();
        List<SpuImages> spuImagesList = images.stream().map(img -> {
            SpuImages image = new SpuImages();
            image.setSpuId(spuInfo.getId());
            image.setImgUrl(img);
            return image;
        }).toList();
        iSpuImagesService.saveSpuInfoImages(spuImagesList);
        //4.保存spu规格参数（pms_product_attr_value）
        List<BaseAttrs> baseAttrs = spuSaveVo.getBaseAttrs();
        List<ProductAttrValue> productAttrValueList = baseAttrs.stream().map(attr -> {
            ProductAttrValue productAttrValue = new ProductAttrValue();
            BeanUtils.copyProperties(attr, productAttrValue);
            productAttrValue.setSpuId(spuInfo.getId());
            productAttrValue.setAttrValue(attr.getAttrValues());
            //获取属性名称
            Attr byId = iAttrService.getById(attr.getAttrId());
            productAttrValue.setAttrName(byId.getAttrName());
            productAttrValue.setQuickShow(Long.valueOf(attr.getShowDesc()));
            return productAttrValue;
        }).toList();
        iProductAttrValueService.saveSpuInfoBaseAttr(productAttrValueList);
        //5.保存spu积分（sms数据库下sms_spu_bounds）跨模块调用
        Bounds bounds = spuSaveVo.getBounds();
        SpuBoundsTO spuBoundsTO = new SpuBoundsTO();
        BeanUtils.copyProperties(bounds,spuBoundsTO);
        //远程调用使用feign
        AjaxResult addBoundsResult = spuCouponFeign.addSpuBound(spuBoundsTO);
        if(!addBoundsResult.isSuccess()){
            log.error("远程调用积分新增失败！");
        }
        //6.保存sku信息
        //6.1保存sku基本信息（pms_sku_info)
        List<Skus> skus = spuSaveVo.getSkus();
        skus.forEach(item -> {
            SkuInfo skuInfo = new SkuInfo();
            BeanUtils.copyProperties(item, skuInfo);
            skuInfo.setSpuId(spuInfo.getId());
            skuInfo.setBrandId(spuInfo.getBrandId());
            skuInfo.setCatalogId(spuInfo.getCatalogId());
            skuInfo.setSkuDesc(String.join(",",item.getDescar()));
            //找出默认图片
            Images defaultImg = item.getImages()==null?null:item.getImages().stream()
                    .filter(img->img.getDefaultImg()!=null && img.getDefaultImg() == 1)
                    .findFirst()
                    .orElse(null);
            skuInfo.setSkuDefaultImg(defaultImg==null?null:defaultImg.getImgUrl());

            iSkuInfoService.savSkuInfo(skuInfo);
            //6.2保存sku图片（pms_sku_image)
            List<Images> skuImages = item.getImages();
            List<SkuImages> skuImagesList = skuImages.stream()
                    .filter(img->img.getImgUrl()!=null&& !img.getImgUrl().isEmpty())
                    .map(img -> {
                SkuImages skuImage = new SkuImages();
                BeanUtils.copyProperties(img, skuImage);
                skuImage.setSkuId(skuInfo.getSkuId());
                return skuImage;
            }).toList();
            iSkuImagesService.saveSkuImages(skuImagesList);
            //6.3保存sku销量属性（pms_sku_sale_attr_value）
            List<com.gulimall.product.vo.Attr> attrList = item.getAttr();
            List<SkuSaleAttrValue> skuSaleAttrList = attrList.stream().map(attr -> {
                SkuSaleAttrValue skuSaleAttrValue = new SkuSaleAttrValue();
                BeanUtils.copyProperties(attr, skuSaleAttrValue);
                skuSaleAttrValue.setSkuId(skuInfo.getSkuId());
                return skuSaleAttrValue;
            }).toList();
            iSkuSaleAttrValueService.saveSkuSaleAttrs(skuSaleAttrList);

            // 6.4保存sku优惠信息，也要跨服务
            SkuReductionTo skuReductionTo = new SkuReductionTo();
            BeanUtils.copyProperties(item,skuReductionTo);
            List<MemberPrice> memberList = item.getMemberPrice().stream()
                    .filter(m->m.getPrice().compareTo(BigDecimal.ZERO)>0)
                    .map(m -> {
                MemberPrice memberPrice1 = new MemberPrice();
                BeanUtils.copyProperties(m, memberPrice1);
                return memberPrice1;
            }).toList();
            skuReductionTo.setMemberPrice(memberList);
            skuReductionTo.setSkuId(skuInfo.getSkuId());
            AjaxResult skuCouponResult = spuCouponFeign.addSkuCoupon(skuReductionTo);
            if(!skuCouponResult.isSuccess()){
                log.error("远程实现sku优惠信息保存出错！");
            }
            //TODO 高级篇进行异常处理
        });
        return 1;
    }

    @Override
    public void saveBaseSpuInfo(SpuInfo spuInfo) {
        this.baseMapper.insert(spuInfo);
    }

    @Override
    public IPage<SpuInfo> pageQuery(Map<String, Object> params) {
        PageDomain pageDomain = TableSupport.buildPageRequest();
        Page<SpuInfo> page = new Page<>(pageDomain.getPageNum(), pageDomain.getPageSize());
        QueryWrapper<SpuInfo> wrapper = new QueryWrapper<>();
        /*
         *  status
            key
            brandId
            catelogId
            pageNum
            pageSize
         */
        String key = params.getOrDefault("key", "").toString();
        if(StringUtils.isNotBlank(key)){
            wrapper.and(w->{
                w.eq("id",key)
                        .or().like("spu_name",key);
            });
        }
        String brandId = params.getOrDefault("brandId",'0').toString();
        if(StringUtils.isNotBlank(brandId) &&!"0".equalsIgnoreCase(brandId)){
            wrapper.eq("brand_id",brandId);
        }
        String catelogId = params.getOrDefault("catelogId",'0').toString();
        if(StringUtils.isNotBlank(catelogId) &&!"0".equalsIgnoreCase(catelogId)){
            wrapper.eq("catalog_id",catelogId);
        }
//        Integer status = (Integer) params.get("status");
//        if(status!=null){
//            wrapper.eq("publish_status",status);
//        }

        String statusStr = params.getOrDefault("status", "").toString();
        if (StringUtils.isNotBlank(statusStr)) {
            Integer status = Integer.parseInt(statusStr);
            wrapper.eq("publish_status", status); // 注意：确认数据库列名是否是 publish_status
        }
        return this.page(page, wrapper);
    }

    @Transactional
    @Override
    public void up(Long spuId) {

        //spuId 查出所有sku信息
        List<SkuInfo> skus = iSkuInfoService.getBySpuId(spuId);
        List<Long> skuIdList = skus.stream().map(SkuInfo::getSkuId).toList();
        //查出sku所有的规格属性(是可以被检索的)
        List<ProductAttrValue> attrs = iAttrService.listforspuES(spuId);
        List<SkuEsModel.Attr> esAttrs = attrs.stream().map(attr -> {
            SkuEsModel.Attr esAttr = new SkuEsModel.Attr();
            BeanUtils.copyProperties(attr, esAttr);
            return esAttr;
        }).toList();
        //TODO 远程查询库存 hasStock
        var ref = new Object() {
            Map<Long, Boolean> skuStockMap = null;
        };
        try {
            AjaxResult listAjaxResult = skuWareFeign.hasStock(skuIdList);
            TypeReference<List<SkuHasstockVo>> listTypeReference = new TypeReference<>(){};
            List<SkuHasstockVo> skuStockVos = listAjaxResult.getData(listTypeReference);
            ref.skuStockMap = skuStockVos.stream().collect(Collectors.toMap(vo -> vo.getSkuId(), vo -> vo.getHasStock()));
        }catch (Exception e){
            log.error("查询库存失败{}",e);
        }
        //组装数据esModel
        List<SkuEsModel> esList = skus.stream().map(s -> {
            SkuEsModel esModel = new SkuEsModel();
            //拷贝属性值
            BeanUtils.copyProperties(s, esModel);
            //需要单独赋值：skuPrice，skuImg，hasStock，hotScore，brandName，catalogName，brandImg，attrs
            esModel.setSkuPrice(s.getPrice());
            esModel.setSkuImg(s.getSkuDefaultImg());
//            AjaxResult ajaxResult = skuWareFeign.hasStock(s.getSkuId());
//            if(!ajaxResult.isSuccess()){
//                esModel.setHasStock((Boolean) ajaxResult.get("hasStock"));
//            }
            //TODO 热度评分，暂赋值为0
            esModel.setHotScore(0L);
            if(ref.skuStockMap ==null){
                esModel.setHasStock(true);
            }else{
                esModel.setHasStock(ref.skuStockMap.get(s.getSkuId()));
            }
            //品牌名、图片、分类名称查询
            Brand brand = iBrandService.getById(esModel.getBrandId());
            esModel.setBrandName(brand.getName());
            esModel.setBrandImg(brand.getLogo());
            Category category = iCategoryService.getById(esModel.getCatalogId());
            esModel.setCatalogName(category.getName());
            esModel.setAttrs(esAttrs);
            return esModel;
        }).toList();

        //TODO 发给es保存
        AjaxResult searchRes = searchFeign.productUp(esList);
        if(searchRes.isSuccess()){
            //修改状态
            spuInfoMapper.updateStatuUp(spuId, ProductConstant.StatusEnum.SPU_UP.getCode());
        }else{
            //TODO 失败 重复调用？接口幂等性？重试机制
            log.error("商品上架失败");
        }
    }
    // 单表 CRUD 由 ServiceImpl 提供，如有自定义业务方法可在此处扩展
}