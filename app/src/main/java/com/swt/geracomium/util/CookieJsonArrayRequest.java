package com.swt.geracomium.util;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;

import java.util.Map;

/**
 * Created by dsun on 11/9/14.
 */
public class CookieJsonArrayRequest extends JsonArrayRequest {
    private CookieRequest cookieRequest = new CookieRequest();

    public CookieJsonArrayRequest(java.lang.String url, com.android.volley.Response.Listener<org.json.JSONArray> listener, com.android.volley.Response.ErrorListener errorListener) {
        super(url, listener, errorListener);
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
    protected Response<JSONArray> parseNetworkResponse(NetworkResponse response) {
        cookieRequest.parseNetworkResponse(response);
        return super.parseNetworkResponse(response);
    }

}
