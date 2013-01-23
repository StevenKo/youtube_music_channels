package com.youtube.music.channels.entity;

import java.util.Date;

public class YoutubeVideo {
    String title;
    String thumbnail;
    String link;
    Date   uploadTime;
    int    viewCount;

    public YoutubeVideo() {
        this("", "", "", new Date(), 0);
    }

    public YoutubeVideo(String title, String link, String thumbnail, Date uploadTime, int viewCount) {
        this.title = title;
        this.link = link;
        this.thumbnail = thumbnail;
        this.uploadTime = uploadTime;
        this.viewCount = viewCount;
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

    public Date getUploadDate() {
        return uploadTime;
    }

}
