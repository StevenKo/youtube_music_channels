package com.youtube.music.sqlite;

import static com.youtube.music.sqlite.DbMyChannelConstants.TABLE_NAME;
import static android.provider.BaseColumns._ID;
import static com.youtube.music.sqlite.DbMyChannelConstants.NAME;
import static com.youtube.music.sqlite.DbMyChannelConstants.LINK;
import static com.youtube.music.sqlite.DbMyChannelConstants.ID;
import static com.youtube.music.sqlite.DbMyChannelConstants.THUMBNAIL;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBMyChannelHelper extends SQLiteOpenHelper {
	
	private final static String DATABASE_NAME = "musicchannel.db";
	private final static int DATABASE_VERSION = 1;
	
	public DBMyChannelHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		final String INIT_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
								  _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
								  NAME + " CHAR, " +
								  LINK + " CHAR, " +
								  ID + " CHAR, " +
								  THUMBNAIL + " CHAR);"; 
		db.execSQL(INIT_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
		db.execSQL(DROP_TABLE);
		onCreate(db);
	}

}
