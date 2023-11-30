package com.example.takeawaybackend.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.takeawaybackend.bean.Collect;
import com.example.takeawaybackend.dao.CollectDao;
import com.example.takeawaybackend.pojo.CollectData;
import com.example.takeawaybackend.tool.DataResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/pre/collect")
public class CollectController {
    @Autowired
    private CollectDao collectDao;
    @PostMapping("/selectCollectByUserIdShopId")
    public DataResult selectCollectByUserIdShopId(@RequestBody CollectData collectData){
        System.out.println("selectCollectByUserIdShopId");
        System.out.println("收到的数据是："+collectData.getShopId()+","+collectData.getUserId());
        QueryWrapper<Collect> wrapper=new QueryWrapper<Collect>()
                .eq("shop_id",collectData.getShopId())
                .eq("user_id",collectData.getUserId());
        Collect collect=collectDao.selectOne(wrapper);
        if(collect!=null){
            return DataResult.success("收藏");
        }
        else
            return DataResult.success("未收藏");
    }
    @PostMapping("/insertCollect")
    public DataResult insertCollect(@RequestBody CollectData collectData){
        System.out.println("insertCollect");
        System.out.println("收到的数据是："+collectData.getShopId()+","+collectData.getUserId());
        Collect collect=new Collect();
        collect.setShopId(collectData.getShopId());
        collect.setUserId(collectData.getUserId());
        int insert = collectDao.insert(collect);
        System.out.println("插入"+insert);
        return DataResult.success(collect);

    }
    @PostMapping("/deleteCollect")
    public DataResult deleteCollect(@RequestBody CollectData collectData){
        System.out.println("deleteCollect");
        System.out.println("收到的数据是："+collectData.getShopId()+","+collectData.getUserId());
        QueryWrapper<Collect> wrapper=new QueryWrapper<Collect>()
                .eq("shop_id",collectData.getShopId())
                .eq("user_id",collectData.getUserId());
        int result = collectDao.delete(wrapper);
        System.out.println("删除"+result);
        return DataResult.success("成功");
    }

}
