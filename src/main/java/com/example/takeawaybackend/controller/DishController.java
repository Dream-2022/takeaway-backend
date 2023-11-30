package com.example.takeawaybackend.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.takeawaybackend.bean.*;
import com.example.takeawaybackend.dao.*;
import com.example.takeawaybackend.pojo.DishAttributeData;
import com.example.takeawaybackend.pojo.DishData;
import com.example.takeawaybackend.pojo.DishFlavorData;
import com.example.takeawaybackend.tool.DataResult;
import com.example.takeawaybackend.tool.ObtainUsername;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/pre/dish")
public class DishController {
    @Autowired
    private DishDao dishDao;
    @Autowired
    private DishShopDao dishShopDao;
    @Autowired
    private DescriptionDao descriptionDao;
    @Autowired
    private CategoryDao categoryDao;
    @Autowired
    private DishAttributeDao dishAttributeDao;
    @Autowired
    private DishFlavorDao dishFlavorDao;
    //查找一个店铺的所有商品
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
            System.out.println(dish.getPicture());
            if(dish.getPicture().equals("")){
                dish.setPicture("http://localhost:8080/upload/None.webp");
            }
            //根据dish_id查找categoryName
            QueryWrapper<Category> wrapper2=new QueryWrapper<Category>()
                    .eq("id",dish.getCategoryId());
            Category category=categoryDao.selectOne(wrapper2);
            dish.setCategoryName(category.getCategoryName());
            dishes.add(dish);
        }
        System.out.println(dishes);
        return DataResult.success(dishes);
    }
    //查找：根据商品id查找商品信息
    @PostMapping("/selectDishById")
    public DataResult selectDishById(@RequestBody DishData dishData){
        System.out.println("selectDishById");
        System.out.println("收到的数据是："+dishData.getDishId());

        QueryWrapper<Dish> wrapper=new QueryWrapper<Dish>()
                .eq("id",dishData.getDishId());
        Dish dish=dishDao.selectOne(wrapper);
        //根据dish_id查找categoryName
        QueryWrapper<Category> wrapper2=new QueryWrapper<Category>()
                .eq("id",dish.getCategoryId());
        if(dish.getPicture().equals("")){
            dish.setPicture("http://localhost:8080/upload/None.webp");
        }
        Category category=categoryDao.selectOne(wrapper2);
        dish.setCategoryName(category.getCategoryName());
        return DataResult.success(dish);
    }
    //查找：通过分类，状态，关键词获取商品列表
    @PostMapping("/selectDishByKeyword")
    public DataResult selectDishByKeyword(@RequestBody DishData dishData){
        System.out.println("selectDishByKeyword");
        System.out.println("收到的数据是："+dishData.getShopId()+","+dishData.getSaleState()+","+dishData.getCategoryId()+","+dishData.getSearchInput());
        QueryWrapper<Dish> wrapper = new QueryWrapper<Dish>();
        if(dishData.getSaleState()!=0){
            System.out.println("筛选1");
            wrapper.eq("sale_state", dishData.getSaleState());
        }
        if(dishData.getCategoryId()!=0){
            System.out.println("筛选2");
            wrapper.eq("category_id", dishData.getCategoryId());
        }
        if(!dishData.getSearchInput().equals("")){
            System.out.println("筛选3");
            wrapper.like("dish_name", dishData.getSearchInput()).or().like("detail", dishData.getSearchInput());
        }
        List<Dish> dishList = dishDao.selectList(wrapper);
        List<Dish> dishes=new ArrayList<>();
        for (Dish dish : dishList) {
            QueryWrapper<DishShop> wrapper1 = new QueryWrapper<DishShop>()
                    .eq("shop_id", dishData.getShopId())
                    .eq("dish_id",dish.getId());
            DishShop dishShop=dishShopDao.selectOne(wrapper1);
            if(dishShop!=null){
                dishes.add(dish);
            }
        }
        return DataResult.success(dishes);
    }
    //查找所有营业类目
    @GetMapping("/descriptionDetailAll")
    public DataResult descriptionDetailAll(){
        System.out.println("descriptionDetailAll");
        List<Description> descriptionList=descriptionDao.selectList(null);
        return DataResult.success(descriptionList);
    }
    //商家插入商品信息
    @PostMapping("/insertDish")
    public DataResult insertDish(@RequestBody DishData dishData){
        System.out.println("insertDish");
        System.out.println("收到的数据是："+dishData.getShopId()+','+dishData.getDishName()+','+dishData.getCategoryName()+','+dishData.getPicture()+','+dishData.getPrice()+','+dishData.getNumber()+','+dishData.getDetail()+','+dishData.getPack()+','+dishData.getWeight()+','+dishData.getMaterial());
        System.out.println(dishData.getAttributeList());
        //先找到对应的categoryId，然后插入dish表，然后根据shopId插入shop表
        QueryWrapper<Category> wrapper=new QueryWrapper<Category>()
                .eq("shop_id",dishData.getShopId())
                .eq("category_name",dishData.getCategoryName());
        Category category=categoryDao.selectOne(wrapper);

        Dish dish=new Dish();
        //生成一个唯一dish_id
        String newStr = ObtainUsername.obtainUsername();
        dish.setDishName(dishData.getDishName());
        dish.setCategoryId(category.getId());
        dish.setPicture(dishData.getPicture());
        dish.setPrice(dishData.getPrice());
        dish.setNumber(dishData.getNumber());
        dish.setDetail(dishData.getDetail());
        dish.setPack(dishData.getPack());
        dish.setWeight(dishData.getWeight());
        dish.setMaterial(dishData.getMaterial());
        dish.setSale(0);
        int insert = dishDao.insert(dish);
        System.out.println(dish+","+insert);
        // 根据查询到的 dish_id 列表，查询 dish 表中的相关数据
        DishShop dishShop=new DishShop();
        dishShop.setDishId(dish.getId());
        dishShop.setShopId(dishData.getShopId());
        int insert1=dishShopDao.insert(dishShop);
        System.out.println(insert1+","+dishData);

        //属性和口味的添加
        System.out.println(dish.getId());
        System.out.println(dishData.getAttributeList());
        List<DishAttributeData> attributeDataList= dishData.getAttributeList();
        for (DishAttributeData dishAttributeData : attributeDataList) {
            System.out.println(dishAttributeData.getAttributeName());
            System.out.println(dishAttributeData.getChecked());
            DishAttribute dishAttribute=new DishAttribute();
            dishAttribute.setDishId(dish.getId());
            dishAttribute.setChecked(dishAttributeData.getChecked());
            dishAttribute.setAttributeName(dishAttributeData.getAttributeName());

            int result=dishAttributeDao.insert(dishAttribute);
            System.out.println("插入"+dishAttributeData.getAttributeName()+":"+result);

            List<DishFlavorData> dishFlavorDataList=dishAttributeData.getFlavorList();
            for (DishFlavorData dishFlavorData : dishFlavorDataList) {
                DishFlavor dishFlavor=new DishFlavor();
                dishFlavor.setFlavorName(dishFlavorData.getFlavorName());
                dishFlavor.setPrice(dishFlavorData.getPrice());
                dishFlavor.setAttributeId(dishAttribute.getId());
                int result1=dishFlavorDao.insert(dishFlavor);
                System.out.println("插入"+dishFlavor.getFlavorName()+":"+result1);
            }
        }
        return DataResult.success(dish);
    }
}
