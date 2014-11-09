package com.swt.geracomium;

// import com.swt.geracomium.CustomListAdapter;
// import com.swt.geracomium.AppController;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.swt.geracomium.entity.Article;
import com.swt.geracomium.entity.User;
import com.swt.geracomium.entity.Utils;
import com.swt.geracomium.util.Cookie;
import com.swt.geracomium.util.CookieJsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

// import android.app.Activity;
// import android.view.Menu;
// import android.view.MenuItem;

public class MainActivity extends Activity {

    // Log tag
    private static final String TAG = MainActivity.class.getSimpleName();

    //json url
    //private static final String url = "http://api.androidhive.info/json/movies.json";
    private ProgressDialog progressDialog;
    private List<Article> articleList = new ArrayList<Article>();
    private ListView listView;
    private CustomListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView)findViewById(R.id.articles);
        adapter = new CustomListAdapter(this, articleList);
        listView.setAdapter(adapter);

        // show progress dialog before making http request;
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("加载中...");
        progressDialog.show();

        getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#1b1b1b")));

        // Create Volley request obj
        CookieJsonArrayRequest articleReq = new CookieJsonArrayRequest(
                Utils.server_address + "/api/articles/?type=ARTICLE&format=json",
                new Response.Listener<JSONArray>(){
            @Override
            public void onResponse(JSONArray response) {
                Log.d(TAG, response.toString());
                hideProgressDialog();

                // Parsing Json
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject obj = response.getJSONObject(i);
                        Article article = new Article();
                        article.parse(obj);

                        articleList.add(article);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                // notify the adapter about data changes
                // so that it renders the list view with updated data
                adapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                hideProgressDialog();
            }
        });
        AppController.getInstance().addToRequestQueue(articleReq);

        this.setFooter();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        hideProgressDialog();
    }

    private void hideProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }
}
