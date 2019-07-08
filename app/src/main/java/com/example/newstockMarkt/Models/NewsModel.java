package com.example.newstockMarkt.Models;

public class NewsModel {
    private String title;
    private String publishedDate;
    private String newsUrl;
    private String description;
    private String publicer;
    private String thumbNailUrl;

    public String getThumbNailUrl() {
        return thumbNailUrl;
    }

    public void setThumbNailUrl(String thumbNailUrl) {
        this.thumbNailUrl = thumbNailUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        if (title == null || title.equals("null")) {

            this.title = "";
        } else {
            this.title = title;
        }
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
    }

    public String getNewsUrl() {
        return newsUrl;
    }

    public void setNewsUrl(String newsUrl) {
        this.newsUrl = newsUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        if (description == null || description.equals("null")) {

            this.description = "";
        } else {
            this.description = description;
        }
    }

    public String getPublicer() {
        return publicer;
    }

    public void setPublicer(String publicer) {
        if (publicer == null || publicer.equals("null")) {

            this.publicer = "";
        } else {
            this.publicer = publicer;
        }
    }


}
