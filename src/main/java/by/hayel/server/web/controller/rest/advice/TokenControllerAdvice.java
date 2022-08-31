package by.hayel.server.web.controller.rest.advice;

import by.hayel.server.exception.security.HttpRequestTokenNotFoundException;
import by.hayel.server.exception.security.RefreshTokenNotFoundException;
import by.hayel.server.exception.security.TokenRefreshException;
import by.hayel.server.model.message.AdviceErrorMessage;
import by.hayel.server.model.message.LocaleMessage;
import by.hayel.server.service.message.MessageService;
import java.util.Locale;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TokenControllerAdvice {
  MessageService messageService;

  @ExceptionHandler(TokenRefreshException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public AdviceErrorMessage handleTokenRefreshException(WebRequest request) {
    Locale locale = request.getLocale();
    String description = request.getDescription(false);
    String message = messageService.getMessage(LocaleMessage.ERROR_REFRESH_TOKEN_EXPIRED, locale);
    return new AdviceErrorMessage(HttpStatus.BAD_REQUEST.value(), message, description);
  }

  @ExceptionHandler(HttpRequestTokenNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public AdviceErrorMessage handleTokenNotFoundException(WebRequest request) {
    Locale locale = request.getLocale();
    String description = request.getDescription(false);
    String message = messageService.getMessage(LocaleMessage.ERROR_TOKEN_NOT_FOUND, locale);
    return new AdviceErrorMessage(HttpStatus.NOT_FOUND.value(), message, description);
  }

  @ExceptionHandler(RefreshTokenNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public AdviceErrorMessage handleRefreshTokenNotFoundException(WebRequest request) {
    Locale locale = request.getLocale();
    String description = request.getDescription(false);
    String message = messageService.getMessage(LocaleMessage.ERROR_REFRESH_TOKEN_NOT_FOUND, locale);
    return new AdviceErrorMessage(HttpStatus.NOT_FOUND.value(), message, description);
  }
}
