package com.swt.geracomium;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.view.View;

/**
 * Created by dsun on 9/23/14.
 */
public class Activity extends FragmentActivity {

    public void setFooter() {
        final Activity self = this;
        this.findViewById(R.id.message).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent = new Intent(self, MainActivity.class);
                startActivity(mIntent);
            }
        });

        this.findViewById(R.id.personal).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent = new Intent(self, ProfileActivity.class);
                startActivity(mIntent);
            }
        });

        this.findViewById(R.id.post).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent = new Intent(self, PostActivity.class);
                startActivity(mIntent);
            }
        });
    }
}
