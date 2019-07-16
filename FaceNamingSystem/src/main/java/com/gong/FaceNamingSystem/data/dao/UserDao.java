package com.gong.FaceNamingSystem.data.dao;


import com.gong.FaceNamingSystem.data.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by Gavinggl on 2018/5/10.
 */
@Component
public class UserDao implements IUserDao {

    @Autowired
    private MongoTemplate monoTemplate;

    @Override
    public int getMaxFaceCount(String userId) {
        return 10000;
    }

    @Override
    public void insert(UserEntity userEntity) {
        monoTemplate.insert(userEntity);
    }

    @Override
    public UserEntity findByID(String userID) {
        return monoTemplate.findById(userID, UserEntity.class);
    }

    @Override
    public List<UserEntity> getAll() {
        Query query = new Query();
        return monoTemplate.find(query, UserEntity.class);
    }
}
