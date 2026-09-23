package com.gulimall.ware.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.spring.service.impl.ServiceImpl;
import com.gulimall.common.core.page.PageDomain;
import com.gulimall.common.core.page.TableSupport;
import com.gulimall.common.utils.StringUtils;
import org.springframework.stereotype.Service;
import com.gulimall.ware.mapper.WareInfoMapper;
import com.gulimall.ware.domain.WareInfo;
import com.gulimall.ware.service.IWareInfoService;

/**
 * 【请填写功能名称】Service业务层处理
 *
 * @author jdjdjd
 * @date 2026-09-22
 */
@Service
public class WareInfoServiceImpl extends ServiceImpl<WareInfoMapper, WareInfo> implements IWareInfoService {
    @Override
    public IPage<WareInfo> pageQuery(WareInfo wareInfo) {
        PageDomain pageDomain = TableSupport.buildPageRequest();
        Page<WareInfo> page = new Page<>(pageDomain.getPageNum(), pageDomain.getPageSize());
        QueryWrapper<WareInfo> wrapper = new QueryWrapper<>();
        String key = wareInfo.getKey();
        if(StringUtils.isNotEmpty(key)){
            wrapper.eq("id",key)
                    .or().like("name",key)
                    .or().like("address",key)
                    .or().like("areacode",key);
        }
        return this.page(page, wrapper);
    }
    // 单表 CRUD 由 ServiceImpl 提供，如有自定义业务方法可在此处扩展
}