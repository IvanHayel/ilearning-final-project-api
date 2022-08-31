package by.hayel.server.web.controller.rest.advice;

import by.hayel.server.exception.cloud.CloudinaryUploadException;
import by.hayel.server.exception.security.AccessDeniedException;
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
public class GlobalControllerAdvice {
  MessageService messageService;

  @ExceptionHandler(CloudinaryUploadException.class)
  @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
  public AdviceErrorMessage handleCloudinaryUploadException(WebRequest request) {
    Locale locale = request.getLocale();
    String description = request.getDescription(false);
    String message = messageService.getMessage(LocaleMessage.ERROR_CLOUDINARY_UPLOAD, locale);
    return new AdviceErrorMessage(HttpStatus.SERVICE_UNAVAILABLE.value(), message, description);
  }

  @ExceptionHandler(AccessDeniedException.class)
  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  public AdviceErrorMessage handleExpiredJwtException(WebRequest request) {
    Locale locale = request.getLocale();
    String description = request.getDescription(false);
    String message = messageService.getMessage(LocaleMessage.ERROR_ACCESS_DENIED, locale);
    return new AdviceErrorMessage(HttpStatus.UNAUTHORIZED.value(), message, description);
  }
}
