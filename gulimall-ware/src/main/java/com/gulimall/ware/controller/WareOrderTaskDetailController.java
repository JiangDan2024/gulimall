package com.gulimall.ware.controller;

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
import com.gulimall.ware.domain.WareOrderTaskDetail;
import com.gulimall.ware.service.IWareOrderTaskDetailService;
import com.gulimall.common.utils.poi.ExcelUtil;

/**
 * 【请填写功能名称】Controller
 *
 * @author jdjdjd
 * @date 2026-09-16
 */
@RestController
@RequestMapping("/ware/wareOrderTaskDetail")
public class WareOrderTaskDetailController extends BaseController
{
    @Autowired
    private IWareOrderTaskDetailService wareOrderTaskDetailService;

    /**
     * 查询【请填写功能名称】列表
     */
    // @PreAuthorize("@ss.hasPermi('ware:wareOrderTaskDetail:list')")
    @GetMapping("/list")
    public TableDataInfo list(WareOrderTaskDetail wareOrderTaskDetail)
    {
        PageDomain pageDomain = TableSupport.buildPageRequest();
        Page<WareOrderTaskDetail> page = new Page<>(pageDomain.getPageNum(), pageDomain.getPageSize());
        QueryWrapper<WareOrderTaskDetail> wrapper = new QueryWrapper<>(wareOrderTaskDetail);
        IPage<WareOrderTaskDetail> result = wareOrderTaskDetailService.page(page, wrapper);

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
    // @PreAuthorize("@ss.hasPermi('ware:wareOrderTaskDetail:export')")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, WareOrderTaskDetail wareOrderTaskDetail)
    {
        List<WareOrderTaskDetail> list = wareOrderTaskDetailService.list(new QueryWrapper<>(wareOrderTaskDetail));
        ExcelUtil<WareOrderTaskDetail> util = new ExcelUtil<WareOrderTaskDetail>(WareOrderTaskDetail.class);
        util.exportExcel(response, list, "【请填写功能名称】数据");
    }

    /**
     * 获取【请填写功能名称】详细信息
     */
    // @PreAuthorize("@ss.hasPermi('ware:wareOrderTaskDetail:query')")
    @GetMapping("/info/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(wareOrderTaskDetailService.getById(id));
    }

    /**
     * 新增【请填写功能名称】
     */
    // @PreAuthorize("@ss.hasPermi('ware:wareOrderTaskDetail:add')")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    public AjaxResult add(@RequestBody WareOrderTaskDetail wareOrderTaskDetail)
    {
        return toAjax(wareOrderTaskDetailService.save(wareOrderTaskDetail));
    }

    /**
     * 修改【请填写功能名称】
     */
    // @PreAuthorize("@ss.hasPermi('ware:wareOrderTaskDetail:edit')")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.UPDATE)
    @PutMapping("/edit")
    public AjaxResult edit(@RequestBody WareOrderTaskDetail wareOrderTaskDetail)
    {
        return toAjax(wareOrderTaskDetailService.updateById(wareOrderTaskDetail));
    }

    /**
     * 删除【请填写功能名称】
     */
    // @PreAuthorize("@ss.hasPermi('ware:wareOrderTaskDetail:remove')")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.DELETE)
    @DeleteMapping("/remove/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(wareOrderTaskDetailService.removeByIds(Arrays.asList(ids)));
    }
}