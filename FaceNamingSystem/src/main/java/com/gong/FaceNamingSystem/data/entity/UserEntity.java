package com.gong.FaceNamingSystem.data.entity;

import org.springframework.data.annotation.Id;

import java.io.Serializable;

/**
 * Created by Gavinggl on 2018/5/10.
 */
public class UserEntity implements Serializable {


    @Id
    private String userID;
    private String userName;
    private String collectionName;

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCollectionName() {
        return collectionName;
    }

    public void setCollectionName(String collectionName) {
        this.collectionName = collectionName;
    }


    @Override
    public String toString() {
        return "UserEntity{" +
                "userID=" + userID +
                ", userName='" + userName + '\'' +
                ", collectionName='" + collectionName + '\'' +
                '}';
    }





}