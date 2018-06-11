package com.example.adapter;

import java.util.ArrayList;

import com.example.activity.R;
import com.example.model.CollectionModel;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CollectionAdapter extends BaseAdapter{

	private ArrayList<CollectionModel> array;
	private Context context;
	
	public CollectionAdapter(Context context,ArrayList<CollectionModel> arr){
		this.context=context;
		this.array=arr;
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
			convertView=View.inflate(context, R.layout.adapter_collection, null);
			vh.textview_title=(TextView) convertView.findViewById(R.id.textview_adapter_collection_title);
			vh.textview_sourcename=(TextView) convertView.findViewById(R.id.textview_adapter_collection_source);
			vh.textview_comments=(TextView) convertView.findViewById(R.id.textview_adapter_collection_comment);
			convertView.setTag(vh);
		
		}
		vh=(ViewHolder) convertView.getTag();
		vh.textview_title.setText(array.get(position).getContent_title());
		vh.textview_sourcename.setText(array.get(position).getContent_sourcename());
		vh.textview_comments.setText(array.get(position).getContent_comments()+"¸úÌû");
		
		return convertView; 
	}
	class ViewHolder{
		TextView textview_title;
		TextView textview_sourcename;
		TextView textview_comments;
	}
}
