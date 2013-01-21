package com.youtube.music.channels;

import java.net.MalformedURLException;
import java.net.URL;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

import com.google.gdata.client.youtube.YouTubeQuery;

public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        YouTubeQuery query;
        try {
            query = new YouTubeQuery(new URL("http://gdata.youtube.com/feeds/api/videos"));
            // order results by the number of views (most viewed first)
            query.setOrderBy(YouTubeQuery.OrderBy.VIEW_COUNT);

            // search for puppies and include restricted content in the search results
            query.setFullTextQuery("puppy");
            query.setSafeSearch(YouTubeQuery.SafeSearch.NONE);
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // VideoFeed videoFeed = service.query(query, VideoFeed.class);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}
