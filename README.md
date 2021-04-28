# YOUTUBE Video Metadata Extractor (YTVME)
The Application is a generic youtube video metadata extractor. This application performs below operations-<br/>
1. Application is invoked by a Rest Endpoint. Rest Endpoint has only 1 mandatory field that is searchQuery. We need to put what we want to search.
2. In second step, application uses [ Google Data Api ] ( https://developers.google.com/youtube/v3/docs ) to make search for the videos having search term.
3. Then application iterate over the search results and filter the videos with title having exact match, but case insensitive.
4. Video id and Title is extracted from the Youtube Video metadata.
```
Video URL = https://www.youtube.com/watch?v= + video_id
```
5. An XML message is generated for Video title and Video Url.
6. The XML message is then sent to queue A.
7. A consumer is listening to Queue A.
8. Query term is transformed to mapped value.
9. Transformed message is sent to queue B.

## Generate the Google API Key 

1. Go to the [ Google cloud console ] ( https://console.cloud.google.com/ ) and create a google account.
2. After login into the google cloud console, create a project.
3. Once the project is created, generate a api key.
4. Enable the Youtube Data Api for your project.
