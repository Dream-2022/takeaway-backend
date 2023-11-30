package com.example.takeawaybackend.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.takeawaybackend.bean.Category;
import com.example.takeawaybackend.dao.CategoryDao;
import com.example.takeawaybackend.pojo.CategoryData;
import com.example.takeawaybackend.tool.DataResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/pre/category")
public class CategoryController {
    @Autowired
    private CategoryDao categoryDao;
    @PostMapping("/selectCategoryAll")
    public DataResult selectCategoryAll(@RequestBody CategoryData categoryData){
        System.out.println("selectCategoryAll"+categoryData.getShopId());
        QueryWrapper<Category> wrapper=new QueryWrapper<Category>()
                .eq("shop_id",categoryData.getShopId());
        List<Category> categoryList=categoryDao.selectList(wrapper);
        System.out.println(categoryList);
        return DataResult.success(categoryList);
    }
}
