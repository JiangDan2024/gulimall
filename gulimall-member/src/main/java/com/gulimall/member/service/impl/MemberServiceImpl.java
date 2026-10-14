package com.gulimall.member.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.spring.service.impl.ServiceImpl;
import com.gulimall.member.exception.PhoneExistException;
import com.gulimall.member.exception.UserNameExistException;
import com.gulimall.member.service.IMemberLevelService;
import com.gulimall.member.vo.UserRegisterVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.gulimall.member.mapper.MemberMapper;
import com.gulimall.member.domain.Member;
import com.gulimall.member.service.IMemberService;

/**
 * 【请填写功能名称】Service业务层处理
 *
 * @author jdjdjd
 * @date 2026-09-20
 */
@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member> implements IMemberService {

    @Autowired
    MemberMapper memberMapper;

    @Autowired
    IMemberLevelService levelService;

    @Override
    public void register(UserRegisterVo vo) {
        Member member = new Member();
        //一一赋值
        //检查用户名和手机号的唯一性
        checkUsernameUnique(vo.getUserName());
        checkPhoneUnique(vo.getPhone());
        member.setUsername(vo.getUserName());
        member.setPassword(vo.getPassword());
        //密码加密
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        member.setPassword(encoder.encode(vo.getPassword()));
        //初始化默认等级
        long defaultLevel = levelService.getDefaultLevel();
        member.setLevelId(defaultLevel);
        //TODO 其他默认值初始化

        //保存
        this.save(member);
    }

    private void checkPhoneUnique(String phone) throws PhoneExistException{
        //为了使controller感知到，使用异常机制，抛出异常
        Long count = memberMapper.selectCount(new QueryWrapper<Member>().eq("mobile", phone));
        if(count>0){
            throw new PhoneExistException();
        }
    }

    private void checkUsernameUnique(String userName) throws UserNameExistException{
        Long count = memberMapper.selectCount(new QueryWrapper<Member>().eq("username", userName));
        if(count>0){
            throw new UserNameExistException();
        }
    }
    // 单表 CRUD 由 ServiceImpl 提供，如有自定义业务方法可在此处扩展
}