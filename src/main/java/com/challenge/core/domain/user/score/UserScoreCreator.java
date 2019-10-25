package com.challenge.core.domain.user.score;

public class UserScoreCreator {

  private final UserScoreRepository repository;

  public UserScoreCreator(UserScoreRepository repository) {
    this.repository = repository;
  }

  public void addScore(Level level, UserScore userScore) {
    repository.save(level, userScore);
  }
}
