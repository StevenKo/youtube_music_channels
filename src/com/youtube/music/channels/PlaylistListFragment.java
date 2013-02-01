package com.youtube.music.channels;


import java.util.ArrayList;

import com.taiwan.imageload.ListChannelAdapter;
import com.taiwan.imageload.ListPlaylistAdapter;
import com.taiwan.imageload.ListVideoAdapter;
import com.youtube.music.channels.api.ChannelApi;
import com.youtube.music.channels.entity.Playlist;
import com.youtube.music.channels.entity.YoutubeVideo;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;



public class PlaylistListFragment extends ListFragment {

  private static ArrayList<Playlist> playlists = new ArrayList<Playlist>();
  private static String myChannelName;
  private static int myPage;

  
  @Override
  public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    
    new DownloadChannelsTask().execute();
    
  }
  
  public static ListFragment newInstance(String channelName, int page) {     
	 
	  myChannelName = channelName;
	  myPage = page;
	  
	  PlaylistListFragment fragment = new PlaylistListFragment();
	    
      return fragment;
  }

  @Override
  public void onListItemClick(ListView l, View v, int position, long id) {
    // Do something with the data

  }
  
  private class DownloadChannelsTask extends AsyncTask {

      @Override
      protected void onPreExecute() {
          // TODO Auto-generated method stub
          super.onPreExecute();
          

      }

      @Override
      protected Object doInBackground(Object... params) {
          // TODO Auto-generated method stub

    	  playlists = ChannelApi.getChannelPlaylists(myChannelName, myPage); 
//    	  videos = ChannelApi.getPlaylistVideos(myChannelName, myPage);
    	  
          return null;
      }

      @Override
      protected void onPostExecute(Object result) {
          // TODO Auto-generated method stub
          super.onPostExecute(result);

          if(playlists != null){
        	  try{
		          ListPlaylistAdapter myListAdapter = new ListPlaylistAdapter(getActivity(), playlists);
		          setListAdapter(myListAdapter);
        	  }catch(Exception e){
        		  
        	  }
          }

      }
  }
  
} 
