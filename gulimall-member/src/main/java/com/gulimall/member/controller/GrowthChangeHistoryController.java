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
import com.gulimall.member.domain.GrowthChangeHistory;
import com.gulimall.member.service.IGrowthChangeHistoryService;
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
@RequestMapping("/member/umsGrowthChangeHistory")
public class GrowthChangeHistoryController extends BaseController
{
    private String prefix = "member/umsGrowthChangeHistory";

    @Autowired
    private IGrowthChangeHistoryService growthChangeHistoryService;

    @GetMapping()
    public String history()
    {
        return prefix + "/history";
    }

        /**
         * 查询【请填写功能名称】列表
         */
        @GetMapping("/list")
        @ResponseBody
        public TableDataInfo list(GrowthChangeHistory growthChangeHistory)
        {
            startPage();
            List<GrowthChangeHistory> list = growthChangeHistoryService.list(
                    new QueryWrapper<>(growthChangeHistory)
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
    public AjaxResult addSave(GrowthChangeHistory growthChangeHistory)
    {
        return growthChangeHistoryService.save(growthChangeHistory)
                ? AjaxResult.success()
                : AjaxResult.error();
    }

    /**
     * 修改【请填写功能名称】
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        GrowthChangeHistory growthChangeHistory = growthChangeHistoryService.getById(id);
        mmap.put("growthChangeHistory", growthChangeHistory);
        return prefix + "/edit";
    }

    /**
     * 修改保存【请填写功能名称】
     */
    @Log(title = "【请填写功能名称】", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(GrowthChangeHistory growthChangeHistory)
    {
        return growthChangeHistoryService.updateById(growthChangeHistory)
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
            return growthChangeHistoryService.removeByIds(ids)
                    ? AjaxResult.success()
                    : AjaxResult.error();
        }
}