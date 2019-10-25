package com.challenge.core.domain.user.session.exception;

public class SessionIdException extends RuntimeException {

  private SessionIdException(String message) {
    super(message);
  }

  public static SessionIdException notValid(String sessionKey) {
    return new SessionIdException(String.format("The session key <%s> is not valid", sessionKey));
  }
}
