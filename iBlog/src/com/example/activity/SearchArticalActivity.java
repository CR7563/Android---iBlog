package com.example.activity;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

import com.example.adapter.SearchArticalAdapter;
import com.example.model.Constant;
import com.example.model.NewsModel;
import com.example.parser.SearchArticleParser;
import com.example.parser.SearchComParser;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class SearchArticalActivity extends Activity {

	private String url;
	private ArrayList<NewsModel> array;
	private TextView textview_author;
	private TextView textView_num;
	private ListView listview;
	private View footView;
	private Button button;
	private int loadNum;
	private SearchArticalAdapter adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_search_artical);
		init();
		Intent intent=this.getIntent();
		url=Constant.SEARCH_PAPER_URL+intent.getStringExtra("blogapp")+"/posts/1/";
		textview_author.setText(intent.getStringExtra("title"));
		textView_num.setText("²©¿ÍÊý£º"+intent.getIntExtra("count", 0));
		toAdapter();
		setListenner();
	}

	private void setListenner() {
		// TODO Auto-generated method stub
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(SearchArticalActivity.this, ContentBlogsActivity.class);
				Bundle bundle=new Bundle();
				bundle.putInt("id", array.get(position).getId());
				bundle.putString("title", array.get(position).getTitle());
				bundle.putString("sourcename", array.get(position).getSourceName());
				bundle.putInt("conments", array.get(position).getConments());
				intent.putExtra("blog", bundle);
				startActivity(intent);
			}
		});
		
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				loadNum=loadNum+20;
				toAdapter();
			}
		});
	}

	private void toAdapter() {
		// TODO Auto-generated method stub
		MyAsyncTask myTask=new MyAsyncTask();
		myTask.execute(url+loadNum);
	}

	private void init() {
		// TODO Auto-generated method stub
		listview=(ListView) findViewById(R.id.listview_artical_search);
		textview_author=(TextView) findViewById(R.id.textview_search_artical);
		textView_num=(TextView) findViewById(R.id.textview_search_num);
		
		footView=View.inflate(this, R.layout.foot_adapter, null);
		button=(Button) footView.findViewById(R.id.button_foot_adpter);
		listview.addFooterView(footView);
		
		loadNum=20;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search_artical, menu);
		return true;
	}
	class MyAsyncTask extends AsyncTask<String, Void, ArrayList<NewsModel>>{

		@Override
		protected ArrayList<NewsModel> doInBackground(String... params) {
			// TODO Auto-generated method stub
			SearchArticleParser myHandler = null;
			try {
				SAXParserFactory factory=SAXParserFactory.newInstance();				 
				SAXParser parser = factory.newSAXParser();
				myHandler=new SearchArticleParser();
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
			return myHandler.getArray();
		}

		@Override
		protected void onPostExecute(ArrayList<NewsModel> result) {
			// TODO Auto-generated method stub
			//super.onPostExecute(result);
			array=result;
			if(loadNum==20){
				adapter=new SearchArticalAdapter(SearchArticalActivity.this, result);
				listview.setAdapter(adapter);
			}
			else{
				adapter.setArray(array);
				adapter.notifyDataSetChanged();
			}
			
		}
		
	}

} 
