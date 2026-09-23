package com.gulimall.ware.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gulimall.ware.domain.WareSku;
import org.apache.ibatis.annotations.Param;

/**
 * 【请填写功能名称】Mapper接口
 *
 * @author jdjdjd
 * @date 2026-09-22
 */
public interface WareSkuMapper extends BaseMapper<WareSku> {
    void updateFinishedStock(@Param("skuId") Long skuId,@Param("wareId") Long wareId,@Param("skuNum") Long skuNum);
    // 单表 CRUD 由 BaseMapper 提供，如有复杂连表查询可在此处扩展
}