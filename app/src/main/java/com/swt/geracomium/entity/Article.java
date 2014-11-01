package com.swt.geracomium.entity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

/**
 * Created by dsun on 10/12/14.
 */
public class Article {
    public String type;
    public String title;
    public User author;
    // public Date created_at;
    public String published_at;
    public String content;
    public String imagelUrl;

    public void parse(JSONObject response) throws JSONException {
        type = response.getString("type");
        title = response.getString("title");
        author = User.getUser(response.getString("author"));
        content = response.getString("content");
        // imagelUrl = response.getString("image");
        imagelUrl = "http://182.92.193.33:8000/images/images/2013-01-20_09-34-08_303413_org-8.jpg";
        Date now_time = new Date();
        now_time.getTime();
        published_at = now_time.toString();
    }

    public Article() {
    }

    /*
    public Article(String type, String title, User author, String content, String thumbnailUrl) {
        this.type = type;
        this.title = title;
        this.author = author;
        this.content = content;
        this.thumbnailUrl = thumbnailUrl;
    }
    */

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public User getAuthor() {
        return author;
    }

    /*
    public void setAuthor(User author) {
        this.author = author;
    }
    */

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImageUrl() {
        return imagelUrl;
    }

    /*
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    */

    public String getPublishedTime() {
        return published_at;
    }

    /*
    public void setPublishedTime(Date new_date) {
        this.published_at = new_date.toString();
    }
    */

}
