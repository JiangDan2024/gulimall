package com.gulimall.cart.web;

import com.gulimall.cart.interceptor.CartInterceptor;
import com.gulimall.cart.vo.Cart;
import com.gulimall.cart.vo.UserInfoTo;
import com.gulimall.common.constant.AuthConstants;
import com.gulimall.common.vo.MemberVo;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CartController {

    //购物车列表页面
    @GetMapping("/cart.html")
    public String cartListPage(HttpSession session) {
        UserInfoTo userInfoTo = CartInterceptor.userInfoToThreadLocal.get();
        if(userInfoTo.getTempUser()){
            //是临时用户
        }
        return "cartList";
    }

    //加入购物车
    @GetMapping("/addToCart")
    public String addToCart(@RequestParam Long skuid,@RequestParam Integer num) {
        //成功后跳转到加入购物车成功的页面
        return "success";
    }
}
