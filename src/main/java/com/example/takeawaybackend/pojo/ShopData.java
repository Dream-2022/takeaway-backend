package com.example.takeawaybackend.pojo;

import java.sql.Timestamp;

public class ShopData {
    private Integer id;
    private String idValue;
    private Integer userId;
    private String name;//商家名称
    private String addressProvince;
    private String addressCity;
    private String addressCounty;
    private String addressDetail;//商家详细地址
    private String profile;//商家简介
    private String logoPhoto;//门店logo
    private String storePhoto;//门店照
    private String inPhoto;//店内照
    private String background;//背景图片
    private Integer saleNum;//销售量
    private String saleStr;
    private Timestamp createAt;//创建时间
    private Float score;//评分
    private Float begin;//起送价
    private String type;//商家类型
    private Float packing;//打包费
    private String state;//是否成功注册，"0">全部,"1">已审核,value="2">未审核,"3">商家停售,"4">保存,"5">管理员停售
    private String takeawayCall;//外卖电话
    private String contactCall;//联系电话
    private String realName;//姓名
    private Float delivery;//配送费
    public Integer getId() {
        return id;
    }

    public String getIdValue() {
        return idValue;
    }

    public void setIdValue(String idValue) {
        this.idValue = idValue;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Float getDelivery() {
        return delivery;
    }

    public void setDelivery(Float delivery) {
        this.delivery = delivery;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddressProvince() {
        return addressProvince;
    }

    public void setAddressProvince(String addressProvince) {
        this.addressProvince = addressProvince;
    }

    public String getAddressCity() {
        return addressCity;
    }

    public void setAddressCity(String addressCity) {
        this.addressCity = addressCity;
    }

    public String getAddressCounty() {
        return addressCounty;
    }

    public void setAddressCounty(String addressCounty) {
        this.addressCounty = addressCounty;
    }

    public String getAddressDetail() {
        return addressDetail;
    }

    public void setAddressDetail(String addressDetail) {
        this.addressDetail = addressDetail;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getLogoPhoto() {
        return logoPhoto;
    }

    public void setLogoPhoto(String logoPhoto) {
        this.logoPhoto = logoPhoto;
    }

    public String getStorePhoto() {
        return storePhoto;
    }

    public void setStorePhoto(String storePhoto) {
        this.storePhoto = storePhoto;
    }

    public String getInPhoto() {
        return inPhoto;
    }

    public void setInPhoto(String inPhoto) {
        this.inPhoto = inPhoto;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public Integer getSaleNum() {
        return saleNum;
    }

    public void setSaleNum(Integer saleNum) {
        this.saleNum = saleNum;
    }

    public String getSaleStr() {
        return saleStr;
    }

    public void setSaleStr(String saleStr) {
        this.saleStr = saleStr;
    }

    public Timestamp getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Timestamp createAt) {
        this.createAt = createAt;
    }

    public Float getScore() {
        return score;
    }

    public void setScore(Float score) {
        this.score = score;
    }

    public Float getBegin() {
        return begin;
    }

    public void setBegin(Float begin) {
        this.begin = begin;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Float getPacking() {
        return packing;
    }

    public void setPacking(Float packing) {
        this.packing = packing;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getTakeawayCall() {
        return takeawayCall;
    }

    public void setTakeawayCall(String takeawayCall) {
        this.takeawayCall = takeawayCall;
    }

    public String getContactCall() {
        return contactCall;
    }

    public void setContactCall(String contactCall) {
        this.contactCall = contactCall;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }
}
