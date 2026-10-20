package com.gulimall.cart.vo;

import java.math.BigDecimal;
import java.util.List;

/**
 * 购物车的一个商品
 */
public class CartItem {
    //skuid
    private String skuId;
    //是否选中
    private Boolean check=false;
    //标题
    private String title;
    //默认图片
    private String image;
    //价格
    private BigDecimal price;
    //销售属性
    private List<String> skuAttrs;
    //商品数量
    private Integer count;
    //商品总价
    private BigDecimal totalPrice;

    public CartItem() {
    }

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public Boolean getCheck() {
        return check;
    }

    public void setCheck(Boolean check) {
        this.check = check;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public List<String> getSkuAttrs() {
        return skuAttrs;
    }

    public void setSkuAttrs(List<String> skuAttrs) {
        this.skuAttrs = skuAttrs;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
    //重写总价，每次获取对象都重新计算
    public BigDecimal getTotalPrice() {
        return this.price.multiply(new BigDecimal(count));
    }
}
