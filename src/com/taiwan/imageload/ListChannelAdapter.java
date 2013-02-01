package com.taiwan.imageload;

import static android.provider.BaseColumns._ID;
import static com.youtube.music.sqlite.DbMyChannelConstants.NAME;
import static com.youtube.music.sqlite.DbMyChannelConstants.LINK;
import static com.youtube.music.sqlite.DbMyChannelConstants.ID;
import static com.youtube.music.sqlite.DbMyChannelConstants.THUMBNAIL;
import static com.youtube.music.sqlite.DbMyChannelConstants.TABLE_NAME;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import com.youtube.music.channels.MainActivity;
import com.youtube.music.channels.R;
import com.youtube.music.channels.VideosActivity;
import com.youtube.music.channels.entity.Channel;
import com.youtube.music.sqlite.DBMyChannelHelper;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class ListChannelAdapter extends BaseAdapter {
	
	private DBMyChannelHelper myDBHelper;
    private final Activity        activity;
    private ArrayList<Channel> data;
    private final ArrayList<Channel> myChannels;
    private static LayoutInflater inflater = null;
    public ImageLoader            imageLoader;
    private Boolean isChecked = false;

    public ListChannelAdapter(Activity a, ArrayList<Channel> d, ArrayList<Channel> myChannel) {
        activity = a;    
        data = d;
        myChannels = myChannel;
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
            vi = inflater.inflate(R.layout.item_channel_list, null);
        
        vi.setClickable(true);
        vi.setFocusable(true);
        vi.setBackgroundResource(android.R.drawable.menuitem_background);
        vi.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
//                Toast.makeText(activity, "tt", Toast.LENGTH_SHORT).show();
                
                Intent intent = new Intent(activity, VideosActivity.class);
 				Bundle bundle = new Bundle();
 				bundle.putInt("ChannelId", data.get(position).getId()); 
 				bundle.putString("ChannelName", data.get(position).getName());
 				bundle.putString("ChannelLink", data.get(position).getLink());
 				intent.putExtras(bundle);
 				activity.startActivity(intent);
            }

        });
        
        
        final CheckBox checkBox = (CheckBox) vi.findViewById(R.id.checkbox_channel);
        
        
        
//        checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//            	
//            	myDBHelper = new DBMyChannelHelper(activity); //open database
//            	
//                if(isChecked){
////                	buttonView.setChecked(true);
////                	Toast.makeText(activity, "tt", Toast.LENGTH_SHORT).show();
//                	// Add to db
//                	SQLiteDatabase db = myDBHelper.getWritableDatabase();
//                	ContentValues values = new ContentValues();
//                	String dName = data.get(position).getName();
//                	values.put(NAME, dName);
//                	values.put(LINK, data.get(position).getLink());
//                	values.put(ID, data.get(position).getId());
//                	values.put(THUMBNAIL, data.get(position).getThumbnail());
//                	db.insert(TABLE_NAME, null, values);
//                }else{
////                    buttonView.setChecked(false);
//                	// Delete from db
//                	SQLiteDatabase db = myDBHelper.getWritableDatabase();
//                	db.delete(TABLE_NAME, ID + "=" + data.get(position).getId(), null);
//                }
//                
//                myDBHelper.close();
//            }
//        });
        
        // check add to MyChannel or not
        if(myChannels!= null){
	        for(int i=0; i<myChannels.size(); i++){
	        	
	        	int kk = data.get(position).getId();
	        	int ii = myChannels.get(i).getId();
	        	
	        	if(kk == ii){
	        		checkBox.setChecked(true);
//	        		isChecked = true;
	        		break;
	        	}else{
	        		checkBox.setChecked(false);
//	        		isChecked =false;
	        	}
	        }
        }
        
        checkBox.setOnClickListener((new OnClickListener(){  
            public void onClick(View v) { 
            	myDBHelper = new DBMyChannelHelper(activity); //open database
            	
                if(checkBox.isChecked()){
//                	buttonView.setChecked(true);
//                	Toast.makeText(activity, "tt", Toast.LENGTH_SHORT).show();
                	// Add to db
                	SQLiteDatabase db = myDBHelper.getWritableDatabase();
                	ContentValues values = new ContentValues();
                	String dName = data.get(position).getName();
                	values.put(NAME, dName);
                	values.put(LINK, data.get(position).getLink());
                	values.put(ID, data.get(position).getId());
                	values.put(THUMBNAIL, data.get(position).getThumbnail());
                	db.insert(TABLE_NAME, null, values);
                }else{
//                    buttonView.setChecked(false);
                	// Delete from db
                	SQLiteDatabase db = myDBHelper.getWritableDatabase();
                	db.delete(TABLE_NAME, ID + "=" + data.get(position).getId(), null);
                }
                
                myDBHelper.close();
  
            }  
        }));
        
        
//        if(channel.getAddBoolean()){
//        	checkBox.setChecked(true);
//        }else{
//        	checkBox.setChecked(false);
//        }
        
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
