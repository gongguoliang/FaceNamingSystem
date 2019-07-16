package com.gong.FaceNamingSystem.data.entity;

import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Created by Gavinggl on 2018/5/11.
 */
public class CMseetaFaceEntiy implements Serializable {


    @Id
    private String faceId;
    private String userId;
    //人脸特征
    public float features[] ;

    public String getFaceId() {
        return faceId;
    }

    public void setFaceId(String faceId) {
        this.faceId = faceId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public float[] getFeatures() {
        return features;
    }

    public void setFeatures(float[] features) {
        this.features = features;
    }


    @Override
    public String toString() {
        return "CMseetaFaceEntiy{" +
                "faceId='" + faceId + '\'' +
                ", userId='" + userId + '\'' +
                ", features=" + Arrays.toString(features) +
                '}';
    }



}
