package com.gulimall.product.controller;

import java.util.List;

import com.gulimall.product.domain.Attr;
import com.gulimall.product.domain.AttrAttrgroupRelation;
import com.gulimall.product.service.IAttrAttrgroupRelationService;
import com.gulimall.product.service.IAttrService;
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
import com.gulimall.common.annotation.Log;
import com.gulimall.common.core.controller.BaseController;
import com.gulimall.common.core.domain.AjaxResult;
import com.gulimall.common.core.page.PageDomain;
import com.gulimall.common.core.page.TableDataInfo;
import com.gulimall.common.core.page.TableSupport;
import com.gulimall.common.enums.BusinessType;
import com.gulimall.product.domain.AttrGroup;
import com.gulimall.product.service.IAttrGroupService;
import com.gulimall.common.utils.poi.ExcelUtil;

/**
 * 【请填写功能名称】Controller
 *
 * @author jdjdjd
 * @date 2026-09-16
 */
@RestController
@RequestMapping("/product/attrGroup")
public class AttrGroupController extends BaseController
{
    @Autowired
    private IAttrGroupService attrGroupService;

    @Autowired
    IAttrService attrService;
    @Autowired
    private IAttrAttrgroupRelationService iAttrAttrgroupRelationService;


    //分组与属性的关联查询，当前分组下关联的所有属性
    @GetMapping("/{groupId}/attr/relation")
    public AjaxResult attrRelation(@PathVariable("groupId") Long groupId){
        return AjaxResult.success().put("data",attrService.queryGroupRelation(groupId));
    }
    //查询当前分组能够新增的属性关联，应满足：在同一分类下、且未被当前分类下其他分组关联
    @GetMapping("/{groupId}/noattr/relation")
    public TableDataInfo noAttrRelation(@PathVariable("groupId") Long groupId,AttrGroup attrGroup){
        PageDomain pageDomain = TableSupport.buildPageRequest();
        IPage<Attr> result = attrGroupService.noAttrRelation(pageDomain, attrGroup,groupId);

        TableDataInfo rspData = new TableDataInfo();
        rspData.setCode(200);
        rspData.setMsg("查询成功");
        rspData.setRows(result.getRecords());
        rspData.setTotal(result.getTotal());
        return rspData;
    }
    //新增分组和属性的关联
    @PostMapping("/attr/relation")
    public AjaxResult addRelation(@RequestBody List<AttrAttrgroupRelation> relationList){
        return toAjax(iAttrAttrgroupRelationService.addRelation(relationList));
    }
    /**
     * 查询【请填写功能名称】列表
     */
    // @PreAuthorize("@ss.hasPermi('product:attrGroup:list')")
    @GetMapping("/list")
    public TableDataInfo list(AttrGroup attrGroup)
    {
        PageDomain pageDomain = TableSupport.buildPageRequest();
        IPage<AttrGroup> result = attrGroupService.queryPage(pageDomain, attrGroup);

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
    // @PreAuthorize("@ss.hasPermi('product:attrGroup:export')")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, AttrGroup attrGroup)
    {
        List<AttrGroup> list = attrGroupService.list(new QueryWrapper<>(attrGroup));
        ExcelUtil<AttrGroup> util = new ExcelUtil<AttrGroup>(AttrGroup.class);
        util.exportExcel(response, list, "【请填写功能名称】数据");
    }

    /**
     * 获取【请填写功能名称】详细信息
     */
    // @PreAuthorize("@ss.hasPermi('product:attrGroup:query')")
    @GetMapping("/info/{attrGroupId}")
    public AjaxResult getInfo(@PathVariable("attrGroupId") Long attrGroupId)
    {
        return success(attrGroupService.getInfo(attrGroupId));
    }

    /**
     * 新增【请填写功能名称】
     */
    // @PreAuthorize("@ss.hasPermi('product:attrGroup:add')")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    public AjaxResult add(@RequestBody AttrGroup attrGroup)
    {
        return toAjax(attrGroupService.save(attrGroup));
    }

    /**
     * 修改【请填写功能名称】
     */
    // @PreAuthorize("@ss.hasPermi('product:attrGroup:edit')")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.UPDATE)
    @PutMapping("/edit")
    public AjaxResult edit(@RequestBody AttrGroup attrGroup)
    {
        return toAjax(attrGroupService.updateById(attrGroup));
    }

    @PostMapping("/attr/relation/delete")
    public AjaxResult relationDelete(@RequestBody List<AttrAttrgroupRelation> list){
        return toAjax(iAttrAttrgroupRelationService.relationDelete(list));
    }
    /**
     * 删除【请填写功能名称】
     */
    // @PreAuthorize("@ss.hasPermi('product:attrGroup:remove')")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    public AjaxResult remove(@RequestBody List<Long> attrGroupIds)
    {
        return toAjax(attrGroupService.removeByIds(attrGroupIds));
    }
}