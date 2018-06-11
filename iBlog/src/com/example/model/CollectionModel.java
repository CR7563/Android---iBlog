package com.example.model;

public class CollectionModel {
	private int id;
	private int content_id;
	private int content_type;
	private int content_comments;
	private String content_title;
	private String content_sourcename;
	
	
	public int getContent_type() {
		return content_type;
	}
	public void setContent_type(int content_type) {
		this.content_type = content_type;
	}
	public int getId() {
		return id; 
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getContent_id() {
		return content_id;
	}
	public void setContent_id(int content_id) {
		this.content_id = content_id;
	}
	public int getContent_comments() {
		return content_comments;
	}
	public void setContent_comments(int content_comments) {
		this.content_comments = content_comments;
	}
	public String getContent_title() {
		return content_title;
	}
	public void setContent_title(String content_title) {
		this.content_title = content_title;
	}
	public String getContent_sourcename() {
		return content_sourcename;
	}
	public void setContent_sourcename(String content_sourcename) {
		this.content_sourcename = content_sourcename;
	}
	
}
