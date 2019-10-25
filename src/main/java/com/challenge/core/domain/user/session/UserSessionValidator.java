package com.challenge.core.domain.user.session;

import com.challenge.core.domain.user.session.exception.SessionExpiredException;

import java.time.Duration;
import java.time.LocalDateTime;

public class UserSessionValidator {

  private static final Long MAX_TIME_SESSION = 60L * 10L;

  public boolean isValidSession(UserSession userSession) {
    Duration sessionTime = Duration.between(LocalDateTime.now(), userSession.createdAt());
    if (sessionTime.getSeconds() < MAX_TIME_SESSION) {
      return true;
    }
    throw SessionExpiredException.notValid(userSession.sessionKey());
  }
}
