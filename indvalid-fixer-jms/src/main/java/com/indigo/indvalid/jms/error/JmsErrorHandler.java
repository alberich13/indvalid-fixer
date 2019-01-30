package com.indigo.indvalid.jms.error;

import org.springframework.stereotype.Service;
import org.springframework.util.ErrorHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class JmsErrorHandler implements ErrorHandler{
	
	public void handleError(Throwable t){
		log.error("Error al procesar mensaje {}", t.getMessage());
	}
}