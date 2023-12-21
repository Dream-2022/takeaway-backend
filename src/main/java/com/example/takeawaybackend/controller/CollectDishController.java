package com.example.takeawaybackend.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.takeawaybackend.bean.CollectDish;
import com.example.takeawaybackend.dao.CollectDishDao;
import com.example.takeawaybackend.pojo.CollectDishData;
import com.example.takeawaybackend.tool.DataResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/pre/collectDish")
public class CollectDishController {
    @Autowired
    private CollectDishDao collectDishDao;
    @PostMapping("/selectCollectDishByIdAndDishId")
    public DataResult selectCollectDishByIdAndDishId(@RequestBody CollectDishData collectDishData){
        System.out.println("selectCollectDishByIdAndDishId");
        System.out.println("收到的数据是："+collectDishData.getDishId()+","+collectDishData.getUserId());
        QueryWrapper<CollectDish> wrapper=new QueryWrapper<CollectDish>()
                .eq("dish_id",collectDishData.getDishId())
                .eq("user_id",collectDishData.getUserId());
        CollectDish collectDish=collectDishDao.selectOne(wrapper);
        if(collectDish!=null){
            return DataResult.success("收藏了商品");
        }
        else
            return DataResult.success("未收藏商品");
    }
    @PostMapping("/addDishCollect")
    public DataResult addDishCollect(@RequestBody CollectDishData collectDishData) {
        System.out.println("addDishCollect");
        System.out.println("收到的数据是：" + collectDishData.getDishId() + "," + collectDishData.getUserId());
        CollectDish collectDish = new CollectDish();
        collectDish.setDishId(collectDishData.getDishId());
        collectDish.setUserId(collectDishData.getUserId());
        int insert = collectDishDao.insert(collectDish);
        System.out.println("插入结果" + insert);
        System.out.println(collectDish);
        return DataResult.success(collectDish);
    }
    @PostMapping("/deleteDishCollect")
    public DataResult deleteDishCollect(@RequestBody CollectDishData collectDishData) {
        System.out.println("deleteDishCollect");
        System.out.println("收到的数据是：" + collectDishData.getDishId() + "," + collectDishData.getUserId());
        QueryWrapper<CollectDish> wrapper=new QueryWrapper<CollectDish>()
                .eq("dish_id",collectDishData.getDishId())
                .eq("user_id",collectDishData.getUserId());
        int insert=collectDishDao.delete(wrapper);
        System.out.println("删除结果" + insert);

        return DataResult.success("删除成功");
    }
}
