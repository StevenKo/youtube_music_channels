package com.youtube.music.channels;

import static android.provider.BaseColumns._ID;
import static com.youtube.music.sqlite.DbMyChannelConstants.NAME;
import static com.youtube.music.sqlite.DbMyChannelConstants.LINK;
import static com.youtube.music.sqlite.DbMyChannelConstants.ID;
import static com.youtube.music.sqlite.DbMyChannelConstants.THUMBNAIL;
import static com.youtube.music.sqlite.DbMyChannelConstants.TABLE_NAME;
import static com.youtube.music.sqlite.DbMyVideoConstants.*;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.LineNumberReader;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Locale;
import java.util.regex.Pattern;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.app.ActionBar.OnNavigationListener;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.taiwan.imageload.ListChannelAdapter;
import com.taiwan.imageload.ListMyVideoAdapter;
import com.taiwan.imageload.ListNothingAdapter;
import com.youtube.music.channels.api.ChannelApi;
import com.youtube.music.channels.entity.Channel;
import com.youtube.music.channels.entity.MyYoutubeVideo;
import com.youtube.music.sqlite.DBMyChannelHelper;
import com.youtube.music.sqlite.DBMyVideoHelper;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
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
	private ArrayList<Channel> searchChannelArray = new ArrayList<Channel>();
	private ArrayList<MyYoutubeVideo> searchVideoArray =  new ArrayList<MyYoutubeVideo>();
	private AlertDialog.Builder finishDialog;
	private String systemLanguage;
//	private SharedPreferences prefs;
//	private AlertDialog.Builder rattingDialog;
//	private int openTimes;
	private String extStorageDirectory;
	private Boolean isFileExist = true;
	String channel_URL="https://docs.google.com/spreadsheet/pub?key=0Ajs1n6DE4vP1dGpxWDlQcTNqWVROU0FnTWxzdmRjUUE&output=csv";
	String channel_name="channels.csv";
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
//        prefs = getSharedPreferences("pref", 0);
//		openTimes = prefs.getInt("openTimes", 0);
//		if(openTimes!=999){
//			openTimes = openTimes +1;
//		}
        extStorageDirectory = Environment.getExternalStorageDirectory().toString();
        
        systemLanguage = Locale.getDefault().getDisplayLanguage();
        
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
	                         myChannels.clear();
	                         getMyChannles();
	                         if(isOnline()){
	                        	 setChannels(channel_name, channelArea);
	                        	 if(!isFileExist){
	                        		 new DownloadChannelsTask().execute();
	                        	 }else{
	                        		 setListUI(theChannels);
	                        	 }
	                         }else{
	                        	 Toast.makeText(MainActivity.this, getResources().getString(R.string.no_net), Toast.LENGTH_LONG).show();
	                         }
                       	 break;
                        case 1: //Japanese
	                         channelArea = 2;
	                         myChannels.clear();
	                         getMyChannles();
	                         if(isOnline()){
	                        	 setChannels(channel_name, channelArea);
	                        	 if(!isFileExist){
	                        		 new DownloadChannelsTask().execute();
	                        	 }else{
	                        		 setListUI(theChannels);
	                        	 }
	                         }else{
	                        	 Toast.makeText(MainActivity.this, getResources().getString(R.string.no_net), Toast.LENGTH_LONG).show();
	                         }
                       	 break;
                        case 2: //Korean
	                         channelArea = 3;
	                         myChannels.clear();
	                         getMyChannles();
	                         if(isOnline()){
	                        	 setChannels(channel_name, channelArea);
	                        	 if(!isFileExist){
	                        		 new DownloadChannelsTask().execute();
	                        	 }else{
	                        		 setListUI(theChannels);
	                        	 }
	                         }else{
	                        	 Toast.makeText(MainActivity.this, getResources().getString(R.string.no_net), Toast.LENGTH_LONG).show();
	                         }
                       	 break;
                        case 3: //English
	                         channelArea = 4;
	                         myChannels.clear();
	                         getMyChannles();
	                         if(isOnline()){
	                        	 setChannels(channel_name, channelArea);
	                        	 if(!isFileExist){
	                        		 new DownloadChannelsTask().execute();
	                        	 }else{
	                        		 setListUI(theChannels);
	                        	 }
	                         }else{
	                        	 Toast.makeText(MainActivity.this, getResources().getString(R.string.no_net), Toast.LENGTH_LONG).show(); 
	                         }
                       	 break;
                        case 4: //My Channels
                        	channelArea = 5;
                        	myChannels.clear();
                        	getMyChannles();
                        	setListUI(myChannels);
                       	 break;
                        case 5: //My Videos
                        	channelArea = 0;
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
        
        if(systemLanguage.equals("中文")){
            ab.setSelectedNavigationItem(0);
         }else if(systemLanguage.equals("日本語")){
            ab.setSelectedNavigationItem(1);	
         }else if(systemLanguage.equals("한국어")){
            ab.setSelectedNavigationItem(2);
         }else{
            ab.setSelectedNavigationItem(3);
         }
        
//        setRattingDialog();
//
//        if(openTimes == 7){
//			openTimes = 0;
//			rattingDialog.show();
//		}

    }
    

    
//    private void setRattingDialog() {
//		// TODO Auto-generated method stub
//    	rattingDialog = new AlertDialog.Builder(this).setTitle("Play Store評分")
//				.setIcon(R.drawable.play_store_icon)
//				.setMessage("嗨, \n給我們一些鼓勵, \n感謝您!")
//				.setPositiveButton("不再提醒", new DialogInterface.OnClickListener() {
//					@Override
//					public void onClick(DialogInterface dialog, int which) {
//						openTimes = 999;
//					}
//				})
//				.setNeutralButton("稍後提醒", new DialogInterface.OnClickListener() {
//					@Override
//					public void onClick(DialogInterface dialog, int which) {
//						dialog.cancel();
//					}
//				})
//				.setNegativeButton("前往", new DialogInterface.OnClickListener() {
//					@Override
//					public void onClick(DialogInterface dialog, int which) {
//						openTimes = 999;
//						Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.taiwan.realtime.news"));
//						startActivity(browserIntent);
//					}
//				});
//		
//	}



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
		
		try{
			cursor.moveToLast();
		    String name = cursor.getString(1);
			String link = cursor.getString(2);
			String  id= cursor.getString(3);
			String  thumbNail= cursor.getString(4);
			Channel aChannel = new Channel(name, link, Integer.valueOf(id), thumbNail,0);
			myChannels.add(aChannel);
			
			while(cursor.moveToPrevious()){
	//    		int id = cursor.getInt(0);
	    		name = cursor.getString(1);
	    		link = cursor.getString(2);
	    	    id= cursor.getString(3);
	    	    thumbNail= cursor.getString(4);
	    		aChannel = new Channel(name, link, Integer.valueOf(id), thumbNail,0);
	    		myChannels.add(aChannel);   		
	    	}
		}catch(Exception e){}
	}
	
	
	private TextWatcher filterTextWatcher = new TextWatcher() {
	    public void afterTextChanged(Editable s) {
	    	if(channelArea>0){
		    	ListChannelAdapter dataAdapter = new ListChannelAdapter(MainActivity.this, searchChannelArray, myChannels);
		    	myList.setAdapter(dataAdapter);
		    	myList.setItemsCanFocus(true);
	    	}else if(channelArea==0){
	    		ListMyVideoAdapter dataAdapter = new ListMyVideoAdapter(MainActivity.this, searchVideoArray, myVideos, 0);
	    		myList.setAdapter(dataAdapter);
		    	myList.setItemsCanFocus(true);
	    	}
	    }

	    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
	    }

	    public void onTextChanged(CharSequence s, int start, int before, int count) {
	        // your search logic here
	    	if(channelArea>0){
	    		searchChannelArray.clear();
	    		ArrayList<Channel> datas = new ArrayList<Channel>();
	    		if(channelArea==5){
	    			datas = myChannels;
	    		}else{
	    			datas = theChannels;
	    		}
		    	for(Channel brandName:datas){
		    		if(brandName.getName().contains(s)){
		    			searchChannelArray.add(brandName);
		    		}
		    	}
	    	}else if(channelArea==0){
	    		searchVideoArray.clear();	    		 
	    		ArrayList<MyYoutubeVideo> datas = new ArrayList<MyYoutubeVideo>();
	    		datas = myVideos;
	    		for(MyYoutubeVideo brandName:datas){
		    		if(brandName.getTitle().contains(s)){
		    			searchVideoArray.add(brandName);
		    		}
		    	}
	    	}
	    }

	};
	
	
	private void getMyVideos() {
		// TODO Auto-generated method stub
		Cursor cursor = getVideoCursor();
		
		try{
			cursor.moveToLast();
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
			
			while(cursor.moveToPrevious()){
	    		id = cursor.getInt(0);
	    		belongchannel = cursor.getInt(1);
	    		title = cursor.getString(2);
	    		link= cursor.getString(3);
	    	    thumbnail= cursor.getString(4);
	    	    uploadtime = cursor.getString(5);
	    		view_count = cursor.getInt(6);
	    		duration= cursor.getInt(7);
	    		likes = cursor.getInt(8);
	    		dislikes = cursor.getInt(9);
	    		aVideo = new MyYoutubeVideo(id,belongchannel, title, link, thumbnail, uploadtime, view_count,  duration, likes, dislikes);
	    		myVideos.add(aVideo);   		
	    	}
		}catch(Exception e){}
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getSupportMenuInflater().inflate(R.menu.activity_main, menu);
		
		 menu.add("Search")
         .setIcon(R.drawable.ic_search_inverse)
         .setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
             @Override
             public boolean onMenuItemActionExpand(MenuItem item) {
            	 search.requestFocus();
                 InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                 imm.showSoftInput(null, InputMethodManager.SHOW_IMPLICIT);
                 return true;
             }

			@Override
			public boolean onMenuItemActionCollapse(MenuItem item) {
				// TODO Auto-generated method stub
				search.setText("");
				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(search.getWindowToken(), 0);
				if(channelArea == 0){
					setListVideo(myVideos); 
				}else if( 0<channelArea && channelArea< 5 ){
					setListUI(theChannels);
				}else if(channelArea == 5){
					setListUI(myChannels);
				}
				return true;
			}})
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
//	    	 Toast.makeText(this, "search pressed", Toast.LENGTH_LONG).show();
	    	 search = (EditText) item.getActionView();
             search.addTextChangedListener(filterTextWatcher);
	    	break;
	    case R.id.menu_refresh:
	    	if(channelArea>0 && channelArea<5){
	            myChannels.clear();
	            getMyChannles();
	            if(isOnline()){
	           	 new DownloadChannelsTask().execute();
	            }else{
	           	 Toast.makeText(MainActivity.this, getResources().getString(R.string.no_net), Toast.LENGTH_LONG).show();
	            }
	    	}else if(channelArea == 0){
	    		myVideos.clear();
            	getMyVideos();
            	setListVideo(myVideos); 
	    	}else if(channelArea == 5){
	    		myChannels.clear();
            	getMyChannles();
            	setListUI(myChannels);
	    	}
	    	break;
	    case R.id.menu_recommand:
	    	final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);

	    	/* Fill it with Data */
	    	emailIntent.setType("plain/text");
	    	emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{"brotherkos@gmail.com"});
	    	emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Recommand Channel from Music Channel");
	    	emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "");

	    	/* Send it off to the Activity-Chooser */
	    	startActivity(Intent.createChooser(emailIntent, "Send mail..."));
	    	break;
	    case R.id.menu_contact:
	    	final Intent contactIntent = new Intent(android.content.Intent.ACTION_SEND);

	    	/* Fill it with Data */
	    	contactIntent.setType("plain/text");
	    	contactIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{"brotherkos@gmail.com"});
	    	contactIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Contact Us from Music Channel");
	    	contactIntent.putExtra(android.content.Intent.EXTRA_TEXT, "");

	    	/* Send it off to the Activity-Chooser */
	    	startActivity(Intent.createChooser(contactIntent, "Send mail..."));
	    	break;
	    }
	    return true;
	}
    
    // Use Google SpreadSheet
    
    
    private class DownloadChannelsTask extends AsyncTask {

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            
            	progressDialog = ProgressDialog.show(MainActivity.this, "", getResources().getString(R.string.downloading));
            	progressDialog.setCancelable(true);

        }

        @Override
		protected Object doInBackground(Object... params) {
			// TODO Auto-generated method stub								
			File Directory = new File(extStorageDirectory+"/Android_Music_Channel/");
			Directory.mkdirs();
			File file = new File(Directory, channel_name);
			try {
				URL url = new URL(channel_URL);
				URLConnection connection = url.openConnection();
				connection.connect();
				InputStream input = new BufferedInputStream(url.openStream());
//				OutputStream output = new FileOutputStream("/sdcard/file_name.extension");
				OutputStream output = new FileOutputStream(file);
				byte data[] = new byte[1];
				while ((input.read(data)) != -1) {
	               
	                output.write(data);
	            }			
				output.flush();
	            output.close();
	            input.close();
			}catch (Exception e) {
				e.toString();
	        }
			
			return null;
		}

        @Override
        protected void onPostExecute(Object result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            progressDialog.dismiss();
            setChannels(channel_name, channelArea);
            if(theChannels!=null){
            	setListUI(theChannels);
            }else{
            	Toast.makeText(MainActivity.this, getResources().getString(R.string.no_net), Toast.LENGTH_LONG).show();
            }

        }
    }
    
    private void setChannels(String file, int area_id) {
		// TODO Auto-generated method stub
		try {
			theChannels.clear();
		 	//read the file and save to Array
	    	String strFile =  extStorageDirectory +"/Android_Music_Channel/"+file;
			BufferedReader reader = new BufferedReader( new FileReader(strFile));
			LineNumberReader lineReader = new LineNumberReader(reader);
	        String line;
	        while ((line = lineReader.readLine()) != null) {
	        	if(lineReader.getLineNumber()!=1){
	        		String[] RowData = parseCsvLine(line.toString());
	        		if(RowData.length == 5){
	        			//Rowdata: id, area_id, name, link_name, thumbnail
	        			//Channel: String name, String link, int id, String thumbnail, int area_id
	        			Channel aChannel = new Channel(RowData[2], RowData[3], Integer.valueOf(RowData[0]), RowData[4],Integer.valueOf(RowData[1]));
	        			if(aChannel.getAreaId()==area_id){
	        				theChannels.add(aChannel);
	        			}
	        		}
	        	}	        	
	        }
	        isFileExist = true;
		}catch (IOException ex) {
			 isFileExist = false;
//			 Toast.makeText(MainActivity.this, "找不到資料",Toast.LENGTH_SHORT).show();
		}		
	}
    
//    private class DownloadChannelsTask extends AsyncTask {
//
//        @Override
//        protected void onPreExecute() {
//            // TODO Auto-generated method stub
//            super.onPreExecute();
//            
//            	progressDialog = ProgressDialog.show(MainActivity.this, "", getResources().getString(R.string.downloading));
//            	progressDialog.setCancelable(true);
//
//        }
//
//        @Override
//        protected Object doInBackground(Object... params) {
//            // TODO Auto-generated method stub
//
//        	theChannels = ChannelApi.getChannels(channelArea);       
//  
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Object result) {
//            // TODO Auto-generated method stub
//            super.onPostExecute(result);
//            progressDialog.dismiss();
//            if(theChannels!=null){
//             setListUI(theChannels);
//            }else{
//            	Toast.makeText(MainActivity.this, getResources().getString(R.string.no_net), Toast.LENGTH_LONG).show();
//            }
//
//        }
//    }
    
    private void setListUI(ArrayList<Channel> channels) {
		// TODO Auto-generated method stub
    	 myList = (ListView) findViewById(R.id.main_listview);
    	 
    	 if(channels.size()!=0){
	         myListAdapter = new ListChannelAdapter(this, channels, myChannels);
	         myList.setAdapter(myListAdapter);
	         myList.setItemsCanFocus(true);
    	 }else{
    		 ListNothingAdapter nothingAdapter = new ListNothingAdapter(this);
    		 myList.setAdapter(nothingAdapter);
    	 }
        
	}
    
    private void setListVideo(ArrayList<MyYoutubeVideo> myVideos) {    	
		
    	if (myVideos.size()!=0){
	    	ListMyVideoAdapter videoAdapter = new ListMyVideoAdapter(this, myVideos, myVideos, 0);
			myList.setAdapter(videoAdapter);
			myList.setItemsCanFocus(true);		
    	}else{
	   		ListNothingAdapter nothingAdapter = new ListNothingAdapter(this);
	   		myList.setAdapter(nothingAdapter);
   	 	}
		
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
    
    @Override
    public void  onBackPressed  () {  
			finishDialog = new AlertDialog.Builder(this).setTitle(getResources().getString(R.string.close))
					.setMessage(getResources().getString(R.string.leave))
					.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							finish();
							System.exit(0);
						}
					})
					.setNegativeButton("No", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.cancel();
						}
					});
			finishDialog.show();          
    }
    
    public boolean isOnline() {
	    ConnectivityManager cm =
	        (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo netInfo = cm.getActiveNetworkInfo();
	    if (netInfo != null && netInfo.isConnectedOrConnecting()) {
	        return true;
	    }
	    return false;
	}
    
    public static String[] parseCsvLine(String line) {
        // Create a pattern to match breaks
        Pattern p =
            Pattern.compile(",(?=([^\"]*\"[^\"]*\")*(?![^\"]*\"))");
        // Split input with the pattern
        String[] fields = p.split(line);
        for (int i = 0; i < fields.length; i++) {
            // Get rid of residual double quotes
            fields[i] = fields[i].replace("\"", "");
        }
        return fields;
    }
    
}
