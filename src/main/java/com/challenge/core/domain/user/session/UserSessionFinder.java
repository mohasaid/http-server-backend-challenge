package com.challenge.core.domain.user.session;

public class UserSessionFinder {

  private final UserSessionRepository sessionRepository;

  public UserSessionFinder(UserSessionRepository sessionRepository) {
    this.sessionRepository = sessionRepository;
  }

  public UserSession find(SessionId sessionId) {
    return sessionRepository.find(sessionId);
  }
}
