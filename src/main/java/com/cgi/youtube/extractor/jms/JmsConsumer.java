package com.cgi.youtube.extractor.jms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.cgi.youtube.extractor.service.YtvmePartBService;

@Component
public class JmsConsumer {
	
	@Autowired
	private YtvmePartBService ytvmePartBService;
	
	@JmsListener(destination = "${intial.queue}", containerFactory="jmsFactory")
	public void receive(String xmlMsg) throws PartBServiceException{
		try {
		ytvmePartBService.sendToPartBQueue(xmlMsg);
		}catch(Exception excep) {
			throw new PartBServiceException(excep.getMessage());
		}
	}
	
}
