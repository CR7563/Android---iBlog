package com.example.parser;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import android.util.Log;

import com.example.model.CommentsModel;

public class CommentsParser extends DefaultHandler{

	private String name;					//ÿ�δ��ÿ���ڵ������
	private CommentsModel commentsModel;	//���ÿ������
	private ArrayList<CommentsModel> array=new ArrayList<CommentsModel>();	//������������б�
	
	public ArrayList<CommentsModel> getComment(){
		return array;
	}
	
	@Override
	public void startDocument() throws SAXException {
		// TODO Auto-generated method stub
		super.startDocument();	
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
			commentsModel=new CommentsModel();
		}
		name=qName;
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		// TODO Auto-generated method stub
		super.endElement(uri, localName, qName);
		name=null;
		if(qName.equals("entry")){
			array.add(commentsModel);
		}
	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		// TODO Auto-generated method stub
		super.characters(ch, start, length);
		String s=new String(ch,start,length);
		if(name.equals("name")){
			commentsModel.setName(s);
		}
		else if(name.equals("published")){
			commentsModel.setPublicshed(s);
		}
		else if(name.equals("content")){
			commentsModel.setContent(s);
		}
		Log.d("title1", "aaa"+s);
	}
	
} 
