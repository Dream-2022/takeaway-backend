package com.example.takeawaybackend.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.example.takeawaybackend.bean.DishOrder;


@Mapper
public interface DishOrderDao extends BaseMapper<DishOrder> {

}
