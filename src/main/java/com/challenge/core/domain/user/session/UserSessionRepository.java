package com.challenge.core.domain.user.session;

import java.util.List;

public interface UserSessionRepository {

  void add(SessionId sessionId, UserSession userSession);

  UserSession find(SessionId sessionId);

  void delete(SessionId sessionId);

  List<UserSession> findAll();
}
