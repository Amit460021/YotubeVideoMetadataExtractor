package com.cgi.youtube.extractor.jms;

public interface SenderAdapter {

	void sendMsg(String msg, String queueName);
}
