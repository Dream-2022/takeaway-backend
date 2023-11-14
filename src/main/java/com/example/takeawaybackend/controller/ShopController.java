package com.example.takeawaybackend.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.takeawaybackend.bean.Shop;
import com.example.takeawaybackend.dao.ShopDao;
import com.example.takeawaybackend.pojo.DishData;
import com.example.takeawaybackend.pojo.ShopData;
import com.example.takeawaybackend.tool.DataResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/pre/shop")
public class ShopController {
    @Autowired
    private ShopDao shopDao;
    //查找所有商家
    @GetMapping("/detailAll")
    public DataResult detailAll(){
        System.out.println("detailAll");
        List<Shop> shops=shopDao.selectList(null);
        List<Shop> shops2=new ArrayList<>();
        for (Shop shop : shops) {
            if(shop.getState().equals("0")){
                continue;
            }
            if(shop.getSaleNum()>100000){
                int saleStr=shop.getSaleNum() / 10000;
                shop.setSaleStr(saleStr+"万+");
            }
            else if(shop.getSaleNum()>1000){
                int saleStr = shop.getSaleNum() / 1000;
                shop.setSaleStr(saleStr+"000+");
            }
            else{
                shop.setSaleStr(shop.getSaleNum()+"");
            }
            shops2.add(shop);
        }
        return DataResult.success(shops2);
    }
    //根据关键词查找商家
    @PostMapping("/selectShop")
    public DataResult selectShop(@RequestBody ShopData shopData){
        System.out.println("selectShop");
        System.out.println("获取到的信息是："+shopData.getName());
        QueryWrapper<Shop> wrapper=new QueryWrapper<Shop>()
                .like("name",shopData.getName())
                .orderByDesc("name");
        List<Shop> shops=shopDao.selectList(wrapper);
        List<Shop> shops2=new ArrayList<>();
        for (Shop shop : shops) {
            if(shop.getState().equals("0")){
                continue;
            }
            if(shop.getSaleNum()>100000){
                int saleStr=shop.getSaleNum() / 10000;
                shop.setSaleStr(saleStr+"万+");
            }
            else if(shop.getSaleNum()>1000){
                int saleStr = shop.getSaleNum() / 1000;
                shop.setSaleStr(saleStr+"000+");
            }
            else{
                shop.setSaleStr(shop.getSaleNum()+"");
            }
            shops2.add(shop);
        }
        return DataResult.success(shops2);
    }
    //根据商家Id（id）查找商家信息
    @PostMapping("/selectById")
    public DataResult selectById(@RequestBody DishData dishData){
        System.out.println("selectById");
        System.out.println("收到的数据是："+dishData.getShopId());

        QueryWrapper<Shop> wrapper=new QueryWrapper<Shop>()
                .eq("id",dishData.getShopId());
        Shop shop=shopDao.selectOne(wrapper);

        return DataResult.success(shop);
    }
}
