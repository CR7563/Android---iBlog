package com.example.adapter;

import java.util.ArrayList;

import com.example.activity.R;
import com.example.model.DownloadModel;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
 
public class DownloadAdapter extends BaseAdapter{
	private Context context;
	private ArrayList<DownloadModel> array;
	public DownloadAdapter(Context context,ArrayList<DownloadModel> array){
		this.context=context;
		this.array=array;
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
			convertView=View.inflate(context, R.layout.adapter_download, null);
			vh.textTitle=(TextView) convertView.findViewById(R.id.textview_adapter_down_title);
			vh.textName=(TextView) convertView.findViewById(R.id.textview_adapter_down_name);
			vh.textContent=(TextView) convertView.findViewById(R.id.textview_adapter_down_content);
			convertView.setTag(vh);
		}
		vh=(ViewHolder) convertView.getTag();
		
		vh.textTitle.setText(array.get(position).getTitle());
		vh.textContent.setText(array.get(position).getContent());
		vh.textName.setText(array.get(position).getName());
		
		return convertView;
	}
	class ViewHolder{
		TextView textTitle;
		TextView textContent;
		TextView textName;
	}
}
