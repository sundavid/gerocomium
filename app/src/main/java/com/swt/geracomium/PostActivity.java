package com.swt.geracomium;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.swt.geracomium.R;

import org.json.JSONException;
import org.json.JSONObject;

public class PostActivity extends Activity {

    private RequestQueue mVolleyQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        mVolleyQueue = Volley.newRequestQueue(this);

        final PostActivity self = this;
        ((Button)findViewById(R.id.post_message)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    self.postArticle();
                }
                catch (Exception e)
                {

                }
            }
        });
    }

    private void postArticle() throws JSONException {
        String content = ((TextView)findViewById(R.id.message_content)).getText().toString();
        JSONObject object = new JSONObject();
        object.put("content", content);
        object.put("type", "Article");
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
