package com.example.takeawaybackend.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.takeawaybackend.bean.Address;
import com.example.takeawaybackend.bean.RcDistrict;
import com.example.takeawaybackend.dao.AddressDao;
import com.example.takeawaybackend.dao.RcDistrictDao;
import com.example.takeawaybackend.pojo.AddressData;
import com.example.takeawaybackend.pojo.LoginData;
import com.example.takeawaybackend.tool.DataResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pre/address")
public class AddressController {
    @Autowired
    private AddressDao addressDao;
    @Autowired
    private RcDistrictDao rcDistrictDao;
    //通过用户id找用户存储的地址
    @PostMapping("/addressById")
    public DataResult addressById(@RequestBody LoginData loginData){
        System.out.println("addressById");
        System.out.println("收到的数据是："+loginData.getId());

        QueryWrapper<Address> wrapper=new QueryWrapper<Address>()
                .eq("user_id",loginData.getId());
        List<Address> addressList=addressDao.selectList(wrapper);
        for (Address address : addressList) {
            address.setProvince(pidFind(address.getAddressProvince()).getDistrict());
            address.setCity(pidFind(address.getAddressCity()).getDistrict());
            address.setCounty(pidFind(address.getAddressCounty()).getDistrict());
        }
        System.out.println("addressList:"+addressList);
        return DataResult.success(addressList);
    }
    //用户插入地址信息
    @PostMapping("/addressInsert")
    public DataResult addressInsert(@RequestBody AddressData addressData){
        System.out.println("addressInsert");
        System.out.println("收到的数据是："+addressData.getAddressProvince()+","+addressData.getAddressCity()+","+addressData.getAddressCounty()+","+addressData.getAddressDetail()+","+addressData.getMyName()+","+addressData.getPhone()+","+addressData.getUser_id());
        //如果之前没有存储地址，则设置为默认地址
        QueryWrapper<Address> wrapper=new QueryWrapper<Address>()
                .eq("user_id",addressData.getUser_id());
        List<Address> addressList=addressDao.selectList(wrapper);
        Address address=new Address();
        if(addressList.size()==0){
            address.setAddressDefault("1");
        }
        address.setAddressProvince(addressData.getAddressProvince());
        address.setAddressCity(addressData.getAddressCity());
        address.setAddressCounty(addressData.getAddressCounty());
        address.setAddressDetail(addressData.getAddressDetail());
        address.setUser_id(addressData.getUser_id());
        address.setMyName(addressData.getMyName());
        address.setPhone(addressData.getPhone());
        int insert = addressDao.insert(address);
        System.out.println(insert);
        return DataResult.success(address);
    }
    //用户删除地址信息
    @PostMapping("/addressDelete")
    public DataResult addressDelete(@RequestBody LoginData loginData){
        System.out.println("addressDelete");
        System.out.println("收到的数据是："+loginData.getId());

        //不能删除默认地址
        QueryWrapper<Address> wrapper=new QueryWrapper<Address>()
                .eq("id",loginData.getId());
        Address address=addressDao.selectOne(wrapper);
        if(address.getAddressDefault().equals("1")){
            System.out.println("不能删除默认地址");
            return DataResult.success("不能删除默认地址");
        }
        else{
            System.out.println("删除成功");
            int result = addressDao.delete(wrapper);
            System.out.println(result);
            return DataResult.success("删除成功");
        }
    }
    //用户设置默认地址信息
    @PostMapping("/addressDefault")
    public DataResult addressDefault(@RequestBody AddressData addressData){
        System.out.println("addressDefault");
        System.out.println("收到的数据是："+addressData.getId()+","+addressData.getUser_id());

        //将原来的默认地址设置为0，然后将对应的地址id设置为1
        QueryWrapper<Address> wrapper=new QueryWrapper<Address>()
                .eq("user_id",addressData.getUser_id());
        List<Address> addressList=addressDao.selectList(wrapper);
        Address address1=new Address();
        for (Address address : addressList) {
            if(address.getAddressDefault().equals("1")){
                address1.setId(address.getId());
                address1.setAddressDefault("0");

                System.out.println("默认地址id为"+address.getId());
                QueryWrapper<Address> wrapper1=new QueryWrapper<Address>()
                        .eq("id",address.getId());
                int result=addressDao.update(address1,wrapper1);
                System.out.println(result);
            }
        }
        QueryWrapper<Address> wrapper2=new QueryWrapper<Address>()
                .eq("id",addressData.getId());
        Address address=new Address();
        address.setAddressDefault("1");
        int result=addressDao.update(address,wrapper2);
        System.out.println(result);
        return DataResult.success(address1);
    }
    //用户修改地址信息
    @PostMapping("/addressUpdate")
    public DataResult addressUpdate(@RequestBody AddressData addressData){
        System.out.println("addressUpdate");
        System.out.println("收到的数据是："+addressData.getAddressProvince()+","+addressData.getAddressCity()+","+addressData.getAddressCounty()+","+addressData.getAddressDetail()+","+addressData.getMyName()+","+addressData.getPhone()+","+addressData.getUser_id()+","+addressData.getId());

        QueryWrapper<Address> wrapper=new QueryWrapper<Address>()
                .eq("id",addressData.getId());
        Address address1=new Address();
        address1.setAddressProvince(addressData.getAddressProvince());
        address1.setAddressCity(addressData.getAddressCity());
        address1.setAddressCounty(addressData.getAddressCounty());
        address1.setAddressDetail(addressData.getAddressDetail());
        address1.setMyName(addressData.getMyName());
        address1.setPhone(addressData.getPhone());
        int result=addressDao.update(address1,wrapper);
        System.out.println(result);
        return DataResult.success(address1);
    }

    //找省
    @GetMapping("/reDistrictProvinceAll")
    public DataResult reDistrictProvinceAll(){
        System.out.println("reDistrictProvinceAll");
        QueryWrapper<RcDistrict> wrapper=new QueryWrapper<RcDistrict>()
                .eq("level",1);
        List<RcDistrict> addressListProvince=rcDistrictDao.selectList(wrapper);
        System.out.println("reDistrictProvinceAll:"+addressListProvince);
        return DataResult.success(addressListProvince);
    }
    //找市
    @PostMapping("/reDistrictCity")
    public DataResult reDistrictCity(@RequestBody LoginData loginData){
        System.out.println("reDistrictCity");
        System.out.println("收到的数据是："+loginData.getPid());
        QueryWrapper<RcDistrict> wrapper=new QueryWrapper<RcDistrict>()
                .eq("pid",loginData.getPid());
        List<RcDistrict> addressListCity=rcDistrictDao.selectList(wrapper);
        System.out.println("addressListCity:"+addressListCity);
        return DataResult.success(addressListCity);
    }
    //根据district_id找地区
    private RcDistrict pidFind(String district_id){
        QueryWrapper<RcDistrict> wrapper=new QueryWrapper<RcDistrict>()
                .eq("district_id",district_id);
        RcDistrict rcDistrict=rcDistrictDao.selectOne(wrapper);
        return rcDistrict;
    }
    //根据地区名找district_id
    private String districtFind(String name){
        QueryWrapper<RcDistrict> wrapper=new QueryWrapper<RcDistrict>()
                .eq("district",name);
        RcDistrict rcDistrict=rcDistrictDao.selectOne(wrapper);
        return rcDistrict.getDistrict();
    }
}
