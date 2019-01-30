package com.indigo.indvalid.jms;

import javax.jms.ConnectionFactory;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.RedeliveryPolicy;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.indigo.indvalid.jms.error.JmsErrorHandler;

import lombok.Data;

@Configuration
@ConfigurationProperties("indvalid.jms")
@ComponentScan
@EnableAutoConfiguration
@EnableTransactionManagement
@EnableJms
@Data
public class IndvalidJmsConfig {
	private String concurrency;
	private String brokerURL;
	private int initialRedeliveryDelay;
	private int redeliveryDelay;
	private int maximumRedeliveries;
	
	@Bean
    public JmsListenerContainerFactory<?> myFactory(
                            ConnectionFactory connectionFactory, DefaultJmsListenerContainerFactoryConfigurer configurer) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setCacheLevel(DefaultMessageListenerContainer.CACHE_CONSUMER);
        factory.setConcurrency(concurrency);
        factory.setErrorHandler(new JmsErrorHandler());
        factory.setSessionTransacted(Boolean.TRUE);
        factory.setSessionAcknowledgeMode(Session.SESSION_TRANSACTED);
        configurer.configure(factory, connectionFactory);
        return factory;
    }
	
	@Bean
    public ConnectionFactory connectionFactory() {
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(brokerURL);
        RedeliveryPolicy policy = new RedeliveryPolicy();
        policy.setInitialRedeliveryDelay(initialRedeliveryDelay);
        policy.setMaximumRedeliveries(maximumRedeliveries);
        policy.setRedeliveryDelay(redeliveryDelay);
        activeMQConnectionFactory.setRedeliveryPolicy(policy);
        return activeMQConnectionFactory;
    }
 
    @Bean
    public MessageConverter jacksonJmsMessageConverter(){
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setTargetType(MessageType.TEXT);
        converter.setTypeIdPropertyName("_type");
        return converter;
    }
}
