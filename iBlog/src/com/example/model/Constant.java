package com.example.model; 

import android.os.Environment;

public class Constant {
	public  static final String HOT_NEWS_URL="http://wcf.open.cnblogs.com/news/hot/";
	public  static final String RECENT_NEWS_URL="http://wcf.open.cnblogs.com/news/recent/";
	public  static final String RECOMMEND_NEWS_URL="http://wcf.open.cnblogs.com/news/recommend/paged/";
	public  static final String	BLOGS_48_URL="http://wcf.open.cnblogs.com/blog/48HoursTopViewPosts/";
	public  static final String	BLOGS_10_URL="http://wcf.open.cnblogs.com/blog/TenDaysTopDiggPosts/";
	public  static final String	BLOGS_FIRST_URL="http://wcf.open.cnblogs.com/blog/sitehome/recent/";	
	public  static final String NEWS_CONTENT_URL="http://wcf.open.cnblogs.com/news/item/";
	public  static final String BLOGS_CONTENT_URL="http://wcf.open.cnblogs.com/blog/post/body/"; 
	public  static final String NEWS_COMMENTS_URL="http://wcf.open.cnblogs.com/news/item/";
	public  static final String BLOGS_COMMENTS_URL="http://wcf.open.cnblogs.com/blog/post/"; 

	public  static final String SEARCH_AUTHOR_URL="http://wcf.open.cnblogs.com/blog/bloggers/recommend/";
	public  static final String SEARCH_BUTTON_URL="http://wcf.open.cnblogs.com/blog/bloggers/search?t=";
	public  static final String	SEARCH_PAPER_URL="http://wcf.open.cnblogs.com/blog/u/";
	
	public  static final int COLLECTION_NEWS_TYPE=1;
	public  static final int COLLECTION_BLOG_TYPE=2;
	
	
	public static final String download_dir = Environment.getExternalStorageDirectory().getAbsolutePath();
	public  static final int interval_day=7*24*60*60*1000;
}
