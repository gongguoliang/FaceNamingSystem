package com.gong.FaceNamingSystem.data.dao;

import com.gong.FaceNamingSystem.data.entity.CMseetaFaceEntiy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by Gavinggl on 2018/5/11.
 */
@Component
public class CMseetaFaceDao implements ICMseetaFaceDao{

    @Autowired
    private MongoTemplate template;

    @Override
    public void insert(CMseetaFaceEntiy faceEntity, String collectionName) {
        template.insert(faceEntity,collectionName);
    }

    @Override
    public void insertIntoCollection(CMseetaFaceEntiy faceEntity, String collectionName) {
        if (!template.getCollectionNames().contains(collectionName)) {
            template.createCollection(collectionName);
        }
        template.insert(faceEntity, collectionName);
    }

    @Override
    public CMseetaFaceEntiy findByFaceId(String FaceId, String collectionName) {
        return template.findById(FaceId, CMseetaFaceEntiy.class, collectionName);
    }

    @Override
    public List<CMseetaFaceEntiy> findByUserId(String UserId, String faceCollectionName) {
        Query query = new Query();
        query.addCriteria(new Criteria("UserId").is(UserId));
        return template.find(query,CMseetaFaceEntiy.class,faceCollectionName);
    }
    @Override
    public void delete( String collectionName) {
        template.dropCollection(collectionName);
    }
}
