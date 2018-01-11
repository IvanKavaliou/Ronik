package com.example.ivank.ronik;




public class FullNews {
    private String date;
    private String title;
    private String text;
    private String img;

    public FullNews(String date, String title, String text, String img) {
        this.date = date;
        this.title = title;
        this.text = text;
        this.img = img;
    }

    public FullNews(FullNews fullNews){
        this.date = fullNews.date;
        this.title = fullNews.title;
        this.text = fullNews.text;
        this.img = fullNews.img;
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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
