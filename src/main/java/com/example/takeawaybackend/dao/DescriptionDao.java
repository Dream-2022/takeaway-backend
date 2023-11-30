package com.example.takeawaybackend.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.takeawaybackend.bean.Description;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DescriptionDao extends BaseMapper<Description> {
}
