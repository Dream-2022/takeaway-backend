package com.example.takeawaybackend.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.takeawaybackend.bean.Cart;
import com.example.takeawaybackend.bean.Shop;
import com.example.takeawaybackend.dao.CartDao;
import com.example.takeawaybackend.dao.ShopDao;
import com.example.takeawaybackend.pojo.CartData;
import com.example.takeawaybackend.tool.DataResult;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/pre/cart")
public class CartController {
    @Autowired
    private CartDao cartDao;
    @Autowired
    private ShopDao shopDao;
    @Autowired
    private Gson gson;

    //根据userId查找全部购物车
    @PostMapping("/selectCartAll")
    public DataResult selectCartAll(@RequestBody CartData cartData){
        System.out.println("selectCartAll"+cartData.getUserId());
        QueryWrapper<Cart> wrapper=new QueryWrapper<Cart>()
                .eq("user_id",cartData.getUserId());
        List<Cart> cartList=cartDao.selectList(wrapper);
        for (Cart cart : cartList) {
            QueryWrapper<Shop> wrapper1=new QueryWrapper<Shop>()
                    .eq("id",cart.getShopId());
            Shop shop=shopDao.selectOne(wrapper1);
            cart.setShopName(shop.getName());
        }
        System.out.println(cartList);
        return DataResult.success(cartList);
    }
    //添加一个购物车
    @PostMapping("/addCart")
    public DataResult addCart(@RequestBody CartData cartData){
        System.out.println("addCart"+cartData.getUserId()+","+cartData.getShopId()+","+cartData.getDetailJson());
        QueryWrapper<Cart> wrapper=new QueryWrapper<Cart>()
                .eq("user_id",cartData.getUserId())
                .eq("shop_id",cartData.getShopId());

        Cart cart=cartDao.selectOne(wrapper);
        if(cart!=null){
            //先删除
            int result=cartDao.delete(wrapper);
            System.out.println("删除结果："+result);
        }
        //在加入
        Cart cartX=new Cart();
        cartX.setUserId(cartData.getUserId());
        cartX.setShopId(cartData.getShopId());
        cartX.setDetailJson(cartData.getDetailJson());
        int result=cartDao.insert(cartX);
        System.out.println("插入结果："+result);
//        String JsonListStr=cartData.getDetailJson().substring(1, cartData.getDetailJson().length() - 1);
        String JsonListStr=cartData.getDetailJson();
        System.out.println(JsonListStr);
        List<CartData> items =(List<CartData>) JSON.parse(JsonListStr);

        System.out.println("转换为对象的数据");
        System.out.println(items);

        System.out.println(items.get(0));
        System.out.println(items.get(0) instanceof Map);

        List <CartData> items2=(List<CartData>) JSON.parse(items.toString());
        System.out.println(items2);
        Map<String, Object> map= (Map<String, Object>) items.get(0);
        System.out.println(map.get("userId"));
        System.out.println(map.get("shopId"));
//        System.out.println(items.get(0).get());
//        System.out.println(items.get(0).getDishIdList());
        return DataResult.success(cartX);
    }
    //删除一个购物车
    @PostMapping("/deleteCart")
    public DataResult deleteCart(@RequestBody CartData cartData){
        System.out.println("deleteCart"+cartData.getUserId()+","+cartData.getShopId());
        QueryWrapper<Cart> wrapper=new QueryWrapper<Cart>()
                .eq("user_id",cartData.getUserId())
                .eq("shop_id",cartData.getShopId());

        int result=cartDao.delete(wrapper);
        System.out.println("删除结果："+result);
        return DataResult.success(result);
    }
    //根据userId删除全部购物车
    @PostMapping("/deleteAllCartByUserId")
    public DataResult deleteAllCartByUserId(@RequestBody CartData cartData){
        System.out.println("deleteAllCartByUserId"+cartData.getUserId());
        QueryWrapper<Cart> wrapper=new QueryWrapper<Cart>()
                .eq("user_id",cartData.getUserId());
        int insert=cartDao.delete(wrapper);
        System.out.println("删除结果"+insert);
        return DataResult.success("删除成功");
    }
}
