package com.youtube.music.channels.entity;

public class Channel {
    String name;
    String link;

    public Channel() {
        this("", "");
    }

    public Channel(String name, String link) {
        this.name = name;
        this.link = link;
    }

    public String getName() {
        return name;
    }

    public String getLink() {
        return link;
    }

}
