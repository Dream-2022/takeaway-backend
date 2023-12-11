package com.example.takeawaybackend.pojo;

import com.example.takeawaybackend.bean.Dish;

import java.util.List;

public class CartData {
    private Integer id;
    private Integer userId;
    private Integer shopId;
    private List<Dish> dishIdList;
    private String detailJson;

    public List<Dish> getDishIdList() {
        return dishIdList;
    }

    public void setDishIdList(List<Dish> dishIdList) {
        this.dishIdList = dishIdList;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public String getDetailJson() {
        return detailJson;
    }

    public void setDetailJson(String detailJson) {
        this.detailJson = detailJson;
    }

    @Override
    public String toString() {
        return "CartData{" +
                "id=" + id +
                ", userId=" + userId +
                ", shopId=" + shopId +
                ", dishIdList=" + dishIdList +
                ", detailJson='" + detailJson + '\'' +
                '}';
    }
}
