package com.example.activity;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

import com.example.database.MyDataBase;
import com.example.model.CollectionModel;
import com.example.model.Constant;
import com.example.model.ContentNewsModel;
import com.example.model.DownloadModel;
import com.example.parser.ContentNewsParser;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class NewsContentActivity extends Activity implements OnClickListener{
	
	private TextView textView_title;		//���ݽ������
	private TextView textView_sourceName;	//���ݽ�����Դ
	private WebView  webView_content;		//�������ݽ�������
	private int id;							//���µ�id
	private String newsTitle;				//���ű���
	private CollectionModel collectionModel;//�ղ���������
	private DownloadModel downModel;		//������������
	MyDataBase myDatabase;					//���ݿ����
	
	private String newsContent;				//��������
	private Button buttonDownload;			//��������
	private Button buttonCollection;		//�����ղذ�ť
	private Button buttonShare;				//���ŷ���ť
	private Button buttonComment;			//�������۰�ť
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_news_content);
		Intent intent=this.getIntent();
		id=intent.getIntExtra("id", 183885);
		init();	
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int ids=v.getId();
		switch (ids) {
		case R.id.button_news_collection:			
			if(myDatabase.toCollectionSelect(id)){
				myDatabase.toCollectionInsert(collectionModel);
				Toast.makeText(this, "�����ղسɹ�", Toast.LENGTH_SHORT).show();
			}
			else{
				Toast.makeText(this, "�ղ��б��Ѵ��ڴ����ţ������ٴ��ղ�", Toast.LENGTH_SHORT).show();
			}
			break;
		case R.id.button_news_download:
			if(myDatabase.toDownLoadSelect(downModel.getTitle())){
				myDatabase.toDownLoadInsert(downModel);
				Toast.makeText(this, "�������سɹ�", Toast.LENGTH_SHORT).show();
			}else{
				Toast.makeText(this, "�����б��Ѵ��ڴ����ţ������ٴ�����", Toast.LENGTH_SHORT).show();
			}
				
			break;
		case R.id.button_news_share:
			Intent inten=new Intent(Intent.ACTION_SEND);
			inten.setType("text/plain");
			inten.putExtra(Intent.EXTRA_TEXT, "����Title:"+newsTitle+"   ����Content:"+newsContent);
			startActivity(inten);
			break;
		case R.id.button_news_comment:
			Intent intent=new Intent(this,CommentActivity.class);
			//intent.putExtra("url", Constant.NEWS_COMMENTS_URL+id+"/comments/1/20");
			intent.putExtra("url", Constant.NEWS_COMMENTS_URL+id+"/comments/1/");
			intent.putExtra("title", newsTitle);
			startActivity(intent);
			break;

		default:
			break;
		}
	}
	

	private void init() {
		// TODO Auto-generated method stub
		textView_title=(TextView) findViewById(R.id.textview_content_news_title);
		textView_sourceName=(TextView) findViewById(R.id.textview_content_news_sourcename);
		webView_content=(WebView) findViewById(R.id.webview_content_news_contents);
		
		buttonDownload=(Button) findViewById(R.id.button_news_download);
		buttonShare=(Button) findViewById(R.id.button_news_share);
		buttonCollection=(Button) findViewById(R.id.button_news_collection);
		buttonComment=(Button) findViewById(R.id.button_news_comment);
		
		buttonDownload.setOnClickListener(this);
		buttonShare.setOnClickListener(this);
		buttonCollection.setOnClickListener(this);
		buttonComment.setOnClickListener(this);
		
		collectionModel=new CollectionModel();
		downModel=new DownloadModel();
		myDatabase=new MyDataBase(this);
		
		MyContentAsyncTask myAsyncTack=new MyContentAsyncTask();
		myAsyncTack.execute(Constant.NEWS_CONTENT_URL+id);
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.news_content, menu);
		return true;
	}
	
	class MyContentAsyncTask extends AsyncTask<String, Void, ContentNewsModel>{

		@Override
		protected ContentNewsModel doInBackground(String... params) {
			// TODO Auto-generated method stub
			ContentNewsParser myHandler = null;
			try {
				SAXParserFactory saxFactory=SAXParserFactory.newInstance();
				SAXParser parser=saxFactory.newSAXParser();
				myHandler=new ContentNewsParser();
				parser.parse(params[0], myHandler);
	
			} catch (ParserConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return myHandler.getContentNewsModel();
		}

		@Override
		protected void onPostExecute(ContentNewsModel result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			newsContent=result.getContent();
			newsTitle=result.getTitle();
			
			
			downModel.setTitle(newsTitle);
			downModel.setName(result.getSourceName());
			downModel.setContent(newsContent);
			
			collectionModel.setContent_id(id);
			collectionModel.setContent_title(result.getTitle());
			collectionModel.setContent_sourcename(result.getSourceName());
			collectionModel.setContent_comments(result.getCommentCount());
			collectionModel.setContent_type(Constant.COLLECTION_NEWS_TYPE);
			
			
			
			textView_title.setText(result.getTitle());
			textView_sourceName.setText(result.getSourceName()+"  "+result.getSubmitDate()+" "+result.getCommentCount()+"����");
			webView_content.getSettings().setJavaScriptEnabled(true);
			webView_content.getSettings().setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);//NORMAL��������ʾ��û����Ⱦ�仯SINGLE_COLUMN�����������ݷŵ�WebView����ȿ��һ���С�NARROW_COLUMNS�����ܵĻ���ʹ�����еĿ�Ȳ�������Ļ��ȡ�
			webView_content.loadDataWithBaseURL(null, result.getContent(), "text/html", "utf-8", null);
		}
		
	} 

	
}
