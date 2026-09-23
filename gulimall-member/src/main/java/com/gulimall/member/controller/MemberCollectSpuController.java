package com.gulimall.member.controller;

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
import com.gulimall.member.domain.MemberCollectSpu;
import com.gulimall.member.service.IMemberCollectSpuService;
import com.gulimall.common.utils.poi.ExcelUtil;

/**
 * 【请填写功能名称】Controller
 *
 * @author jdjdjd
 * @date 2026-09-20
 */
@RestController
@RequestMapping("/member/memberCollectSpu")
public class MemberCollectSpuController extends BaseController
{
    @Autowired
    private IMemberCollectSpuService memberCollectSpuService;

    /**
     * 查询【请填写功能名称】列表
     */
    // @PreAuthorize("@ss.hasPermi('member:memberCollectSpu:list')")
    @GetMapping("/list")
    public TableDataInfo list(MemberCollectSpu memberCollectSpu)
    {
        PageDomain pageDomain = TableSupport.buildPageRequest();
        Page<MemberCollectSpu> page = new Page<>(pageDomain.getPageNum(), pageDomain.getPageSize());
        QueryWrapper<MemberCollectSpu> wrapper = new QueryWrapper<>(memberCollectSpu);
        IPage<MemberCollectSpu> result = memberCollectSpuService.page(page, wrapper);

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
    // @PreAuthorize("@ss.hasPermi('member:memberCollectSpu:export')")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, MemberCollectSpu memberCollectSpu)
    {
        List<MemberCollectSpu> list = memberCollectSpuService.list(new QueryWrapper<>(memberCollectSpu));
        ExcelUtil<MemberCollectSpu> util = new ExcelUtil<MemberCollectSpu>(MemberCollectSpu.class);
        util.exportExcel(response, list, "【请填写功能名称】数据");
    }

    /**
     * 获取【请填写功能名称】详细信息
     */
    // @PreAuthorize("@ss.hasPermi('member:memberCollectSpu:query')")
    @GetMapping("/info/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(memberCollectSpuService.getById(id));
    }

    /**
     * 新增【请填写功能名称】
     */
    // @PreAuthorize("@ss.hasPermi('member:memberCollectSpu:add')")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.INSERT)
    @PostMapping("/save")
    public AjaxResult add(@RequestBody MemberCollectSpu memberCollectSpu)
    {
        return toAjax(memberCollectSpuService.save(memberCollectSpu));
    }

    /**
     * 修改【请填写功能名称】
     */
    // @PreAuthorize("@ss.hasPermi('member:memberCollectSpu:edit')")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.UPDATE)
    @PutMapping("/update")
    public AjaxResult edit(@RequestBody MemberCollectSpu memberCollectSpu)
    {
        return toAjax(memberCollectSpuService.updateById(memberCollectSpu));
    }

    /**
     * 删除【请填写功能名称】
     */
    // @PreAuthorize("@ss.hasPermi('member:memberCollectSpu:remove')")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.DELETE)
    @PostMapping("/delete")
    public AjaxResult remove(@RequestBody List<Long> ids)
    {
        return toAjax(memberCollectSpuService.removeByIds(ids));
    }
}