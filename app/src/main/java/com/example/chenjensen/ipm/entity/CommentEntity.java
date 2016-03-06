package com.example.chenjensen.ipm.entity;

/**
 * Created by chenjensen on 16/3/3.
 */
public class CommentEntity {
    private String userName;
    private String userComment;
    private String userPhoto;
    private String userLike;
    private boolean ifLike;

    public void setUserName(String userName){
        this.userName = userName;
    }

    public void setIfLike(boolean ifLike) {
        this.ifLike = ifLike;
    }

    public void setUserComment(String userComment) {
        this.userComment = userComment;
    }

    public void setUserLike(String userLike) {
        this.userLike = userLike;
    }

    public void setUserPhoto(String userPhoto) {
        this.userPhoto = userPhoto;
    }

    public String getUserComment() {
        return userComment;
    }

    public String getUserLike() {
        return userLike;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserPhoto() {
        return userPhoto;
    }
}
