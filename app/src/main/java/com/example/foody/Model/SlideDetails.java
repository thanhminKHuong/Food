package com.example.foody.Model;

import java.util.List;

public class SlideDetails {
    private String img ;
    private String title ;

    public SlideDetails() {
    }

    public SlideDetails(String img, String title) {
        this.img = img;
        this.title = title;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
