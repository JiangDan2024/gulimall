package com.gulimall.product.service.impl;

import com.baomidou.mybatisplus.spring.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.gulimall.product.mapper.SpuCommentMapper;
import com.gulimall.product.domain.SpuComment;
import com.gulimall.product.service.ISpuCommentService;

/**
 * 【请填写功能名称】Service业务层处理
 *
 * @author jdjdjd
 * @date 2026-09-19
 */
@Service
public class SpuCommentServiceImpl extends ServiceImpl<SpuCommentMapper, SpuComment> implements ISpuCommentService {
    // 单表 CRUD 由 ServiceImpl 提供，如有自定义业务方法可在此处扩展
}