package com.swt.geracomium.util;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by dsun on 11/1/14.
 */
public class CookieStringRequest extends StringRequest {
    CookieRequest cookieRequest = new CookieRequest();

    public CookieStringRequest(String url, Response.Listener<String> listener,
                               Response.ErrorListener errorListener) {
        super(url, listener, errorListener);
    }

    public CookieStringRequest(int method, String url, Response.Listener<String> listener,
                               Response.ErrorListener errorListener) {
        super(method, url, listener, errorListener);
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return cookieRequest.getHeaders(super.getHeaders());
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        cookieRequest.parseNetworkResponse(response);
        return super.parseNetworkResponse(response);
    }

}
