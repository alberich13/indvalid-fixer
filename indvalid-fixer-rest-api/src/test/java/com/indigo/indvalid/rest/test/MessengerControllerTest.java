package com.indigo.indvalid.rest.test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import com.indigo.indvalid.jms.model.Message;
import com.indigo.indvalid.rest.IndvalidRestApp;
import lombok.extern.slf4j.Slf4j;
import com.google.gson.Gson;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ContextHierarchy({ @ContextConfiguration(classes = {IndvalidRestApp.class}) })
@Slf4j
public class MessengerControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	private Gson gson;
		
	private Message message;
		
	@Before
	public void setUp(){
		gson = new Gson();
		message = new Message();
		message.setId(1);
		message.setBody("Body test");
		message.setDate(LocalDateTime.now());
	}
	
	@Test
	public void hire(){
		hire(message, status().isOk());
	}
	
	private void hire(Message message, ResultMatcher matcher){
		log.info("Enviando mensaje para procesar en queue");
		try {
			this.mockMvc.perform(
					post("/indvalid/sendMessage")
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(gson.toJson(message)))
				.andExpect(matcher);	
								
					
		} catch (Exception e) {
			log.error("Error on userFoundByUserId", e);
		}
	}

}
