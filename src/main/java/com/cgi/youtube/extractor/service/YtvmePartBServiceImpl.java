package com.cgi.youtube.extractor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.cgi.youtube.extractor.converter.Converter;
import com.cgi.youtube.extractor.domain.YoutubeVideo;
import com.cgi.youtube.extractor.jms.SenderAdapter;
import com.cgi.youtube.extractor.transformer.Transformer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

@Service
public class YtvmePartBServiceImpl implements YtvmePartBService{

	@Value("${final.queue}")
	private String partBQueue;
	
	@Autowired
	private Converter converter;
	
	@Autowired
	private Transformer transformer;
	
	@Autowired
	private SenderAdapter senderAdapter;
	
	public void sendToPartBQueue(String xmlMsg) throws JsonMappingException, JsonProcessingException {
		YoutubeVideo youtubeVideo=converter.convert(xmlMsg);
		youtubeVideo=transformer.transform(youtubeVideo);
		senderAdapter.sendMsg(converter.convert(youtubeVideo), partBQueue);
	}
	
}
