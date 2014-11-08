package com.swt.geracomium.entity;

/**
 * Created by dsun on 10/11/14.
 */
public class Utils {
    public static String server_address;

    public static String csrf_token;

    public static User user;

    public static int window_width;

    public static int window_height;


    static {
        server_address = "";
        csrf_token = "";
        user = User.getUser();
    }
}
