package com.youtube.music.sqlite;

import android.provider.BaseColumns;

public interface DbMyVideoConstants extends BaseColumns {
	public static final String VIDEO_TABLE_NAME = "myvideos";
	
	public static final String VIDEO_BELONGO_CHANNEL = "belongchannel";
	public static final String VIDEO_TITLE = "title";
	public static final String VIDEO_LINK = "link";
	public static final String VIDEO_THUMBNAIL = "thumbnail";
	public static final String VIDEO_UPLOADTIME = "uploadtime";
	public static final String VIDEO_VIEWCOUNT = "view_count";
	public static final String VIDEO_DURATION = "duration";
	public static final String VIDEO_LIKES = "likes";
	public static final String VIDEO_DISLIKES = "dislikes";

}
