package com.example.adapter;

import java.util.ArrayList;

import com.example.activity.R;
import com.example.model.SearchAuthorListModel;
import com.example.unity.GetHttpImage;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class SearchAuthorAdapter extends BaseAdapter{

	private Context context;
	private ArrayList<SearchAuthorListModel> array;
	private GetHttpImage getImage;
	public SearchAuthorAdapter(Context con,ArrayList<SearchAuthorListModel> arr){
		this.context=con;
		this.array=arr;
		this.getImage=new GetHttpImage();
	}
	public void setArray(ArrayList<SearchAuthorListModel> arr){
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
		if(convertView ==null){
			vh=new ViewHolder();
			convertView=View.inflate(context, R.layout.adapter_search, null);
			vh.iv=(ImageView) convertView.findViewById(R.id.imageview_search_adapter);
			vh.textview_title=(TextView) convertView.findViewById(R.id.textview_search_author_adapter);
			vh.textview_count=(TextView) convertView.findViewById(R.id.textview_search_num_adapter);
			convertView.setTag(vh);
		}
		vh=(ViewHolder) convertView.getTag();
		vh.textview_title.setText(array.get(position).getTitle());
		vh.textview_count.setText("²©¿ÍÊý£º"+array.get(position).getPostcount());
		getImage.getHttpBitmap(array.get(position).getAvatar(), vh.iv);
		return convertView;
	}
	class ViewHolder{
		ImageView iv;
		TextView textview_title;
		TextView textview_count;
	}
}
