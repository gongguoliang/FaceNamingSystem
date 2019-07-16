package com.gong.FaceNamingSystem.Mapper;

import com.gong.FaceNamingSystem.model.Admin;
import org.springframework.stereotype.Component;

@Component(value = "AdminMapper")
public interface AdminMapper {
    int deleteByPrimaryKey(String username);

    int insert(Admin record);

    int insertSelective(Admin record);

    Admin selectByPrimaryKey(String username);

    int updateByPrimaryKeySelective(Admin record);

    int updateByPrimaryKey(Admin record);
}