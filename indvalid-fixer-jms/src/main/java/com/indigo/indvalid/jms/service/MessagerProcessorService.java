package com.indigo.indvalid.jms.service;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.indigo.indvalid.jms.model.Message;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class MessagerProcessorService {
	
	@JmsListener(destination = "indvalid.queue", containerFactory = "myFactory")
	public void process(final Message message) {
		boolean noTieneId = message.getId() == null|| message.getId().equals(3);
		log.info("Aqui se ejecuta algo periodicamente: "+ message.getId());
		if(noTieneId){
			throw new RuntimeException("Fallamos a proposito");
		}
	}
}