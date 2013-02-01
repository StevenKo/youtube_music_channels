package com.youtube.music.sqlite;


import static android.provider.BaseColumns._ID;
import static com.youtube.music.sqlite.DbMyVideoConstants.*;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBMyVideoHelper extends SQLiteOpenHelper {
	
	private final static String DATABASE_NAME = "musicvideo.db";
	private final static int DATABASE_VERSION = 1;
	
	public DBMyVideoHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		final String INIT_TABLE = "CREATE TABLE " + VIDEO_TABLE_NAME + " (" +
								  _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
								  VIDEO_BELONGO_CHANNEL + " INT, " +
								  VIDEO_TITLE + " CHAR, " +
								  VIDEO_LINK + " CHAR, " +
								  VIDEO_THUMBNAIL + " CHAR, " +
								  VIDEO_UPLOADTIME + " CHAR, " +
								  VIDEO_VIEWCOUNT + " INT, " +
								  VIDEO_DURATION + " INT, " +
								  VIDEO_LIKES + " INT, " +
								  VIDEO_DISLIKES + " INT);"; 
		db.execSQL(INIT_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		final String DROP_TABLE = "DROP TABLE IF EXISTS " + VIDEO_TABLE_NAME;
		db.execSQL(DROP_TABLE);
		onCreate(db);
	}

}
