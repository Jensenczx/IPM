package com.example.chenjensen.ipm.entity;

/**
 * Created by chenjensen on 16/3/3.
 */
public class CommentEntity {
    private String userName;
    private String userComment;
    private String userPhoto;
    private int userLike;
    private boolean isLike;

    public void setUserName(String userName){
        this.userName = userName;
    }

    public void setIsLike(boolean ifLike) {
        this.isLike = ifLike;
    }

    public void setUserComment(String userComment) {
        this.userComment = userComment;
    }

    public void setUserLike(int userLike) {
        this.userLike = userLike;
    }

    public void setUserPhoto(String userPhoto) {
        this.userPhoto = userPhoto;
    }

    public String getUserComment() {
        return userComment;
    }

    public int getUserLike() {
        return userLike;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserPhoto() {
        return userPhoto;
    }

    public boolean getIsLike() {
        return isLike;
    }
}
