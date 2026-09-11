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
import com.gulimall.product.domain.SpuInfoDesc;
import com.gulimall.product.service.ISpuInfoDescService;
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
@RequestMapping("/product/pmsSpuInfoDesc")
public class SpuInfoDescController extends BaseController
{
    private String prefix = "product/pmsSpuInfoDesc";

    @Autowired
    private ISpuInfoDescService spuInfoDescService;

    @GetMapping()
    public String desc()
    {
        return prefix + "/desc";
    }

        /**
         * 查询【请填写功能名称】列表
         */
        @GetMapping("/list")
        @ResponseBody
        public TableDataInfo list(SpuInfoDesc spuInfoDesc)
        {
            startPage();
            List<SpuInfoDesc> list = spuInfoDescService.list(
                    new QueryWrapper<>(spuInfoDesc)
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
    public AjaxResult addSave(SpuInfoDesc spuInfoDesc)
    {
        return spuInfoDescService.save(spuInfoDesc)
                ? AjaxResult.success()
                : AjaxResult.error();
    }

    /**
     * 修改【请填写功能名称】
     */
    @GetMapping("/edit/{spuId}")
    public String edit(@PathVariable("spuId") Long spuId, ModelMap mmap)
    {
        SpuInfoDesc spuInfoDesc = spuInfoDescService.getById(spuId);
        mmap.put("spuInfoDesc", spuInfoDesc);
        return prefix + "/edit";
    }

    /**
     * 修改保存【请填写功能名称】
     */
    @Log(title = "【请填写功能名称】", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(SpuInfoDesc spuInfoDesc)
    {
        return spuInfoDescService.updateById(spuInfoDesc)
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
            return spuInfoDescService.removeByIds(ids)
                    ? AjaxResult.success()
                    : AjaxResult.error();
        }
}