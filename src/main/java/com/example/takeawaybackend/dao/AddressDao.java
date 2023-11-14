package com.example.takeawaybackend.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.takeawaybackend.bean.Address;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AddressDao extends BaseMapper<Address> {
}
