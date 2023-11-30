package com.example.takeawaybackend.pojo;

import java.util.List;

public class DishAttributeData {
    private Integer id;
    private Integer dishId;
    private String attributeName;
    private String checked;
    private List<DishFlavorData> flavorList;

    public Integer getId() {
        return id;
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

    public List<DishFlavorData> getFlavorList() {
        return flavorList;
    }

    public void setFlavorList(List<DishFlavorData> flavorList) {
        this.flavorList = flavorList;
    }
}
