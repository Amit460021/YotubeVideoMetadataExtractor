package com.cgi.youtube.extractor.domain;

import lombok.Data;

@Data
public class SearchRequest {

	private String searchQuery;
	private String searchObjTyp;
	private int restrictCount;
	
}
