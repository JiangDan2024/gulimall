package com.gulimall.member.service;

import com.baomidou.mybatisplus.spring.service.IService;
import com.gulimall.member.domain.Member;
import com.gulimall.member.vo.SocialUser;
import com.gulimall.member.vo.UserLoginVo;
import com.gulimall.member.vo.UserRegisterVo;

/**
 * 【请填写功能名称】Service接口
 *
 * @author jdjdjd
 * @date 2026-09-20
 */
public interface IMemberService extends IService<Member> {
    void register(UserRegisterVo vo);

    Member login(UserLoginVo vo);

    Member login(SocialUser vo);
    // 单表 CRUD 由 IService 提供，如有自定义业务方法可在此处扩展
}