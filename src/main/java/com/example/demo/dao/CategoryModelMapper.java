package com.example.demo.dao;

import com.example.demo.model.CategoryModel;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryModelMapper {

    int deleteByPrimaryKey(Integer id);

    List<CategoryModel> selectAll();

    Integer countAllCategory();

    int insert(CategoryModel record);

    int insertSelective(CategoryModel record);

    CategoryModel selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CategoryModel record);

    int updateByPrimaryKey(CategoryModel record);
}