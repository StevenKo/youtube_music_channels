package com.youtube.music.channels;


import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.ListFragment;
import android.support.v4.view.ViewPager;
import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.viewpagerindicator.TabPageIndicator;


public class VideosActivity extends SherlockFragmentActivity {
	
//	private ListView listView;
	private Bundle mBundle;
	private String channelName;
	private String channelLink;
	private int channelId;
//	private EditText search;
	private String[] CONTENT;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.simple_tabs);
        
		
		
		final ActionBar ab = getSupportActionBar();		     
        mBundle = this.getIntent().getExtras();
        channelName = mBundle.getString("ChannelName");
        channelId = mBundle.getInt("ChannelId");
        channelLink = mBundle.getString("ChannelLink");
        
        ab.setTitle(channelName);
        ab.setDisplayHomeAsUpEnabled(true);
       
		
        Resources res = getResources();
        CONTENT = res.getStringArray(R.array.tabs);
        
        FragmentPagerAdapter adapter = new GoogleMusicAdapter(getSupportFragmentManager());

        ViewPager pager = (ViewPager)findViewById(R.id.pager);
        pager.setAdapter(adapter);

        TabPageIndicator indicator = (TabPageIndicator)findViewById(R.id.indicator);
        indicator.setViewPager(pager);
        
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
	
	class GoogleMusicAdapter extends FragmentPagerAdapter {
        public GoogleMusicAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public ListFragment getItem(int position) {
//            return TestFragment.newInstance(CONTENT[position % CONTENT.length]);
        	
        	ListFragment kk = new ListFragment();
        	
        	if(position==0){
        		kk = NewsListFragment.newInstance(channelLink, 0);
        	}else if(position == 1){
        		kk = PopularListFragment.newInstance(channelLink, 0);
        	}else if(position == 2){
        		kk = PlaylistListFragment.newInstance(channelLink, 0);
        	}else if(position == 3){
        		kk = FavoriteListFragment.newInstance(channelLink, 0);
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
   
	
	

}
