package com.example.database;

import java.util.ArrayList;

import com.example.model.CollectionModel;
import com.example.model.DownloadModel;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * 收藏和下载的数据库操作
 */
public class MyDataBase {
	private Context context;
	private MySqliteOpenHelper mySql;
	private SQLiteDatabase myDataBase;
	
	public MyDataBase(Context context){
		this.context=context;
		mySql=new MySqliteOpenHelper(context);
	}
	public ArrayList<CollectionModel> getCollectionList(){
		ArrayList<CollectionModel> array=new ArrayList<CollectionModel>();	
		myDataBase=mySql.getWritableDatabase();
		Cursor cursor=myDataBase.rawQuery("select * from collection", null);
		cursor.moveToFirst();
		while(!cursor.isAfterLast()){
			CollectionModel model=new CollectionModel();
			model.setId(cursor.getInt(cursor.getColumnIndex("ids")));
			model.setContent_id(cursor.getInt(cursor.getColumnIndex("content_id")));
			model.setContent_type(cursor.getInt(cursor.getColumnIndex("content_type")));
			model.setContent_title(cursor.getString(cursor.getColumnIndex("content_title")));
			model.setContent_sourcename(cursor.getString(cursor.getColumnIndex("content_sourcename")));
			model.setContent_comments(cursor.getInt(cursor.getColumnIndex("content_comments")));
			array.add(model);
			cursor.moveToNext();
		}
		return array;
	}
	public ArrayList<DownloadModel> getDownLoadList(){
		ArrayList<DownloadModel> array=new ArrayList<DownloadModel>();		
		myDataBase=mySql.getWritableDatabase();
		
		Cursor cursor=myDataBase.rawQuery("select * from download", null);
		cursor.moveToFirst();
		while(!cursor.isAfterLast()){
			DownloadModel model=new DownloadModel();
			model.setId(cursor.getInt(cursor.getColumnIndex("ids")));
			Log.d("ss", "s:"+model.getId());
			model.setContent(cursor.getString(cursor.getColumnIndex("content")));
			model.setName(cursor.getString(cursor.getColumnIndex("name")));
			model.setTitle(cursor.getString(cursor.getColumnIndex("title")));
			array.add(model);
			cursor.moveToNext();
		}
		myDataBase.close();
		return array;
	}
	
	public void toCollectionInsert(CollectionModel model){
		myDataBase=mySql.getWritableDatabase();
		myDataBase.execSQL("insert into collection(content_id,content_type,content_title,content_sourcename,content_comments) values" +
				"("+model.getContent_id()+
				","+model.getContent_type()+",'"+model.getContent_title()+
				"','"+model.getContent_sourcename()+"',"+model.getContent_comments()+")");
		myDataBase.close();
	}
	
	public void toDownLoadInsert(DownloadModel model){
		myDataBase=mySql.getWritableDatabase();
		myDataBase.execSQL("insert into download(name,content,title) values('"+model.getName()+"','"+model.getContent()+"','"+model.getTitle()+"')");
		myDataBase.close();
	}
	
	public void toCollectionDelete(int id){
		myDataBase=mySql.getWritableDatabase();
		myDataBase.execSQL("delete from collection where ids= "+id);
		myDataBase.close();
	}
	
	public void toDownLoadDelete(int id){
		myDataBase=mySql.getWritableDatabase();
		myDataBase.execSQL("delete from download where ids= "+id);
		myDataBase.close();
	}
	
	public boolean toCollectionSelect(int id){
		myDataBase=mySql.getWritableDatabase();
		Cursor cursor=myDataBase.rawQuery("select * from collection where content_id="+id, null);
		boolean se=cursor.moveToFirst();
		myDataBase.close();
		return !se;
	}
	
	public boolean toDownLoadSelect(String title){
		myDataBase=mySql.getWritableDatabase();
		Cursor cursor=myDataBase.rawQuery("select * from download where title='"+title+"'", null);
		boolean se=cursor.moveToFirst();
		myDataBase.close();
		return !se;
	}
	
	public void toDeleteAllCollection(){
		myDataBase=mySql.getWritableDatabase();
		myDataBase.execSQL("delete from collection ");
		myDataBase.close();
	}
	
	public void toDeleteAllDownLoad(){
		myDataBase=mySql.getWritableDatabase();
		myDataBase.execSQL("delete from download ");
		myDataBase.close();
	}
	
	
	
}
