package com.swt.geracomium;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.swt.geracomium.entity.User;
import com.swt.geracomium.entity.Utils;
import com.swt.geracomium.util.CookieStringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class PostActivity extends Activity {

    private RequestQueue mVolleyQueue;
    private RadioGroup type;
    private EditText messageContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        this.setFooter();

        mVolleyQueue = AppController.getInstance().getRequestQueue();

        type = (RadioGroup)findViewById(R.id.message_type);
        messageContent = (EditText)findViewById(R.id.message_content);


        final PostActivity self = this;
        ((Button)findViewById(R.id.post_message)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                self.postArticle();
            }
        });

        User user = User.getUser();
        if (user.role != "nurse") {
            type.setVisibility(View.GONE);
        }
    }

    private void postArticle() {
        final String messageContent = this.messageContent.getText().toString();
        Log.v("selected", "" + this.type.getCheckedRadioButtonId());
        RadioButton selectedButton = (RadioButton)findViewById(this.type.getCheckedRadioButtonId());
        String typeStr = selectedButton != null ? selectedButton.getText().toString() : getString(R.string.message_article);
        String type = getString(R.string.message_type_article);
        if (typeStr == getString(R.string.message_alert)) type = getString(R.string.message_type_alert);
        else if(typeStr == getString(R.string.message_notification)) type = getString(R.string.message_type_notification);

        final String messageType = type;
        final PostActivity self = this;
        CookieStringRequest r = new CookieStringRequest(Request.Method.POST, Utils.server_address + "/api/articles/",
                new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Intent mIntent = new Intent(self, MainActivity.class);
                startActivity(mIntent);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("csrfmiddlewaretoken", Utils.csrf_token);
                params.put("_content_type", "application/json");
                JSONObject json = new JSONObject();
                try {
                    json.put("type", messageType);
                    json.put("title", "title is optional");
                    json.put("published_at", new SimpleDateFormat(getString(R.string.date_server_format)).format(new Date()));
                    json.put("content", messageContent);
                    json.put("author", User.getUser().id);

                    params.put("_content", json.toString(4));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.v("post message", params.toString());
                return params;
            }
        };
        mVolleyQueue.add(r);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.post, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
