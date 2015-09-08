package com.bettycc.sample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.umeng.analytics.AnalyticsConfig;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String channel = AnalyticsConfig.getChannel(this);
        ((TextView)findViewById(R.id.channel)).setText(String.format("现在的友盟渠道是: %s", channel));
    }
}
