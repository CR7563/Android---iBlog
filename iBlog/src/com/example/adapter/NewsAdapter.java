package com.example.adapter;

import java.util.ArrayList;

import com.example.activity.R;
import com.example.model.NewsModel;
import com.example.unity.GetHttpImage;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class NewsAdapter extends BaseAdapter{
	
	private ArrayList<NewsModel> array;
	private Context context;
	private GetHttpImage getImage;
	
	public NewsAdapter(ArrayList<NewsModel> arra,Context context){
		this.array=arra;
		this.context=context;		
		getImage=new GetHttpImage();
	}
	public void setArray(ArrayList<NewsModel> arra){
		this.array=arra;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return array.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return array.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHoler vh;
		if(convertView==null){
			vh=new ViewHoler();
			convertView=View.inflate(context, R.layout.adapter_news, null);
			vh.iv=(ImageView) convertView.findViewById(R.id.image_adapter_news);
			vh.tv1=(TextView) convertView.findViewById(R.id.textView_adapter_news_title);
			vh.tv2=(TextView) convertView.findViewById(R.id.textView_adapter_news_summary);
			vh.tv3=(TextView) convertView.findViewById(R.id.textView_adapter_news_views);  
			vh.tv4=(TextView) convertView.findViewById(R.id.textView_adapter_news_comments);
			vh.tv5=(TextView) convertView.findViewById(R.id.textView_adapter_news_sourcename);
			convertView.setTag(vh);
		}
		vh=(ViewHoler) convertView.getTag();
		vh.tv1.setText(array.get(position).getTitle());
		vh.tv2.setText(array.get(position).getSummary());
		vh.tv3.setText(array.get(position).getViews()+"µã»÷");
		vh.tv4.setText(array.get(position).getConments()+"¸úÌû");
		vh.tv5.setText(array.get(position).getSourceName());
		 
		getImage.getHttpBitmap(array.get(position).getTopicIcon(), vh.iv);
		
		return convertView;
	}
	
	class ViewHoler{
		ImageView iv;
		TextView tv1,tv2,tv3,tv4,tv5;
	}
}
