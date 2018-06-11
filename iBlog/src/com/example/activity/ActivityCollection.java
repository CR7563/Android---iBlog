package com.example.activity;

import java.util.ArrayList; 

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;

import com.example.adapter.CollectionAdapter;
import com.example.database.MyDataBase;
import com.example.logic.LogicParser;
import com.example.model.CollectionModel;
import com.example.model.Constant;
import com.example.model.InterfaceActivity;
import com.example.unity.Internets;

public class ActivityCollection implements InterfaceActivity{
	
	private View v;
	private ListView listView;
	private Context context;
	private ImageView imageview_delete;
	private MyDataBase myDatabase;
	private ArrayList<CollectionModel> array;
	
	
	public ActivityCollection(Context context){
		v=View.inflate(context, R.layout.activity_list_collection, null);
		this.context=context;
		init();
		myDatabase=new MyDataBase(context);
		array=myDatabase.getCollectionList();		
		CollectionAdapter adapter=new CollectionAdapter(context, array);
		listView.setAdapter(adapter);
		setListenner();
	}
	private void setListenner() {
		// TODO Auto-generated method stub
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				// TODO Auto-generated method stub
				Internets internet=new Internets();
				if(internet.isConnection(context)){
					int type=array.get(position).getContent_type();
					if(type==Constant.COLLECTION_BLOG_TYPE){
						Intent intent=new Intent(context, ContentBlogsActivity.class);
						Bundle bundle=new Bundle();
						bundle.putInt("id", array.get(position).getContent_id());
						bundle.putString("title", array.get(position).getContent_title());
						bundle.putString("sourcename", array.get(position).getContent_sourcename());
						bundle.putInt("conments", array.get(position).getContent_comments());
						intent.putExtra("blog", bundle);
						context.startActivity(intent);
					}
					else if(type==Constant.COLLECTION_NEWS_TYPE){
						Intent intent=new Intent(context,NewsContentActivity.class);
						intent.putExtra("id", array.get(position).getContent_id());
						context.startActivity(intent);
					}
				}
				else{
					Toast.makeText(context, "没有联网无法获得数据", Toast.LENGTH_SHORT).show();
				}
							
			}
		});
		
		listView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					final int position, long id) {
				// TODO Auto-generated method stub
				new AlertDialog.Builder(context)
				.setTitle("删除收藏")
				.setMessage("是否删除收藏")
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						
					}
				})
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						myDatabase.toCollectionDelete(array.get(position).getId());
						array=myDatabase.getCollectionList();
						CollectionAdapter adapter=new CollectionAdapter(context, array);
						listView.setAdapter(adapter);
					}
				})
				.create().show();
			
				return true;
			}
		});
		
		imageview_delete.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new AlertDialog.Builder(context)
				.setTitle("删除所有收藏")
				.setMessage("是否删除所有收藏")
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						
					}
				})
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						myDatabase.toDeleteAllCollection();
						array=myDatabase.getCollectionList();
						CollectionAdapter adapter=new CollectionAdapter(context, array);
						listView.setAdapter(adapter);
					}
				})
				.create().show();
			}
		});
		
	}
	private void init() {
		// TODO Auto-generated method stub
		listView=(ListView) v.findViewById(R.id.listview_collection);
		imageview_delete=(ImageView) v.findViewById(R.id.image_collection_make);
	}
	@Override
	public View getView() {
		// TODO Auto-generated method stub
		return v;
	}

}
