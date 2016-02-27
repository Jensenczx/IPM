package com.example.chenjensen.ipm.entity;

/**
 * Created by chenjensen on 16/2/27.
 */
public class PageListEntity {

    private String name;
    private String url;
    //0表示未关注，1表示关注了
    private int isFollow;

    public PageListEntity(){

    }

    public void setName(String name){
        this.name = name;
    }

    public void setUrl(String url){
        this.url = url;
    }

    public void setIsFollow(int isFollow){
        this.isFollow = isFollow;
    }

    public String getName(){
        return name;
    }

    public String getUrl(){
        return url;
    }

    public int getIsFollow(){
        return isFollow;
    }

}
