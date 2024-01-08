package com.example.takeawaybackend.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.takeawaybackend.bean.*;
import com.example.takeawaybackend.dao.*;
import com.example.takeawaybackend.pojo.DishData;
import com.example.takeawaybackend.tool.DataResult;
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
        System.out.println("收到的数据是："+dishData.getShopId()+","+dishData.getPageNum());

        IPage<DishShop> page=new Page<>();
        page.setCurrent(dishData.getPageNum());
        page.setSize(7);
        QueryWrapper<DishShop> wrapper=new QueryWrapper<DishShop>()
                .eq("shop_id",dishData.getShopId());
        IPage<DishShop> iPageList = dishShopDao.selectPage(page,wrapper);

        // 根据查询到的 dish_id 列表，查询 dish 表中的相关数据
        System.out.println(iPageList);
        System.out.println(iPageList.getPages());
        System.out.println(iPageList.getRecords());
        System.out.println(iPageList.getCurrent());
        ArrayList<Dish> dishes=new ArrayList<>();
        for (DishShop dishShop : iPageList.getRecords()) {
            QueryWrapper<Dish> wrapper1=new QueryWrapper<Dish>()
                    .eq("id",dishShop.getDishId());
            Dish dish=dishDao.selectOne(wrapper1);
            System.out.println(dish.getPicture());
            if(dish.getPicture()==null){
                dish.setPicture("http://localhost:8080/upload/None.webp");
            }
            //根据dish_id查找categoryName
            dish.setCategoryName(obtainCategoryName(dish.getCategoryId()));
            dish.setPageNum((int) iPageList.getPages());
            dishes.add(dish);
        }
        System.out.println(dishes);
        return DataResult.success(dishes);
    }
    //查找一个店铺的所有商品
    @PostMapping("/selectDishByIdAndShopIdAndNameAndDetail")
    public DataResult selectDishByIdAndShopIdAndNameAndDetail(@RequestBody DishData dishData){
        System.out.println("selectDishByIdAndShopIdAndNameAndDetail");
        System.out.println("收到的数据是："+dishData.getIdValue()+","+dishData.getShopIdValue()+","+dishData.getDishName()+","+dishData.getState()+","+dishData.getDetail());
        QueryWrapper<Dish> wrapper=new QueryWrapper<Dish>()
                .like(!dishData.getIdValue().equals(""),"id",dishData.getIdValue())
                .like(!dishData.getDishName().equals(""),"dish_name",dishData.getDishName())
                .eq(!dishData.getState().equals("0"),"sale_state",dishData.getState())
                .like(!dishData.getDetail().equals(""),"detail",dishData.getDetail()) ;
        List<Dish>dishList=dishDao.selectList(wrapper);
        List<Dish>dishes=new ArrayList<>();
        if(!dishData.getShopIdValue().equals("")){
            for (Dish dish : dishList) {
                QueryWrapper<DishShop> wrapper1=new QueryWrapper<DishShop>()
                        .eq("dish_id",dishData.getIdValue())
                        .eq("shop_id",dishData.getShopIdValue());
                DishShop dishShop=dishShopDao.selectOne(wrapper1);
                System.out.println(dishShop);
                if(dishShop!=null){
                    dishes.add(dish);
                }
            }
        }else{
            dishes=dishList;
        }

        System.out.println(dishList);
        System.out.println(dishes);
        return DataResult.success(dishList);
    }
    //不用分页查找
    @PostMapping("/dishDetailAllNoPage")
    public DataResult dishDetailAllNoPage(@RequestBody DishData dishData){
        System.out.println("dishDetailAllNoPage");
        System.out.println("收到的数据是："+dishData.getShopId());

        QueryWrapper<DishShop> wrapper=new QueryWrapper<DishShop>()
                .eq("shop_id",dishData.getShopId());
        List<DishShop> dishShopList=dishShopDao.selectList(wrapper);
        // 根据查询到的 dish_id 列表，查询 dish 表中的相关数据
        ArrayList<Dish> dishes=new ArrayList<>();
        int flag=0;
        for (DishShop dishShop : dishShopList) {
            System.out.println(flag);

            QueryWrapper<Dish> wrapper1=new QueryWrapper<Dish>()
                    .eq("id",dishShop.getDishId());
            Dish dish=dishDao.selectOne(wrapper1);
            System.out.println(dish);
            if(dish.getPicture()==null){
                dish.setPicture("http://localhost:8080/upload/None.webp");
            }
            //根据dish_id查找categoryName
            dish.setCategoryName(obtainCategoryName(dish.getCategoryId()));
            dishes.add(dish);
            System.out.println(flag);
            flag++;
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
        IPage<Dish> page=new Page<>();
        page.setCurrent(dishData.getPageNum());
        page.setSize(7);

        System.out.println("selectDishByKeyword");
        System.out.println("收到的数据是："+dishData.getShopId()+","+dishData.getSaleState()+","+dishData.getCategoryId()+","+dishData.getSearchInput());
        if(dishData.getSaleState()==null){
            dishData.setSaleState(0);
        }
        if(dishData.getCategoryId()==null){
            dishData.setCategoryId(0);
        }
        if(dishData.getSearchInput()==null){
            dishData.setSearchInput("");
        }
        System.out.println("收到的数据是："+dishData.getShopId()+","+dishData.getSaleState()+","+dishData.getCategoryId()+","+dishData.getSearchInput());
        System.out.println(dishData.getCategoryId()!=0);
        QueryWrapper<Dish> wrapper = new QueryWrapper<Dish>()
                .eq(dishData.getSaleState()!=0,"sale_state", dishData.getSaleState())
                .eq(dishData.getCategoryId()!=0,"category_id", dishData.getCategoryId())
                .exists("SELECT 1 FROM dish_shop WHERE dish_shop.dish_id = dish.id AND dish_shop.shop_id = " + dishData.getShopId());
        wrapper.like(!dishData.getSearchInput().equals(""),"dish_name", dishData.getSearchInput())
                .or().like(!dishData.getSearchInput().equals(""),"detail", dishData.getSearchInput());
        System.out.println("进入selectDishByKeyword查询");
        //加入另一个表的查询条件
        IPage<Dish> iPageList = dishDao.selectPage(page,wrapper);
        System.out.println("selectDishByKeyword查询后");
//        List<Dish> dishList = dishDao.selectList(wrapper);
        List<Dish> dishes=new ArrayList<>();
        System.out.println( iPageList.getRecords());
        for (Dish dish : iPageList.getRecords()) {
            dish.setPageNum((int) iPageList.getPages());
            dish.setCategoryName(obtainCategoryName(dish.getCategoryId()));
            if(dish.getPicture()==null){
                dish.setPicture("http://localhost:8080/upload/None.gif");
            }
            dishes.add(dish);
        }
        System.out.println(dishes);
        return DataResult.success(dishes);
    }
    //在商家内通过输入关键词查找商品
    @PostMapping("/selectDishByValue")
    public DataResult selectDishByValue(@RequestBody DishData dishData){
        System.out.println("selectDishByValue");
        System.out.println("收到的数据是："+dishData.getShopId()+","+dishData.getSearchInput());

        QueryWrapper<DishShop> wrapper=new QueryWrapper<DishShop>()
                .eq("shop_id",dishData.getShopId());

        List<DishShop> dishShopList=dishShopDao.selectList(wrapper);
        List<Dish> dishList=new ArrayList<>();
        for (DishShop dishShop : dishShopList) {
            QueryWrapper<Dish> wrapper1=new QueryWrapper<Dish>()
                    .eq("id",dishShop.getDishId())
                    .like("dish_name",dishData.getSearchInput())
                    .or()
                    .like("detail",dishData.getDetail());
            Dish dish=dishDao.selectOne(wrapper1);
            System.out.println(dish);
            if(dish!=null){
                if(dish.getPicture()==null){
                    dish.setPicture("http://localhost:8080/upload/None.gif");
                }
                dishList.add(dish);
            }

        }
        return DataResult.success(dishList);
    }
    //根据categoryId找到name
    private String obtainCategoryName(int categoryId){
        QueryWrapper<Category> wrapper = new QueryWrapper<Category>()
                .eq("id", categoryId);
        Category category=categoryDao.selectOne(wrapper);
        return category.getCategoryName();
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
        return DataResult.success(dish);
    }
    //通过Id更新菜品saleState
    @PostMapping("/updateDishStateById")
    public DataResult updateDishStateById(@RequestBody DishData dishData){
        System.out.println("updateDishStateById");
        System.out.println("收到的数据是："+dishData.getDishId()+","+dishData.getSaleState());
        //先找到对应的categoryId，然后插入dish表，然后根据shopId插入shop表
        QueryWrapper<Dish> wrapper=new QueryWrapper<Dish>()
                .eq("id",dishData.getDishId());
        Dish dish=new Dish();
        dish.setSaleState(String.valueOf(dishData.getSaleState()));
        int update=dishDao.update(dish,wrapper);
        System.out.println("更新："+update);
        return DataResult.success("更新成功");
    }
    //通过categoryId找到所有的商品
    @PostMapping("/selectDishByCategoryId")
    public DataResult selectDishByCategoryId(@RequestBody DishData dishData){
        System.out.println("selectDishByCategoryId");
        System.out.println("收到的数据是："+dishData.getCategoryId());
        System.out.println(dishData.getAttributeList());
        //先找到对应的categoryId，然后插入dish表，然后根据shopId插入shop表
        QueryWrapper<Dish> wrapper=new QueryWrapper<Dish>()
                .eq("category_id",dishData.getCategoryId());
        List<Dish> dishList=dishDao.selectList(wrapper);
        String categoryName = obtainCategoryName(dishData.getCategoryId());
        for (Dish dish : dishList) {
            dish.setCategoryName(categoryName);
        }
        System.out.println(dishList);
        return DataResult.success(dishList);
    }
    //通过dishId找删除商品
    @PostMapping("/deleteDishById")
    public DataResult deleteDishById(@RequestBody DishData dishData){
        System.out.println("deleteDishById");
        System.out.println("收到的数据是："+dishData.getDishId());
        //先找到对应的categoryId，然后插入dish表，然后根据shopId插入shop表
        QueryWrapper<Dish> wrapper=new QueryWrapper<Dish>()
                .eq("id",dishData.getDishId());
        QueryWrapper<DishAttribute> wrapper1=new QueryWrapper<DishAttribute>()
                .eq("dish_id",dishData.getDishId());
        //删除对应的口味
        List<DishAttribute> attributeList=dishAttributeDao.selectList(wrapper1);
        for (DishAttribute dishAttribute : attributeList) {
            QueryWrapper<DishFlavor> wrapper2=new QueryWrapper<DishFlavor>()
                    .eq("attribute_id",dishAttribute.getId());
            int result2=dishFlavorDao.delete(wrapper2);
            System.out.println("删除result2"+result2);
        }
        int result1=dishAttributeDao.delete(wrapper1);
        System.out.println("删除result1"+result1);
        int result=dishDao.delete(wrapper);
        System.out.println("删除："+result);

        //删除dish_shop表中的关系
        QueryWrapper<DishShop> wrapper2=new QueryWrapper<DishShop>()
                .eq("dish_id",dishData.getDishId());
        int result2=dishShopDao.delete(wrapper2);
        System.out.println("删除2"+result2);
        return DataResult.success("删除成功");
    }
    //更新商品信息
    @PostMapping("/updateDishByAll")
    public DataResult updateDishByAll(@RequestBody DishData dishData){
        System.out.println("updateDishByAll");
        System.out.println("收到的数据是："+dishData.getDishId()+","+dishData.getShopId()+','+dishData.getDishName()+','+dishData.getCategoryName()+','+dishData.getPicture()+','+dishData.getPrice()+','+dishData.getNumber()+','+dishData.getDetail()+','+dishData.getPack()+','+dishData.getWeight()+','+dishData.getMaterial());
        System.out.println(dishData.getAttributeList());
        Dish dish =new Dish();
        QueryWrapper<Category> wrapper=new QueryWrapper<Category>()
                .eq("shop_id",dishData.getShopId())
                .eq("category_name",dishData.getCategoryName());
        Category category=categoryDao.selectOne(wrapper);
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
        QueryWrapper<Dish> wrapper1=new QueryWrapper<Dish>()
                .eq("id",dishData.getDishId());
        int result = dishDao.update(dish,wrapper1);
        System.out.println("更新结果："+result);
        return DataResult.success(dish);
    }
}
