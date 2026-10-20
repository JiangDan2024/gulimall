package com.gulimall.cart.service;

import com.gulimall.cart.vo.Cart;
import com.gulimall.cart.vo.CartItem;

import java.util.concurrent.ExecutionException;

public interface CartService {

    CartItem addToCart(Long skuid, Integer num) throws ExecutionException, InterruptedException;

    CartItem getCartItem(Long skuId);

    Cart getCart();

    void deleteCart(String key);

    void checkItem(Long skuId, Integer check);

    void countItem(Long skuId, Integer num);

    void deleteItem(Long skuId);
}
