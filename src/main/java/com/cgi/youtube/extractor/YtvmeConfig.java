package com.cgi.youtube.extractor;

import javax.jms.ConnectionFactory;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;

import lombok.extern.slf4j.Slf4j;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@Slf4j
@EnableSwagger2
public class YtvmeConfig {
	
	@Value("${ytvme.jmsType}")
	private String jmsType;
	
	@Value("${activeMq.brokerUrl}")
	private String brokerUrl;
	@Value("${activeMq.user}")
	private String userName;
	@Value("${activeMq.password}")
	private String password;
	
	  @Bean
	    public JmsTemplate jmsTemplate(){
	    	String messagingType=jmsType.toUpperCase();
	    	JmsTemplate template=null;
	    	switch(messagingType) {
	    	case "ACTIVEMQ":
	    		ActiveMQConnectionFactory activeMQconnectionFactory = new ActiveMQConnectionFactory();
	    		activeMQconnectionFactory.setBrokerURL(brokerUrl);
	    		activeMQconnectionFactory.setUserName(userName);
	    		activeMQconnectionFactory.setPassword(password);
	    		template=new JmsTemplate(activeMQconnectionFactory);
		        break;
		    //configure other jmsTemplates here, you can use only one type of JMS Template at a time here
	    	}
	       return template; 
	    }
	
	@Bean
	public Docket apiDocket() {
		log.info("***Initializing the Swagger****");
		Docket docket= new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any()).build();
		return docket;
	}

	
	@Value("${consumer.concurrency}")
	private String consumerConcurrency;
	@Value("${consumer.recovery.interval}")
	private Long recoveryInterval;
	
	@Bean
    public JmsListenerContainerFactory<?> jmsFactory(ConnectionFactory connectionFactory,
                                                    DefaultJmsListenerContainerFactoryConfigurer configurer) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        configurer.configure(factory, jmsTemplate().getConnectionFactory());
        factory.setRecoveryInterval(recoveryInterval);
        factory.setConcurrency(consumerConcurrency);
        return factory;
    }
	  
}
