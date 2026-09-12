package com.gulimall.member.service.impl;

import com.baomidou.mybatisplus.spring.service.impl.ServiceImpl;

import org.springframework.stereotype.Service;
import com.gulimall.member.mapper.MemberMapper;
import com.gulimall.member.domain.Member;
import com.gulimall.member.service.IMemberService;

/**
 * 【请填写功能名称】Service业务层处理
 * 
 * @author jiangdan
 * @date 2026-09-12
 */
@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper,Member> implements IMemberService
{

}
