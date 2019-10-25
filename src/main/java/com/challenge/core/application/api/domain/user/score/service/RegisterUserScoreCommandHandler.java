package com.challenge.core.application.api.domain.user.score.service;

import com.challenge.core.application.api.domain.user.score.use_case.RegisterUserScoreCommand;
import com.challenge.core.domain.user.score.Level;
import com.challenge.core.domain.user.score.Score;
import com.challenge.core.domain.user.score.UserScore;
import com.challenge.core.domain.user.score.UserScoreCreator;
import com.challenge.core.domain.user.session.SessionId;
import com.challenge.core.domain.user.session.UserSession;
import com.challenge.core.domain.user.session.UserSessionFinder;
import com.challenge.core.domain.user.session.UserSessionValidator;
import com.challenge.core.domain.user.session.exception.SessionDoesNotExistException;
import com.challenge.shared.application.service.DefaultCommandExecutionHandler;
import com.challenge.shared.cqs.CommandExecutionHandler;

public class RegisterUserScoreCommandHandler
    extends DefaultCommandExecutionHandler<RegisterUserScoreCommand>
    implements CommandExecutionHandler<RegisterUserScoreCommand> {

  private final UserScoreCreator userScoreCreator;
  private final UserSessionFinder userSessionFinder;
  private final UserSessionValidator userSessionValidator;

  public RegisterUserScoreCommandHandler(
      UserScoreCreator userScoreCreator,
      UserSessionFinder userSessionFinder,
      UserSessionValidator userSessionValidator) {
    this.userScoreCreator = userScoreCreator;
    this.userSessionFinder = userSessionFinder;
    this.userSessionValidator = userSessionValidator;
  }

  @Override
  protected void internalExecute(RegisterUserScoreCommand command) {
    Level level = Level.with(command.level());
    Score score = Score.with(command.score());
    SessionId sessionId = SessionId.with(command.sessionKey());
    UserSession userSession = userSessionFinder.find(sessionId);
    if (userSession == null) {
      throw SessionDoesNotExistException.notValid(sessionId);
    }
    if (userSessionValidator.isValidSession(userSession)) {
      UserScore userScore = UserScore.with(userSession.userId(), score);
      userScoreCreator.addScore(level, userScore);
    }
  }
}
