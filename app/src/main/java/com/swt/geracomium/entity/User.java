package com.swt.geracomium.entity;

import com.swt.geracomium.R;
import com.swt.geracomium.entity.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by dsun on 10/10/14.
 */
public class User {
    public int id;
    public String email;
    public String name;
    public String phone_number;
    public String photo;
    public String address;
    public int age;
    public String gender;
    public User emergency_contact;
    public String role;
    public String room_number;

    public static User user;
    public static HashMap<String, User> users;
    static {
        user = new User();
        users = new HashMap<String, User>();
    }

    public static User getUser() {
        return getUser("");
    }

    public static User getUser(String url) {
        if (url.isEmpty()) return user;
        if (!users.containsKey(url)) users.put(url, new User());
        return users.get(url);
    }

    public boolean login(String username, String password)
    {
        return false;
    }

    public void parse(JSONObject response) throws JSONException {
        User user = this;
        user.address = response.getString("address");
        user.age = response.getInt("age");
        //user.email = response.getString("email");
        //user.emergency_contact = response.getString("emergency_contact");
        user.gender = response.getString("gender");
        user.id = response.getInt("id");
        user.name = response.getString("name");
        user.phone_number = response.getString("phone_number");
        user.photo = response.getString("photo");
        user.role = response.getString("role");
        user.room_number = response.getString("room_number");
    }
}
