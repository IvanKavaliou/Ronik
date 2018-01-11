package com.example.ivank.ronik;


public class News {
    private String url;
    private String date;
    private String title;
    private String text;
    private String img;


    public News(String url, String img, String date, String title, String text) {
        this.url = url;
        this.img = img;
        this.date = date;
        this.title = title;
        this.text = text;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
