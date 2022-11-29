package com.example.foody.Model;

public class Slide {

    private int sourceImg ;
    private String title ;
    private String desc ;

    public Slide(int sourceImg, String title, String desc) {
        this.sourceImg = sourceImg;
        this.title = title;
        this.desc = desc;
    }

    public int getSourceImg() {
        return sourceImg;
    }

    public void setSourceImg(int sourceImg) {
        this.sourceImg = sourceImg;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
