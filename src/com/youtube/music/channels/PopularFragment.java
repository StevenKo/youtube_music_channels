package com.youtube.music.channels;

import java.util.ArrayList;

import com.costum.android.widget.LoadMoreListView;
import com.costum.android.widget.LoadMoreListView.OnLoadMoreListener;
import com.taiwan.imageload.ListNothingAdapter;
import com.taiwan.imageload.ListVideoAdapter;
import com.youtube.music.channels.api.ChannelApi;
import com.youtube.music.channels.entity.MyYoutubeVideo;
import com.youtube.music.channels.entity.YoutubeVideo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

public final class PopularFragment extends Fragment {
    
	private ArrayList<YoutubeVideo> videos = new ArrayList<YoutubeVideo>();
	private static String myChannelName;
	private static int myPage;
	private static ArrayList<MyYoutubeVideo> myVideos;
	private static int channelInt;
	private LoadMoreListView  myList;
	private ArrayList<YoutubeVideo> moreVideos;
	private ListVideoAdapter myListAdapter;
	private Boolean checkLoad = true;
	private LinearLayout progressLayout;
	
    public static PopularFragment newInstance(String channelName, int page, ArrayList<MyYoutubeVideo> mVideo, int channelId) {     
   	 
  	  myChannelName = channelName;
  	  myPage = page;
  	  myVideos = mVideo;
  	  channelInt = channelId;
  	  PopularFragment fragment = new PopularFragment();
  	    
      return fragment;
        
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        
        
        new DownloadChannelsTask().execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        
    	View myFragmentView = inflater.inflate(R.layout.loadmore, container, false);
    	progressLayout = (LinearLayout) myFragmentView.findViewById(R.id.layout_progress);
    	myList = (LoadMoreListView) myFragmentView.findViewById(R.id.news_list);
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
        return myFragmentView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
       
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

        	videos = ChannelApi.getChannelMostViewedVideo(myChannelName, myPage);

            return null;
        }

        @Override
        protected void onPostExecute(Object result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            progressLayout.setVisibility(View.GONE);
            if(videos !=null){
          	  try{
          		  myListAdapter = new ListVideoAdapter(getActivity(), videos, myVideos, channelInt);
  		          myList.setAdapter(myListAdapter);
          	  }catch(Exception e){
          		 
          	  }
            }else{
          	  ListNothingAdapter nothingAdapter = new ListNothingAdapter(getActivity());
          	     myList.setAdapter(nothingAdapter);
            }

        }
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

        	moreVideos = ChannelApi.getChannelMostViewedVideo(myChannelName, myPage);  
        	if(moreVideos!= null){
	        	for(int i=0; i<moreVideos.size();i++){
	            	videos.add(moreVideos.get(i));
	            }
        	}
        	
        	
            return null;
        }

        @Override
        protected void onPostExecute(Object result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            
            if(moreVideos!= null){
            	myListAdapter.notifyDataSetChanged();	                
            }else{
                checkLoad= false;
                Toast.makeText(getActivity(), "no more data", Toast.LENGTH_SHORT).show();            	
            }       
          	myList.onLoadMoreComplete();
          	
          	
        }
    }
}
