package com.indigo.indvalid.jms.test;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringRunner;

import com.indigo.indvalid.jms.IndvalidJmsConfig;
import com.indigo.indvalid.jms.model.Message;

import lombok.extern.slf4j.Slf4j;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
@ContextHierarchy({
    @ContextConfiguration(classes = {IndvalidJmsConfig.class})})
public class JMSTest {
	
	@Autowired
	private JmsTemplate jmsTemplate;
	private static final String DESTINATION_NAME = "indvalid.queue";
	
	@Test
	public void sendSaveJmsList() throws InterruptedException {
		IntStream.range(1, 5)
			.boxed()
			.collect(Collectors.toList())
			.stream()
			.map(this::getMessage)
			.forEach(message -> jmsTemplate.convertAndSend(DESTINATION_NAME, message));
		
      log.info("Esperando a que la lista de mensajes sea consumida");
      TimeUnit.SECONDS.sleep(20);
      System.exit(-1);
	}
	
	private Message getMessage(Integer id) {
		Message message = new Message();
		message.setId(id);
		message.setBody(new StringBuilder("Cuerpo de el mensaje con id: ").append(id).toString());
		message.setDate(LocalDateTime.now());
		return message;
	}
	
}
