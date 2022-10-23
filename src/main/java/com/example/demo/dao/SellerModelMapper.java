package com.example.demo.dao;

import com.example.demo.model.SellerModel;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SellerModelMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(SellerModel record);

    List<SellerModel> selectAll();

    int insertSelective(SellerModel record);

    SellerModel selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SellerModel record);

    int updateByPrimaryKey(SellerModel record);

    Integer countAllSeller();
}
