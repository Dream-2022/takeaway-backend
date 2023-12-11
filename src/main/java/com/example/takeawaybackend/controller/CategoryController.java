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

import static com.example.takeawaybackend.tool.ResponseCode.CATEGORY_REPEAT;

@RestController
@RequestMapping("/api/pre/category")
public class CategoryController {
    @Autowired
    private CategoryDao categoryDao;
    //根据商家id查找全部分类
    @PostMapping("/selectCategoryAll")
    public DataResult selectCategoryAll(@RequestBody CategoryData categoryData){
        System.out.println("selectCategoryAll"+categoryData.getShopId());
        QueryWrapper<Category> wrapper=new QueryWrapper<Category>()
                .eq("shop_id",categoryData.getShopId());
        List<Category> categoryList=categoryDao.selectList(wrapper);
        System.out.println(categoryList);
        return DataResult.success(categoryList);
    }
    //根据商家id和搜索的内容查询分类
    @PostMapping("/selectCategoryByShopIdAndContent")
    public DataResult selectCategoryByShopIdAndContent(@RequestBody CategoryData categoryData){
        System.out.println("selectCategoryByShopIdAndContent"+categoryData.getShopId()+","+categoryData.getCategoryContent());
        QueryWrapper<Category> wrapper=new QueryWrapper<Category>()
                .eq("shop_id",categoryData.getShopId())
                .like(!categoryData.getCategoryContent().equals(""),"category_name",categoryData.getCategoryName());
        List<Category> categoryList=categoryDao.selectList(wrapper);
        System.out.println(categoryList);
        return DataResult.success(categoryList);
    }
    //新建分类名，要判断对应的商家有没有存在该分类名
    @PostMapping("/insertCategory")
    public DataResult insertCategory(@RequestBody CategoryData categoryData){
        System.out.println("insertCategory"+categoryData.getShopId()+","+categoryData.getCategoryName());
        QueryWrapper<Category> wrapper=new QueryWrapper<Category>()
                .eq("shop_id",categoryData.getShopId())
                .eq("category_name",categoryData.getCategoryName());
        Category category=categoryDao.selectOne(wrapper);
        System.out.println(category);
        if ((category != null)) {
            //说明有重复的
            return DataResult.fail(CATEGORY_REPEAT);
        }
        Category categoryX=new Category();
        categoryX.setCategoryName(categoryData.getCategoryName());
        categoryX.setShopId(categoryData.getShopId());
        int insert=categoryDao.insert(categoryX);
        System.out.println("插入："+insert);
        return DataResult.success(categoryX);
    }
    @PostMapping("/updateCategoryName")
    public DataResult updateCategoryName(@RequestBody CategoryData categoryData){
        System.out.println("updateCategoryName"+categoryData.getShopId()+","+categoryData.getCategoryName()+","+categoryData.getId());
        QueryWrapper<Category> wrapper=new QueryWrapper<Category>()
                .eq("shop_id",categoryData.getShopId())
                .eq("category_name",categoryData.getCategoryName());
        List<Category> categoryList=categoryDao.selectList(wrapper);
        System.out.println(categoryList.size());
        if(categoryList.size()==2){
            //说明更改之后的名字是重复的
            return DataResult.fail(CATEGORY_REPEAT);
        }
        //更改categoryName
        Category category=new Category();
        category.setCategoryName(categoryData.getCategoryName());
        QueryWrapper<Category>wrapper1=new QueryWrapper<Category>()
                .eq("id",categoryData.getId());
        categoryDao.update(category,wrapper1);

        return DataResult.success(category);
    }
}
