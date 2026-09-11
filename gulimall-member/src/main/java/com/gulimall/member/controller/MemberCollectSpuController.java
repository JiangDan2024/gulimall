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
import com.gulimall.common.core.page.TableDataInfo;
import com.gulimall.member.domain.MemberCollectSpu;
import com.gulimall.member.service.IMemberCollectSpuService;
import com.gulimall.common.core.controller.BaseController;
import com.gulimall.common.core.domain.AjaxResult;
import com.gulimall.common.utils.poi.ExcelUtil;
import com.gulimall.common.core.page.TableDataInfo;


/**
 * 【请填写功能名称】Controller
 *
 * @author jiangdan
 * @date 2026-09-11
 */
@Controller
@RequestMapping("/member/umsMemberCollectSpu")
public class MemberCollectSpuController extends BaseController
{
    private String prefix = "member/umsMemberCollectSpu";

    @Autowired
    private IMemberCollectSpuService memberCollectSpuService;

    @GetMapping()
    public String spu()
    {
        return prefix + "/spu";
    }

        /**
         * 查询【请填写功能名称】列表
         */
        @GetMapping("/list")
        @ResponseBody
        public TableDataInfo list(MemberCollectSpu memberCollectSpu)
        {
            startPage();
            List<MemberCollectSpu> list = memberCollectSpuService.list(
                    new QueryWrapper<>(memberCollectSpu)
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
    public AjaxResult addSave(MemberCollectSpu memberCollectSpu)
    {
        return memberCollectSpuService.save(memberCollectSpu)
                ? AjaxResult.success()
                : AjaxResult.error();
    }

    /**
     * 修改【请填写功能名称】
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        MemberCollectSpu memberCollectSpu = memberCollectSpuService.getById(id);
        mmap.put("memberCollectSpu", memberCollectSpu);
        return prefix + "/edit";
    }

    /**
     * 修改保存【请填写功能名称】
     */
    @Log(title = "【请填写功能名称】", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(MemberCollectSpu memberCollectSpu)
    {
        return memberCollectSpuService.updateById(memberCollectSpu)
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
            return memberCollectSpuService.removeByIds(ids)
                    ? AjaxResult.success()
                    : AjaxResult.error();
        }
}