package com.challenge.core.domain.user.session;

import com.challenge.core.domain.user.UserId;

import java.time.LocalDateTime;

public class UserSession {

  private final UserId userId;
  private final SessionId sessionId;
  private final LocalDateTime createdAt;

  public UserSession(UserId userId, SessionId sessionId, LocalDateTime createdAt) {
    this.userId = userId;
    this.sessionId = sessionId;
    this.createdAt = createdAt;
  }

  public static UserSession with(UserId userId, SessionId sessionId, LocalDateTime createdAt) {
    return new UserSession(userId, sessionId, createdAt);
  }

  public static UserSession with(UserId userId, SessionId sessionId) {
    return with(userId, sessionId, LocalDateTime.now());
  }

  public UserId userId() {
    return userId;
  }

  public SessionId sessionKey() {
    return sessionId;
  }

  public LocalDateTime createdAt() {
    return createdAt;
  }
}
