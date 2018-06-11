package com.example.activity;


import java.util.Timer;
import java.util.TimerTask;

import com.example.model.InterfaceActivity;
import com.example.view.MyHorizontalScrollView;

import android.os.Bundle;
import android.app.Activity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener{
	
	/*
	 * 菜单界面的菜单项
	 */
	private LinearLayout  linearHotNews;
	private LinearLayout  linearNowNews;
	private LinearLayout  linearRecommentNews;
	private LinearLayout  linear48Blog;
	private LinearLayout  linear10Blog;
	private LinearLayout  linearFirstBlog;
	private LinearLayout  linearCollection;
	private LinearLayout  linearDownLoad;
	private LinearLayout  linearSearch;
	
	/*
	 * 内容界面的左滑图片按钮
	 */
	private ImageView imageNews;
	private ImageView imageBlogs;
	private ImageView imageCollection;
	private ImageView imageDownload;
	private ImageView imageSearch;
	
	private ImageView picture;
	
	private LinearLayout  linearLeft;				//左面的菜单
	private LinearLayout  linearScrollView;			//HorizontalScrollView下的唯一布局
	private TextView 	   textViewLeft;					//HorizontalScrollView左面透明的界面
	
	private InterfaceActivity interfaceActivity;   //界面接口 
	private MyHorizontalScrollView myScrollView;   //上层可以滑动的HorizontalScrollView
	
	public static boolean flagMove;				//控制HorizontalScrollView的滑动
	private boolean firstSetView;					//第一次启动时设置界面
	
	private boolean back;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		
		init();
		
		AlphaAnimation aa=new AlphaAnimation(1, 0);
		aa.setDuration(5000);
		picture.startAnimation(aa);
		picture.setVisibility(View.INVISIBLE);

		
		
	}
	
	
	/*
	 * 设置新闻界面
	 */
	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		// TODO Auto-generated method stub
		super.onWindowFocusChanged(hasFocus);
		
		if(firstSetView){
			setView(interfaceActivity.getView());
			moveFlag();
			firstSetView=false;
		}
		
	}


	/*
	 * 初始化函数
	 */
	private void init(){
		linearHotNews=(LinearLayout) findViewById(R.id.linear_left_scroll_hotnews);
		linearNowNews=(LinearLayout) findViewById(R.id.linear_left_scroll_nownews);
		linearRecommentNews=(LinearLayout) findViewById(R.id.linear_left_scroll_recommentnews);
		linear48Blog=(LinearLayout) findViewById(R.id.linear_left_scroll_48blog);
		linear10Blog=(LinearLayout) findViewById(R.id.linear_left_scroll_10blog);
		linearFirstBlog=(LinearLayout) findViewById(R.id.linear_left_scroll_firstblog);
		linearCollection=(LinearLayout) findViewById(R.id.linear_left_scroll_collection);
		linearDownLoad=(LinearLayout) findViewById(R.id.linear_left_scroll_download);
		linearSearch=(LinearLayout) findViewById(R.id.linear_left_scroll_search);
		
		imageSearch=(ImageView) findViewById(R.id.image_search_title);
		
		linearLeft=(LinearLayout) findViewById(R.id.linear_left);
		linearScrollView=(LinearLayout) findViewById(R.id.linear_horizontal);
		textViewLeft=new TextView(this);
		myScrollView=(MyHorizontalScrollView) findViewById(R.id.my_horizontal);
		
		picture=(ImageView) findViewById(R.id.image_picture);
		
		linearHotNews.setOnClickListener(this);
		linearNowNews.setOnClickListener(this);
		linearRecommentNews.setOnClickListener(this);
		linear48Blog.setOnClickListener(this);
		linear10Blog.setOnClickListener(this);
		linearFirstBlog.setOnClickListener(this);
		linearCollection.setOnClickListener(this);
		linearDownLoad.setOnClickListener(this);
		linearSearch.setOnClickListener(this);
		
		
		interfaceActivity=new ActivityNews(this,1);
		flagMove=false;
		firstSetView=true;
		back=false;
		
	}
	/*
	 * 控制界面的滑动
	 */
	public void moveFlag(){
		if(flagMove){
			myScrollView.smoothScrollBy(linearLeft.getMeasuredWidth(), 0);
		}else{
			myScrollView.smoothScrollBy(-linearLeft.getMeasuredWidth(), 0);
		}
		flagMove=!flagMove;
	}
	/*
	 * 设置右面的界面改变
	 */
	public void setView(View v){
		linearScrollView.removeAllViews();
		int leftWidth=linearLeft.getMeasuredWidth();
		int height=myScrollView.getMeasuredHeight();
		int width=myScrollView.getMeasuredWidth();
		linearScrollView.addView(textViewLeft, leftWidth, height);
		linearScrollView.addView(v,width,height);
		myScrollView.setLeftWidth(leftWidth);
	}
	
	
	
	
	/*
	 * 菜单的点击事件，进入相应的界面
	 */
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int id=v.getId();
		switch (id) {
		case R.id.linear_left_scroll_hotnews:
			interfaceActivity=new ActivityNews(this,1);
			setView(interfaceActivity.getView());
			moveFlag();
			imageNews=(ImageView)interfaceActivity.getView().findViewById(R.id.image_news);
			imageNews.setOnClickListener(this);
			break;
		case R.id.linear_left_scroll_nownews:
			interfaceActivity=new ActivityNews(this,2);
			setView(interfaceActivity.getView());
			moveFlag();
			imageNews=(ImageView)interfaceActivity.getView().findViewById(R.id.image_news);
			imageNews.setOnClickListener(this);
			break;
		case R.id.linear_left_scroll_recommentnews:
			interfaceActivity=new ActivityNews(this,3);
			setView(interfaceActivity.getView());
			moveFlag();
			imageNews=(ImageView)interfaceActivity.getView().findViewById(R.id.image_news);
			imageNews.setOnClickListener(this);
			break;					
		case R.id.linear_left_scroll_48blog:
			interfaceActivity=new ActivityBlogs(this,4);
			setView(interfaceActivity.getView());
			moveFlag();
			imageBlogs=(ImageView) interfaceActivity.getView().findViewById(R.id.image_blogs);
			imageBlogs.setOnClickListener(this);
			break;	
		case R.id.linear_left_scroll_10blog:
			interfaceActivity=new ActivityBlogs(this,5);
			setView(interfaceActivity.getView());
			moveFlag();
			imageBlogs=(ImageView) interfaceActivity.getView().findViewById(R.id.image_blogs);
			imageBlogs.setOnClickListener(this);
			break;
		case R.id.linear_left_scroll_firstblog:
			interfaceActivity=new ActivityBlogs(this,6);
			setView(interfaceActivity.getView());
			moveFlag();
			imageBlogs=(ImageView) interfaceActivity.getView().findViewById(R.id.image_blogs);
			imageBlogs.setOnClickListener(this);
			break;
		case R.id.linear_left_scroll_collection:
			interfaceActivity=new ActivityCollection(this);
			setView(interfaceActivity.getView());
			moveFlag();
			imageCollection=(ImageView) interfaceActivity.getView().findViewById(R.id.image_collection_title);
			imageCollection.setOnClickListener(this);
			break;			
		case R.id.linear_left_scroll_download:
			interfaceActivity=new ActivityDownLoad(this);
			setView(interfaceActivity.getView());
			moveFlag();
			imageDownload=(ImageView)interfaceActivity.getView().findViewById(R.id.image_download_title);
			imageDownload.setOnClickListener(this);
			break;			
		case R.id.linear_left_scroll_search:
			interfaceActivity=new ActivitySearch(this);
			setView(interfaceActivity.getView());
			moveFlag();
			imageSearch=(ImageView)interfaceActivity.getView(). findViewById(R.id.image_search_title);
			imageSearch.setOnClickListener(this);
			break;			
		case R.id.image_news:
			moveFlag();
			break;
		case R.id.image_blogs:
			moveFlag();
			break;
		case R.id.image_collection_title:
			moveFlag();
			break;
		case R.id.image_download_title:
			moveFlag();
			break;
		case R.id.image_search_title:
			moveFlag();
			break;
		default:
			break;
		}
	}
	
	
	
	

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if(keyCode==KeyEvent.KEYCODE_BACK){
			if(back){
				finish();				
			}else{
				back=true;
				Toast.makeText(this, "再次点击，退出程序", Toast.LENGTH_SHORT).show();
				new Timer().schedule(new TimerTask() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						back=false;
					}
				}, 3000);
			}
		}	
		return false;
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
