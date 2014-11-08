package com.swt.geracomium.entity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by dsun on 10/12/14.
 */
public class Article {
    public String type;
    public String title;
    public int author_id;
    public String author_name;
    public String author_icon;
    // public Date created_at;
    public Date published_at;
    public String content;
    public String[] images;

    public Article() {
    }

    public void parse(JSONObject response) throws JSONException {
        type = response.getString("type");
        title = response.getString("title");
        author_id = response.getInt("author");
        author_name = response.getString("author_name");
        author_icon = response.getString("author_icon");
        content = response.getString("content");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:SS");
        try {
            published_at = format.parse(response.getString("published_at"));
        } catch (ParseException e) {
            published_at = new Date();
        }

        JSONArray imgs = response.getJSONArray("images");
        images = new String[imgs.length()];
        for (int i = 0; i < imgs.length(); ++i) {
            images[i] = imgs.getString(i);
        }
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

    /*
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    */

    public String getPublishedTime() {
        long diff = (new Date()).getTime() - published_at.getTime();
        diff /= 1000 * 60;
        if (diff < 60) return String.valueOf(diff) + "分钟前";
        if (diff < 60 * 20) return String.valueOf(diff / 60) + "小时前";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        format.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        return format.format(published_at);
    }

}
