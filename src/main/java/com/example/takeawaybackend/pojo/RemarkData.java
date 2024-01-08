package com.example.takeawaybackend.pojo;

import java.util.Date;

public class RemarkData {
    private Integer id;
    private Integer orderId;
    private String orderIdValue;
    private Integer userId;
    private String userIdValue;
    private String content;
    private Float star;
    private String images;
    private Date createTime;
    private Integer shopId;
    private String remarkStarSelect;
    private String shopName;
    private String startTime;
    private String endTime;

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }


    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getOrderIdValue() {
        return orderIdValue;
    }

    public void setOrderIdValue(String orderIdValue) {
        this.orderIdValue = orderIdValue;
    }

    public String getUserIdValue() {
        return userIdValue;
    }

    public void setUserIdValue(String userIdValue) {
        this.userIdValue = userIdValue;
    }

    public String getRemarkStarSelect() {
        return remarkStarSelect;
    }

    public void setRemarkStarSelect(String remarkStarSelect) {
        this.remarkStarSelect = remarkStarSelect;
    }

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Float getStar() {
        return star;
    }

    public void setStar(Float star) {
        this.star = star;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }
}
