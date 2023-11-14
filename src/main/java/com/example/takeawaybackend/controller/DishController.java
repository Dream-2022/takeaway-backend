package com.example.takeawaybackend.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.takeawaybackend.bean.Dish;
import com.example.takeawaybackend.bean.DishShop;
import com.example.takeawaybackend.dao.DishDao;
import com.example.takeawaybackend.dao.DishShopDao;
import com.example.takeawaybackend.pojo.DishData;
import com.example.takeawaybackend.tool.DataResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/pre/dish")
public class DishController {
    @Autowired
    private DishDao dishDao;
    @Autowired
    private DishShopDao dishShopDao;
    @PostMapping("/dishDetailAll")
    public DataResult dishDetailAll(@RequestBody DishData dishData){
        System.out.println("dishDetailAll");
        System.out.println("收到的数据是："+dishData.getShopId());

        QueryWrapper<DishShop> wrapper=new QueryWrapper<DishShop>()
                .eq("shop_id",dishData.getShopId());
        List<DishShop> dishShopList=dishShopDao.selectList(wrapper);
        // 根据查询到的 dish_id 列表，查询 dish 表中的相关数据
        ArrayList<Dish> dishes=new ArrayList<>();
        for (DishShop dishShop : dishShopList) {
            QueryWrapper<Dish> wrapper1=new QueryWrapper<Dish>()
                    .eq("id",dishShop.getDishId());
            Dish dish=dishDao.selectOne(wrapper1);
            if(dish.getPicture() != null && !dish.getPicture().isEmpty()){
            }else{
                dish.setPicture("http://localhost:8080/upload/None.webp");
            }
            dish.setIdStr("myBox"+dish.getId());
            dishes.add(dish);
        }
        System.out.println(dishes);
        return DataResult.success(dishes);
    }
    @PostMapping("/selectDishById")
    public DataResult selectDishById(@RequestBody DishData dishData){
        System.out.println("selectDishById");
        System.out.println("收到的数据是："+dishData.getDishId());

        QueryWrapper<Dish> wrapper=new QueryWrapper<Dish>()
                .eq("shop_id",dishData.getShopId());
        Dish dishList=dishDao.selectOne(wrapper);
        // 根据查询到的 dish_id 列表，查询 dish 表中的相关数据
        ArrayList<Dish> dish=new ArrayList<>();
        System.out.println(dish);
        return DataResult.success(dish);
    }
}
