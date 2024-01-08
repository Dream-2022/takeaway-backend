package com.example.takeawaybackend.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.takeawaybackend.bean.Report;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ReportDao extends BaseMapper<Report> {
}
