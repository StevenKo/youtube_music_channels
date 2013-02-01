package com.youtube.music.sqlite;

import android.provider.BaseColumns;

public interface DbMyChannelConstants extends BaseColumns {
	public static final String TABLE_NAME = "mychannels";
	
	public static final String NAME = "name";
	public static final String LINK = "link";
	public static final String ID = "id";
	public static final String THUMBNAIL = "thumbnail";
}
