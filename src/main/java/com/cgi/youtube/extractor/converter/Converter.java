package com.cgi.youtube.extractor.converter;

import com.cgi.youtube.extractor.domain.YoutubeVideo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

public interface Converter {

	String convert(YoutubeVideo video) throws JsonProcessingException;
	YoutubeVideo convert(String xmlMsg) throws JsonMappingException, JsonProcessingException;
	
}
