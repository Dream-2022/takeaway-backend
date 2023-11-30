package com.example.takeawaybackend.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.takeawaybackend.bean.Collect;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CollectDao extends BaseMapper<Collect> {
}
