package com.gulimall.product.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;
import com.gulimall.common.annotation.Log;
import com.gulimall.common.enums.BusinessType;
import com.gulimall.product.domain.Category;
import com.gulimall.product.service.ICategoryService;
import com.gulimall.common.core.controller.BaseController;
import com.gulimall.common.core.domain.AjaxResult;


/**
 * 【请填写功能名称】Controller
 *
 * @author jiangdan
 * @date 2026-09-12
 */
@RestController
@RequestMapping("/product/pmsCategory")
public class CategoryController extends BaseController
{
    private String prefix = "product/pmsCategory";

    @Autowired
    private ICategoryService categoryService;

    @GetMapping()
    public String category()
    {
        return prefix + "/category";
    }

        /**
         * 查询【请填写功能名称】列表
         */
        @GetMapping("/list/tree")
        @ResponseBody
        public AjaxResult list()
        {
//            startPage();
//            List<Category> list = categoryService.list(
//                    new QueryWrapper<>(category)
//            );
//            return getDataTable(list);
            List<Category> entities = categoryService.listWithTree();

            return AjaxResult.success().put("data", entities);
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
    public AjaxResult addSave(@RequestBody Category category)
    {
        return categoryService.save(category)
                ? AjaxResult.success()
                : AjaxResult.error();
    }

    /*
     * 批量更新
     */
    @PostMapping("/batchEdit")
    @ResponseBody
    public AjaxResult batchEdit(@RequestBody List<Category> list)
    {
        return categoryService.updateBatchById(list)
                ? AjaxResult.success()
                : AjaxResult.error();
    }

    /**
     * 修改【请填写功能名称】
     */
    @GetMapping("/getById/{catId}")
    public AjaxResult getById(@PathVariable Long catId)
    {
        Category category = categoryService.getById(catId);
        return AjaxResult.success().put("data",category);
    }

    /**
     * 修改保存【请填写功能名称】
     */
    @Log(title = "【请填写功能名称】", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(@RequestBody Category category)
    {
        return categoryService.updateCascader(category)
                ? AjaxResult.success()
                : AjaxResult.error();
    }

    /**
     * 删除【请填写功能名称】
     */
    @Log(title = "【请填写功能名称】", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(@RequestBody List<Long> ids)
    {
        return categoryService.removeMenusByIds(ids)>0
                ? AjaxResult.success()
                : AjaxResult.error();
    }
}