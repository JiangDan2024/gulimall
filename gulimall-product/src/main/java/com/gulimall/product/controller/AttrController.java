package com.gulimall.product.controller;

import java.util.Arrays;
import java.util.List;

import com.gulimall.product.vo.AttrGroupCateVo;
import com.gulimall.product.vo.AttrVo;
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
import com.gulimall.product.domain.Attr;
import com.gulimall.product.service.IAttrService;
import com.gulimall.common.utils.poi.ExcelUtil;

/**
 * 【请填写功能名称】Controller
 *
 * @author jdjdjd
 * @date 2026-09-19
 */
@RestController
@RequestMapping("/product/attr")
public class AttrController extends BaseController
{
    @Autowired
    private IAttrService attrService;

    @GetMapping("/{type}/list")
    public TableDataInfo baseList(@PathVariable("type") String type,Attr attr){
        PageDomain pageDomain = TableSupport.buildPageRequest();
        IPage<AttrGroupCateVo> result = attrService.baseList(attr, pageDomain, type);

        TableDataInfo rspData = new TableDataInfo();
        rspData.setCode(200);
        rspData.setMsg("查询成功");
        rspData.setRows(result.getRecords());
        rspData.setTotal(result.getTotal());
        return rspData;
    }

    /**
     * 查询【请填写功能名称】列表
     */
    // @PreAuthorize("@ss.hasPermi('product:attr:list')")
    @GetMapping("/list")
    public TableDataInfo list(Attr attr)
    {
        PageDomain pageDomain = TableSupport.buildPageRequest();
        Page<Attr> page = new Page<>(pageDomain.getPageNum(), pageDomain.getPageSize());
        QueryWrapper<Attr> wrapper = new QueryWrapper<>(attr);
        IPage<Attr> result = attrService.page(page, wrapper);

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
    // @PreAuthorize("@ss.hasPermi('product:attr:export')")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Attr attr)
    {
        List<Attr> list = attrService.list(new QueryWrapper<>(attr));
        ExcelUtil<Attr> util = new ExcelUtil<Attr>(Attr.class);
        util.exportExcel(response, list, "【请填写功能名称】数据");
    }

    /**
     * 获取【请填写功能名称】详细信息
     */
    // @PreAuthorize("@ss.hasPermi('product:attr:query')")
    @GetMapping("/info/{attrId}")
    public AjaxResult getInfo(@PathVariable("attrId") Long attrId)
    {
        return success(attrService.getAttr(attrId));
    }

    /**
     * 新增【请填写功能名称】
     */
    // @PreAuthorize("@ss.hasPermi('product:attr:add')")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.INSERT)
    @PostMapping("/save")
    public AjaxResult add(@RequestBody AttrVo attr)
    {
        return toAjax(attrService.saveAttr(attr));
    }

    /**
     * 修改【请填写功能名称】
     */
    // @PreAuthorize("@ss.hasPermi('product:attr:edit')")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.UPDATE)
    @PutMapping("/update")
    public AjaxResult edit(@RequestBody AttrVo attr)
    {
        return toAjax(attrService.updateAttr(attr));
    }

    /**
     * 删除【请填写功能名称】
     */
    // @PreAuthorize("@ss.hasPermi('product:attr:remove')")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.DELETE)
    @PostMapping("/delete")
    public AjaxResult remove(@RequestBody List<Long> attrIds)
    {
        return toAjax(attrService.removeByIds(attrIds));
    }
}