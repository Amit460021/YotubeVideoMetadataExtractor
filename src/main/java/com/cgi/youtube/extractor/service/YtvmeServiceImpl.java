package com.cgi.youtube.extractor.service;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.cgi.youtube.extractor.converter.Converter;
import com.cgi.youtube.extractor.domain.YoutubeVideo;
import com.cgi.youtube.extractor.exception.PartAServiceException;
import com.cgi.youtube.extractor.jms.SenderAdapter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.ResourceId;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class YtvmeServiceImpl implements YtvmeService{
	
	@Value("${ytvme.apiKey}")
	private String apiKey;

	//YouTube URL = BASE URL+ VideoId
	@Value("${ytvme.url}")
	private String baseUrl;
	
	@Value("${ytvme.maxResultsPerPage}")
	private Long maxResultsPerPage;
	
	@Autowired
	private SenderAdapter jmsSender;
	
	@Value("${intial.queue}")
	private String partAQueue;
	
	@Autowired
	private Converter converter;
	
	 private static YouTube youtube =new YouTube.Builder(new NetHttpTransport(), new JacksonFactory(), new HttpRequestInitializer() {
         public void initialize(HttpRequest request) throws IOException {
         }
     }).setApplicationName("YTVideoMetadataExtractor").build(); 
	
	 
	 public int searchPostYotubeVideoMetadata(String searchTerm, String searchObjType) throws Exception{
		 try {
		 YouTube.Search.List search = youtube.search().list("id,snippet");
		 search.setKey(apiKey);
         search.setQ(searchTerm);
         setType(search, searchObjType);
         search.setFields("items(id/kind,id/videoId,snippet/title),nextPageToken,pageInfo,prevPageToken");
         search.setMaxResults(maxResultsPerPage);
         makeApiCall(search, searchTerm);
		 }catch(Exception excep) {
			 log.error("Error occurred while searching.converting/posting search Result-{}", excep.getMessage());
			 throw new PartAServiceException(excep);
		 }
	  return 0;	 
	 }
	 
	 private void makeApiCall(YouTube.Search.List search, String searchTerm) throws IOException {
		 String nextPageToken=StringUtils.EMPTY;
		 while(nextPageToken!=null) {
		 SearchListResponse searchResponse = search.execute();
		 nextPageToken = searchResponse.getNextPageToken();
		 search.setPageToken(nextPageToken);
		 List<SearchResult> searchResultList = searchResponse.getItems();
		 generateJmsMsg(searchResultList.iterator(), searchTerm);
		 }
		 
	 }
	 
	 private void generateJmsMsg(Iterator<SearchResult> searchResultIterator, String searchTerm) {
		 String jmsMsg= StringUtils.EMPTY;	 
		 String upperCaseSearchTerm=searchTerm.toUpperCase();
		 while (searchResultIterator.hasNext()) {
		            SearchResult youtubeVideoMetdata = searchResultIterator.next();
		            ResourceId rId = youtubeVideoMetdata.getId();

		            if (rId.getKind().equals("youtube#video")) {
		            	YoutubeVideo video= new YoutubeVideo();
		            	String title= youtubeVideoMetdata.getSnippet().getTitle();
		            	if(title.toUpperCase().contains(upperCaseSearchTerm)) {
		            	video.setTitle(title);
		            	video.setUrl(baseUrl+rId.getVideoId());
		            	
		            	try {
		            	jmsMsg=converter.convert(video);
		            	}catch(JsonProcessingException jsonProcessingExcep) {
		            		log.error("Error-{}, occurred while converting video metadata to xml -{}", jsonProcessingExcep.getMessage(), video);
		                    //Purposely not failing it here, as no metadata cleanup is applied, special characters which might interfere in parsing
		            	}
		            	if(StringUtils.isNotBlank(jmsMsg)){
		            		log.info(jmsMsg);
		            		jmsSender.sendMsg(jmsMsg,partAQueue);
		            	}
		            }
		        }
		            }
		}
	 
	 private void setType(YouTube.Search.List search, String type) {
		 if(StringUtils.isBlank(type)) {
			 search.setType("video");
		 }else {
			 search.setType(type);
		 }
	 }
	 
	 
}
