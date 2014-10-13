package com.swt.geracomium;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.swt.geracomium.R;
import com.swt.geracomium.entity.User;

public class ProfileActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        this.setFooter();

        ((TextView)this.findViewById(R.id.name)).setText(getString(R.string.name) + ": " + User.getUser().name);
        ((TextView)this.findViewById(R.id.phone_number)).setText(getString(R.string.phone_number) + ": " + User.getUser().phone_number);
        ((TextView)this.findViewById(R.id.age)).setText(getString(R.string.age) + ": " + User.getUser().age);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.profile, menu);
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
