package com.youtube.music.channels;


import java.util.ArrayList;

import com.taiwan.imageload.ListChannelAdapter;
import com.taiwan.imageload.ListNothingAdapter;
import com.taiwan.imageload.ListVideoAdapter;
import com.youtube.music.channels.api.ChannelApi;
import com.youtube.music.channels.entity.MyYoutubeVideo;
import com.youtube.music.channels.entity.YoutubeVideo;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;



public class PopularListFragment extends ListFragment {

  private static ArrayList<YoutubeVideo> videos = new ArrayList<YoutubeVideo>();
  private static String myChannelName;
  private static int myPage;
  private static ArrayList<MyYoutubeVideo> myVideos;
  private static int channelInt;

  
  @Override
  public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    
    new DownloadChannelsTask().execute();
    
  }
  
  public static ListFragment newInstance(String channelName, int page, ArrayList<MyYoutubeVideo> mVideo, int channelId) {     
	 
	  myChannelName = channelName;
	  myPage = page;
	  myVideos = mVideo;
	  channelInt = channelId;
	  PopularListFragment fragment = new PopularListFragment();
	    
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

//    	  videos = ChannelApi.getChannelVideo(myChannelName, myPage, "");
    	  videos = ChannelApi.getChannelMostViewedVideo(myChannelName, myPage);

          return null;
      }

      @Override
      protected void onPostExecute(Object result) {
          // TODO Auto-generated method stub
          super.onPostExecute(result);

          if(videos!=null){
        	  try{
		          ListVideoAdapter myListAdapter = new ListVideoAdapter(getActivity(), videos, myVideos, channelInt);
		          setListAdapter(myListAdapter);
        	  }catch(Exception e){}
          }else{
        	  ListNothingAdapter nothingAdapter = new ListNothingAdapter(getActivity());
    		  setListAdapter(nothingAdapter);
          }

      }
  }
  
} 
