package com.example.activity;

import java.util.ArrayList; 

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adapter.NewsAdapter;
import com.example.logic.LogicParser;
import com.example.model.Constant;
import com.example.model.InterfaceActivity;
import com.example.model.NewsModel;
import com.example.unity.Internets;

public class ActivityNews implements InterfaceActivity{
	
	private View v;
	private View footViw;
	private Button button;
	private Context context;
	private int id;			        //�ж����Ǹ�����
	private TextView  textTitle;			//����
	private ListView  listView;			//�����б�
	private String url;				//�������ݵ���ַ
	private LogicParser parser;
	private int clickNum;			//���ص�����
	private NewsAdapter adapter;
	ArrayList<NewsModel> array;		//���������
	
	
	public ActivityNews(Context context,int i){
		this.id=i;
		this.context=context;
		init();
		
		clickNum=20;						
		setTitle();
		array=new ArrayList<NewsModel>();
		adapter=new NewsAdapter(array, context);
		listView.setAdapter(adapter);
		toAdapter();
		
		setListViewClickListenner();	
	}
	
	private void init() {
		// TODO Auto-generated method stub
		v=View.inflate(context, R.layout.activity_list_news, null);
		textTitle=(TextView) v.findViewById(R.id.textview_news_title);
		listView=(ListView) v.findViewById(R.id.listview_news);
		footViw=View.inflate(context, R.layout.foot_adapter, null);
		button=(Button) footViw.findViewById(R.id.button_foot_adpter);	
		listView.addFooterView(footViw);
	}

	private void setListViewClickListenner() {
		// TODO Auto-generated method stub
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				array=parser.getListView();
				int newsId=array.get(position).getId();
				Intent intent=new Intent(context,NewsContentActivity.class);
				intent.putExtra("id", newsId);
				context.startActivity(intent);		
			}
		});
		
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				clickNum=clickNum+20;
				switch (id) {
				case 1:
					url=Constant.HOT_NEWS_URL+clickNum;
					break;
				case 2:
					url=Constant.RECENT_NEWS_URL+clickNum;
					break;
				case 3:
					url=Constant.RECOMMEND_NEWS_URL+"1/"+clickNum;
					break;
				default:
					break;
				}
				toAdapter();
			}
		});
	}
	/*
	 * ���ñ���
	 */
	private void setTitle() {
		// TODO Auto-generated method stub
		switch (id) {
		case 1:
			textTitle.setText("��������");
			url=Constant.HOT_NEWS_URL+clickNum;
			break;
		case 2:
			textTitle.setText("��������");
			url=Constant.RECENT_NEWS_URL+clickNum;
			break;
		case 3:
			textTitle.setText("�Ƽ�����");
			url=Constant.RECOMMEND_NEWS_URL+"1/"+clickNum;
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
			Toast.makeText(context, "û�������޷��������", Toast.LENGTH_SHORT).show();
		}
	}
	

	
	@Override
	public View getView() {
		// TODO Auto-generated method stub
		return v;
	}
	

	
	
}
