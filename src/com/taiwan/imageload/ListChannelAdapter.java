package com.taiwan.imageload;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import com.youtube.music.channels.R;
import com.youtube.music.channels.entity.Channel;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class ListChannelAdapter extends BaseAdapter {

    private final Activity        activity;
    private final ArrayList<Channel> data;
    private static LayoutInflater inflater = null;
    public ImageLoader            imageLoader;

    public ListChannelAdapter(Activity a, ArrayList<Channel> d) {
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

    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        if (convertView == null)
            vi = inflater.inflate(R.layout.item_channel_list, null);

        TextView text = (TextView) vi.findViewById(R.id.text_channel_list);
        ImageView image = (ImageView) vi.findViewById(R.id.image_channel_list);
        
        text.setText(data.get(position).getName());
//        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm");  
//        String dateString = formatter.format(data.get(position).getReleaseTime()); 
//        textDate.setText(dateString);
        
        if(data.get(position).getThumbnail().equals("") || data.get(position).getThumbnail() == null){
        	image.setImageResource(R.drawable.app_icon);
        }else{        	
        	imageLoader.DisplayImage(data.get(position).getThumbnail(), image);
        }
        
        return vi;
    }
}
