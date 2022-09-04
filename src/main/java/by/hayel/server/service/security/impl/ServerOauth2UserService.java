package by.hayel.server.service.security.impl;

import by.hayel.server.model.entity.user.User;
import by.hayel.server.model.security.UserPrincipal;
import by.hayel.server.model.security.oauth2.GitHubOAuth2User;
import by.hayel.server.model.security.oauth2.OAuth2Providers;
import by.hayel.server.service.user.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Random;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.commons.text.CaseUtils;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ServerOauth2UserService extends DefaultOAuth2UserService {
  private static final String GIT_PREFIX = "GIT.";
  private static final String PROVIDER_NOT_SUPPORTED_MESSAGE =
      "The OAuth2 provider %s is not supported!";

  private static final Random GENERATOR = new Random();

  UserService userService;
  ObjectMapper mapper;

  @Override
  public OAuth2User loadUser(OAuth2UserRequest request) throws OAuth2AuthenticationException {
    OAuth2User oAuth2User = super.loadUser(request);
    return authenticateUser(request, oAuth2User);
  }

  private UserPrincipal authenticateUser(OAuth2UserRequest request, OAuth2User oAuth2User) {
    String providerName = request.getClientRegistration().getRegistrationId();
    if (providerName.equalsIgnoreCase(OAuth2Providers.GITHUB.name())) {
      GitHubOAuth2User githubUser =
          mapper.convertValue(oAuth2User.getAttributes(), GitHubOAuth2User.class);
      String email = GIT_PREFIX.concat(githubUser.getLogin());
      var userOptional = userService.getUserByEmail(email);
      User user;
      if (userOptional.isEmpty()) {
        var username = generateUniqueUsername(githubUser.getLogin());
        user = new User();
        user.setUsername(username);
        user.setPassword(UUID.randomUUID().toString());
        user.setEmail(email);
        userService.saveNewUser(user);
      } else {
        user = userOptional.get();
      }
      var principal = UserPrincipal.build(user);
      principal.setAttributes(oAuth2User.getAttributes());
      return principal;
    } else {
      throw new InternalAuthenticationServiceException(
          String.format(PROVIDER_NOT_SUPPORTED_MESSAGE, providerName));
    }
  }

  private String generateUniqueUsername(String seed) {
    if (!userService.isUsernameAlreadyExist(seed)) return seed;
    String upperCase = seed.toUpperCase();
    if (!userService.isUsernameAlreadyExist(upperCase)) return upperCase;
    String lowerCase = seed.toLowerCase();
    if (!userService.isUsernameAlreadyExist(lowerCase)) return lowerCase;
    char[] availableDelimiters = new char[] {' ', ';', ',', '.', '-'};
    boolean isFirstCapitalized = true;
    String camelCase = CaseUtils.toCamelCase(seed, isFirstCapitalized, availableDelimiters);
    if (!userService.isUsernameAlreadyExist(camelCase)) return camelCase;
    return generateUniqueUsername(seed + GENERATOR.nextInt(10));
  }
}
