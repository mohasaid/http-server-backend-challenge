package com.challenge.core.domain.user.session.exception;

import com.challenge.core.domain.user.session.SessionId;

public class SessionExpiredException extends RuntimeException {

  private SessionExpiredException(String message) {
    super(message);
  }

  public static SessionExpiredException notValid(SessionId sessionKey) {
    return new SessionExpiredException(
        String.format("The session key <%s> is expired", sessionKey.value()));
  }
}
