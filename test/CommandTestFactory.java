package com.king.backend.test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import com.king.backend.app.endpoints.HighScoreEndpoint;
import com.king.backend.app.endpoints.LoginEndpoint;
import com.king.backend.app.endpoints.ScoreEndpoint;
import com.king.backend.test.BaseCommandTest.RunnableCommand;
import com.sun.net.httpserver.HttpExchange;
import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;

public class CommandTestFactory {

	public static BaseCommandTest.RunnableCommand createCheckLoginCommand(ServerContext context,String sessionkey){
		Map<String, String> attributes = new HashMap<String, String>(); 
		attributes.put("sessionkey", sessionkey);
				
		HttpExchange exchange = mock(HttpExchange.class);
		when(exchange.getRequestBody()).thenReturn(new ByteInputStream());
		
		RunnableCommand loginCommand = new BaseCommandTest.RunnableCommand(new CheckLoginCommand(), context, exchange, attributes);
		return loginCommand;
		
	}
	
	public static BaseCommandTest.RunnableCommand createHScoreCommand(ServerContext context,String level){
		Map<String, String> attributes = new HashMap<String, String>(); 
		attributes.put("param1", level);

		HttpExchange exchange = mock(HttpExchange.class);
		
		RunnableCommand loginCommand = new BaseCommandTest.RunnableCommand(new HighScoreEndpoint(), context, exchange, attributes);
		return loginCommand;
		
	}
	
	
	public static BaseCommandTest.RunnableCommand  createLoginCommand(ServerContext context,String userId){
		Map<String, String> attributes = new HashMap<String, String>(); 
		attributes.put("param1", userId);
		
		HttpExchange exchange = mock(HttpExchange.class);
		when(exchange.getRequestBody()).thenReturn(new ByteInputStream());
		
		RunnableCommand loginCommand = new BaseCommandTest.RunnableCommand(new LoginEndpoint(), context, exchange, attributes);
		return loginCommand;
		
	}
	
	
	public static BaseCommandTest.RunnableCommand createScoreCommand(ServerContext context,String sessionkey,String userId,String level,String score){
		Map<String, String> attributes = new HashMap<String, String>(); 
		attributes.put("param1", level);
		attributes.put("userId", userId);
				
		HttpExchange exchange = mock(HttpExchange.class);
		when(exchange.getRequestBody()).thenReturn(new ByteArrayInputStream(score.getBytes(StandardCharsets.UTF_8)));
		
		RunnableCommand loginCommand = new BaseCommandTest.RunnableCommand(new ScoreEndpoint(), context, exchange, attributes);
		return loginCommand;
		
	}
}
