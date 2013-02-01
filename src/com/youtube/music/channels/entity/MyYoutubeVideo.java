package com.youtube.music.channels.entity;

import java.util.Date;

public class MyYoutubeVideo {
	int channelInt;
    String title;
    String thumbnail;
    String link;
    String   uploadTime;
    int    viewCount;
    int    duration;
    int    likes;
    int    dislikes;
    int id;

    public MyYoutubeVideo() {
        this(0,0,"", "", "", "", 0, 0, 0, 0);
    }

    public MyYoutubeVideo(int _id,int channelInt,String title, String link, String thumbnail, String uploadTime, int viewCount, int duration, int likes, int dislikes) {
        this.id= _id;
    	this.channelInt = channelInt;
    	this.title = title;
        this.link = link;
        this.thumbnail = thumbnail;
        this.uploadTime = uploadTime;
        this.viewCount = viewCount;
        this.dislikes = dislikes;
        this.likes = likes;
        this.duration = duration;
    }
    
    public int getId(){
    	return id;
    }
    
    public int getChannelInt(){
    	return channelInt;
    }

    public String getTitle() {
        return title;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public String getLink() {
        return link;
    }

    public int getViewCount() {
        return viewCount;
    }

    public String getUploadDate() {
        return uploadTime;
    }

    public int getLikes() {
        return likes;
    }

    public int getDislikes() {
        return dislikes;
    }

    public int getDuration() {
        return duration;
    }

}
