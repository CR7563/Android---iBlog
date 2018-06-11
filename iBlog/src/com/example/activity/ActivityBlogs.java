package com.example.activity;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adapter.NewsAdapter;
import com.example.logic.LogicParser;
import com.example.model.Constant;
import com.example.model.InterfaceActivity;
import com.example.model.NewsModel;
import com.example.unity.Internets;

public class ActivityBlogs implements InterfaceActivity{
	
	private View v;
	private View footView;
	private Button button;
	private int id;
	private int clickNum;
	private Context context;
	private TextView textTitle;			//标题
	private ListView  listView;			//新闻列表
	private String url;				//申请数据的网址
	private LogicParser parser;
	private NewsAdapter adapter;
	public ActivityBlogs(Context context,int i){
		v=View.inflate(context, R.layout.activity_list_blogs, null);
		footView=View.inflate(context, R.layout.foot_adapter, null);
		button=(Button) footView.findViewById(R.id.button_foot_adpter);
		this.context=context;
		this.id=i;
		clickNum=20;
		textTitle=(TextView) v.findViewById(R.id.textview_blogs_title);
		listView=(ListView) v.findViewById(R.id.listview_blogs);
		listView.addFooterView(footView);
		setTitle();
		ArrayList<NewsModel> array=new ArrayList<NewsModel>();
		adapter=new NewsAdapter(array, context);
		listView.setAdapter(adapter);
		toAdapter();
		setOnClickListenner();
	}
	
	private void setOnClickListenner() {
		// TODO Auto-generated method stub
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				NewsModel blogsModel=parser.getListView().get(position);
				Intent intent=new Intent(context, ContentBlogsActivity.class);
				Bundle bundle=new Bundle();
				bundle.putInt("id", blogsModel.getId());
				bundle.putString("title", blogsModel.getTitle());
				bundle.putString("sourcename", blogsModel.getSourceName());
				bundle.putInt("conments", blogsModel.getConments());
				intent.putExtra("blog", bundle);
				context.startActivity(intent);
			}
			
		});
		
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				clickNum=clickNum+20;
					switch (id) {
					case 4:
						url=Constant.BLOGS_48_URL+clickNum;
						break;
					case 5:
						url=Constant.BLOGS_10_URL+clickNum;
						break;
					case 6:
						url=Constant.BLOGS_FIRST_URL+clickNum;
						break;
					default:
						break;
					}
					toAdapter();
			}
		});
		
	}

	private void setTitle() {
		// TODO Auto-generated method stub
		switch (id) {
		case 4:
			textTitle.setText("48小时排行");
			url=Constant.BLOGS_48_URL+clickNum;
			break;
		case 5:
			textTitle.setText("10天推荐排行");
			url=Constant.BLOGS_10_URL+clickNum;
			break;
		case 6:
			textTitle.setText("首页博客");
			url=Constant.BLOGS_FIRST_URL+clickNum;
			break;
		default:
			break;
		} 
	}
	
	public void toAdapter(){
		Internets internet=new Internets();
		if(internet.isConnection(context)){
			parser=new LogicParser(context,adapter);
			parser.execute(url);
		}
		else{
			Toast.makeText(context, "没有联网无法获得数据", Toast.LENGTH_SHORT).show();
		}
	
	}
	
	@Override
	public View getView() {
		// TODO Auto-generated method stub
		return v;
	}

}
