package com.gulimall.ware.controller;

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
import com.gulimall.ware.domain.PurchaseDetail;
import com.gulimall.ware.service.IPurchaseDetailService;
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
@RequestMapping("/ware/wmsPurchaseDetail")
public class PurchaseDetailController extends BaseController
{
    private String prefix = "ware/wmsPurchaseDetail";

    @Autowired
    private IPurchaseDetailService purchaseDetailService;

    @GetMapping()
    public String detail()
    {
        return prefix + "/detail";
    }

        /**
         * 查询【请填写功能名称】列表
         */
        @GetMapping("/list")
        @ResponseBody
        public TableDataInfo list(PurchaseDetail purchaseDetail)
        {
            startPage();
            List<PurchaseDetail> list = purchaseDetailService.list(
                    new QueryWrapper<>(purchaseDetail)
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
    public AjaxResult addSave(PurchaseDetail purchaseDetail)
    {
        return purchaseDetailService.save(purchaseDetail)
                ? AjaxResult.success()
                : AjaxResult.error();
    }

    /**
     * 修改【请填写功能名称】
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        PurchaseDetail purchaseDetail = purchaseDetailService.getById(id);
        mmap.put("purchaseDetail", purchaseDetail);
        return prefix + "/edit";
    }

    /**
     * 修改保存【请填写功能名称】
     */
    @Log(title = "【请填写功能名称】", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(PurchaseDetail purchaseDetail)
    {
        return purchaseDetailService.updateById(purchaseDetail)
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
            return purchaseDetailService.removeByIds(ids)
                    ? AjaxResult.success()
                    : AjaxResult.error();
        }
}