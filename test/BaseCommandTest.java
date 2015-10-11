package com.king.backend.test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import java.util.Map;

import org.junit.Before;

import com.king.backend.server.http.HttpResponseSender;
import com.sun.net.httpserver.HttpExchange;

public abstract class BaseCommandTest {

	protected DataRepository repository;
	protected ServerContext context;
	protected HttpResponseSender handler;
	protected AccessTokenExpirer accessTokenExpirer;

	@Before
	public void setUpTestEnvironment() {
		repository = spy(new DataRepository());
		context = mock(ServerContext.class);
		handler = mock(HttpResponseSender.class);
		accessTokenExpirer = mock(AccessTokenExpirer.class);
		when(accessTokenExpirer.getExpirationTime()).thenReturn(new Long(10 * 60 *1000));
		
		when(context.getAccessTokenExpirer()).thenReturn(accessTokenExpirer);
		when(context.getRepository()).thenReturn(repository);
		when(context.getHttpHandler()).thenReturn(handler);
		
	}


	

	protected static class RunnableCommand implements Runnable {

		private final Command command;
		private final ServerContext context;
		private final HttpExchange exchange;
		private final Map<String, String> attributes;

		public RunnableCommand(Command command, ServerContext context,HttpExchange exchange, Map<String, String> attributes) {
			super();
			this.command = command;
			this.context = context;
			this.exchange =exchange;
			this.attributes = attributes;
		}

		@Override
		public void run() {
			command.execute(context, exchange, attributes);

		}

		public Map<String, String> getAttributes() {
			return attributes;
		}
		
		

	}

}
