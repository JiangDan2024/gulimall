package com.gulimall.cart.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.gulimall.cart.feign.ProductFeignController;
import com.gulimall.cart.interceptor.CartInterceptor;
import com.gulimall.cart.service.CartService;
import com.gulimall.cart.vo.Cart;
import com.gulimall.cart.vo.CartItem;
import com.gulimall.cart.vo.UserInfoTo;
import com.gulimall.common.core.domain.AjaxResult;
import com.gulimall.common.utils.StringUtils;
import com.gulimall.common.vo.SkuInfoVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadPoolExecutor;

@Slf4j
@Service
public class CartServiceImpl implements CartService {
    @Autowired
    StringRedisTemplate stringRedisTemplate;
    @Autowired
    ProductFeignController productFeign;
    @Autowired
    ThreadPoolExecutor executor;

    private final String CART_PREFIX = "gulimall:cart:";

    @Override
    public CartItem addToCart(Long skuid, Integer num) throws ExecutionException, InterruptedException {
        //获取redis中的CartItem,key=CART_PREFIX+userId/userKey
        BoundHashOperations<String, Object, Object> itemOps = getItemOps();
        if(itemOps.get(skuid.toString())==null){
            CartItem cartItem = new CartItem();
            //redis无则新增
            //使用skuid查询skuinfo信息
            CompletableFuture<Void> getInfoFuture = CompletableFuture.runAsync(() -> {
                AjaxResult infoRes = productFeign.getInfo(skuid);
                if (infoRes.isSuccess()) {
                    SkuInfoVo data = infoRes.getData(new TypeReference<SkuInfoVo>() {
                    });
                    cartItem.setSkuId(skuid.toString());
                    cartItem.setTitle(data.getSkuTitle());
                    cartItem.setCount(num);
                    cartItem.setImage(data.getSkuDefaultImg());
                    cartItem.setPrice(data.getPrice());
                }
            }, executor);

            //使用skuid查询skuattr
            CompletableFuture<Void> getSkuAttrFuture = CompletableFuture.runAsync(() -> {
                List<String> values = productFeign.getSkuAttrValues(skuid);
                if (values != null && values.size() > 0) {
                    cartItem.setSkuAttrs(values);
                } else {
                    cartItem.setSkuAttrs(null);
                }
            });
            //远程查询使用异步编排提高速度
            CompletableFuture.allOf(getInfoFuture,getSkuAttrFuture).get();
            //把cartitem放入redis
            String jsonString = JSON.toJSONString(cartItem);
            itemOps.put(skuid.toString(), jsonString);
            return cartItem;
        }else{
            CartItem cartItem = JSON.parseObject(itemOps.get(skuid.toString()).toString(), CartItem.class);
            cartItem.setCount(cartItem.getCount() + num);
            String cartJson = JSON.toJSONString(cartItem);
            itemOps.put(skuid.toString(), cartJson);
            return cartItem;
        }
    }

    @Override
    public CartItem getCartItem(Long skuId) {
        BoundHashOperations<String, Object, Object> itemOps = getItemOps();
        Object o = itemOps.get(skuId.toString());
        if (o != null) {
            String itemStr = o.toString();
            CartItem cartItem = JSON.parseObject(itemStr, CartItem.class);
            return cartItem;
        }

        return null;
    }

    @Override
    public Cart getCart() {
        //用户是否登录
        UserInfoTo userInfoTo = CartInterceptor.userInfoToThreadLocal.get();
        if(StringUtils.isNotEmpty(userInfoTo.getUserId())) {
            //用户已登录
            String cartKey = CART_PREFIX+userInfoTo.getUserId();
            String tempKey = CART_PREFIX+userInfoTo.getUserKey();
            //判断临时购物车是否有数据
            List<CartItem> tempCartItems = getCartItems(tempKey);
            if(tempCartItems!=null && tempCartItems.size()>0){
                //有的话，合并到用户购物车中
                tempCartItems.forEach(item->{
                    try {
                        addToCart(Long.parseLong(item.getSkuId()),item.getCount());
                    } catch (ExecutionException e) {
                        throw new RuntimeException(e);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                });
                //合并结束删除临时购物车

            }
            //重新获取用户购物车数据（合并后的值）
            List<CartItem> cartItems = getCartItems(cartKey);
            Cart cart = new Cart();
            cart.setItems(cartItems);
            return cart;
        }else{
            //未登录状态
            String tempKey = CART_PREFIX+userInfoTo.getUserKey();
            List<CartItem> tempCartItems = getCartItems(tempKey);
            Cart cart = new Cart();
            cart.setItems(tempCartItems);
            return cart;
        }
    }

    @Override
    public void deleteCart(String key) {
        stringRedisTemplate.delete(key);
    }

    @Override
    public void checkItem(Long skuId, Integer check) {
        BoundHashOperations<String, Object, Object> itemOps = getItemOps();
        CartItem cartItem = getCartItem(skuId);
        cartItem.setCheck(check==1?true:false);
        itemOps.put(skuId.toString(),JSON.toJSONString(cartItem));
    }

    @Override
    public void countItem(Long skuId, Integer num) {
        BoundHashOperations<String, Object, Object> itemOps = getItemOps();
        CartItem cartItem = getCartItem(skuId);
        cartItem.setCount(num);
        itemOps.put(skuId.toString(),JSON.toJSONString(cartItem));
    }

    @Override
    public void deleteItem(Long skuId) {
        BoundHashOperations<String, Object, Object> itemOps = getItemOps();
        itemOps.delete(skuId.toString());
    }

    private BoundHashOperations<String, Object, Object> getItemOps() {
        String key = "";
        UserInfoTo userInfoTo = CartInterceptor.userInfoToThreadLocal.get();
        if(StringUtils.isNotEmpty(userInfoTo.getUserId())){
            //用户已登录
            key = CART_PREFIX + userInfoTo.getUserId();
        }else{
            //未登录，分配临时key
            key = CART_PREFIX + userInfoTo.getUserKey();
        }
        //避免每次存取都要穿key使用
        BoundHashOperations<String, Object, Object> itemOps = stringRedisTemplate.boundHashOps(key);
        return itemOps;
    }

    public List<CartItem> getCartItems(String cartKey) {
        BoundHashOperations<String, Object, Object> ops = stringRedisTemplate.boundHashOps(cartKey);
        List<Object> values = ops.values();
        List<CartItem> list = values.stream().map(v -> {
            String cartStr = v.toString();
            CartItem cartItem = JSON.parseObject(cartStr, CartItem.class);
            return cartItem;
        }).toList();

        return list;
    }
}
