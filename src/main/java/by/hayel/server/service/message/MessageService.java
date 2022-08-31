package by.hayel.server.service.message;

import by.hayel.server.model.message.LocaleMessage;
import by.hayel.server.web.payload.response.MessageResponse;
import java.util.Locale;

public interface MessageService {
  MessageResponse buildMessageResponse(LocaleMessage message, Locale locale);

  String getMessage(LocaleMessage message, Locale locale);
}
