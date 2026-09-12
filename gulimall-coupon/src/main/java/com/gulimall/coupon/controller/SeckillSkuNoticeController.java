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
import com.gulimall.coupon.domain.SeckillSkuNotice;
import com.gulimall.coupon.service.ISeckillSkuNoticeService;
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
@RequestMapping("/coupon/smsSeckillSkuNotice")
public class SeckillSkuNoticeController extends BaseController
{
    private String prefix = "coupon/smsSeckillSkuNotice";

    @Autowired
    private ISeckillSkuNoticeService seckillSkuNoticeService;

    @GetMapping()
    public String notice()
    {
        return prefix + "/notice";
    }

        /**
         * 查询【请填写功能名称】列表
         */
        @GetMapping("/list")
        @ResponseBody
        public TableDataInfo list(SeckillSkuNotice seckillSkuNotice)
        {
            startPage();
            List<SeckillSkuNotice> list = seckillSkuNoticeService.list(
                    new QueryWrapper<>(seckillSkuNotice)
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
    public AjaxResult addSave(SeckillSkuNotice seckillSkuNotice)
    {
        return seckillSkuNoticeService.save(seckillSkuNotice)
                ? AjaxResult.success()
                : AjaxResult.error();
    }

    /**
     * 修改【请填写功能名称】
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        SeckillSkuNotice seckillSkuNotice = seckillSkuNoticeService.getById(id);
        mmap.put("seckillSkuNotice", seckillSkuNotice);
        return prefix + "/edit";
    }

    /**
     * 修改保存【请填写功能名称】
     */
    @Log(title = "【请填写功能名称】", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(SeckillSkuNotice seckillSkuNotice)
    {
        return seckillSkuNoticeService.updateById(seckillSkuNotice)
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
            return seckillSkuNoticeService.removeByIds(ids)
                    ? AjaxResult.success()
                    : AjaxResult.error();
        }
}