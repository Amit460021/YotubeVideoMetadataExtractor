package com.cgi.youtube.extractor.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

public interface YtvmePartBService {
	void sendToPartBQueue(String xmlMsg) throws JsonMappingException, JsonProcessingException;
}
