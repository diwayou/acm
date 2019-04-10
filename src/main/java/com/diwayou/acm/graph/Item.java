package com.diwayou.acm.graph;

import java.math.BigDecimal;

public class Item {

    /**
     * 商品id
     */
    private Long itemId;

    /**
     * 商品价格
     */
    private BigDecimal price;

    public Item() {
    }

    public Item(Long itemId, BigDecimal price) {
        this.itemId = itemId;
        this.price = price;
    }

    public Long getItemId() {
        return itemId;
    }

    public Item setItemId(Long itemId) {
        this.itemId = itemId;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Item setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    @Override
    public String toString() {
        return "Item{" +
                "itemId=" + itemId +
                ", price=" + price +
                '}';
    }
}
