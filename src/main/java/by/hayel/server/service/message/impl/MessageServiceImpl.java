package by.hayel.server.service.message.impl;

import by.hayel.server.model.message.LocaleMessage;
import by.hayel.server.service.message.MessageService;
import by.hayel.server.web.payload.response.MessageResponse;
import java.util.Locale;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MessageServiceImpl implements MessageService {
  MessageSource messageSource;

  @Override
  public MessageResponse buildMessageResponse(LocaleMessage message, Locale locale) {
    String code = message.getCode();
    String messageText = messageSource.getMessage(code, null, locale);
    return new MessageResponse(messageText);
  }

  @Override
  public String getMessage(LocaleMessage message, Locale locale) {
    String code = message.getCode();
    return messageSource.getMessage(code, null, locale);
  }
}
