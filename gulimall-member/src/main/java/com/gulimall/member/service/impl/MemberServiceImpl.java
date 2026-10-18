package com.gulimall.member.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.spring.service.impl.ServiceImpl;
import com.gulimall.member.exception.PhoneExistException;
import com.gulimall.member.exception.UserNameExistException;
import com.gulimall.member.service.IMemberLevelService;
import com.gulimall.member.vo.SocialUser;
import com.gulimall.member.vo.UserLoginVo;
import com.gulimall.member.vo.UserRegisterVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.gulimall.member.mapper.MemberMapper;
import com.gulimall.member.domain.Member;
import com.gulimall.member.service.IMemberService;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

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

    @Autowired
    RestTemplate restTemplate;

    @Override
    public void register(UserRegisterVo vo) {
        Member member = new Member();
        //一一赋值
        //检查用户名和手机号的唯一性
        checkUsernameUnique(vo.getUserName());
        checkPhoneUnique(vo.getPhone());
        member.setUsername(vo.getUserName());
        member.setNickname(vo.getUserName());
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

    @Override
    public Member login(UserLoginVo vo) {
        //根据用户名查询出用户信息
        Member member = baseMapper.selectOne(new QueryWrapper<Member>().eq("mobile", vo.getLoginacct()).or().eq("username", vo.getLoginacct()));
        if(member==null){
            return null;
        }else{
            //比较密码是否一致，一致返回用户信息，不一致返回null
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            boolean b = encoder.matches(vo.getPassword(), member.getPassword());
            if(b){
                return member;
            }else{
                return null;
            }
        }
    }

    //社交登录方法实现
    @Override
    public Member login(SocialUser vo) {
        //根据uid查询用户是第一次注册还是已有账号
        Member update = memberMapper.selectOne(new QueryWrapper<Member>().eq("social_uid", vo.getUid()));
        if(update==null){
            Member member = new Member();
            //如果用户是第一次注册，需要构建一个Member插入，需要查询社交API，查询用户相关信息，然后一一赋值
            //远程查询用户的信息
            try {
                String url = "https://api.weibo.com/2/users/show.json?access_token={accessToken}&uid={uid}";
                Map<String, String> map = new HashMap<>();
                map.put("accessToken",vo.getAccessToken());
                map.put("uid",vo.getUid());
                ResponseEntity<String> res = restTemplate.getForEntity(url, String.class, map);
                if(res.getStatusCode()== HttpStatus.OK){
                    String body = res.getBody();
                    Map<String,Object> userMap = JSON.parseObject(body, new TypeReference<Map<String,Object>>(){});
                    //用户名
                    String name = userMap.get("name").toString();
                    //性别
                    long gender = userMap.get("gender") == "f" ? 1L : 0;
                    //其他内容
                    //TODO 默认等级 其他默认信息
                    member.setNickname(name);
                    member.setUsername(name);
                    member.setGender(gender);
                }
            }catch (Exception e){
            }
            //把用户的信息赋值到member中
            member.setSocialUid(vo.getUid());
            member.setAccessToken(vo.getAccessToken());
            member.setExpiresIn(Long.valueOf(vo.getExpiresIn()));
            //新增member
            memberMapper.insert(member);
            return member;
        }else{
            //如果用户已有账号，需要更新accessToken和expiresIn
            update.setSocialUid(vo.getUid());
            update.setAccessToken(vo.getAccessToken());
            update.setExpiresIn(Long.valueOf(vo.getExpiresIn()));
            memberMapper.updateById(update);
            return update;
        }
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