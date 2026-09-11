package com.gulimall.product.controller;

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
import com.gulimall.product.domain.SkuInfo;
import com.gulimall.product.service.ISkuInfoService;
import com.gulimall.common.core.controller.BaseController;
import com.gulimall.common.core.domain.AjaxResult;
import com.gulimall.common.utils.poi.ExcelUtil;
import com.gulimall.common.core.page.TableDataInfo;


/**
 * 【请填写功能名称】Controller
 *
 * @author jiangdan
 * @date 2026-09-12
 */
@Controller
@RequestMapping("/product/pmsSkuInfo")
public class SkuInfoController extends BaseController
{
    private String prefix = "product/pmsSkuInfo";

    @Autowired
    private ISkuInfoService skuInfoService;

    @GetMapping()
    public String info()
    {
        return prefix + "/info";
    }

        /**
         * 查询【请填写功能名称】列表
         */
        @GetMapping("/list")
        @ResponseBody
        public TableDataInfo list(SkuInfo skuInfo)
        {
            startPage();
            List<SkuInfo> list = skuInfoService.list(
                    new QueryWrapper<>(skuInfo)
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
    public AjaxResult addSave(SkuInfo skuInfo)
    {
        return skuInfoService.save(skuInfo)
                ? AjaxResult.success()
                : AjaxResult.error();
    }

    /**
     * 修改【请填写功能名称】
     */
    @GetMapping("/edit/{skuId}")
    public String edit(@PathVariable("skuId") Long skuId, ModelMap mmap)
    {
        SkuInfo skuInfo = skuInfoService.getById(skuId);
        mmap.put("skuInfo", skuInfo);
        return prefix + "/edit";
    }

    /**
     * 修改保存【请填写功能名称】
     */
    @Log(title = "【请填写功能名称】", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(SkuInfo skuInfo)
    {
        return skuInfoService.updateById(skuInfo)
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
            return skuInfoService.removeByIds(ids)
                    ? AjaxResult.success()
                    : AjaxResult.error();
        }
}