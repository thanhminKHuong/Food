package com.example.foody.Model;

public class Tienhanh {

    private String title ;
    private String content ;

    public Tienhanh() {
    }

    public Tienhanh(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Tienhanh{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
