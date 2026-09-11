package com.gulimall.coupon.controller;

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
import com.gulimall.coupon.domain.HomeSubject;
import com.gulimall.coupon.service.IHomeSubjectService;
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
@RequestMapping("/coupon/smsHomeSubject")
public class HomeSubjectController extends BaseController
{
    private String prefix = "coupon/smsHomeSubject";

    @Autowired
    private IHomeSubjectService homeSubjectService;

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
        public TableDataInfo list(HomeSubject homeSubject)
        {
            startPage();
            List<HomeSubject> list = homeSubjectService.list(
                    new QueryWrapper<>(homeSubject)
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
    public AjaxResult addSave(HomeSubject homeSubject)
    {
        return homeSubjectService.save(homeSubject)
                ? AjaxResult.success()
                : AjaxResult.error();
    }

    /**
     * 修改【请填写功能名称】
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        HomeSubject homeSubject = homeSubjectService.getById(id);
        mmap.put("homeSubject", homeSubject);
        return prefix + "/edit";
    }

    /**
     * 修改保存【请填写功能名称】
     */
    @Log(title = "【请填写功能名称】", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(HomeSubject homeSubject)
    {
        return homeSubjectService.updateById(homeSubject)
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
            return homeSubjectService.removeByIds(ids)
                    ? AjaxResult.success()
                    : AjaxResult.error();
        }
}