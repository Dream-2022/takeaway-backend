package com.example.takeawaybackend.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.takeawaybackend.bean.Remark;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RemarkDao extends BaseMapper<Remark> {
}
