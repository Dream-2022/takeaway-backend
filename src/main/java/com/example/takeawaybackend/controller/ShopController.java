package com.example.takeawaybackend.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.takeawaybackend.bean.Description;
import com.example.takeawaybackend.bean.Dish;
import com.example.takeawaybackend.bean.DishShop;
import com.example.takeawaybackend.bean.Shop;
import com.example.takeawaybackend.dao.DescriptionDao;
import com.example.takeawaybackend.dao.DishDao;
import com.example.takeawaybackend.dao.DishShopDao;
import com.example.takeawaybackend.dao.ShopDao;
import com.example.takeawaybackend.pojo.DishData;
import com.example.takeawaybackend.pojo.ShopData;
import com.example.takeawaybackend.tool.DataResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/pre/shop")
public class ShopController {
    @Autowired
    private ShopDao shopDao;
    @Autowired
    private DishShopDao dishShopDao;
    @Autowired
    private DescriptionDao descriptionDao;
    @Autowired
    private DishDao dishDao;
    //查找所有商家
    @GetMapping("/detailAll")
    public DataResult detailAll(){
        System.out.println("detailAll");
        List<Shop> shops=shopDao.selectList(null);
        List<Shop> shops2=new ArrayList<>();
        for (Shop shop : shops) {
//            if(shop.getState().equals("0")){
//                continue;
//            }
//            if(shop.getSaleNum()>100000){
//                int saleStr=shop.getSaleNum() / 10000;
//                shop.setSaleStr(saleStr+"万+");
//            }
//            else if(shop.getSaleNum()>1000){
//                int saleStr = shop.getSaleNum() / 1000;
//                shop.setSaleStr(saleStr+"000+");
//            }
//            else{
//                shop.setSaleStr(shop.getSaleNum()+"");
//            }
            //根据description_id,查找名称
            QueryWrapper<Description> wrapper=new QueryWrapper<Description>()
                    .eq("id",shop.getType());
            Description description=descriptionDao.selectOne(wrapper);
            shop.setType(description.getDescriptionName());
            shops2.add(shop);
        }
        return DataResult.success(shops2);
    }
    //根据关键词查找商家
    @PostMapping("/selectShopKeywords")
    public DataResult selectShopKeywords(@RequestBody ShopData shopData){
        System.out.println("selectShopKeywords");
        System.out.println("获取到的信息是："+shopData.getName());
        // 通过商家名查找
        QueryWrapper<Shop> shopWrapper = new QueryWrapper<Shop>()
                .like("name", shopData.getName())
                .orderByDesc("name");
        List<Shop> shops = shopDao.selectList(shopWrapper);
        List<Shop> shops2 = new ArrayList<>();

        for (Shop shop : shops) {
            // 根据description_id，查找名称
            QueryWrapper<Description> wrapper1 = new QueryWrapper<Description>()
                    .eq("id", shop.getType());
            Description description = descriptionDao.selectOne(wrapper1);
            shop.setType(description.getDescriptionName());

            // 通过shop的id找到dishList
            List<Dish> dishList = FindDishListByShopId(shop.getId(), -1);
            shop.setDishList(dishList);
            shops2.add(shop);
        }

        // 通过菜品名查找
        QueryWrapper<Dish> dishWrapper = new QueryWrapper<>();
        dishWrapper.like("dish_name", shopData.getName()).orderByDesc("dish_name");

        List<Dish> dishes = dishDao.selectList(dishWrapper);

        if (dishes != null && !dishes.isEmpty()) {
            // 获取菜品对应的商家ID列表
            List<Integer> dishIds = dishes.stream().map(Dish::getId).collect(Collectors.toList());

            // 查询对应的商家和商家菜品关系
            QueryWrapper<DishShop> dishShopWrapper = new QueryWrapper<>();
            dishShopWrapper.in("dish_id", dishIds);

            List<DishShop> dishShopList = dishShopDao.selectList(dishShopWrapper);

            // 获取商家ID列表
            List<Integer> shopIds = dishShopList.stream().map(DishShop::getShopId).collect(Collectors.toList());

            // 查询商家信息
            QueryWrapper<Shop> shopWrapper2 = new QueryWrapper<>();
            shopWrapper2.in("id", shopIds);

            List<Shop> shops3 = shopDao.selectList(shopWrapper2);

            // 将商家信息关联到对应的菜品对象中
            for (Shop shop : shops3) {
                List<Dish> allDishes = dishShopList.stream()
                        .filter(d -> d.getShopId().equals(shop.getId()))
                        .map(d -> dishes.stream().filter(s -> s.getId().equals(d.getDishId())).findFirst().orElse(null))
                        .filter(Objects::nonNull)
                        .collect(Collectors.toList());
                QueryWrapper<Description> wrapper1 = new QueryWrapper<Description>()
                        .eq("id", shop.getType());
                Description description = descriptionDao.selectOne(wrapper1);
                shop.setType(description.getDescriptionName());
                shop.setDishList(allDishes);
            }

            // 合并两次查询的结果
            shops2.addAll(shops3);

            return DataResult.success(shops2);
        } else {
            return DataResult.success(shops2);
        }

    }
    //根据商家Id（id）查找商家信息
    @PostMapping("/selectById")
    public DataResult selectById(@RequestBody DishData dishData){
        System.out.println("selectById");
        System.out.println("收到的数据是："+dishData.getShopId());

        QueryWrapper<Shop> wrapper=new QueryWrapper<Shop>()
                .eq("id",dishData.getShopId());
        Shop shop=shopDao.selectOne(wrapper);
        QueryWrapper<Description> wrapper1=new QueryWrapper<Description>()
                .eq("id",shop.getType());
        Description description=descriptionDao.selectOne(wrapper1);
        shop.setType(description.getDescriptionName());
        return DataResult.success(shop);
    }
    //保存或确认新建商家
    @PostMapping("/insertShop")
    public DataResult insertShop(@RequestBody ShopData shopData){
        System.out.println("insertShop");
        System.out.println("收到的数据是："+shopData.getName()+","+shopData.getAddressProvince()+","+shopData.getAddressCity()+","+shopData.getAddressCounty()+","+shopData.getAddressDetail()+","+shopData.getType()+","+shopData.getStorePhoto()+","+shopData.getInPhoto()+","+shopData.getLogoPhoto()+","+shopData.getBackground()+","+shopData.getProfile()+","+shopData.getTakeawayCall()+","+shopData.getRealName()+","+shopData.getContactCall()+","+shopData.getBegin()+","+shopData.getPacking()+","+shopData.getState()+","+shopData.getUserId());

        QueryWrapper<Shop> wrapper=new QueryWrapper<Shop>()
                .eq("user_id",shopData.getUserId());
        Shop shopX=shopDao.selectOne(wrapper);
        Shop shop=new Shop();
        shop.setUserId(shopData.getUserId());
        shop.setName(shopData.getName());
        shop.setAddressProvince(shopData.getAddressProvince());
        shop.setAddressCity(shopData.getAddressCity());
        shop.setAddressCounty(shopData.getAddressCounty());
        shop.setAddressDetail(shopData.getAddressDetail());
        shop.setProfile(shopData.getProfile());
        if(!shopData.getLogoPhoto().equals("http://localhost:8080/upload/upload.png")){
            shop.setLogoPhoto(shopData.getLogoPhoto());
        }else{
            shop.setLogoPhoto("http://localhost:8080/upload/None.gif");
        }
        if(!shopData.getStorePhoto().equals("http://localhost:8080/upload/upload.png")){
            shop.setStorePhoto(shopData.getStorePhoto());
        }else{
            shop.setStorePhoto("http://localhost:8080/upload/None.gif");
        }
        if(!shopData.getInPhoto().equals("http://localhost:8080/upload/upload.png")){
            shop.setInPhoto(shopData.getInPhoto());
        }else{
            shop.setInPhoto("http://localhost:8080/upload/None.gif");
        }
        if(!shopData.getBackground().equals("http://localhost:8080/upload/upload.png")){
            shop.setBackground(shopData.getBackground());
        }else{
            shop.setBackground("http://localhost:8080/upload/None.gif");
        }
        shop.setBegin(shopData.getBegin());
        shop.setTakeawayCall(shopData.getTakeawayCall());
        shop.setContactCall(shopData.getContactCall());
        shop.setRealName(shopData.getRealName());
        shop.setType(shopData.getType());
        shop.setPacking(shopData.getPacking());
        shop.setDelivery(shopData.getDelivery());
        shop.setState(shopData.getState());

        //没有商家信息时
        if(shopX==null){
            //插入商家信息,state设置为2
            int result=shopDao.insert(shop);
            System.out.println("插入商家信息"+result);
        }
        //更新商家信息
        else{
            if(shopX.getState().equals("0")&&shopData.getState().equals("2")){
                System.out.println("第一次是确认，第二次是保存");
                shop.setState("0");
            }
            int result=shopDao.update(shop,wrapper);
            System.out.println("更新商家信息"+result);
        }
        return DataResult.success(shop);
    }
    //判断用户是否为商家
    @PostMapping("/selectShopMerchant")
    public DataResult selectShopMerchant(@RequestBody ShopData shopData){
        System.out.println("selectShopMerchant");
        System.out.println("收到的数据是："+shopData.getUserId());

        QueryWrapper<Shop> wrapper=new QueryWrapper<Shop>()
                .eq("user_id",shopData.getUserId());
        Shop shopX=shopDao.selectOne(wrapper);
        //没有商家信息时
        if(shopX==null){
            return DataResult.success("成为商家");
        }
        else{
            return DataResult.success("进入商家端");
        }
    }
    //根据用户id查找商家信息
    @PostMapping("/selectShopByUserId")
    public DataResult selectShopByUserId(@RequestBody ShopData shopData){
        System.out.println("selectShopByUserId");
        System.out.println("收到的数据是："+shopData.getUserId());

        QueryWrapper<Shop> wrapper=new QueryWrapper<Shop>()
                .eq("user_id",shopData.getUserId());
        Shop shopX=shopDao.selectOne(wrapper);
        return DataResult.success(shopX);
    }
    //通过shop的id找到dishList
    public List<Dish> FindDishListByShopId(int shopId,int number){
        QueryWrapper<DishShop> wrapper1=new QueryWrapper<DishShop>()
                .eq("shop_id",shopId);
        List<DishShop> dishShopList=dishShopDao.selectList(wrapper1);
        List<Dish> disheList=new ArrayList<>();
        for (DishShop dishShop : dishShopList) {
            QueryWrapper<Dish> wrapper2=new QueryWrapper<Dish>()
                    .eq("id",dishShop.getDishId());
            Dish dish =dishDao.selectOne(wrapper2);
            disheList.add(dish);
            if(number>0&&--number<=0){
                break;
            }
        }
        return disheList;
    }
}
/*
保存（state2插入），确认（state0插入），

保存{保存（state2更新），确认（state0更新）}
确认{保存（state2->0更新），确认（state0更新）}
 */
