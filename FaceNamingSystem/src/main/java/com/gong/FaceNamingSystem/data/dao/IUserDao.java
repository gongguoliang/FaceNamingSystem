package com.gong.FaceNamingSystem.data.dao;


import com.gong.FaceNamingSystem.data.entity.UserEntity;

import java.util.List;

/**
 * Created by Gavinggl on 2018/5/10.
 */
public interface IUserDao {


    int getMaxFaceCount(String userId);

    void insert(UserEntity userEntity);

    UserEntity findByID(String userID);

    List<UserEntity> getAll();
}