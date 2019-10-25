package com.challenge.core.domain.user.session;

import com.challenge.core.domain.user.UserId;

public class UserSessionCreator {

  private final SessionIdCreator sessionIdCreator;
  private final UserSessionRepository repository;

  public UserSessionCreator(SessionIdCreator sessionIdCreator, UserSessionRepository repository) {
    this.sessionIdCreator = sessionIdCreator;
    this.repository = repository;
  }

  public UserSession create(UserId userId) {
    SessionId sessionId = sessionIdCreator.create();
    UserSession userSession = UserSession.with(userId, sessionId);
    repository.add(sessionId, userSession);
    return userSession;
  }
}
