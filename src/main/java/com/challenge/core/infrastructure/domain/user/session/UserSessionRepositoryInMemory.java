package com.challenge.core.infrastructure.domain.user.session;

import com.challenge.core.domain.user.session.SessionId;
import com.challenge.core.domain.user.session.UserSession;
import com.challenge.core.domain.user.session.UserSessionRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class UserSessionRepositoryInMemory implements UserSessionRepository {

  private final Map<SessionId, UserSession> repository = new ConcurrentHashMap<>();

  @Override
  public void add(SessionId sessionId, UserSession userSession) {
    repository.put(sessionId, userSession);
  }

  @Override
  public UserSession find(SessionId sessionId) {
    return repository.get(sessionId);
  }

  @Override
  public void delete(SessionId sessionId) {
    repository.remove(sessionId);
  }

  @Override
  public List<UserSession> findAll() {
    return new ArrayList<>(repository.values());
  }
}
