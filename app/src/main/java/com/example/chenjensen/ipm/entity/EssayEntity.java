package com.example.chenjensen.ipm.entity;

/**
 * Created by chenjensen on 16/3/1.
 */
public class EssayEntity {

    private String title;
    private String page;
    private String content;
    private String id;
    private boolean isCollect;

    public void setTitle(String title){
        this.title = title;
    }

    public void setPage(String page){
        this.page = page;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPage() {
        return page;
    }

    public String getTitle() {
        return title;
    }


    public String getContent() {
        return content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isCollect() {
        return isCollect;
    }

    public void setIsCollect(boolean isCollect) {
        this.isCollect = isCollect;
    }
}
