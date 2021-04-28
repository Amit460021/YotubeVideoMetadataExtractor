package com.cgi.youtube.extractor.domain;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
@JacksonXmlRootElement(localName="video")
@XmlRootElement
public class YoutubeVideo {

	/**
	 * <video>
	 *    <title>Title</title>
	 *    <url>URL</url>
	 * </video>
	 * 
	 */
	private String title;
	private String url;
	
}
