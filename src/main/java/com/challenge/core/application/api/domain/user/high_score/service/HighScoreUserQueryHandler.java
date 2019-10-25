package com.challenge.core.application.api.domain.user.high_score.service;

import com.challenge.core.application.api.domain.user.high_score.use_case.HighScoreUserQuery;
import com.challenge.core.application.api.domain.user.high_score.use_case.HighScoreUserQueryResponse;
import com.challenge.core.domain.user.score.Level;
import com.challenge.core.domain.user.score.UserScore;
import com.challenge.core.domain.user.score.UserScoreFinder;
import com.challenge.shared.application.service.DefaultQueryExecutionHandler;
import com.challenge.shared.cqs.QueryExecutionHandler;

import java.util.Set;

public class HighScoreUserQueryHandler
    extends DefaultQueryExecutionHandler<HighScoreUserQuery, HighScoreUserQueryResponse>
    implements QueryExecutionHandler<HighScoreUserQuery, HighScoreUserQueryResponse> {

  private final UserScoreFinder userScoreFinder;

  public HighScoreUserQueryHandler(UserScoreFinder userScoreFinder) {
    this.userScoreFinder = userScoreFinder;
  }

  @Override
  protected HighScoreUserQueryResponse internalExecute(HighScoreUserQuery query) {
    Level level = Level.with(query.level());
    Set<UserScore> scoresAtLevel = userScoreFinder.findAll(level);
    return HighScoreUserQueryResponse.of(scoresAtLevel);
  }
}
