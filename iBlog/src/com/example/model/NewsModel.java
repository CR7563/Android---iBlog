package com.example.model;

import android.graphics.Bitmap;

public class NewsModel {
	
	private int id;
	private String topicIcon;
	private Bitmap icon;
	private String title;
	private	String summary;
	private int views;
	private int conments;
	private String sourceName;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTopicIcon() {
		return topicIcon;
	}
	public void setTopicIcon(String topicIcon) {
		this.topicIcon = topicIcon;
	}
	public Bitmap getIcon() {
		return icon;
	}
	public void setIcon(Bitmap icon) {
		this.icon = icon;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public int getViews() {
		return views;
	}
	public void setViews(int views) {
		this.views = views;
	}
	public int getConments() {
		return conments;
	}
	public void setConments(int conments) {
		this.conments = conments;
	}
	public String getSourceName() {
		return sourceName;
	}
	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}
	
} 
