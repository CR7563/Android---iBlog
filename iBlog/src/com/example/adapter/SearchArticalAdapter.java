package com.example.adapter;

import java.util.ArrayList;

import com.example.activity.R;
import com.example.adapter.SearchAuthorAdapter.ViewHolder;
import com.example.model.NewsModel;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class SearchArticalAdapter extends BaseAdapter{
	
	private Context context;
	private ArrayList<NewsModel> array;
	
	public SearchArticalAdapter(Context context,ArrayList<NewsModel> arra){
		this.context=context;
		this.array=arra;
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
		ViewHolder vh;
		if(convertView==null){
			vh=new ViewHolder();
			convertView=View.inflate(context, R.layout.adapter_artical_search, null);
			vh.textView_comments=(TextView) convertView.findViewById(R.id.text_adapter_article_comments);
			vh.textview_published=(TextView) convertView.findViewById(R.id.text_adapter_article_published);
			vh.textview_title=(TextView) convertView.findViewById(R.id.textview_adapter_artical);
			vh.wibview=(TextView) convertView.findViewById(R.id.webview_adapter_article);
			vh.textview_view=(TextView) convertView.findViewById(R.id.text_adapter_article_views);
			convertView.setTag(vh);		
		}
		vh=(ViewHolder) convertView.getTag();
		vh.textView_comments.setText(""+array.get(position).getConments()+"¸úÌû");
		vh.textview_published.setText(array.get(position).getSourceName());
		vh.textview_title.setText(array.get(position).getTitle());
		vh.textview_view.setText(array.get(position).getViews()+"µã»÷");
		
		
		vh.wibview.setText(array.get(position).getSummary());
		
		return convertView; 
	}
	class ViewHolder{
		TextView textview_title;
		TextView wibview;
		TextView textview_published;
		TextView textview_view;
		TextView textView_comments;
	}
}
