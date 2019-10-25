package com.challenge.core.application.api.domain.user.login.use_case;

import com.challenge.core.domain.user.session.UserSession;

public class UserLoginQueryResponse {

  private String userSessionId;

  public UserLoginQueryResponse(String userSessionId) {
    this.userSessionId = userSessionId;
  }

  public static UserLoginQueryResponse of(UserSession userSession) {
    return new UserLoginQueryResponse(userSession.sessionKey().value());
  }

  public String sessionId() {
    return userSessionId;
  }
}
