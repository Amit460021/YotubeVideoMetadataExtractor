package com.cgi.youtube.extractor.transformer;

import com.cgi.youtube.extractor.domain.YoutubeVideo;

@FunctionalInterface
public interface Transformer {

	YoutubeVideo transform(YoutubeVideo youtubeVideo);
	
}
