package com.example.takeawaybackend.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.takeawaybackend.bean.Category;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CategoryDao extends BaseMapper<Category> {
}
