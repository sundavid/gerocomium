package com.swt.geracomium;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.SimpleAdapter;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.swt.geracomium.entity.Article;
import com.swt.geracomium.entity.User;
import com.swt.geracomium.entity.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


public class MainActivity extends Activity {

    private AbsListView articlesView;
    private RequestQueue mVolleyQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        articlesView = (AbsListView) this.findViewById(R.id.articles);
        mVolleyQueue = Volley.newRequestQueue(this);

        final MainActivity self = this;

        JsonArrayRequest r = new JsonArrayRequest(Utils.server_address + "api/articles?type=ARTICLE&format=json&user=" + String.valueOf(User.getUser().id),
                new Response.Listener<JSONArray>(){
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            ArrayList<Article> articles = new ArrayList<Article>();
                            for (int i = 0;i < response.length(); ++i) {
                                JSONObject o = response.getJSONObject(i);
                                Article article = new Article();
                                article.parse(o);
                                articles.add(article);
                            }
                            self.initialize_view(articles);
                        }
                        catch (JSONException e) {
                            System.out.println(e.toString());
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        mVolleyQueue.add(r);

        this.setFooter();

    }

    public void initialize_view(ArrayList<Article> articles) {
        // SimpleAdapter adapter = new SimpleAdapter(this, get)
        ArrayList<HashMap<String, Object> > data = new ArrayList<HashMap<String, Object>>();
        for (Article a: articles) {
            HashMap<String, Object> article = new HashMap<String, Object>();
            article.put("title", a.title);
            article.put("author", a.author);
            article.put("content", a.content);
            data.add(article);
        }
        SimpleAdapter adapter = new SimpleAdapter(this, data, R.layout.article,
                new String[] {"title", "author", "content"},
                new int[] {R.id.title, R.id.author, R.id.content});
        articlesView.setAdapter(adapter);
    }
}
