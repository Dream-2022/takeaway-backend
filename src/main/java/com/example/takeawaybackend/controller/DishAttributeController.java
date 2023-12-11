package com.example.takeawaybackend.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.takeawaybackend.bean.DishAttribute;
import com.example.takeawaybackend.bean.DishFlavor;
import com.example.takeawaybackend.dao.DishAttributeDao;
import com.example.takeawaybackend.dao.DishFlavorDao;
import com.example.takeawaybackend.pojo.DishAttributeData;
import com.example.takeawaybackend.tool.DataResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/pre/dishAttribute")
public class DishAttributeController {
    @Autowired
    private DishAttributeDao dishAttributeDao;
    @Autowired
    private DishFlavorDao dishFlavorDao;
    //通过dishId找到对应的属性和口味列表
    @PostMapping("/selectDishAttributeByDishId")
    public DataResult selectDishAttributeByDishId(@RequestBody DishAttributeData dishAttributeData){
        System.out.println("selectDishAttributeByDishId");
        System.out.println("收到的数据是："+dishAttributeData.getDishId());

        //不能删除默认地址
        QueryWrapper<DishAttribute> wrapper=new QueryWrapper<DishAttribute>()
                .eq("dish_id",dishAttributeData.getDishId());
        List<DishAttribute> dishAttributeList=dishAttributeDao.selectList(wrapper);
        List<DishAttributeData> dishAttributeDataList=new ArrayList<>();
        for (DishAttribute dishAttribute : dishAttributeList) {
            QueryWrapper<DishFlavor> wrapper1=new QueryWrapper<DishFlavor>()
                    .eq("attribute_id",dishAttribute.getId());
            List<DishFlavor> dishFlavorList=dishFlavorDao.selectList(wrapper1);
            DishAttributeData dishAttributeDataX=new DishAttributeData();
            dishAttributeDataX.setAttributeName(dishAttribute.getAttributeName());
            dishAttributeDataX.setDishId(dishAttribute.getDishId());
            dishAttributeDataX.setChecked(dishAttribute.getChecked());
            dishAttributeDataX.setId(dishAttribute.getId());
            dishAttributeDataX.setFlavorList(dishFlavorList);
            dishAttributeDataList.add(dishAttributeDataX);
        }
        return DataResult.success(dishAttributeDataList);
    }
    @PostMapping("/insertAttributeOne")
    public DataResult insertAttributeOne(@RequestBody DishAttributeData dishAttributeData){
        System.out.println("insertAttributeOne");
        System.out.println("收到的数据是："+dishAttributeData.getDishId()+dishAttributeData.getAttributeName()+dishAttributeData.getChecked()+dishAttributeData.getFlavorList());

        DishAttribute dishAttribute=new DishAttribute();
        dishAttribute.setDishId(dishAttributeData.getDishId());
        dishAttribute.setAttributeName(dishAttributeData.getAttributeName());
        dishAttribute.setChecked(dishAttributeData.getChecked());

        int insert = dishAttributeDao.insert(dishAttribute);
        System.out.println("结果0"+insert);

        for (DishFlavor dishFlavor : dishAttributeData.getFlavorList()) {
            dishFlavor.setAttributeId(dishAttribute.getId());
            System.out.println(dishFlavor);
            int insert1=dishFlavorDao.insert(dishFlavor);
            System.out.println("结果1"+insert1);
        }

        return DataResult.success(dishAttribute);
    }


}
