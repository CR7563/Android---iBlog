package com.example.activity;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;
 
import com.example.adapter.CommentAdapter;
import com.example.model.CommentsModel;
import com.example.parser.CommentsParser;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class CommentActivity extends Activity {

	private TextView textView_title;
	private ListView listView_comment;
	private View footView;
	private Button button;
	
	private String url;
	private String title;
	private ArrayList<CommentsModel> array;
	private int loadNum;	//加载的评论条数；
	private CommentAdapter commentAdapter;
	
	Handler handler=new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			if(msg.what==20){
				array=(ArrayList<CommentsModel>) msg.obj;
				commentAdapter=new CommentAdapter(CommentActivity.this, array);
				listView_comment.setAdapter(commentAdapter);
			}	
			else{
				array=(ArrayList<CommentsModel>) msg.obj;
				commentAdapter.setArray(array);
				commentAdapter.notifyDataSetChanged();
			}
		}			
	};
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_comment);
		init();
		Intent intent=this.getIntent();
		url=intent.getStringExtra("url");
		title=intent.getStringExtra("title");
		textView_title.setText(title);
		getComment();
		setClickListenner();
	}

	private void setClickListenner() {
		// TODO Auto-generated method stub
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				loadNum=loadNum+20;
				getComment();
			}
		});
	}

	private void getComment() {
		// TODO Auto-generated method stub
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				CommentsParser myHandler = null;
				try {
					SAXParserFactory saxFactory=SAXParserFactory.newInstance();					 
					SAXParser parser = saxFactory.newSAXParser();
					myHandler=new CommentsParser();
					parser.parse(new URL(url+loadNum).openStream(), myHandler);
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
				Message message=handler.obtainMessage();
				message.what=loadNum;
				message.obj=myHandler.getComment();
				handler.sendMessage(message);
			}
		}).start();
	}

	private void init() {
		// TODO Auto-generated method stub
		textView_title=(TextView) findViewById(R.id.textview_comment_fianl_title);
		listView_comment=(ListView) findViewById(R.id.listview_final_comment);
		footView=View.inflate(this, R.layout.foot_adapter, null);
		button=(Button) footView.findViewById(R.id.button_foot_adpter);
		listView_comment.addFooterView(footView);
		loadNum=20;
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.comment, menu);
		return true;
	}
	

}
