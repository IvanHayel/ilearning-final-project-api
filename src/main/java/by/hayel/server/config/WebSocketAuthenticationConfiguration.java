package by.hayel.server.config;

import by.hayel.server.exception.security.AccessDeniedException;
import by.hayel.server.exception.security.BlockedUserException;
import by.hayel.server.service.security.JwtService;
import by.hayel.server.service.user.UserService;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
@Order(Ordered.HIGHEST_PRECEDENCE)
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class WebSocketAuthenticationConfiguration implements WebSocketMessageBrokerConfigurer {
  final UserService userService;
  final JwtService jwtService;
  final UserDetailsService userDetailsService;

  @Value("${jwt.header.name}")
  String headerName;

  @Override
  public void configureClientInboundChannel(ChannelRegistration registration) {
    registration.interceptors(
        new ChannelInterceptor() {
          @Override
          public Message<?> preSend(@NonNull Message<?> message, @NonNull MessageChannel channel) {
            StompHeaderAccessor accessor =
                MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
            if (StompCommand.CONNECT.equals(Objects.requireNonNull(accessor).getCommand())) {
              var tokenFromAccessor = accessor.getFirstNativeHeader(headerName);
              var token = jwtService.getTokenFromSocketAccessor(tokenFromAccessor);
              if (token != null && jwtService.isTokenValid(token)) {
                String username = jwtService.getUsernameFromToken(token);
                if (userService.isBlocked(username)) throw new BlockedUserException();
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                var authorities = userDetails.getAuthorities();
                var authentication =
                    new UsernamePasswordAuthenticationToken(userDetails, null, authorities);
                accessor.setUser(authentication);
                SecurityContextHolder.getContext().setAuthentication(authentication);
              } else {
                throw new AccessDeniedException();
              }
            }
            return message;
          }
        });
  }
}
