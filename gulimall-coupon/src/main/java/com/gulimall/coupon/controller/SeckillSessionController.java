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
import com.gulimall.coupon.domain.SeckillSession;
import com.gulimall.coupon.service.ISeckillSessionService;
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
@RequestMapping("/coupon/smsSeckillSession")
public class SeckillSessionController extends BaseController
{
    private String prefix = "coupon/smsSeckillSession";

    @Autowired
    private ISeckillSessionService seckillSessionService;

    @GetMapping()
    public String session()
    {
        return prefix + "/session";
    }

        /**
         * 查询【请填写功能名称】列表
         */
        @GetMapping("/list")
        @ResponseBody
        public TableDataInfo list(SeckillSession seckillSession)
        {
            startPage();
            List<SeckillSession> list = seckillSessionService.list(
                    new QueryWrapper<>(seckillSession)
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
    public AjaxResult addSave(SeckillSession seckillSession)
    {
        return seckillSessionService.save(seckillSession)
                ? AjaxResult.success()
                : AjaxResult.error();
    }

    /**
     * 修改【请填写功能名称】
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        SeckillSession seckillSession = seckillSessionService.getById(id);
        mmap.put("seckillSession", seckillSession);
        return prefix + "/edit";
    }

    /**
     * 修改保存【请填写功能名称】
     */
    @Log(title = "【请填写功能名称】", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(SeckillSession seckillSession)
    {
        return seckillSessionService.updateById(seckillSession)
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
            return seckillSessionService.removeByIds(ids)
                    ? AjaxResult.success()
                    : AjaxResult.error();
        }
}