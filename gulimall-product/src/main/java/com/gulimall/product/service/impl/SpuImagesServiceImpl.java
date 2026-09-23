package com.gulimall.product.service.impl;

import com.baomidou.mybatisplus.spring.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.gulimall.product.mapper.SpuImagesMapper;
import com.gulimall.product.domain.SpuImages;
import com.gulimall.product.service.ISpuImagesService;

import java.util.List;

/**
 * 【请填写功能名称】Service业务层处理
 *
 * @author jdjdjd
 * @date 2026-09-19
 */
@Service
public class SpuImagesServiceImpl extends ServiceImpl<SpuImagesMapper, SpuImages> implements ISpuImagesService {
    @Override
    public void saveSpuInfoImages(List<SpuImages> spuImagesList) {
        if(spuImagesList!=null&&spuImagesList.size()>0){
            this.baseMapper.insert(spuImagesList);
        }
    }
    // 单表 CRUD 由 ServiceImpl 提供，如有自定义业务方法可在此处扩展
}