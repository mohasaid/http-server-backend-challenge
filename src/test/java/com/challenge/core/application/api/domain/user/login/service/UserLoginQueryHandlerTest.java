package com.challenge.core.application.api.domain.user.login.service;

import com.challenge.core.application.api.domain.user.login.use_case.UserLoginQuery;
import com.challenge.core.application.api.domain.user.login.use_case.UserLoginQueryResponse;
import com.challenge.core.domain.user.UserId;
import com.challenge.core.domain.user.session.SessionId;
import com.challenge.core.domain.user.session.UserSession;
import com.challenge.core.domain.user.session.UserSessionCreator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static com.challenge.core.helper.mockito.MockitoVerificationMode.once;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserLoginQueryHandlerTest {

  @InjectMocks private UserLoginQueryHandler userLoginQueryHandler;
  @Mock private UserSessionCreator userSessionCreator;

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
  public void GivenRequestOfNewUser_WhenCallUseCase_ThenReturnsSessionKey() {
    when(userSessionCreator.create(existentUserId)).thenReturn(existentSession);

    UserLoginQuery query = UserLoginQuery.with(existentUserId.id());
    UserLoginQueryResponse response = userLoginQueryHandler.internalExecute(query);
    assertNotNull(response);
    assertEquals(existentSessionId.value(), response.sessionId());

    verify(userSessionCreator, once()).create(existentUserId);
  }
}
