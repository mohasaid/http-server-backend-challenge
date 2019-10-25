package com.challenge.core.domain.user.score;

import java.util.Set;

public interface UserScoreRepository {

  void save(Level level, UserScore userScore);

  Set<UserScore> getHighScores(Level level, Long maxScores);
}
