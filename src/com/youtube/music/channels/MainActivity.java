package com.youtube.music.channels;

import static android.provider.BaseColumns._ID;
import static com.youtube.music.sqlite.DbMyChannelConstants.NAME;
import static com.youtube.music.sqlite.DbMyChannelConstants.LINK;
import static com.youtube.music.sqlite.DbMyChannelConstants.ID;
import static com.youtube.music.sqlite.DbMyChannelConstants.THUMBNAIL;
import static com.youtube.music.sqlite.DbMyChannelConstants.TABLE_NAME;
import static com.youtube.music.sqlite.DbMyVideoConstants.*;

import java.util.ArrayList;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.app.ActionBar.OnNavigationListener;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.taiwan.imageload.ListChannelAdapter;
import com.taiwan.imageload.ListMyVideoAdapter;
import com.taiwan.imageload.ListVideoAdapter;
import com.youtube.music.channels.api.ChannelApi;
import com.youtube.music.channels.entity.Channel;
import com.youtube.music.channels.entity.MyYoutubeVideo;
import com.youtube.music.channels.entity.YoutubeVideo;
import com.youtube.music.channels.VideosActivity;
import com.youtube.music.sqlite.DBMyChannelHelper;
import com.youtube.music.sqlite.DBMyVideoHelper;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class MainActivity extends SherlockActivity {
	
	private DBMyVideoHelper mDbVideoHelper;
	private DBMyChannelHelper mDbChannelHelper;
	private int channelArea;
	private ArrayList<Channel> theChannels = new ArrayList<Channel>();
	private ArrayList<Channel> myChannels = new ArrayList<Channel>();
	private ArrayList<MyYoutubeVideo> myVideos = new ArrayList<MyYoutubeVideo>();
	private ListView myList;
	private ListChannelAdapter myListAdapter;
	private ProgressDialog  progressDialog   = null;
	private EditText search;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        openDatabase();
        getMyChannles();
        
        
        
        final ActionBar ab = getSupportActionBar();
        // set up list nav
        ab.setListNavigationCallbacks(ArrayAdapter
                .createFromResource(this, R.array.sections,
                        R.layout.sherlock_spinner_dropdown_item),
                new OnNavigationListener() {
                    public boolean onNavigationItemSelected(int itemPosition,
                            long itemId) {
                    	
                    	switch(itemPosition){
                        case 0: //Chinese
	                         channelArea = 1; 
	                         new DownloadChannelsTask().execute();
                       	 break;
                        case 1: //Japanese
	                         channelArea = 2;
	                         new DownloadChannelsTask().execute();
                       	 break;
                        case 2: //Korean
	                         channelArea = 3;
	                         new DownloadChannelsTask().execute();
                       	 break;
                        case 3: //English
	                         channelArea = 4;
	                         new DownloadChannelsTask().execute();
                       	 break;
                        case 4: //My Channels
                        	myChannels.clear();
                        	getMyChannles();
                        	setListUI(myChannels);
                       	 break;
                        case 5: //My Videos
                        	myVideos.clear();
                        	getMyVideos();
                        	setListVideo(myVideos);                     	
                          	 break;
                       }
                                    
                    	return false;
                }



										
         });
        
        if (ab.getNavigationMode() != ActionBar.NAVIGATION_MODE_LIST) {
            ab.setDisplayShowTitleEnabled(false);
            ab.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
        }
        
//        channelArea = 1;
//        new DownloadChannelsTask().execute();

    }
    

    
    @Override
	protected void onDestroy() {
		super.onDestroy();
		closeDatabase();
	}
	
    
	private void openDatabase() {
		// TODO Auto-generated method stub
    	mDbChannelHelper = new DBMyChannelHelper(this);
    	mDbVideoHelper = new DBMyVideoHelper(this);
	}
	private void closeDatabase(){
		mDbChannelHelper.close();
		mDbVideoHelper.close();
	}
	
	private void getMyChannles() {
		// TODO Auto-generated method stub
		Cursor cursor = getCursor();
		while(cursor.moveToNext()){
//    		int id = cursor.getInt(0);
    		String name = cursor.getString(1);
    		String link = cursor.getString(2);
    		String  id= cursor.getString(3);
    		String  thumbNail= cursor.getString(4);
    		Channel aChannel = new Channel(name, link, Integer.valueOf(id), thumbNail);
    		myChannels.add(aChannel);   		
    	}
	}
	
	
//	public static final String VIDEO_BELONGO_CHANNEL = "belongchannel";
//	public static final String VIDEO_TITLE = "title";
//	public static final String VIDEO_LINK = "link";
//	public static final String VIDEO_THUMBNAIL = "thumbnail";
//	public static final String VIDEO_UPLOADTIME = "uploadtime";
//	public static final String VIDEO_VIEWCOUNT = "view_count";
//	public static final String VIDEO_DURATION = "duration";
//	public static final String VIDEO_LIKES = "likes";
//	public static final String VIDEO_DISLIKES = "dislikes";
	
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
		// Inflate the menu; this adds items to the action bar if it is present.
		getSupportMenuInflater().inflate(R.menu.activity_main, menu);
		
		 menu.add("Search")
         .setIcon(R.drawable.ic_search_inverse)
         .setActionView(R.layout.collapsible_edittext)
         .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
		
        return true;
	}
    
    public boolean onMenuItemSelected(int featureId, MenuItem item) {

	    int itemId = item.getItemId();
	    switch (itemId) {
	    case android.R.id.home:
	        finish();
	        // Toast.makeText(this, "home pressed", Toast.LENGTH_LONG).show();
	        break;
	    case 0:
	    	 Toast.makeText(this, "search pressed", Toast.LENGTH_LONG).show();
	    	 search = (EditText) item.getActionView();
//             search.addTextChangedListener(filterTextWatcher);
	    	break;
	    }
	    return true;
	}
    
    private class DownloadChannelsTask extends AsyncTask {

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            
            	progressDialog = ProgressDialog.show(MainActivity.this, "", "Loading, please wait...");
            

        }

        @Override
        protected Object doInBackground(Object... params) {
            // TODO Auto-generated method stub

        	theChannels = ChannelApi.getChannels(channelArea);       
  
            return null;
        }

        @Override
        protected void onPostExecute(Object result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            progressDialog.dismiss();
            setListUI(theChannels);

        }
    }
    
    private void setListUI(ArrayList<Channel> channels) {
		// TODO Auto-generated method stub
    	 myList = (ListView) findViewById(R.id.main_listview);
         myListAdapter = new ListChannelAdapter(this, channels, myChannels);
         myList.setAdapter(myListAdapter);
         myList.setItemsCanFocus(true);
        
	}
    
    private void setListVideo(ArrayList<MyYoutubeVideo> myVideos) {    	
		ListMyVideoAdapter videoAdapter = new ListMyVideoAdapter(this, myVideos, myVideos, 0);
		myList.setAdapter(videoAdapter);
		myList.setItemsCanFocus(true);
		
	}
    
    private Cursor getCursor(){
    	SQLiteDatabase db = mDbChannelHelper.getReadableDatabase();
    	String[] columns = {_ID, NAME, LINK, ID, THUMBNAIL};
    	
    	Cursor cursor = db.query(TABLE_NAME, columns, null, null, null, null, null);
    	startManagingCursor(cursor);
    	
    	return cursor;
    }
    
    private Cursor getVideoCursor(){
    	SQLiteDatabase db = mDbVideoHelper.getReadableDatabase();
    	String[] columns = {_ID, VIDEO_BELONGO_CHANNEL, VIDEO_TITLE, VIDEO_LINK, VIDEO_THUMBNAIL,VIDEO_UPLOADTIME,VIDEO_VIEWCOUNT, VIDEO_DURATION, VIDEO_LIKES, VIDEO_DISLIKES};
    	
    	Cursor cursor = db.query(VIDEO_TABLE_NAME, columns, null, null, null, null, null);
    	startManagingCursor(cursor);
    	
    	return cursor;
    }
    
}
