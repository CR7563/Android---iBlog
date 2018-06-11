package com.example.adapter;

import java.util.ArrayList;

import com.example.activity.R;
import com.example.model.CommentsModel;

 
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CommentAdapter extends BaseAdapter{
	
	private Context context;
	private ArrayList<CommentsModel> array;
	public CommentAdapter(Context context, ArrayList<CommentsModel> arr){
		this.context=context;
		this.array=arr;
	}
	public void setArray(ArrayList<CommentsModel> arr){
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
			convertView=View.inflate(context, R.layout.adapter_comment, null);
			vh.name=(TextView) convertView.findViewById(R.id.textView1);
			vh.date=(TextView) convertView.findViewById(R.id.textView2);
			vh.comment=(TextView) convertView.findViewById(R.id.textView3);
			convertView.setTag(vh);
		}
		else{
			vh=(ViewHolder) convertView.getTag();
		}
		
		vh.name.setText(array.get(position).getName());
		vh.date.setText(array.get(position).getPublicshed());
		vh.comment.setText(array.get(position).getContent());
		
		return convertView;
	}
	class ViewHolder{
		TextView name;
		TextView date;
		TextView comment;
	}
}
