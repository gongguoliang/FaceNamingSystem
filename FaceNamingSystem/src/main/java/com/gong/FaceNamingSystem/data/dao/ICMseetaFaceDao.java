package com.gong.FaceNamingSystem.data.dao;

import com.gong.FaceNamingSystem.data.entity.CMseetaFaceEntiy;

import java.util.List;

/**
 * Created by Gavinggl on 2018/5/11.
 */
public interface ICMseetaFaceDao {

    void insert(CMseetaFaceEntiy faceEntity, String collectionName);

    void insertIntoCollection(CMseetaFaceEntiy faceEntity, String collectionName);

    CMseetaFaceEntiy findByFaceId(String FaceId, String collectionName);

    List<CMseetaFaceEntiy> findByUserId(String UserId, String faceCollectionName);

    void delete(String collectionName);

}
