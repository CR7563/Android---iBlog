package com.example.parser;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import android.util.Log; 

import com.example.model.ContentNewsModel;

public class ContentNewsParser extends DefaultHandler{
	
	private String name;
	private ContentNewsModel content;
	private StringBuffer sb=new StringBuffer();
	
	public ContentNewsModel getContentNewsModel(){
		return content;
	}
	
	
	@Override
	public void startDocument() throws SAXException {
		// TODO Auto-generated method stub
		super.startDocument();
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		// TODO Auto-generated method stub
		super.startElement(uri, localName, qName, attributes);
		if(qName.equals("NewsBody")){
			content=new ContentNewsModel();
		}
		name=qName;
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		// TODO Auto-generated method stub
		super.endElement(uri, localName, qName);
	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		// TODO Auto-generated method stub
		super.characters(ch, start, length);
		String s=new String(ch, start, length);
		if(name.equals("Title")||name.equals("SourceName")||name.equals("SubmitDate")){
			
		}else{
			sb.append(s);
		}
		if(name.equals("Title")){
			content.setTitle(s);
		}
		else if(name.equals("SourceName")){
			content.setSourceName(s);
		}
		else if(name.equals("SubmitDate")){
			content.setSubmitDate(s);
		}
		else if(name.equals("Content")){
			content.setContent(sb.toString());
			Log.d("content", "1"+s);
		}
		else if(name.equals("ImageUrl")){
			content.setImageUrl(s);
		}
		else if(name.equals("CommentCount")){
			content.setCommentCount(Integer.parseInt(s));
		}
		Log.d("content", "2"+s);
	}
	
	
}
