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
import com.gulimall.product.domain.ProductAttrValue;
import com.gulimall.product.service.IProductAttrValueService;
import com.gulimall.common.utils.poi.ExcelUtil;

/**
 * 【请填写功能名称】Controller
 *
 * @author jdjdjd
 * @date 2026-09-19
 */
@RestController
@RequestMapping("/product/productAttrValue")
public class ProductAttrValueController extends BaseController
{
    @Autowired
    private IProductAttrValueService productAttrValueService;

    /**
     * 查询【请填写功能名称】列表
     */
    // @PreAuthorize("@ss.hasPermi('product:productAttrValue:list')")
    @GetMapping("/list")
    public TableDataInfo list(ProductAttrValue productAttrValue)
    {
        PageDomain pageDomain = TableSupport.buildPageRequest();
        Page<ProductAttrValue> page = new Page<>(pageDomain.getPageNum(), pageDomain.getPageSize());
        QueryWrapper<ProductAttrValue> wrapper = new QueryWrapper<>(productAttrValue);
        IPage<ProductAttrValue> result = productAttrValueService.page(page, wrapper);

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
    // @PreAuthorize("@ss.hasPermi('product:productAttrValue:export')")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ProductAttrValue productAttrValue)
    {
        List<ProductAttrValue> list = productAttrValueService.list(new QueryWrapper<>(productAttrValue));
        ExcelUtil<ProductAttrValue> util = new ExcelUtil<ProductAttrValue>(ProductAttrValue.class);
        util.exportExcel(response, list, "【请填写功能名称】数据");
    }

    /**
     * 获取【请填写功能名称】详细信息
     */
    // @PreAuthorize("@ss.hasPermi('product:productAttrValue:query')")
    @GetMapping("/info/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(productAttrValueService.getById(id));
    }

    /**
     * 新增【请填写功能名称】
     */
    // @PreAuthorize("@ss.hasPermi('product:productAttrValue:add')")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.INSERT)
    @PostMapping("/save")
    public AjaxResult add(@RequestBody ProductAttrValue productAttrValue)
    {
        return toAjax(productAttrValueService.save(productAttrValue));
    }

    /**
     * 修改【请填写功能名称】
     */
    // @PreAuthorize("@ss.hasPermi('product:productAttrValue:edit')")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.UPDATE)
    @PutMapping("/update")
    public AjaxResult edit(@RequestBody ProductAttrValue productAttrValue)
    {
        return toAjax(productAttrValueService.updateById(productAttrValue));
    }

    /**
     * 删除【请填写功能名称】
     */
    // @PreAuthorize("@ss.hasPermi('product:productAttrValue:remove')")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.DELETE)
    @PostMapping("/delete")
    public AjaxResult remove(@RequestBody List<Long> ids)
    {
        return toAjax(productAttrValueService.removeByIds(ids));
    }
}