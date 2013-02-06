package com.youtube.music.channels.entity;

public class Channel {
	int areaId;
    String name;
    String link;
    int    id;
    String thumbnail;

    public Channel() {
        this("", "", 0, "",0);
    }

    public Channel(String name, String link, int id, String thumbnail, int area_id) {
        this.name = name;
        this.link = link;
        this.id = id;
        this.thumbnail = thumbnail;
        this.areaId = area_id;
    }

    public String getName() {
        return name;
    }

    public String getLink() {
        return link;
    }

    public int getId() {
        return id;
    }

    public String getThumbnail() {
        return thumbnail;
    }
    
    public int getAreaId(){
    	return areaId;
    }

}
