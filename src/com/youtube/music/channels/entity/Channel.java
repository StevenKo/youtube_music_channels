package com.youtube.music.channels.entity;

public class Channel {
    String name;
    String link;
    int    id;
    String thumbnail;

    public Channel() {
        this("", "", 0, "");
    }

    public Channel(String name, String link, int id, String thumbnail) {
        this.name = name;
        this.link = link;
        this.id = id;
        this.thumbnail = thumbnail;
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

}
