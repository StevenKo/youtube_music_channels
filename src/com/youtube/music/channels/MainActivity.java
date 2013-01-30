package com.youtube.music.channels;

import java.util.ArrayList;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.app.ActionBar.OnNavigationListener;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.taiwan.imageload.ListChannelAdapter;
import com.youtube.music.channels.api.ChannelApi;
import com.youtube.music.channels.entity.Channel;
import com.youtube.music.channels.VideosActivity;

import android.app.ProgressDialog;
import android.content.Intent;
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
	
	private int channelArea;
	private ArrayList<Channel> theChannels;
	private ListView myList;
	private ListChannelAdapter myListAdapter;
	private ProgressDialog  progressDialog   = null;
	private EditText search;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
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
//                        case 4: //others                 
//                       	 break;
                        case 4: //My Channels                       
                       	 break;
                        case 5: //My Videos                       
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
            setListUI();

        }
    }
    
    private void setListUI() {
		// TODO Auto-generated method stub
    	 myList = (ListView) findViewById(R.id.main_listview);
         myListAdapter = new ListChannelAdapter(this, theChannels);
         myList.setAdapter(myListAdapter);
         
         myList.setOnItemClickListener(new OnItemClickListener() {
 			@Override
 			public void onItemClick(AdapterView<?> parent, View view,
 					int position, long id) {

	 				Intent intent = new Intent(MainActivity.this, VideosActivity.class);
	 				Bundle bundle = new Bundle();
	 				bundle.putInt("ChannelId", theChannels.get(position).getId()); 
	 				bundle.putString("ChannelName", theChannels.get(position).getName());
	 				bundle.putString("ChannelLink", theChannels.get(position).getLink());
	 				intent.putExtras(bundle);
	   				startActivity(intent);
 		

 			}
 		});
	}
    
}
