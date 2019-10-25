package com.challenge.core.application.api.domain.user.high_score.use_case;

import com.challenge.shared.cqs.Query;

public class HighScoreUserQuery extends Query<HighScoreUserQueryResponse> {

  private final Long level;

  public HighScoreUserQuery(Long level) {
    this.level = level;
  }

  public static HighScoreUserQuery with(Long level) {
    return new HighScoreUserQuery(level);
  }

  public Long level() {
    return level;
  }
}
