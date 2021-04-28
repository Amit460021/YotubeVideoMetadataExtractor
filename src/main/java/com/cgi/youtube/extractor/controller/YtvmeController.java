package com.cgi.youtube.extractor.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cgi.youtube.extractor.domain.SearchRequest;
import com.cgi.youtube.extractor.service.YtvmeService;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * The controller for YouTube Video Metadata Extractor application 
 * 2
 * @author amibhati0
 *
 */
@Slf4j
@Controller
@RequestMapping("/youtube/metadata/video/extractor")
public class YtvmeController {
	
	@Autowired
	private YtvmeService ytvmeService;

	@ApiOperation(value="Method for posting the Search results to external Queue System", nickname="postSearchResults")
	@PostMapping(consumes= {MediaType.APPLICATION_JSON_VALUE}, produces= {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<String> postSearchResults(@RequestBody SearchRequest request) throws Exception{
		String searchQuery=request.getSearchQuery();
		String searchObjTyp= request.getSearchObjTyp();
		log.info("*****Request is received for search term-{} and search object type-{}", searchQuery, searchObjTyp);
		if(StringUtils.isNotBlank(searchQuery)) {
		  int postedResultCount=ytvmeService.searchPostYotubeVideoMetadata(searchQuery, searchObjTyp);	
		  String result=String.format("{0} messages are pushed to external queue system", postedResultCount);
		  return new ResponseEntity<String>(result, HttpStatus.OK);
		}else{
			return new ResponseEntity<String>("Search Query is not valid.", HttpStatus.BAD_REQUEST);
		}
	}
	
}
