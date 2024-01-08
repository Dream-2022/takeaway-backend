package com.example.takeawaybackend.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.takeawaybackend.bean.DishOrder;
import com.example.takeawaybackend.bean.Remark;
import com.example.takeawaybackend.bean.User;
import com.example.takeawaybackend.dao.DishOrderDao;
import com.example.takeawaybackend.dao.RemarkDao;
import com.example.takeawaybackend.dao.ShopDao;
import com.example.takeawaybackend.dao.UserDao;
import com.example.takeawaybackend.pojo.RemarkData;
import com.example.takeawaybackend.tool.DataResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/pre/remark")
public class RemarkController {
    @Autowired
    private RemarkDao remarkDao;
    @Autowired
    private DishOrderDao dishOrderDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private ShopDao shopDao;
    //根据条件查询商家的全部评论
    @PostMapping("/selectRemarkAll")
    public DataResult selectRemarkAll(@RequestBody RemarkData remarkData){
        System.out.println("selectRemarkAll:"+remarkData.getShopId()+remarkData.getUserIdValue()+","+remarkData.getOrderIdValue()+","+remarkData.getRemarkStarSelect());
        QueryWrapper<DishOrder> wrapper=new QueryWrapper<DishOrder>()
                .eq("shop_id",remarkData.getShopId());
        List<DishOrder> dishOrderList=dishOrderDao.selectList(wrapper);
        List<Remark> remarkList=new ArrayList<>();
        for (DishOrder dishOrder : dishOrderList) {
            QueryWrapper<Remark> wrapper1=new QueryWrapper<Remark>()
                    .eq("order_id",dishOrder.getId())
                    .like(!remarkData.getUserIdValue().equals(""),"user_id",remarkData.getUserIdValue())
                    .like(!remarkData.getOrderIdValue().equals(""),"order_id",remarkData.getOrderIdValue())
                    .eq(!remarkData.getRemarkStarSelect().equals("0"),"star",remarkData.getRemarkStarSelect());
            Remark remark=remarkDao.selectOne(wrapper1);
            if(remark!=null){
                //找对应的user
                QueryWrapper<User> wrapper2=new QueryWrapper<User>()
                        .eq("id",remark.getUserId());
                User user=userDao.selectOne(wrapper2);
                remark.setUser(user);
                remark.setDishOrder(dishOrder);
                remarkList.add(remark);
            }
        }
        System.out.println(remarkList);
        return DataResult.success(remarkList);
    }

    //根据条件查询用户的全部评论
    @PostMapping("/selectRemarkByUserId")
    public DataResult selectRemarkByUserId(@RequestBody RemarkData remarkData){
        System.out.println("selectRemarkByUserId:"+remarkData.getOrderIdValue()+","+remarkData.getStartTime()+","+remarkData.getEndTime());
        System.out.println(remarkData.getUserId());
        QueryWrapper<Remark> wrapper=new QueryWrapper<Remark>()
                .eq("user_id",remarkData.getUserId())
                .like(!remarkData.getOrderIdValue().equals(""),"order_id",remarkData.getOrderIdValue())
                .ge(!remarkData.getStartTime().equals(""),"create_time", remarkData.getStartTime())
                .le(!remarkData.getEndTime().equals(""),"create_time", remarkData.getEndTime());
        List<Remark> remarkList=remarkDao.selectList(wrapper);
        for (Remark remark : remarkList) {
            QueryWrapper<DishOrder> wrapper1=new QueryWrapper<DishOrder>()
                    .eq("id",remark.getOrderId());
            DishOrder dishOrder=dishOrderDao.selectOne(wrapper1);
            remark.setDishOrder(dishOrder);
        }
        System.out.println(remarkList);
        return DataResult.success(remarkList);
    }

    //插入一条评论
    @PostMapping("/insertRemark")
    public DataResult insertRemark(@RequestBody RemarkData remarkData){
        System.out.println("insertRemark"+remarkData.getUserId()+","+remarkData.getOrderId()+','+remarkData.getContent()+','+remarkData.getImages()+','+remarkData.getStar());

        Remark remark=new Remark();
        remark.setStar(remarkData.getStar());
        remark.setContent(remarkData.getContent());
        remark.setImages(remarkData.getImages());
        remark.setOrderId(remarkData.getOrderId());
        remark.setUserId(remarkData.getUserId());
        Date create_time=new Date();
        remark.setCreateTime(create_time);
        int insert = remarkDao.insert(remark);
        System.out.println(remark);
        System.out.println("插入："+insert);
        return DataResult.success(remark);
    }

}
