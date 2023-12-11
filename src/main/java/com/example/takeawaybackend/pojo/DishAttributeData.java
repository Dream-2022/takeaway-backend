package com.example.takeawaybackend.pojo;

import com.example.takeawaybackend.bean.DishFlavor;

import java.util.List;

public class DishAttributeData {
    private Integer id;
    private Integer dishId;
    private String attributeName;
    private String checked;
    private List<DishFlavor> flavorList;
    public Integer getId() {
        return id;
    }

    public List<DishFlavor> getFlavorList() {
        return flavorList;
    }

    public void setFlavorList(List<DishFlavor> flavorList) {
        this.flavorList = flavorList;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDishId() {
        return dishId;
    }

    public void setDishId(Integer dishId) {
        this.dishId = dishId;
    }

    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    public String getChecked() {
        return checked;
    }

    public void setChecked(String checked) {
        this.checked = checked;
    }


}
