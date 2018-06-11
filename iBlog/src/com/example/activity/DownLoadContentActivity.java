package com.example.activity;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.widget.TextView;

public class DownLoadContentActivity extends Activity {
	
	private TextView textView_title;
	private TextView textView_name;
	private WebView webView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_down_load_content);
		init();
		Intent intent=this.getIntent();
		textView_name.setText(intent.getStringExtra("name"));
		textView_title.setText(intent.getStringExtra("title"));
		
		webView.getSettings().setJavaScriptEnabled(true);
		webView.getSettings().setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);//NORMAL��������ʾ��û����Ⱦ�仯SINGLE_COLUMN�����������ݷŵ�WebView����ȿ��һ���С�NARROW_COLUMNS�����ܵĻ���ʹ�����еĿ�Ȳ�������Ļ��ȡ�
		webView.loadDataWithBaseURL(null, intent.getStringExtra("content"), "text/html", "utf-8", null);
		
	}

	private void init() {
		// TODO Auto-generated method stub
		textView_name=(TextView) findViewById(R.id.textview_content_download_sourcename);
		textView_title=(TextView) findViewById(R.id.textview_content_download_title);
		webView=(WebView) findViewById(R.id.webview_content_download_contents);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.down_load_content, menu);
		return true;
	}

}
 