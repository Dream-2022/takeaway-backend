package com.example.takeawaybackend.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.takeawaybackend.bean.Shop;
import com.example.takeawaybackend.dao.ShopDao;
import com.example.takeawaybackend.pojo.ShopData;
import com.example.takeawaybackend.tool.DataResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/pre/shop")
public class ShopController {
    @Autowired
    private ShopDao shopDao;
    @GetMapping("/detailAll")
    public DataResult detailAll(){
        System.out.println("detailAll");
        List<Shop> shops=shopDao.selectList(null);
        for (Shop shop : shops) {
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
        }
        return DataResult.success(shops);
    }
    @GetMapping("/selectShop")
    public DataResult selectShop(@RequestBody ShopData shopData){
        System.out.println("selectShop");
        QueryWrapper<Shop> wrapper=new QueryWrapper<Shop>()
                .like("name",shopData.getName());
        List<Shop> shops=shopDao.selectList(wrapper);
        for (Shop shop : shops) {
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
        }
        return DataResult.success(shops);
    }
}
