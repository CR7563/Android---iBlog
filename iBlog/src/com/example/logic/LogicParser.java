package com.example.logic;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.ListView;

import com.example.adapter.NewsAdapter;
import com.example.model.NewsModel;
import com.example.parser.NewsParser;

public class LogicParser extends AsyncTask<String, Void, ArrayList<NewsModel>>{
	
		private Context context;
		private ArrayList<NewsModel> array;
		private NewsAdapter newsAdapter;
		
		public LogicParser(Context context,NewsAdapter adapter){
			this.context=context;
			this.newsAdapter=adapter;
		}
		public ArrayList<NewsModel> getListView(){
			return array;
		}
		
		@Override
		protected ArrayList<NewsModel> doInBackground(String... params) {
			// TODO Auto-generated method stub
			NewsParser newsHandler = null;
			try {
				SAXParserFactory saxFactory=SAXParserFactory.newInstance();
				SAXParser sax=saxFactory.newSAXParser();
				newsHandler=new NewsParser();
				Log.d("title", "开始解析"+params[0]);
				sax.parse(new URL(params[0]).openStream(), newsHandler);
				Log.d("title", "完成解析");
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
			return newsHandler.getArray();
		} 

		@Override
		protected void onPostExecute(ArrayList<NewsModel> result) {
			// TODO Auto-generated method stub
			//super.onPostExecute(result);
				array=result;			
				newsAdapter.setArray(result);
				newsAdapter.notifyDataSetChanged();
		}
		
		
	}
