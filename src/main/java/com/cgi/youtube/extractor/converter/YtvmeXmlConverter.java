package com.cgi.youtube.extractor.converter;

import org.springframework.stereotype.Component;

import com.cgi.youtube.extractor.domain.YoutubeVideo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

@Component
public class YtvmeXmlConverter implements Converter{

	@Override
	public String convert(YoutubeVideo video) throws JsonProcessingException {
		ObjectMapper mapper= new XmlMapper();
		return mapper.writeValueAsString(video);
	}

	@Override
	public YoutubeVideo convert(String xmlMsg) throws JsonMappingException, JsonProcessingException {
		ObjectMapper mapper= new XmlMapper();
		return mapper.readValue(xmlMsg, YoutubeVideo.class);
	}

}
