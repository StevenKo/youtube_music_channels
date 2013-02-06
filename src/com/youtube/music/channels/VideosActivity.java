package com.youtube.music.channels;


import static android.provider.BaseColumns._ID;
import static com.youtube.music.sqlite.DbMyVideoConstants.VIDEO_BELONGO_CHANNEL;
import static com.youtube.music.sqlite.DbMyVideoConstants.VIDEO_DISLIKES;
import static com.youtube.music.sqlite.DbMyVideoConstants.VIDEO_DURATION;
import static com.youtube.music.sqlite.DbMyVideoConstants.VIDEO_LIKES;
import static com.youtube.music.sqlite.DbMyVideoConstants.VIDEO_LINK;
import static com.youtube.music.sqlite.DbMyVideoConstants.VIDEO_TABLE_NAME;
import static com.youtube.music.sqlite.DbMyVideoConstants.VIDEO_THUMBNAIL;
import static com.youtube.music.sqlite.DbMyVideoConstants.VIDEO_TITLE;
import static com.youtube.music.sqlite.DbMyVideoConstants.VIDEO_UPLOADTIME;
import static com.youtube.music.sqlite.DbMyVideoConstants.VIDEO_VIEWCOUNT;

import java.util.ArrayList;

import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.ListFragment;
import android.support.v4.view.ViewPager;
import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.viewpagerindicator.TabPageIndicator;
import com.youtube.music.channels.entity.MyYoutubeVideo;
import com.youtube.music.sqlite.DBMyChannelHelper;
import com.youtube.music.sqlite.DBMyVideoHelper;


public class VideosActivity extends SherlockFragmentActivity {
	
//	private ListView listView;
	private Bundle mBundle;
	private String channelName;
	private String channelLink;
	private int channelId;
//	private EditText search;
	private String[] CONTENT;
	private ArrayList<MyYoutubeVideo> myVideos = new ArrayList<MyYoutubeVideo>();
	private DBMyVideoHelper mDbVideoHelper;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.simple_tabs);
        
		openDatabase();
		getMyVideos();
		
		final ActionBar ab = getSupportActionBar();		     
        mBundle = this.getIntent().getExtras();
        channelName = mBundle.getString("ChannelName");
        channelId = mBundle.getInt("ChannelId");
        channelLink = mBundle.getString("ChannelLink");
        
        ab.setTitle(channelName);
        ab.setDisplayHomeAsUpEnabled(true);
       
		
        Resources res = getResources();
        CONTENT = res.getStringArray(R.array.tabs);
        
        FragmentStatePagerAdapter adapter = new GoogleMusicAdapter(getSupportFragmentManager());

        ViewPager pager = (ViewPager)findViewById(R.id.pager);
        pager.setAdapter(adapter);

        TabPageIndicator indicator = (TabPageIndicator)findViewById(R.id.indicator);
        indicator.setViewPager(pager);
        
	}
	
	private void getMyVideos() {
		// TODO Auto-generated method stub
		Cursor cursor = getVideoCursor();
		while(cursor.moveToNext()){
    		int id = cursor.getInt(0);
    		int belongchannel = cursor.getInt(1);
    		String title = cursor.getString(2);
    		String link= cursor.getString(3);
    		String  thumbnail= cursor.getString(4);
    		String uploadtime = cursor.getString(5);
    		int view_count = cursor.getInt(6);
    		int  duration= cursor.getInt(7);
    		int likes = cursor.getInt(8);
    		int dislikes = cursor.getInt(9);
    		MyYoutubeVideo aVideo = new MyYoutubeVideo(id,belongchannel, title, link, thumbnail, uploadtime, view_count,  duration, likes, dislikes);
    		myVideos.add(aVideo);   		
    	}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
        return true;
	}
	
	public boolean onMenuItemSelected(int featureId, MenuItem item) {

	    int itemId = item.getItemId();
	    switch (itemId) {
	    case android.R.id.home:
	        finish();
	        // Toast.makeText(this, "home pressed", Toast.LENGTH_LONG).show();
	        break;
	    }
	    return true;
	}
	
	class GoogleMusicAdapter extends FragmentStatePagerAdapter {
        public GoogleMusicAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
//            return TestFragment.newInstance(CONTENT[position % CONTENT.length]);
        	
        	Fragment kk = new Fragment();
        	
        	
        	
        	if(position==0){
        		myVideos.clear();
            	getMyVideos();
            	kk = NewsFragment.newInstance(channelLink, 0, myVideos, channelId);
        	}else if(position == 1){
        		myVideos.clear();
        		getMyVideos();
        		kk = PopularFragment.newInstance(channelLink, 0, myVideos, channelId);
        	}else if(position == 2){
        		kk = PlaylistFragment.newInstance(channelLink, 0);
        	}
        	else if(position == 3){
        		myVideos.clear();
        		getMyVideos();
        		kk = FavoriteListFragment.newInstance(channelId, myVideos);
        	}
            return kk;
        }
       

        @Override
        public CharSequence getPageTitle(int position) {
//            return CONTENT[position % CONTENT.length].toUpperCase();
            return CONTENT[position % CONTENT.length];
        }

        @Override
        public int getCount() {
            return CONTENT.length;
        }
    }
   
	private void openDatabase() {
		// TODO Auto-generated method stub
//    	mDbChannelHelper = new DBMyChannelHelper(this);
    	mDbVideoHelper = new DBMyVideoHelper(this);
	}
	private Cursor getVideoCursor(){
    	SQLiteDatabase db = mDbVideoHelper.getReadableDatabase();
    	String[] columns = {_ID, VIDEO_BELONGO_CHANNEL, VIDEO_TITLE, VIDEO_LINK, VIDEO_THUMBNAIL,VIDEO_UPLOADTIME,VIDEO_VIEWCOUNT, VIDEO_DURATION, VIDEO_LIKES, VIDEO_DISLIKES};
    	
    	Cursor cursor = db.query(VIDEO_TABLE_NAME, columns, null, null, null, null, null);
    	startManagingCursor(cursor);
    	
    	return cursor;
    }
	
	 @Override
		protected void onDestroy() {
			super.onDestroy();
			closeDatabase();
		}
	
	 private void closeDatabase(){
			mDbVideoHelper.close();
	}

}
