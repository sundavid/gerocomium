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
    public Date created_at;
    public Date published_at;
    public String content;
    //public images;

    public void parse(JSONObject response) throws JSONException {
        type = response.getString("type");
        title = response.getString("title");
        author = User.getUser(response.getString("author"));
        content = response.getString("content");
    }

}
