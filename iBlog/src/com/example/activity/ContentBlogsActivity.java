package com.example.activity;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException; 

import com.example.database.MyDataBase;
import com.example.model.CollectionModel;
import com.example.model.Constant;
import com.example.model.DownloadModel;
import com.example.parser.ContentBlogsParser;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * �������ݽ���
 */
public class ContentBlogsActivity extends Activity implements OnClickListener{

	private TextView textViewTitle;			//����
	private TextView textViewSourceName;	//����
	private WebView  webView;				//����
	private int 	 blog_content_id;		//��������id
	private String blogTitle;				//���ͱ���
	private String blogContent;				//��������
	private CollectionModel collectionModel;
	private DownloadModel downloadModel;
	private MyDataBase myDataBase;
	
	
	private Button buttonDownload;			//�������ذ�ť
	private Button buttonCollection;		//�����ղذ�ť
	private Button buttonShare;				//���ͷ���ť
	private Button buttonComment;			//�������۰�ť
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_content_blogs);
		
		init();
		
		Intent intent=this.getIntent();
		Bundle bundle=intent.getBundleExtra("blog");
		blog_content_id=bundle.getInt("id");
		blogTitle=bundle.getString("title");
		textViewTitle.setText(blogTitle);
		textViewSourceName.setText(bundle.getString("sourcename")+"   ���ۣ�"+bundle.getInt("conments"));
		collectionModel.setContent_id(blog_content_id);
		collectionModel.setContent_type(Constant.COLLECTION_BLOG_TYPE);
		collectionModel.setContent_title(blogTitle);
		collectionModel.setContent_sourcename(bundle.getString("sourcename"));
		collectionModel.setContent_comments(bundle.getInt("conments"));
		
		downloadModel.setName(bundle.getString("sourcename"));
		downloadModel.setTitle(blogTitle);
		 
		MyAsyncTack myAsyncTack=new MyAsyncTack();
		myAsyncTack.execute(Constant.BLOGS_CONTENT_URL+blog_content_id);
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int id=v.getId();
		switch (id) {
		case R.id.button_blogs_collection:
			if(myDataBase.toCollectionSelect(blog_content_id)){
				myDataBase.toCollectionInsert(collectionModel);
				Toast.makeText(this, "�����ղسɹ�", Toast.LENGTH_SHORT).show();
			}
			else{
				Toast.makeText(this, "�Ѿ��ղع��˲��ͣ������ٴ��ղ�", Toast.LENGTH_SHORT).show();
			}
			break;
		case R.id.button_blogs_download:
			if(myDataBase.toDownLoadSelect(blogTitle)){
				myDataBase.toDownLoadInsert(downloadModel);
				Toast.makeText(this, "�������سɹ�", Toast.LENGTH_SHORT).show();
			}
			else{
				Toast.makeText(this, "�Ѿ����ع��˲��ͣ������ٴ�����", Toast.LENGTH_SHORT).show();
			}			
			break;
		case R.id.button_blogs_share:
			Intent inten=new Intent(Intent.ACTION_SEND);
			inten.setType("text/plain");
			inten.putExtra(Intent.EXTRA_TEXT, "���ͱ��⣺"+blogTitle+"   �������ݣ�"+blogContent);
			startActivity(inten);
			break;
		case R.id.button_blogs_comment:
			Intent intent=new Intent(this,CommentActivity.class);
			intent.putExtra("url", Constant.BLOGS_COMMENTS_URL+blog_content_id+"/comments/1/");
			intent.putExtra("title", blogTitle);
			startActivity(intent);
			break;

		default:
			break;
		}
	}
	
	private void init() {
		// TODO Auto-generated method stub
		textViewTitle=(TextView) findViewById(R.id.textview_content_blogs_title);
		textViewSourceName=(TextView) findViewById(R.id.textview_content_blogs_sourcename);
		webView=(WebView) findViewById(R.id.webview_content_blogs_contents);	
		
		buttonDownload=(Button) findViewById(R.id.button_blogs_download);
		buttonShare=(Button) findViewById(R.id.button_blogs_share);
		buttonCollection=(Button) findViewById(R.id.button_blogs_collection);
		buttonComment=(Button) findViewById(R.id.button_blogs_comment);
		
		buttonDownload.setOnClickListener(this);
		buttonShare.setOnClickListener(this);
		buttonCollection.setOnClickListener(this);
		buttonComment.setOnClickListener(this);
		
		collectionModel=new CollectionModel();
		downloadModel=new DownloadModel();
		myDataBase=new MyDataBase(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.content_blogs, menu);
		return true;
	}
	
	class MyAsyncTack extends AsyncTask<String, Void, String>{

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub		
			ContentBlogsParser myHandler = null;
			try {
				SAXParserFactory saxFactory=SAXParserFactory.newInstance();
				SAXParser parser = saxFactory.newSAXParser();
				myHandler=new ContentBlogsParser();
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
			
			return myHandler.getContent();
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			blogContent=result;
			downloadModel.setContent(result);
			
			webView.getSettings().setJavaScriptEnabled(true);
			webView.getSettings().setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);//NORMAL��������ʾ��û����Ⱦ�仯SINGLE_COLUMN�����������ݷŵ�WebView����ȿ��һ���С�NARROW_COLUMNS�����ܵĻ���ʹ�����еĿ�Ȳ�������Ļ��ȡ�
			webView.loadDataWithBaseURL(null, result, "text/html", "utf-8", null);
		}
		
	}

	
}
