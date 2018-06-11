package com.example.activity;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException; 

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.example.adapter.SearchAuthorAdapter;
import com.example.logic.LogicParser;
import com.example.model.Constant;
import com.example.model.InterfaceActivity;
import com.example.model.SearchAuthorListModel;
import com.example.parser.SearchComParser;
import com.example.unity.Internets;

public class ActivitySearch implements InterfaceActivity{
	
	private View v;
	private Context context;
	private EditText editText;
	private Button button_search;
	private GridView gridView;
	private Button button_load;
	private int load_num;
	private SearchAuthorAdapter adapter;
	private ArrayList<SearchAuthorListModel> array;
	
	public ActivitySearch(Context context){
		v=View.inflate(context, R.layout.activity_list_search, null);
		this.context=context;
		init();
		toAdapter();
		setListenner();
	}
	private void setListenner() {
		// TODO Auto-generated method stub
		button_search.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Internets internet=new Internets();
				if(internet.isConnection(context)){
					MyAsyncTask myTask=new MyAsyncTask();
					myTask.execute(Constant.SEARCH_BUTTON_URL+editText.getText());
				}
				else{
					Toast.makeText(context, "没有联网无法获得数据", Toast.LENGTH_SHORT).show();
				}
				
			}
		});
		gridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(context,SearchArticalActivity.class);
				intent.putExtra("blogapp", array.get(position).getBlogapp());
				intent.putExtra("title", array.get(position).getTitle());
				intent.putExtra("count", array.get(position).getPostcount());
				context.startActivity(intent);
			}
		});
		
		button_load.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				load_num=load_num+20;
				toAdapter();
			}
		});
	}
	private void toAdapter() {
		// TODO Auto-generated method stub
		
		Internets internet=new Internets();
		if(internet.isConnection(context)){
			MyAsyncTask myTask=new MyAsyncTask();
			myTask.execute(Constant.SEARCH_AUTHOR_URL+"/1/"+load_num);
		}
		else{
			Toast.makeText(context, "没有联网无法获得数据", Toast.LENGTH_SHORT).show();
		}
		
		
	}
	private void init() {
		// TODO Auto-generated method stub
		editText=(EditText) v.findViewById(R.id.edittext_search);
		button_search=(Button) v.findViewById(R.id.button_search_te);
		gridView=(GridView) v.findViewById(R.id.gridview_search);
		
		button_load=(Button) v.findViewById(R.id.button_searchs_adpter);

		load_num=20;
	}
	@Override
	public View getView() {
		// TODO Auto-generated method stub
		return v;
	}
	class MyAsyncTask extends AsyncTask<String, Void, ArrayList<SearchAuthorListModel>>{

		@Override
		protected ArrayList<SearchAuthorListModel> doInBackground(
				String... params) {
			// TODO Auto-generated method stub
			SearchComParser myHandler = null;
			try {
				SAXParserFactory factory=SAXParserFactory.newInstance();				 
				SAXParser parser = factory.newSAXParser();
				myHandler=new SearchComParser();
				parser.parse(new URL(params[0]).openStream(), myHandler);
			} catch (ParserConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			return myHandler.getSearchAuthorListModel();
		}

		@Override
		protected void onPostExecute(ArrayList<SearchAuthorListModel> result) {
			// TODO Auto-generated method stub
			//super.onPostExecute(result);
			
			array=result;
			if(load_num==20){
				adapter=new SearchAuthorAdapter(context, result);
				gridView.setAdapter(adapter);
			}
			else{
				adapter.setArray(array);
				adapter.notifyDataSetChanged();	
			}
			
			
		}
		
		
	}

}
