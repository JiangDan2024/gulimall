package com.gulimall.ware.controller;

import java.time.LocalDateTime;
import java.util.List;

import com.gulimall.ware.vo.MergeVo;
import com.gulimall.ware.vo.PurchaseDoneVo;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
import com.gulimall.ware.domain.Purchase;
import com.gulimall.ware.service.IPurchaseService;
import com.gulimall.common.utils.poi.ExcelUtil;

/**
 * 【请填写功能名称】Controller
 *
 * @author jdjdjd
 * @date 2026-09-22
 */
@RestController
@RequestMapping("/ware/purchase")
public class PurchaseController extends BaseController
{
    @Autowired
    private IPurchaseService purchaseService;

    //完成采购单
    @PostMapping("/done")
    public AjaxResult finished(@RequestBody PurchaseDoneVo purchaseDoneVo){
        return toAjax(purchaseService.done(purchaseDoneVo));
    }

    //PostMan模拟员工领取采购单
    @PostMapping("/received")
    public AjaxResult received(@RequestBody List<Long> purchaseIds){
        return toAjax(purchaseService.received(purchaseIds));
    }

    //合并采购单
    @PostMapping("/merge")
    public AjaxResult merge(@RequestBody MergeVo vos){
        return toAjax(purchaseService.merge(vos));
    }
    //查询新建、已分配状态的采购单
    @GetMapping("/unreceive/list")
    public TableDataInfo unreceiveList()
    {
        IPage<Purchase> result = purchaseService.unreceiveList();

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
    // @PreAuthorize("@ss.hasPermi('ware:purchase:list')")
    @GetMapping("/list")
    public TableDataInfo list(Purchase purchase)
    {
        PageDomain pageDomain = TableSupport.buildPageRequest();
        Page<Purchase> page = new Page<>(pageDomain.getPageNum(), pageDomain.getPageSize());
        QueryWrapper<Purchase> wrapper = new QueryWrapper<>(purchase);
        IPage<Purchase> result = purchaseService.page(page, wrapper);

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
    // @PreAuthorize("@ss.hasPermi('ware:purchase:export')")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Purchase purchase)
    {
        List<Purchase> list = purchaseService.list(new QueryWrapper<>(purchase));
        ExcelUtil<Purchase> util = new ExcelUtil<Purchase>(Purchase.class);
        util.exportExcel(response, list, "【请填写功能名称】数据");
    }

    /**
     * 获取【请填写功能名称】详细信息
     */
    // @PreAuthorize("@ss.hasPermi('ware:purchase:query')")
    @GetMapping("/info/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(purchaseService.getById(id));
    }

    /**
     * 新增【请填写功能名称】
     */
    // @PreAuthorize("@ss.hasPermi('ware:purchase:add')")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.INSERT)
    @PostMapping("/save")
    public AjaxResult add(@RequestBody Purchase purchase)
    {
        purchase.setUpdateTime(LocalDateTime.now());
        purchase.setCreateTime(LocalDateTime.now());
        return toAjax(purchaseService.save(purchase));
    }

    /**
     * 修改【请填写功能名称】
     */
    // @PreAuthorize("@ss.hasPermi('ware:purchase:edit')")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.UPDATE)
    @PutMapping("/update")
    public AjaxResult edit(@RequestBody Purchase purchase)
    {
        purchase.setUpdateTime(LocalDateTime.now());
        return toAjax(purchaseService.updateById(purchase));
    }

    /**
     * 删除【请填写功能名称】
     */
    // @PreAuthorize("@ss.hasPermi('ware:purchase:remove')")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.DELETE)
    @PostMapping("/delete")
    public AjaxResult remove(@RequestBody List<Long> ids)
    {
        return toAjax(purchaseService.removeByIds(ids));
    }
}