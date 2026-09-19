package com.gulimall.product.controller;

import java.util.List;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
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
import com.gulimall.product.domain.CategoryBrandRelation;
import com.gulimall.product.service.ICategoryBrandRelationService;
import com.gulimall.common.utils.poi.ExcelUtil;

/**
 * 【请填写功能名称】Controller
 *
 * @author jdjdjd
 * @date 2026-09-19
 */
@RestController
@RequestMapping("/product/categoryBrandRelation")
public class CategoryBrandRelationController extends BaseController
{
    @Autowired
    private ICategoryBrandRelationService categoryBrandRelationService;

    @GetMapping("/category/list")
    public AjaxResult categoryList(@RequestParam("brandId") Long brandId){
        return AjaxResult.success().put("data",categoryBrandRelationService.categoryList(brandId));
    }

    /**
     * 查询【请填写功能名称】列表
     */
    // @PreAuthorize("@ss.hasPermi('product:categoryBrandRelation:list')")
    @GetMapping("/list")
    public TableDataInfo list(CategoryBrandRelation categoryBrandRelation)
    {
        PageDomain pageDomain = TableSupport.buildPageRequest();
        Page<CategoryBrandRelation> page = new Page<>(pageDomain.getPageNum(), pageDomain.getPageSize());
        QueryWrapper<CategoryBrandRelation> wrapper = new QueryWrapper<>(categoryBrandRelation);
        IPage<CategoryBrandRelation> result = categoryBrandRelationService.page(page, wrapper);

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
    // @PreAuthorize("@ss.hasPermi('product:categoryBrandRelation:export')")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, CategoryBrandRelation categoryBrandRelation)
    {
        List<CategoryBrandRelation> list = categoryBrandRelationService.list(new QueryWrapper<>(categoryBrandRelation));
        ExcelUtil<CategoryBrandRelation> util = new ExcelUtil<CategoryBrandRelation>(CategoryBrandRelation.class);
        util.exportExcel(response, list, "【请填写功能名称】数据");
    }

    /**
     * 获取【请填写功能名称】详细信息
     */
    // @PreAuthorize("@ss.hasPermi('product:categoryBrandRelation:query')")
    @GetMapping("/info/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(categoryBrandRelationService.getById(id));
    }

    /**
     * 新增【请填写功能名称】
     */
    // @PreAuthorize("@ss.hasPermi('product:categoryBrandRelation:add')")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.INSERT)
    @PostMapping("/save")
    public AjaxResult add(@RequestBody CategoryBrandRelation categoryBrandRelation)
    {
        return toAjax(categoryBrandRelationService.cascaderSave(categoryBrandRelation));
    }

    /**
     * 修改【请填写功能名称】
     */
    // @PreAuthorize("@ss.hasPermi('product:categoryBrandRelation:edit')")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.UPDATE)
    @PutMapping("/update")
    public AjaxResult edit(@RequestBody CategoryBrandRelation categoryBrandRelation)
    {
        return toAjax(categoryBrandRelationService.updateById(categoryBrandRelation));
    }

    /**
     * 删除【请填写功能名称】
     */
    // @PreAuthorize("@ss.hasPermi('product:categoryBrandRelation:remove')")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.DELETE)
    @PostMapping("/delete")
    public AjaxResult remove(@RequestBody List<Long> ids)
    {
        return toAjax(categoryBrandRelationService.removeByIds(ids));
    }
}