package com.swt.geracomium;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.TextView;

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

        this.findViewById(R.id.alert).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent = new Intent(self, AlertActivity.class);
                startActivity(mIntent);
            }
        });

        int id = R.id.message;
        if (this instanceof PostActivity) {
            id = R.id.post;
        }
        else if (this instanceof AlertActivity) {
            id = R.id.alert;
        }
        else if (this instanceof ProfileActivity) {
            id = R.id.personal;
        }
        TextView view = (TextView)this.findViewById(id);
        view.setTextColor(getResources().getColor(R.color.selected));
    }
}
