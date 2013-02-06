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
import android.app.ProgressDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.costum.android.widget.LoadMoreListView;
import com.costum.android.widget.LoadMoreListView.OnLoadMoreListener;
import com.taiwan.imageload.ListNothingAdapter;
import com.taiwan.imageload.ListVideoAdapter;
import com.youtube.music.channels.api.ChannelApi;
import com.youtube.music.channels.entity.MyYoutubeVideo;
import com.youtube.music.channels.entity.YoutubeVideo;
import com.youtube.music.sqlite.DBMyVideoHelper;


public class PlayListActivity extends SherlockActivity {
	
//	private ListView listView;
	private Bundle mBundle;
	private String listTitle;
	private String listId;
//	private EditText search;
	private String[] CONTENT;
	private ArrayList<MyYoutubeVideo> myVideos = new ArrayList<MyYoutubeVideo>();
	private ArrayList<YoutubeVideo> listVideo = new ArrayList<YoutubeVideo>();
	private ArrayList<YoutubeVideo> moreVideos = new ArrayList<YoutubeVideo>();
	private DBMyVideoHelper mDbVideoHelper;
	private ProgressDialog  progressDialog   = null;
	private LoadMoreListView  myList;
	private LinearLayout progressLayout;
	private ListVideoAdapter videoAdapter;
	private Boolean checkLoad = true;
	private int myPage = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.loadmore);
		myList = (LoadMoreListView) findViewById(R.id.news_list);
		progressLayout = (LinearLayout) findViewById(R.id.layout_progress);
		progressLayout.setVisibility(View.GONE);
		
		openDatabase();
		getMyVideos();
		
		final ActionBar ab = getSupportActionBar();		     
        mBundle = this.getIntent().getExtras();
        listTitle = mBundle.getString("ListTitle");
        listId = mBundle.getString("ListId");
        
        ab.setTitle(listTitle);
        ab.setDisplayHomeAsUpEnabled(true);
       
        new DownloadChannelsTask().execute();
       
        myList.setOnLoadMoreListener(new OnLoadMoreListener() {
			public void onLoadMore() {
				// Do the work to load more items at the end of list
				
				if(checkLoad){
					myPage = myPage +1;
					new LoadMoreTask().execute();
				}else{
					myList.onLoadMoreComplete();
				}
			}
		});
        
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
	
	private class DownloadChannelsTask extends AsyncTask {

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            
            	progressDialog = ProgressDialog.show(PlayListActivity.this, "", "Loading, please wait...");
            	progressDialog.setCancelable(true);

        }

        @Override
        protected Object doInBackground(Object... params) {
            // TODO Auto-generated method stub

        	listVideo = ChannelApi.getPlaylistVideos(listId, 0);      
  
            return null;
        }

        @Override
        protected void onPostExecute(Object result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            progressDialog.dismiss();
            if(listVideo!=null){
            	setListUI(listVideo);
            }else{
            	ListNothingAdapter nothingAdapter = new ListNothingAdapter(PlayListActivity.this);
            	myList.setAdapter(nothingAdapter);
            }

        }
    }
	
	private void setListUI(ArrayList<YoutubeVideo> listVideo) {
		// TODO Auto-generated method stub
		videoAdapter = new ListVideoAdapter(PlayListActivity.this, listVideo, myVideos, 0);
		myList.setAdapter(videoAdapter);
		
	}
	
	private class LoadMoreTask extends AsyncTask {

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            

        }

        @Override
        protected Object doInBackground(Object... params) {
            // TODO Auto-generated method stub

        	moreVideos = ChannelApi.getPlaylistVideos(listId, myPage);  
        	if(moreVideos!= null){
	        	for(int i=0; i<moreVideos.size();i++){
	        		listVideo.add(moreVideos.get(i));
	            }
        	}
        	
        	
            return null;
        }

        @Override
        protected void onPostExecute(Object result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            
            if(moreVideos!= null){
            	videoAdapter.notifyDataSetChanged();	                
            }else{
                checkLoad= false;
                Toast.makeText(PlayListActivity.this, "no more data", Toast.LENGTH_SHORT).show();            	
            }       
          	myList.onLoadMoreComplete();
          	
          	
        }
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
