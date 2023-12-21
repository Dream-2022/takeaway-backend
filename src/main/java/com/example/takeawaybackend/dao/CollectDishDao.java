package com.example.takeawaybackend.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.takeawaybackend.bean.CollectDish;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CollectDishDao extends BaseMapper<CollectDish> {
}
