package com.niit.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

@Configuration
@EnableWebSocketMessageBroker
@ComponentScan(basePackages="com.niit")
public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer{
//At the time loading the @EnableWebSocketMessageBroker, configureMessageBroker()method will automatically call
	@Override
	public void configureMessageBroker(MessageBrokerRegistry config) {
		config.enableSimpleBroker("/topic", "/queue");
		config.setApplicationDestinationPrefixes("/app");
	}
	
	
	public void registerStompEndpoints(StompEndpointRegistry registery) {
		registery.addEndpoint("/chat").withSockJS();
		/*registery.addEndpoint("/chat_forum").withSockJS();*/
		}

//stomp =>Simple Text Oriented Message Protocol
	
	//@override annotation is required or not
	/*ANS
	 * --
	 * Use it every time you override a method for two benefits. Do it so that you can take advantage of the compiler
	 *  checking to make sure you actually are overriding a method when you think you are. This way, 
	 *  if you make a common mistake of misspelling a method name or not correctly matching the parameters,
	 *   you will be warned that you method does not actually override as you think it does. Secondly, 
	 *   it makes your code easier to understand because it is more obvious when methods are overwritten.
Additionally, in Java 1.6 you can use it to mark when a method implements an interface for the same benefits.
 I think it would be better to have a separate annotation (like @Implements), but it's better than nothing.
* */
	
}