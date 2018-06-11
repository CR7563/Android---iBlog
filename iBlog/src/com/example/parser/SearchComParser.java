package com.example.parser;

import java.util.ArrayList;

import javax.xml.namespace.QName;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import android.util.Log;

import com.example.model.SearchAuthorListModel;

public class SearchComParser extends DefaultHandler{
	
	private String name;
	private SearchAuthorListModel model;
	private ArrayList<SearchAuthorListModel> array;
	
	public ArrayList<SearchAuthorListModel> getSearchAuthorListModel(){
		return array;
	}
	
	@Override
	public void startDocument() throws SAXException {
		// TODO Auto-generated method stub
		super.startDocument();
		array=new ArrayList<SearchAuthorListModel>();
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
			model=new SearchAuthorListModel();
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
		String s=new String(ch,start,length);
		if(model!=null&&name.equals("title")){
			model.setTitle(s);
		}
		else if(name.equals("avatar")){
			model.setAvatar(s);
		}
		else if(name.equals("postcount")){
			model.setPostcount(Integer.parseInt(s));
		}
		else if(name.equals("blogapp")){
			model.setBlogapp(s);
		}
		Log.d("aa", "aaa"+s);
	}

	
}
