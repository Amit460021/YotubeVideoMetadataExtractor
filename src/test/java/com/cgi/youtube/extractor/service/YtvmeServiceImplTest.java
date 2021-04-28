package com.cgi.youtube.extractor.service;

import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.*;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class YtvmeServiceImplTest {

	//class under test
	private YtvmeServiceImpl ytvmeService;
	
	@Before
	public void setUp() {
		ytvmeService=new YtvmeServiceImpl();
	}
	
	
}
