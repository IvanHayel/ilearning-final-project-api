package by.hayel.server.web.controller.rest.advice;

import by.hayel.server.exception.security.BlockedUserException;
import by.hayel.server.exception.user.RoleNotFoundException;
import by.hayel.server.exception.user.UserNotFoundException;
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
public class UserControllerAdvice {
  MessageService messageService;

  @ExceptionHandler(UserNotFoundException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public AdviceErrorMessage handleUserNotFoundException(WebRequest request) {
    Locale locale = request.getLocale();
    String description = request.getDescription(false);
    String message = messageService.getMessage(LocaleMessage.ERROR_USER_NOT_FOUND, locale);
    return new AdviceErrorMessage(HttpStatus.BAD_REQUEST.value(), message, description);
  }

  @ExceptionHandler(BlockedUserException.class)
  @ResponseStatus(HttpStatus.FORBIDDEN)
  public AdviceErrorMessage handleBlockedUserException(WebRequest request) {
    Locale locale = request.getLocale();
    String description = request.getDescription(false);
    String message = messageService.getMessage(LocaleMessage.ERROR_USER_BLOCKED, locale);
    return new AdviceErrorMessage(HttpStatus.FORBIDDEN.value(), message, description);
  }

  @ExceptionHandler(RoleNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public AdviceErrorMessage handleRoleNotFoundException(WebRequest request) {
    Locale locale = request.getLocale();
    String description = request.getDescription(false);
    String message = messageService.getMessage(LocaleMessage.ERROR_ROLE_NOT_FOUND, locale);
    return new AdviceErrorMessage(HttpStatus.NOT_FOUND.value(), message, description);
  }
}
