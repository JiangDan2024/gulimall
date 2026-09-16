package com.gulimall.member.controller;

import java.util.List;

import com.gulimall.member.feign.CouponFeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import com.gulimall.common.annotation.Log;
import com.gulimall.common.enums.BusinessType;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gulimall.member.domain.Member;
import com.gulimall.member.service.IMemberService;
import com.gulimall.common.core.controller.BaseController;
import com.gulimall.common.core.domain.AjaxResult;
import com.gulimall.common.core.page.TableDataInfo;


/**
 * 【请填写功能名称】Controller
 *
 * @author jiangdan
 * @date 2026-09-12
 */
@RestController
@RequestMapping("/member/umsMember")
public class MemberController extends BaseController
{
    private String prefix = "member/umsMember";

    @Autowired
    private IMemberService memberService;

    @Autowired
    CouponFeignService couponFeignService;

    @GetMapping()
    public String member()
    {
        return prefix + "/member";
    }

    @RequestMapping("/coupons")
    public AjaxResult test(){
        Member member = new Member();
        member.setNickname("张三");

        AjaxResult couponsRes = couponFeignService.memberCoupons();
        return AjaxResult.success().put("member",member).put("coupons",couponsRes.get("coupons"));

    }

        /**
         * 查询【请填写功能名称】列表
         */
        @GetMapping("/list")
        @ResponseBody
        public TableDataInfo list(Member member)
        {
            startPage();
            List<Member> list = memberService.list(
                    new QueryWrapper<>(member)
            );
            return getDataTable(list);
        }

    /**
     * 新增【请填写功能名称】
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存【请填写功能名称】
     */
    @Log(title = "【请填写功能名称】", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(Member member)
    {
        return memberService.save(member)
                ? AjaxResult.success()
                : AjaxResult.error();
    }

    /**
     * 修改【请填写功能名称】
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        Member member = memberService.getById(id);
        mmap.put("member", member);
        return prefix + "/edit";
    }

    /**
     * 修改保存【请填写功能名称】
     */
    @Log(title = "【请填写功能名称】", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(Member member)
    {
        return memberService.updateById(member)
                ? AjaxResult.success()
                : AjaxResult.error();
    }

        /**
         * 删除【请填写功能名称】
         */
        @Log(title = "【请填写功能名称】", businessType = BusinessType.DELETE)
        @PostMapping( "/remove")
        @ResponseBody
        public AjaxResult remove(List<String> ids)
        {
            return memberService.removeByIds(ids)
                    ? AjaxResult.success()
                    : AjaxResult.error();
        }
}