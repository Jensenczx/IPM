package com.example.chenjensen.ipm.entity;

/**
 * Created by chenjensen on 16/3/7.
 */
public class UserEntity {

    private String name;
    private String photo;
    private String introduce;

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getIntroduce() {
        return introduce;
    }

    public String getName() {
        return name;
    }

    public String getPhoto() {
        return photo;
    }
}

