package com.swt.geracomium.util;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.swt.geracomium.entity.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by dsun on 11/1/14.
 */
public class CookieJsonRequest extends JsonObjectRequest {
    private CookieRequest cookieRequest = new CookieRequest();

    public CookieJsonRequest(String url, JSONObject jsonRequest, Response.Listener<JSONObject> listener,
                             Response.ErrorListener errorListener) {
        super(url, jsonRequest, listener, errorListener);
    }

    public CookieJsonRequest(int method, String url, JSONObject jsonRequest, Response.Listener<JSONObject> listener,
                             Response.ErrorListener errorListener) {
        super(method, url, jsonRequest, listener, errorListener);
    }

    public void setCookie(String key, String value) {
        Cookie.getCookie().setCookie(key, value);
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return cookieRequest.getHeaders(super.getHeaders());
    }

    @Override
    protected Map<String, String> getParams()
            throws com.android.volley.AuthFailureError {
        return cookieRequest.getParams(this.getMethod(), super.getParams());
    }

    @Override
    protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
        cookieRequest.parseNetworkResponse(response);
        return super.parseNetworkResponse(response);
    }

}
