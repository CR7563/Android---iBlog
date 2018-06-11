package com.example.parser;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import android.util.Log; 

import com.example.model.NewsModel;

public class NewsParser extends DefaultHandler{
	
	private ArrayList<NewsModel> array;
	private NewsModel newsModel;
	private String name;
	
	public ArrayList<NewsModel> getArray(){
		return array;
	}
	
	@Override
	public void startDocument() throws SAXException {
		// TODO Auto-generated method stub
		super.startDocument();
		array=new ArrayList<NewsModel>();
	}

	@Override
	public void endDocument() throws SAXException {
		// TODO Auto-generated method stub
		super.endDocument();
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		// TODO Auto-generated method stub
		super.startElement(uri, localName, qName, attributes);
		
		if(qName.equals("entry")){
			newsModel=new NewsModel();
		}
		name=qName;
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		// TODO Auto-generated method stub
		super.endElement(uri, localName, qName);
		if(qName.equals("entry")){
			array.add(newsModel);
			name=null;
		}
	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		// TODO Auto-generated method stub
		super.characters(ch, start, length);
		String content=new String(ch, start, length);
		if(newsModel!=null&&name.equals("id")){
			newsModel.setId(Integer.parseInt(content));
		}
		else if(newsModel!=null&&name.equals("title")){
			newsModel.setTitle(content);
		}
		else if(name.equals("sourceName")){
			newsModel.setSourceName(content);
		}
		else if(name.equals("topicIcon")){
			newsModel.setTopicIcon(content);
		}
		else if(name.equals("summary")){
			newsModel.setSummary(content);
		}
		else if(name.equals("views")){
			newsModel.setViews(Integer.parseInt(content));
		}
		else if(name.equals("comments")){
			newsModel.setConments(Integer.parseInt(content));
		}
		else if(name.equals("name")){
			newsModel.setSourceName(content);
		}
		else if(name.equals("avatar")){
			newsModel.setTopicIcon(content);
		}
		Log.d("title", content);
	}
	
	

}
