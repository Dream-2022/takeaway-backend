package com.example.takeawaybackend.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.takeawaybackend.bean.Report;
import com.example.takeawaybackend.bean.Shop;
import com.example.takeawaybackend.bean.User;
import com.example.takeawaybackend.dao.ReportDao;
import com.example.takeawaybackend.dao.ShopDao;
import com.example.takeawaybackend.dao.UserDao;
import com.example.takeawaybackend.pojo.ReportData;
import com.example.takeawaybackend.tool.DataResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/pre/remark")
public class ReportController {
    @Autowired
    private ReportDao reportDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private ShopDao shopDao;
    //插入一条举报
    @PostMapping("/insertReport")
    public DataResult insertReport(@RequestBody ReportData reportData){
        System.out.println("insertReport"+reportData.getState()+","+reportData.getContent()+","+reportData.getShopId());

        Report report=new Report();
        report.setUserId(reportData.getUserId());
        report.setContent(reportData.getContent());
        report.setShopId(reportData.getShopId());
        int insert = reportDao.insert(report);
        System.out.println(report);
        System.out.println("插入："+insert);
        return DataResult.success(report);
    }
    //从数据库获取对应状态的举报
    @PostMapping("/selectReportByState")
    public DataResult selectReportByState(@RequestBody ReportData reportData){
        System.out.println("selectReportByState"+reportData.getUserId());

        QueryWrapper<Report> wrapper=new QueryWrapper<Report>()
                .eq(!reportData.getState().equals("0"),"state",reportData.getState());
        List<Report> reportList=reportDao.selectList(wrapper);
        System.out.println(reportList);
        for (Report report : reportList) {
            System.out.println(report);
            //通过user_id获取到user，通过shop_id获取到shop
            QueryWrapper<User> wrapper1=new QueryWrapper<User>()
                    .eq("id",report.getUserId());
            User user=userDao.selectOne(wrapper1);
            report.setUser(user);
            QueryWrapper<Shop> wrapper2=new QueryWrapper<Shop>()
                    .eq("id",report.getShopId());
            Shop shop=shopDao.selectOne(wrapper2);
            report.setShop(shop);
        }
        return DataResult.success(reportList);
    }
    //点击通过举报，商家状态改为5（管理员停售），举报状态改为审核成功（state
    @PostMapping("/updateByState")
    public DataResult updateByState(@RequestBody ReportData reportData){
        System.out.println("updateByState"+reportData.getId()+","+reportData.getShopId()+","+reportData.getState());

        QueryWrapper<Report> wrapper1=new QueryWrapper<Report>()
                .eq("id",reportData.getId());
        Report report=new Report();
        report.setState("2");
        int update1=reportDao.update(report,wrapper1);
        System.out.println("更新1："+update1);
        QueryWrapper<Shop> wrapper2=new QueryWrapper<Shop>()
                .eq("id",reportData.getShopId());
        Shop shop=new Shop();
        shop.setState(report.getState());
        int update2=shopDao.update(shop,wrapper2);
        System.out.println("更新2："+update2);

        return DataResult.success("更新成功");
    }

}
