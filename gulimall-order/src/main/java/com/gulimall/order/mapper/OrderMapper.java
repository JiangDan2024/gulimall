package com.gulimall.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gulimall.order.domain.Order;

/**
 * 【请填写功能名称】Mapper接口
 *
 * @author jdjdjd
 * @date 2026-09-22
 */
public interface OrderMapper extends BaseMapper<Order> {
    // 单表 CRUD 由 BaseMapper 提供，如有复杂连表查询可在此处扩展
}