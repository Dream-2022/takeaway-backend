package com.example.takeawaybackend.pojo;

import java.util.Date;

public class DishOrderData {
    private Integer id;
    private Integer userId;
    private Integer shopId;
    private String state;
    private String notes;
    private Integer tablewareNum;
    private String addressValue;
    private String dishValue;
    private Date updateTime;
    private Integer pageNum;//分页数
    private String startTime;
    private String endTime;
    private String phone;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setAddressValue(String addressValue) {
        this.addressValue = addressValue;
    }

    public void setDishValue(String dishValue) {
        this.dishValue = dishValue;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
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

    public String getState() {
        return state;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Integer getTablewareNum() {
        return tablewareNum;
    }

    public void setTablewareNum(Integer tablewareNum) {
        this.tablewareNum = tablewareNum;
    }

    public String getAddressValue() {
        return addressValue;
    }

    public String getDishValue() {
        return dishValue;
    }
}
