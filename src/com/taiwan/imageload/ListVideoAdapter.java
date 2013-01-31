package com.taiwan.imageload;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import com.youtube.music.channels.R;
import com.youtube.music.channels.entity.YoutubeVideo;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.TextView;

public class ListVideoAdapter extends BaseAdapter {

    private final Activity        activity;
    private final ArrayList<YoutubeVideo> data;
    private static LayoutInflater inflater = null;
    public ImageLoader            imageLoader;

    public ListVideoAdapter(Activity a, ArrayList<YoutubeVideo> d) {
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
            vi = inflater.inflate(R.layout.item_news_list, null);

        TextView text = (TextView) vi.findViewById(R.id.text_news_list);
        ImageView image = (ImageView) vi.findViewById(R.id.image_news_list);
        TextView textDate = (TextView) vi.findViewById(R.id.text_list_date);
        TextView textViews = (TextView) vi.findViewById(R.id.text_list_views);
        TextView textDuration = (TextView) vi.findViewById(R.id.text_list_duration);
        TextView textLikes = (TextView) vi.findViewById(R.id.text_list_like);
        
        
        
//        String countString = Integer.toString(data.get(position).getViewCount());
        int views = data.get(position).getViewCount();
        textViews.setText(NumberFormat.getNumberInstance(Locale.US).format(views)+" "+activity.getResources().getString(R.string.views));
        
        int[] intTime = splitToComponentTimes(data.get(position).getDuration());
        if(intTime[0]!=0){
        	textDuration.setText(activity.getResources().getString(R.string.time)+":"+Integer.toString(intTime[0])+":"+Integer.toString(intTime[1])+":"+Integer.toString(intTime[2]));
        }else{
        	textDuration.setText(activity.getResources().getString(R.string.time)+":"+Integer.toString(intTime[1])+":"+Integer.toString(intTime[2]));
        }
        
        textLikes.setText(Integer.toString(data.get(position).getLikes())+" "+activity.getResources().getString(R.string.likes));
        
        text.setText(data.get(position).getTitle());
        
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");  
        String dateString = formatter.format(data.get(position).getUploadDate()); 
        textDate.setText(activity.getResources().getString(R.string.launch)+": "+dateString);
        
        if(data.get(position).getThumbnail().equals("") || data.get(position).getThumbnail() == null){
        	image.setImageResource(R.drawable.app_icon);
        }else{        	
        	imageLoader.DisplayImage(data.get(position).getThumbnail(), image);
        }
        
        return vi;
    }
    
    public static int[] splitToComponentTimes(int i)
    {
        long longVal = (long)i;
        int hours = (int) longVal / 3600;
        int remainder = (int) longVal - hours * 3600;
        int mins = remainder / 60;
        remainder = remainder - mins * 60;
        int secs = remainder;

        int[] ints = {hours , mins , secs};
        return ints;
    }
}
