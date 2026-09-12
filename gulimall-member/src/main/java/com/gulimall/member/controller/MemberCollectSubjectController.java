package com.gulimall.member.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.gulimall.common.annotation.Log;
import com.gulimall.common.enums.BusinessType;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gulimall.member.domain.MemberCollectSubject;
import com.gulimall.member.service.IMemberCollectSubjectService;
import com.gulimall.common.core.controller.BaseController;
import com.gulimall.common.core.domain.AjaxResult;
import com.gulimall.common.core.page.TableDataInfo;


/**
 * 【请填写功能名称】Controller
 *
 * @author jiangdan
 * @date 2026-09-12
 */
@Controller
@RequestMapping("/member/umsMemberCollectSubject")
public class MemberCollectSubjectController extends BaseController
{
    private String prefix = "member/umsMemberCollectSubject";

    @Autowired
    private IMemberCollectSubjectService memberCollectSubjectService;

    @GetMapping()
    public String subject()
    {
        return prefix + "/subject";
    }

        /**
         * 查询【请填写功能名称】列表
         */
        @GetMapping("/list")
        @ResponseBody
        public TableDataInfo list(MemberCollectSubject memberCollectSubject)
        {
            startPage();
            List<MemberCollectSubject> list = memberCollectSubjectService.list(
                    new QueryWrapper<>(memberCollectSubject)
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
    public AjaxResult addSave(MemberCollectSubject memberCollectSubject)
    {
        return memberCollectSubjectService.save(memberCollectSubject)
                ? AjaxResult.success()
                : AjaxResult.error();
    }

    /**
     * 修改【请填写功能名称】
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        MemberCollectSubject memberCollectSubject = memberCollectSubjectService.getById(id);
        mmap.put("memberCollectSubject", memberCollectSubject);
        return prefix + "/edit";
    }

    /**
     * 修改保存【请填写功能名称】
     */
    @Log(title = "【请填写功能名称】", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(MemberCollectSubject memberCollectSubject)
    {
        return memberCollectSubjectService.updateById(memberCollectSubject)
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
            return memberCollectSubjectService.removeByIds(ids)
                    ? AjaxResult.success()
                    : AjaxResult.error();
        }
}