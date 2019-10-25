package com.challenge.core.application.api.domain.user.score.service;

import com.challenge.core.application.api.domain.user.score.use_case.RegisterUserScoreCommand;
import com.challenge.core.domain.user.UserId;
import com.challenge.core.domain.user.score.Level;
import com.challenge.core.domain.user.score.Score;
import com.challenge.core.domain.user.score.UserScore;
import com.challenge.core.domain.user.score.UserScoreCreator;
import com.challenge.core.domain.user.session.SessionId;
import com.challenge.core.domain.user.session.UserSession;
import com.challenge.core.domain.user.session.UserSessionFinder;
import com.challenge.core.domain.user.session.UserSessionValidator;
import com.challenge.core.domain.user.session.exception.SessionDoesNotExistException;
import com.challenge.core.domain.user.session.exception.SessionExpiredException;
import com.challenge.core.helper.matcher.MatcherException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static com.challenge.core.helper.mockito.MockitoVerificationMode.once;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class RegisterUserScoreCommandHandlerTest {

  @InjectMocks private RegisterUserScoreCommandHandler registerUserScoreCommandHandler;
  @Mock private UserScoreCreator userScoreCreator;
  @Mock private UserSessionFinder userSessionFinder;
  @Mock private UserSessionValidator userSessionValidator;

  private UserSession existentSession;
  private UserId existentUserId;
  private SessionId existentSessionId;
  private Level existingLevel;
  private UserScore existingScore;

  @Before
  public void init() {
    doNothing().when(userScoreCreator).addScore(any(Level.class), any(UserScore.class));

    existentUserId = UserId.with(1L);
    existentSessionId = SessionId.with("existentSession");
    existentSession = UserSession.with(existentUserId, existentSessionId);
    existingLevel = Level.with(1L);
    existingScore = UserScore.with(existentUserId, Score.with(10L));
  }

  @Test
  public void GivenValidSession_WhenCallCreateScore_ThenSavesItInRepository() {
    when(userSessionFinder.find(existentSessionId)).thenReturn(existentSession);
    when(userSessionValidator.isValidSession(existentSession)).thenReturn(true);

    RegisterUserScoreCommand command =
        RegisterUserScoreCommand.with(
            existingLevel.value(), existentSessionId.value(), existingScore.score().value());

    registerUserScoreCommandHandler.internalExecute(command);

    verify(userSessionFinder, once()).find(existentSessionId);
    verify(userSessionValidator, once()).isValidSession(existentSession);
    verify(userScoreCreator, once()).addScore(existingLevel, existingScore);
  }

  @Test
  public void GivenInValidSession_WhenCallCreateScore_ThenThrowsException() {
    when(userSessionFinder.find(existentSessionId)).thenReturn(null);

    RegisterUserScoreCommand command =
        RegisterUserScoreCommand.with(
            existingLevel.value(), existentSessionId.value(), existingScore.score().value());

    shouldThrowSessionNotExists(() -> registerUserScoreCommandHandler.internalExecute(command));

    verify(userSessionFinder, once()).find(existentSessionId);
    verify(userSessionValidator, never()).isValidSession(existentSession);
    verify(userScoreCreator, never()).addScore(existingLevel, existingScore);
  }

  @Test
  public void GivenExpiredSession_WhenCallCreateScore_ThenThrowsException() {
    when(userSessionFinder.find(existentSessionId)).thenReturn(existentSession);
    when(userSessionValidator.isValidSession(existentSession))
        .thenThrow(SessionExpiredException.class);

    RegisterUserScoreCommand command =
        RegisterUserScoreCommand.with(
            existingLevel.value(), existentSessionId.value(), existingScore.score().value());

    shouldThrowSessionExpired(() -> registerUserScoreCommandHandler.internalExecute(command));

    verify(userSessionFinder, once()).find(existentSessionId);
    verify(userSessionValidator, once()).isValidSession(existentSession);
    verify(userScoreCreator, never()).addScore(existingLevel, existingScore);
  }

  private void shouldThrowSessionNotExists(Runnable runnable) {
    MatcherException.assertThrows(runnable, SessionDoesNotExistException.class);
  }

  private void shouldThrowSessionExpired(Runnable runnable) {
    MatcherException.assertThrows(runnable, SessionExpiredException.class);
  }
}
