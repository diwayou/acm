package com.diwayou.acm.coupon;

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Item item = (Item) o;

        if (!itemId.equals(item.itemId)) return false;
        return price.equals(item.price);

    }

    @Override
    public int hashCode() {
        int result = itemId.hashCode();
        result = 31 * result + price.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Item{" +
                "itemId=" + itemId +
                ", price=" + price +
                '}';
    }
}
