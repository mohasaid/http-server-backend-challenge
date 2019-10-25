package com.challenge.core.domain.user.score;

import com.challenge.core.domain.user.UserId;

import java.util.Objects;

public class UserScore implements Comparable<UserScore> {

  private final UserId userId;
  private final Score score;

  public UserScore(UserId userId, Score score) {
    this.userId = userId;
    this.score = score;
  }

  public static UserScore with(UserId userId, Score score) {
    return new UserScore(userId, score);
  }

  public UserId userId() {
    return userId;
  }

  public Score score() {
    return score;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    UserScore userScore = (UserScore) o;
    return Objects.equals(userId, userScore.userId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(userId);
  }

  @Override
  public int compareTo(UserScore o) {
    return o.score.value().compareTo(this.score.value());
  }
}
