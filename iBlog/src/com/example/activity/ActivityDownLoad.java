package com.example.activity;

import java.util.ArrayList;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

import android.webkit.WebView.FindListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.adapter.DownloadAdapter;
import com.example.database.MyDataBase;
import com.example.model.DownloadModel;
import com.example.model.InterfaceActivity;

public class ActivityDownLoad implements InterfaceActivity{
	
	private View v;
	private Context context;
	private ListView listView;
	private ImageView imageview_delete;
	private MyDataBase myDataBase;
	private ArrayList<DownloadModel> array;
 
	
	public ActivityDownLoad(Context context){
		this.context=context;
		v=View.inflate(context, R.layout.activity_list_download, null);
		listView=(ListView) v.findViewById(R.id.listview_download);
		imageview_delete=(ImageView) v.findViewById(R.id.image_download_make);
		myDataBase=new MyDataBase(context);
		array=myDataBase.getDownLoadList();
		DownloadAdapter adapter=new DownloadAdapter(context, array);
		listView.setAdapter(adapter);
		
		
		setListViewListenner();
	}
	
	
	private void setListViewListenner() {
		// TODO Auto-generated method stub
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(context, DownLoadContentActivity.class);
				intent.putExtra("title", array.get(position).getTitle());
				intent.putExtra("name", array.get(position).getName());
				intent.putExtra("content", array.get(position).getContent());
				context.startActivity(intent);
			}
		});
		
		listView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					final int position, long id) {
				// TODO Auto-generated method stub
				new AlertDialog.Builder(context)
				.setTitle("删除")
				.setMessage("是否删除此项")
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
						myDataBase.toDownLoadDelete(array.get(position).getId());
						array=myDataBase.getDownLoadList();
						DownloadAdapter adapter=new DownloadAdapter(context, array);
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
				.setTitle("删除")
				.setMessage("是否删除所有下载内容")
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
						myDataBase.toDeleteAllDownLoad();
						array=myDataBase.getDownLoadList();
						DownloadAdapter adapter=new DownloadAdapter(context, array);
						listView.setAdapter(adapter);
					}
				})
				.create().show(); 
			}
		});
		
	}


	@Override
	public View getView() {
		// TODO Auto-generated method stub
		return v;
	}

}
