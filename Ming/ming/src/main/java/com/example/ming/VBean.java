package com.example.ming;

public class VBean{
    private String content;

    public VBean(){}

    public VBean(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return  " content= " + content;
    }
}
