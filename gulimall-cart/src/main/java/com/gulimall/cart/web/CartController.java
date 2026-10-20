package com.gulimall.cart.web;

import com.gulimall.cart.interceptor.CartInterceptor;
import com.gulimall.cart.service.CartService;
import com.gulimall.cart.vo.Cart;
import com.gulimall.cart.vo.CartItem;
import com.gulimall.cart.vo.UserInfoTo;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.concurrent.ExecutionException;

@Controller
public class CartController {

    @Autowired
    CartService cartService;

    //更新购物项选中/不选中状态
    @GetMapping("/checkItem")
    public String checkItem(@RequestParam Long skuId, @RequestParam Integer check) {
        cartService.checkItem(skuId,check);
        return "redirect:http://cart.gulimall.com/cart.html";
    }

    //更新购物项数量
    @GetMapping("/countItem")
    public String countItem(@RequestParam Long skuId, @RequestParam Integer num) {
        cartService.countItem(skuId,num);
        return "redirect:http://cart.gulimall.com/cart.html";
    }

    //删除购物项
    @GetMapping("/deleteItem")
    public String deleteItem(@RequestParam Long skuId) {
        cartService.deleteItem(skuId);
        return "redirect:http://cart.gulimall.com/cart.html";
    }

    //购物车列表页面
    @GetMapping("/cart.html")
    public String cartListPage(Model model) {
        Cart cart = cartService.getCart();
        model.addAttribute("cart", cart);
        return "cartList";
    }

    //加入购物车
    @GetMapping("/addToCart")
    public String addToCart(@RequestParam Long skuid,
                            @RequestParam Integer num,
                            RedirectAttributes ra) throws ExecutionException, InterruptedException {
        cartService.addToCart(skuid,num);
        //成功后跳转到加入购物车成功的页面
        //存在刷新重复提交的问题，使用重定向解决
//        model.addAttribute("cartItem",cartItem);
        ra.addAttribute("skuId",skuid);
        return "redirect:http://cart.gulimall.com/addToCartSuccess.html";
    }

    @GetMapping("/addToCartSuccess.html")
    public String addToCartSuccess(@RequestParam Long skuId,Model model) {
        CartItem cartItem = cartService.getCartItem(skuId);
        model.addAttribute("cartItem",cartItem);
        return "success";
    }

    @GetMapping("/delete")
    public String deleteCart(){
        //清空购物车
//        cartService.deleteCart();
        return "cartList";
    }

}
