package by.hayel.server.model.message;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Getter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum LocaleMessage {
  SUCCESS_REGISTRATION("success.registration"),
  SUCCESS_LOGOUT("success.logout"),
  SUCCESS_DELETE_USER("success.delete.user"),
  SUCCESS_BLOCK_USER("success.block.user"),
  SUCCESS_UNBLOCK_USER("success.unblock.user"),
  SUCCESS_GRANT_ADMIN_RIGHTS("success.grant.admin.rights"),
  SUCCESS_REVOKE_ADMIN_RIGHTS("success.revoke.admin.rights"),
  ERROR_USERNAME_TAKEN("error.username.taken"),
  ERROR_EMAIL_TAKEN("error.email.taken"),
  ERROR_DELETE_ROOT("error.delete.root"),
  ERROR_BLOCK_ROOT("error.block.root"),
  ERROR_USER_BLOCKED("error.user.blocked"),
  ERROR_USER_NOT_FOUND("error.user.not.found"),
  ERROR_TOKEN_NOT_FOUND("error.token.not.found"),
  ERROR_ROLE_NOT_FOUND("error.role.not.found"),
  ERROR_REFRESH_TOKEN_EXPIRED("error.refresh.token.expired"),
  ERROR_REFRESH_TOKEN_NOT_FOUND("error.refresh.token.not.found"),
  ERROR_COLLECTION_NAME_ALREADY_EXIST("error.collection.name.already.exist"),
  ERROR_CLOUDINARY_UPLOAD("error.cloudinary.upload"),
  ERROR_CLOUDINARY_DELETE("error.cloudinary.delete"),
  ERROR_COLLECTION_NOT_FOUND("error.collection.not.found"),
  ERROR_ACCESS_DENIED("error.access.denied"),
  ERROR_ITEM_NAME_ALREADY_EXIST("error.item.name.already.exist"),
  ERROR_ITEM_NOT_FOUND("error.item.not.found");

  String code;
}
