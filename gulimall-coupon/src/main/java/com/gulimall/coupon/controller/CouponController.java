package com.gulimall.coupon.controller;

import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import com.gulimall.common.annotation.Log;
import com.gulimall.common.enums.BusinessType;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gulimall.coupon.domain.Coupon;
import com.gulimall.coupon.service.ICouponService;
import com.gulimall.common.core.controller.BaseController;
import com.gulimall.common.core.domain.AjaxResult;
import com.gulimall.common.core.page.TableDataInfo;


/**
 * 【请填写功能名称】Controller
 *
 * @author jiangdan
 * @date 2026-09-12
 */
@RefreshScope
@RestController
@RequestMapping("/coupon/smsCoupon")
public class CouponController extends BaseController
{
    private String prefix = "coupon/smsCoupon";

    @Autowired
    private ICouponService couponService;

    @Value("${coupon.user.name}")
    private String userName;

    @Value("${coupon.user.age}")
    private Integer userAge;

    @GetMapping()
    public String coupon()
    {
        return prefix + "/coupon";
    }

    @RequestMapping("/test")
    public String test(){
        return "userName:"+userName+",age:"+userAge;
    }

    @RequestMapping("/member/list")
    public AjaxResult memberCoupons(){
        Coupon coupon = new Coupon();
        coupon.setCouponName("满100减10");
        return AjaxResult.success().put("coupons", Arrays.asList(coupon));
    }

        /**
         * 查询【请填写功能名称】列表
         */
        @GetMapping("/list")
        @ResponseBody
        public TableDataInfo list(Coupon coupon)
        {
            startPage();
            List<Coupon> list = couponService.list(
                    new QueryWrapper<>(coupon)
            );
            return getDataTable(list);
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
    public AjaxResult addSave(Coupon coupon)
    {
        return couponService.save(coupon)
                ? AjaxResult.success()
                : AjaxResult.error();
    }

    /**
     * 修改【请填写功能名称】
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        Coupon coupon = couponService.getById(id);
        mmap.put("coupon", coupon);
        return prefix + "/edit";
    }

    /**
     * 修改保存【请填写功能名称】
     */
    @Log(title = "【请填写功能名称】", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(Coupon coupon)
    {
        return couponService.updateById(coupon)
                ? AjaxResult.success()
                : AjaxResult.error();
    }

        /**
         * 删除【请填写功能名称】
         */
        @Log(title = "【请填写功能名称】", businessType = BusinessType.DELETE)
        @PostMapping( "/remove")
        @ResponseBody
        public AjaxResult remove(List<String> ids)
        {
            return couponService.removeByIds(ids)
                    ? AjaxResult.success()
                    : AjaxResult.error();
        }
}