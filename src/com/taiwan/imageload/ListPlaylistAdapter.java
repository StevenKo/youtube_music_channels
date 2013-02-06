package com.taiwan.imageload;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import com.youtube.music.channels.PlayListActivity;
import com.youtube.music.channels.R;
import com.youtube.music.channels.VideosActivity;
import com.youtube.music.channels.entity.Playlist;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ListPlaylistAdapter extends BaseAdapter {

    private final Activity        activity;
    private final ArrayList<Playlist> data;
    private static LayoutInflater inflater = null;
    public ImageLoader            imageLoader;

    public ListPlaylistAdapter(Activity a, ArrayList<Playlist> d) {
        activity = a;
        data = d;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        imageLoader = new ImageLoader(activity.getApplicationContext(), 70);
        
    }

    public int getCount() {
        return data.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        if (convertView == null)
        	vi = inflater.inflate(R.layout.item_playlist_item, null);
        
        
        vi.setClickable(true);
        vi.setFocusable(true);
        vi.setBackgroundResource(android.R.drawable.menuitem_background);
        vi.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
//                Toast.makeText(activity, "tt", Toast.LENGTH_SHORT).show();
            	Intent intent = new Intent(activity, PlayListActivity.class);
 				Bundle bundle = new Bundle();
 				bundle.putString("ListId", data.get(position).getListId()); 
 				bundle.putString("ListTitle", data.get(position).getTitle());
 				intent.putExtras(bundle);
 				activity.startActivity(intent); 
                
            }

        });
        
        TextView text = (TextView) vi.findViewById(R.id.text_list);
        ImageView image = (ImageView) vi.findViewById(R.id.image_list);
        
        text.setText(data.get(position).getTitle());
        
        if(data.get(position).getThumbnail().equals("") || data.get(position).getThumbnail() == null){
        	image.setImageResource(R.drawable.app_icon);
        }else{        	
        	imageLoader.DisplayImage(data.get(position).getThumbnail(), image);
        }
        
        return vi;
    }
}
