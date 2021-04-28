package com.cgi.youtube.extractor.jms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class SenderAdapterImpl implements SenderAdapter{

	@Autowired
	private JmsTemplate jmsTemplate;
	
	
	
	@Override
	public void sendMsg(String msg, String queueName) {
		jmsTemplate.convertAndSend(queueName, msg);
		
	}

}
