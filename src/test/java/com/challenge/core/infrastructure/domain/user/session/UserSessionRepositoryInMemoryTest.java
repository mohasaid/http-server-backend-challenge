package com.challenge.core.infrastructure.domain.user.session;

import com.challenge.core.domain.user.UserId;
import com.challenge.core.domain.user.session.SessionId;
import com.challenge.core.domain.user.session.UserSession;
import com.challenge.core.domain.user.session.UserSessionRepository;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class UserSessionRepositoryInMemoryTest {

  private final UserSessionRepository userSessionRepository = new UserSessionRepositoryInMemory();

  private UserSession existentSession;
  private UserId existentUserId;
  private SessionId existentSessionId;

  @Before
  public void init() {
    existentUserId = UserId.with(1L);
    existentSessionId = SessionId.with("existentSession");
    existentSession = UserSession.with(existentUserId, existentSessionId);
  }

  @Test
  public void GivenNewUserSession_WhenAddingIt_ThenSavesItToRepository() {
    userSessionRepository.add(existentSessionId, existentSession);

    UserSession session = userSessionRepository.find(existentSessionId);
    assertNotNull(session);
    assertEquals(session, existentSession);
  }

  @Test
  public void GivenExistingUserSession_WhenRemovingIt_ThenRemovesItFromRepository() {
    userSessionRepository.add(existentSessionId, existentSession);

    UserSession session = userSessionRepository.find(existentSessionId);
    assertNotNull(session);
    assertEquals(session, existentSession);

    userSessionRepository.delete(existentSessionId);

    session = userSessionRepository.find(existentSessionId);
    assertNull(session);
  }

  @Test
  public void GivenTwoExistingUserSession_WhenFindingAll_ThenReturnsThem() {
    userSessionRepository.add(existentSessionId, existentSession);
    SessionId sessionId = SessionId.with("otherSession");
    UserSession userSession = UserSession.with(UserId.with(2L), sessionId);
    userSessionRepository.add(sessionId, userSession);

    List<UserSession> sessions = userSessionRepository.findAll();
    assertNotNull(sessionId);
    assertFalse(sessions.isEmpty());
    assertEquals(2, sessions.size());
  }
}
