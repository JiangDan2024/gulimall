package com.gulimall.product.controller;

import java.util.List;


import com.gulimall.common.valid.AddGroup;
import com.gulimall.common.valid.UpdateGroup;
import com.gulimall.common.valid.UpdateStatusGroup;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gulimall.common.annotation.Log;
import com.gulimall.common.core.controller.BaseController;
import com.gulimall.common.core.domain.AjaxResult;
import com.gulimall.common.core.page.PageDomain;
import com.gulimall.common.core.page.TableDataInfo;
import com.gulimall.common.core.page.TableSupport;
import com.gulimall.common.enums.BusinessType;
import com.gulimall.product.domain.Brand;
import com.gulimall.product.service.IBrandService;
import com.gulimall.common.utils.poi.ExcelUtil;

/**
 * 【请填写功能名称】Controller
 *
 * @author jdjdjd
 * @date 2026-09-16
 */
@RestController
@RequestMapping("/product/brand")
public class BrandController extends BaseController
{
    @Autowired
    private IBrandService brandService;

    /**
     * 查询【请填写功能名称】列表
     */
    // @PreAuthorize("@ss.hasPermi('product:brand:list')")
    @GetMapping("/list")
    public TableDataInfo list(Brand brand)
    {
        PageDomain pageDomain = TableSupport.buildPageRequest();
        IPage<Brand> result = brandService.queryPage(pageDomain, brand);

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
    // @PreAuthorize("@ss.hasPermi('product:brand:export')")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Brand brand)
    {
        List<Brand> list = brandService.list(new QueryWrapper<>(brand));
        ExcelUtil<Brand> util = new ExcelUtil<Brand>(Brand.class);
        util.exportExcel(response, list, "【请填写功能名称】数据");
    }

    /**
     * 获取【请填写功能名称】详细信息
     */
    // @PreAuthorize("@ss.hasPermi('product:brand:query')")
    @GetMapping("/info/{brandId}")
    public AjaxResult getInfo(@PathVariable("brandId") Long brandId)
    {
        return success(brandService.getById(brandId));
    }

    /**
     * 新增【请填写功能名称】
     */
    // @PreAuthorize("@ss.hasPermi('product:brand:add')")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    public AjaxResult add(@Validated(AddGroup.class) @RequestBody Brand brand)
    {
//        if(result.hasErrors()){
//            Map<String,String> errors = new HashMap<>();
//            result.getFieldErrors().forEach((e)->{
//                errors.put(e.getField(),e.getDefaultMessage());
//            });
//            return AjaxResult.error().put("code",400).put("msg","数据校验出现问题").put("data",errors);
//        }else{
            return toAjax(brandService.save(brand));
//        }
    }

    /**
     * 修改【请填写功能名称】
     */
    // @PreAuthorize("@ss.hasPermi('product:brand:edit')")
    @Log(title = "更新", businessType = BusinessType.UPDATE)
    @PutMapping("/edit")
    public AjaxResult edit(@Validated(UpdateGroup.class) @RequestBody Brand brand)
    {
        return toAjax(brandService.updateCascader(brand));
    }

    @Log(title = "更新展示状态", businessType = BusinessType.UPDATE)
    @PutMapping("/editShowStatus")
    public AjaxResult editShowstatus(@Validated(UpdateStatusGroup.class) @RequestBody Brand brand)
    {
        return toAjax(brandService.updateById(brand));
    }

    /**
     * 删除【请填写功能名称】
     */
    // @PreAuthorize("@ss.hasPermi('product:brand:remove')")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    public AjaxResult remove(@RequestBody List<Long> brandIds)
    {
        return toAjax(brandService.removeByIds(brandIds));
    }
}