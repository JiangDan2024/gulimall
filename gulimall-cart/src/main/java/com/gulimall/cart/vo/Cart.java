package com.gulimall.cart.vo;

import java.math.BigDecimal;
import java.util.List;

/**
 * 整个购物车
 * 需要计算的属性，必须重写他的get方法，保证每次获取属性都会进行计算
 */
public class Cart {
    //购物车里的商品列表
    private List<CartItem> items;
    //商品总数
    private Integer countNum;
    //商品类型数量
    private Integer countType;
    //商品总价
    private BigDecimal totalAmount;
    //商品优惠
    private BigDecimal reduce=new BigDecimal(0.00);

    public Cart() {
    }

    public List<CartItem> getItems() {
        return items;
    }

    public void setItems(List<CartItem> items) {
        this.items = items;
    }
    //重写getCountNum，计算商品总数
    public Integer getCountNum() {
        int count = 0;
        for (CartItem item : items) {
            count += item.getCount();
        }
        return count;
    }

    //重写getCountNum，计算商品类型总数
    public Integer getCountType() {
        int count = 0;
        for (CartItem item : items) {
            count += 1;
        }
        return count;
    }

    //重写getCountNum，计算商品类型总数
    public BigDecimal getTotalAmount() {
        BigDecimal amount = new BigDecimal("0.00");
        for (CartItem item : items) {
            amount = amount.add(item.getTotalPrice());
        }
        amount = amount.subtract(reduce);
        return amount;
    }

    public BigDecimal getReduce() {
        return reduce;
    }

    public void setReduce(BigDecimal reduce) {
        this.reduce = reduce;
    }
}
