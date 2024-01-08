package com.example.takeawaybackend.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.takeawaybackend.bean.Refund;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RefundDao extends BaseMapper<Refund> {
}
