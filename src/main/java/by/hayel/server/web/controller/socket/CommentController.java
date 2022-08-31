package by.hayel.server.web.controller.socket;

import by.hayel.server.model.entity.collection.dto.CommentDto;
import by.hayel.server.service.collection.ItemService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CommentController {
  private static final String DESTINATION_PRIVATE = "/private";

  ItemService itemService;
  SimpMessagingTemplate template;

  @MessageMapping("/comment/{id}")
  public CommentDto handleComment(@DestinationVariable Long id, @Payload CommentDto commentDto) {
    var owner = itemService.getItemOwner(id);
    var comment = itemService.addComment(id, commentDto);
    template.convertAndSend("/comment/items/" + id, comment);
    template.convertAndSendToUser(owner, DESTINATION_PRIVATE, comment);
    return comment;
  }
}
