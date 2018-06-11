package com.example.parser;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import android.util.Log;
 
import com.example.model.NewsModel;

public class SearchArticleParser extends DefaultHandler{
	private String name;
	private NewsModel model;
	private ArrayList<NewsModel> array;
	
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
			model=new NewsModel();
		}
		name=qName;
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		// TODO Auto-generated method stub
		super.endElement(uri, localName, qName);
		if(qName.equals("entry")){
			array.add(model);
		}
	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		// TODO Auto-generated method stub
		super.characters(ch, start, length);
		String s=new String(ch, start,length);
		if(model!=null&&name.equals("id")){
			model.setId(Integer.parseInt(s));
		}
		else if(model!=null&&name.equals("title")){
			model.setTitle(s);
		}
		else if(name.equals("summary")){
			model.setSummary(s);
		}
		else if(name.equals("published")){
			model.setSourceName(s);
		}
		else if(name.equals("views")){
			model.setViews(Integer.parseInt(s));
		}
		else if(name.equals("comments")){
			model.setConments(Integer.parseInt(s));
		}
		Log.d("ll", s);
	}

	
}
