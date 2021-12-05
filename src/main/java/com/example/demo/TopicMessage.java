package com.example.demo;


public class TopicMessage {

	private String content;
	private String user;

	public TopicMessage() {
	}

	public TopicMessage(
			String content,
			String user) {
		this.content = content;
		this.user = user;
	}

	public String getContent() {
		return content;
	}

	public String getUser() {
		return user;
	}
}