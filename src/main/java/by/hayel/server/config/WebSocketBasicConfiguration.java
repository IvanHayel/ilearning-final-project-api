package by.hayel.server.config;

import by.hayel.server.config.property.WebSocketProperty;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class WebSocketBasicConfiguration implements WebSocketMessageBrokerConfigurer {
  WebSocketProperty properties;

  @Override
  public void registerStompEndpoints(StompEndpointRegistry registry) {
    registry
        .addEndpoint(properties.getEndpoint())
        .setAllowedOriginPatterns(properties.getAllowedOrigins())
        .withSockJS();
  }

  @Override
  public void configureMessageBroker(MessageBrokerRegistry registry) {
    registry.enableSimpleBroker(properties.getBroker(), properties.getUser());
    registry.setUserDestinationPrefix(properties.getUser());
    registry.setApplicationDestinationPrefixes(properties.getApp());
  }
}
