package com.example.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class MySqliteOpenHelper extends SQLiteOpenHelper{

	public MySqliteOpenHelper(Context context) {
		super(context, "iBlo", null, 1);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL("create table download(" +
				"ids integer  primary key autoincrement," +
				"name varchar(50)," +
				"content text," +
				"title varchar(100)" +
				")");
		
		db.execSQL("create table collection(" +
				"ids integer primary key autoincrement," +
				"content_id integer," +
				"content_type integer," +
				"content_title varchar(100)," +
				"content_sourcename varchar(50)," +
				"content_comments integer" +
				")");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}
 
}
