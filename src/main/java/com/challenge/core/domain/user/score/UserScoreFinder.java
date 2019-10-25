package com.challenge.core.domain.user.score;

import java.util.Set;

public class UserScoreFinder {

  private static final Long MAX_LIMIT = 15L;
  private final UserScoreRepository scoreRepository;

  public UserScoreFinder(UserScoreRepository scoreRepository) {
    this.scoreRepository = scoreRepository;
  }

  public Set<UserScore> findAll(Level level) {
    return findAllLimited(level, MAX_LIMIT);
  }

  private Set<UserScore> findAllLimited(Level level, Long maxScores) {
    return scoreRepository.getHighScores(level, maxScores);
  }
}
