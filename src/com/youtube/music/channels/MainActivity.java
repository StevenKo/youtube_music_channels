package com.youtube.music.channels;

import java.util.ArrayList;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;

import com.youtube.music.channels.api.ChannelApi;
import com.youtube.music.channels.entity.Channel;

public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new AsyncTask() {

            @Override
            protected Object doInBackground(Object... params) {
                ArrayList<Channel> news = ChannelApi.getPromotionNews(1);
                return null;
            }

        }.execute();

        setContentView(R.layout.activity_main);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}
