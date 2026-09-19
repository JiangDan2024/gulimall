package com.gulimall.product.controller;

import java.util.Arrays;
import java.util.List;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gulimall.common.annotation.Log;
import com.gulimall.common.core.controller.BaseController;
import com.gulimall.common.core.domain.AjaxResult;
import com.gulimall.common.core.page.PageDomain;
import com.gulimall.common.core.page.TableDataInfo;
import com.gulimall.common.core.page.TableSupport;
import com.gulimall.common.enums.BusinessType;
import com.gulimall.product.domain.SpuInfoDesc;
import com.gulimall.product.service.ISpuInfoDescService;
import com.gulimall.common.utils.poi.ExcelUtil;

/**
 * 【请填写功能名称】Controller
 *
 * @author jdjdjd
 * @date 2026-09-19
 */
@RestController
@RequestMapping("/product/spuInfoDesc")
public class SpuInfoDescController extends BaseController
{
    @Autowired
    private ISpuInfoDescService spuInfoDescService;

    /**
     * 查询【请填写功能名称】列表
     */
    // @PreAuthorize("@ss.hasPermi('product:spuInfoDesc:list')")
    @GetMapping("/list")
    public TableDataInfo list(SpuInfoDesc spuInfoDesc)
    {
        PageDomain pageDomain = TableSupport.buildPageRequest();
        Page<SpuInfoDesc> page = new Page<>(pageDomain.getPageNum(), pageDomain.getPageSize());
        QueryWrapper<SpuInfoDesc> wrapper = new QueryWrapper<>(spuInfoDesc);
        IPage<SpuInfoDesc> result = spuInfoDescService.page(page, wrapper);

        TableDataInfo rspData = new TableDataInfo();
        rspData.setCode(200);
        rspData.setMsg("查询成功");
        rspData.setRows(result.getRecords());
        rspData.setTotal(result.getTotal());
        return rspData;
    }

    /**
     * 导出【请填写功能名称】列表
     */
    // @PreAuthorize("@ss.hasPermi('product:spuInfoDesc:export')")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SpuInfoDesc spuInfoDesc)
    {
        List<SpuInfoDesc> list = spuInfoDescService.list(new QueryWrapper<>(spuInfoDesc));
        ExcelUtil<SpuInfoDesc> util = new ExcelUtil<SpuInfoDesc>(SpuInfoDesc.class);
        util.exportExcel(response, list, "【请填写功能名称】数据");
    }

    /**
     * 获取【请填写功能名称】详细信息
     */
    // @PreAuthorize("@ss.hasPermi('product:spuInfoDesc:query')")
    @GetMapping("/info/{spuId}")
    public AjaxResult getInfo(@PathVariable("spuId") Long spuId)
    {
        return success(spuInfoDescService.getById(spuId));
    }

    /**
     * 新增【请填写功能名称】
     */
    // @PreAuthorize("@ss.hasPermi('product:spuInfoDesc:add')")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.INSERT)
    @PostMapping("/save")
    public AjaxResult add(@RequestBody SpuInfoDesc spuInfoDesc)
    {
        return toAjax(spuInfoDescService.save(spuInfoDesc));
    }

    /**
     * 修改【请填写功能名称】
     */
    // @PreAuthorize("@ss.hasPermi('product:spuInfoDesc:edit')")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.UPDATE)
    @PutMapping("/update")
    public AjaxResult edit(@RequestBody SpuInfoDesc spuInfoDesc)
    {
        return toAjax(spuInfoDescService.updateById(spuInfoDesc));
    }

    /**
     * 删除【请填写功能名称】
     */
    // @PreAuthorize("@ss.hasPermi('product:spuInfoDesc:remove')")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.DELETE)
    @PostMapping("/delete")
    public AjaxResult remove(@RequestBody List<Long> spuIds)
    {
        return toAjax(spuInfoDescService.removeByIds(spuIds));
    }
}