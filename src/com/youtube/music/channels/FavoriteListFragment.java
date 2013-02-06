package com.youtube.music.channels;


import java.util.ArrayList;

import com.taiwan.imageload.ListChannelAdapter;
import com.taiwan.imageload.ListMyVideoAdapter;
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



public class FavoriteListFragment extends ListFragment {

  private static ArrayList<MyYoutubeVideo> myVideos = new ArrayList<MyYoutubeVideo>();
  private static int channelId;
 

  
  @Override
  public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    
    new DownloadChannelsTask().execute();
    
  }
  
  public static ListFragment newInstance(int mchannelId, ArrayList<MyYoutubeVideo> mVideos ) {     
	 
	  channelId = mchannelId;
	  myVideos = mVideos;
	  
	  FavoriteListFragment fragment = new FavoriteListFragment();
	    
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

          return null;
      }

      @Override
      protected void onPostExecute(Object result) {
          // TODO Auto-generated method stub
          super.onPostExecute(result);        
          
          if(myVideos != null){
        	  
        	  ArrayList<MyYoutubeVideo> myChannelVideo = new ArrayList<MyYoutubeVideo>();
              for(int i=myVideos.size()-1; i>-1;i--){
            	  if(myVideos.get(i).getChannelInt()==channelId){
            		  myChannelVideo.add(myVideos.get(i));
            	  }
              }
        	  
             try{ 
              if(myChannelVideo.size()!=0){
            	  ListMyVideoAdapter myListAdapter = new ListMyVideoAdapter(getActivity(), myChannelVideo,myChannelVideo,channelId);
    	          setListAdapter(myListAdapter);        	  
              }else{
            	  ListNothingAdapter nothingAdapter = new ListNothingAdapter(getActivity());
        		  setListAdapter(nothingAdapter);
              }
             }catch(Exception e){
            	 
             }
	          
          }

      }
  }
  
} 
