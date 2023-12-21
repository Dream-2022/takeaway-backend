package com.example.takeawaybackend.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.takeawaybackend.bean.DishOrder;
import com.example.takeawaybackend.dao.DishOrderDao;
import com.example.takeawaybackend.pojo.DishOrderData;
import com.example.takeawaybackend.tool.DataResult;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.example.takeawaybackend.tool.ResponseCode.ORDER_TIMEOUT;

@RestController
@RequestMapping("/api/pre/dishOrder")
//@Controller
//@Service
public class DishOrderController {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    private DishOrderDao dishOrderDao;
    @Autowired
    private Gson gson;

    private IPage<DishOrder> iPageList;

    //添加一个订单信息
    @PostMapping("/addDishOrderOne")
    public DataResult addDishOrderOne(@RequestBody DishOrderData dishOrderData) throws InterruptedException {
        System.out.println("addDishOrderOne"+dishOrderData.getUserId()+","+ dishOrderData.getState()+","+dishOrderData.getAddressValue()+","+dishOrderData.getDishValue()+","+dishOrderData.getNotes()+","+dishOrderData.getTablewareNum());
        DishOrder dishOrder=new DishOrder();
        dishOrder.setState(dishOrderData.getState());
        dishOrder.setUserId(dishOrderData.getUserId());
        dishOrder.setShopId(dishOrderData.getShopId());
        dishOrder.setAddressValue(dishOrderData.getAddressValue());
        dishOrder.setDishValue(dishOrderData.getDishValue());
        dishOrder.setNotes(dishOrderData.getNotes());
        dishOrder.setTablewareNum(dishOrderData.getTablewareNum());
        dishOrder.setPhone(dishOrderData.getPhone());
        Date create_at=new Date();
        dishOrder.setUpdateTime(create_at);
        int insert=dishOrderDao.insert(dishOrder);
        System.out.println("插入结果："+insert);
        QueryWrapper<DishOrder> wrapper=new QueryWrapper<DishOrder>()
                .eq("id",dishOrder.getId());
        System.out.println(dishOrder.getId());
        DishOrder dishOrderX=dishOrderDao.selectOne(wrapper);
        System.out.println(dishOrderX);

        redisTemplate.opsForValue().set("OrderId"+dishOrderX.getId(),"int",15, TimeUnit.MINUTES);
        System.out.println("执行一次插入redis");
        return DataResult.success(dishOrder);

    }
    //根据订单id查找
    @PostMapping("/selectDishOrderById")
    public DataResult selectDishOrderById(@RequestBody DishOrderData dishOrderData){
        System.out.println("selectDishOrderById"+dishOrderData.getId());
        QueryWrapper<DishOrder> wrapper=new QueryWrapper<DishOrder>()
                .eq("id",dishOrderData.getId());
        DishOrder dishOrder=dishOrderDao.selectOne(wrapper);
        System.out.println(dishOrder);
        return DataResult.success(dishOrder);
    }
    //根据订单id取消订单
    @PostMapping("/updateDishOrderCancel")
    public DataResult updateDishOrderCancel(@RequestBody DishOrderData dishOrderData){
        System.out.println("updateDishOrderCancel"+dishOrderData.getId()+","+dishOrderData.getState());
        QueryWrapper<DishOrder> wrapper=new QueryWrapper<DishOrder>()
                .eq("id",dishOrderData.getId());
        DishOrder dishOrder=dishOrderDao.selectOne(wrapper);
        System.out.println(dishOrder.getState());
        if (dishOrder.getState().equals("已取消")) {
            System.out.println("订单已超时，取消");
            return DataResult.fail(ORDER_TIMEOUT);
        }
        dishOrder.setState(dishOrderData.getState());
        int insert=dishOrderDao.update(dishOrder,wrapper);
        System.out.println("修改状态："+insert);
        System.out.println(dishOrder);
        return DataResult.success(dishOrder);
    }
    //分页查找:商家根据商家id和pageNUm查找他的订单
    @PostMapping("/selectDishOrderByShpIdAndPageNum")
    public DataResult selectDishOrderByShpIdAndPageNum(@RequestBody DishOrderData dishOrderData){

        System.out.println("selectDishOrderByShpIdAndPageNum"+dishOrderData.getShopId()+","+dishOrderData.getPageNum()+","+dishOrderData.getId()+","+dishOrderData.getStartTime()+","+dishOrderData.getEndTime()+","+dishOrderData.getPhone());
        IPage<DishOrder> page=new Page<>();
        page.setCurrent(dishOrderData.getPageNum());//设置第几页的
        page.setSize(7);
        System.out.println(dishOrderData.getId()+",dishOrderData.getId()!=0"+(dishOrderData.getId()!=0));
        System.out.println(dishOrderData.getPhone()+",!dishOrderData.getPhone().equals(\"\")"+(!dishOrderData.getPhone().equals("")));
        System.out.println(dishOrderData.getState()+",!dishOrderData.getState().equals(\"全部\")"+(!dishOrderData.getState().equals("全部")));
        System.out.println(dishOrderData.getStartTime()+",!dishOrderData.getStartTime().equals(\"\")"+!dishOrderData.getStartTime().equals(""));
        System.out.println(dishOrderData.getEndTime()+",!dishOrderData.getEndTime().equals(\"\")"+!dishOrderData.getEndTime().equals(""));
        String state=dishOrderData.getState();
        if(dishOrderData.getState().equals("待接单")){
            state="已付款";
        }
        if(dishOrderData.getState().equals("未支付")){
            state="提交订单";
        }
        QueryWrapper<DishOrder> wrapper=new QueryWrapper<DishOrder>()
                .eq("shop_id",dishOrderData.getShopId())
                .like(dishOrderData.getId()!=0,"id",dishOrderData.getId())
                .like(!dishOrderData.getPhone().equals(""),"phone",dishOrderData.getPhone())
                .eq(!dishOrderData.getState().equals("全部订单"),"state",state)
                .ge(!dishOrderData.getStartTime().equals(""),"update_time", dishOrderData.getStartTime())
                .le(!dishOrderData.getEndTime().equals(""),"update_time", dishOrderData.getEndTime())
                .orderByDesc("update_time");
        IPage<DishOrder> iPageList = dishOrderDao.selectPage(page,wrapper);
        System.out.println("selectDishOrderByShpIdAndPageNum");
        System.out.println(iPageList);
        System.out.println(iPageList.getPages());
        System.out.println(iPageList.getRecords());
        System.out.println(iPageList.getCurrent());
        System.out.println("selectDishOrderByShpIdAndPageNum");
        List<DishOrder> dishOrderList=iPageList.getRecords();
        for (DishOrder dishOrder : dishOrderList) {
            if(dishOrder.getNotes()==null&&dishOrder.getNotes().equals("")){
                dishOrder.setNotes("无");
            }
            dishOrder.setPageNum((int) iPageList.getPages());
        }
        System.out.println(dishOrderList);
        return DataResult.success(dishOrderList);
    }




}
