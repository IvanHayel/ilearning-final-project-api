package by.hayel.server.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.security.config.annotation.web.messaging.MessageSecurityMetadataSourceRegistry;
import org.springframework.security.config.annotation.web.socket.AbstractSecurityWebSocketMessageBrokerConfigurer;

@Configuration
public class WebSocketSecurityConfiguration
    extends AbstractSecurityWebSocketMessageBrokerConfigurer {
  @Override
  protected void configureInbound(MessageSecurityMetadataSourceRegistry messages) {
    messages
        .simpTypeMatchers(
            SimpMessageType.CONNECT, SimpMessageType.DISCONNECT, SimpMessageType.SUBSCRIBE)
        .permitAll()
        .anyMessage()
        .authenticated();
  }

  @Override
  protected boolean sameOriginDisabled() {
    return true;
  }
}
