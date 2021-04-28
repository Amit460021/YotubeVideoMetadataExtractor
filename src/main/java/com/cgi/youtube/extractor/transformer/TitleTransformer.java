package com.cgi.youtube.extractor.transformer;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.cgi.youtube.extractor.domain.YoutubeVideo;

@Component
public class TitleTransformer implements Transformer{

	@Value("#{${transformerMap}}")
	private Map<String, String> transformerMap;
	
	@Override
	public YoutubeVideo transform(YoutubeVideo youtubeVideo) {
		String title=youtubeVideo.getTitle().toUpperCase();
		Set<Map.Entry<String, String>> entrySet=transformerMap.entrySet();
		for(Entry<String, String> entry: entrySet) {
			if(title.contains(entry.getKey())) {
				String modifiedTitle=youtubeVideo.getTitle();
				//Hack
				modifiedTitle= modifiedTitle.replaceAll("(?i)"+entry.getKey(), entry.getValue());
				youtubeVideo.setTitle(modifiedTitle);
			}
		}
		return youtubeVideo;
	}
    
}
