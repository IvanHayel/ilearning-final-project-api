package by.hayel.server.web.controller.rest.advice;

import by.hayel.server.exception.collection.CollectionNameAlreadyExistException;
import by.hayel.server.exception.collection.CollectionNotFoundException;
import by.hayel.server.exception.collection.ItemNameAlreadyExistException;
import by.hayel.server.exception.collection.ItemNotFoundException;
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
public class CollectionControllerAdvice {
  MessageService messageService;

  @ExceptionHandler(CollectionNameAlreadyExistException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public AdviceErrorMessage handleCollectionNameAlreadyExistException(WebRequest request) {
    Locale locale = request.getLocale();
    String description = request.getDescription(false);
    String message =
        messageService.getMessage(LocaleMessage.ERROR_COLLECTION_NAME_ALREADY_EXIST, locale);
    return new AdviceErrorMessage(HttpStatus.BAD_REQUEST.value(), message, description);
  }

  @ExceptionHandler(CollectionNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public AdviceErrorMessage handleCollectionNotFoundException(WebRequest request) {
    Locale locale = request.getLocale();
    String description = request.getDescription(false);
    String message = messageService.getMessage(LocaleMessage.ERROR_COLLECTION_NOT_FOUND, locale);
    return new AdviceErrorMessage(HttpStatus.NOT_FOUND.value(), message, description);
  }

  @ExceptionHandler(ItemNameAlreadyExistException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public AdviceErrorMessage handleItemNameAlreadyExistException(WebRequest request) {
    Locale locale = request.getLocale();
    String description = request.getDescription(false);
    String message = messageService.getMessage(LocaleMessage.ERROR_ITEM_NAME_ALREADY_EXIST, locale);
    return new AdviceErrorMessage(HttpStatus.BAD_REQUEST.value(), message, description);
  }

  @ExceptionHandler(ItemNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public AdviceErrorMessage handleItemNotFoundException(WebRequest request) {
    Locale locale = request.getLocale();
    String description = request.getDescription(false);
    String message = messageService.getMessage(LocaleMessage.ERROR_ITEM_NOT_FOUND, locale);
    return new AdviceErrorMessage(HttpStatus.NOT_FOUND.value(), message, description);
  }
}
