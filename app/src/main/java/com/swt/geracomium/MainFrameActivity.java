package com.swt.geracomium;

import android.os.Bundle;
import android.view.Menu;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;

public class MainFrameActivity extends FragmentActivity {

    private FragmentTabHost mTabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainframe);

        mTabHost = (FragmentTabHost) findViewById(R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);

        // set tabs
        Bundle b = new Bundle();
        b.putString("key", "消息");
        mTabHost.addTab(mTabHost.newTabSpec("msg").setIndicator("消息"),
                SwipeViewsFragment.class,
                b);

        b = new Bundle();
        b.putString("key", "发表");
        mTabHost.addTab(mTabHost.newTabSpec("post").setIndicator("发表"),
                PostFragment.class,
                b);

        // b = new Bundle();
        // b.putString("key", "提醒");
        // mTabHost.addTab(mTabHost.newTabSpec("alert").setIndicator("提醒"),
        //        AlertFragment.class,
        //        b);

        b = new Bundle();
        b.putString("key", "我的");
        mTabHost.addTab(mTabHost.newTabSpec("personal").setIndicator("我的"),
                ProfileFragment.class,
                b);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}
