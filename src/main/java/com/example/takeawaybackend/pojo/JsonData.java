package com.example.takeawaybackend.pojo;

import com.example.takeawaybackend.bean.Cart;

import java.util.List;

public class JsonData {
    private List<Cart> cartList;

    public List<Cart> getCartList() {
        return cartList;
    }

    public void setCartList(List<Cart> cartList) {
        this.cartList = cartList;
    }
}
