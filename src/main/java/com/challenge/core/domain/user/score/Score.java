package com.challenge.core.domain.user.score;

import com.challenge.core.domain.user.score.exception.UserScoreException;

import java.util.Objects;

public class Score implements Comparable<Score> {

  private final Long value;

  public Score() {
    this.value = null;
  }

  public Score(Long value) {
    guard(value);
    this.value = value;
  }

  public static Score from(String value) {
    try {
      Long parsedId = Long.parseLong(value);
      return new Score(parsedId);
    } catch (NumberFormatException e) {
      throw UserScoreException.notValid(value);
    }
  }

  public static Score with(Long score) {
    return new Score(score);
  }

  public Long value() {
    return value;
  }

  public Boolean isEmpty() {
    return null == value;
  }

  @Override
  public String toString() {
    return String.valueOf(value);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Score score = (Score) o;
    return Objects.equals(value, score.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(value);
  }

  private void guard(Long score) {
    if (null == score || score < 0) {
      throw UserScoreException.notValid(score);
    }
  }

  @Override
  public int compareTo(Score o) {
    return this.value.compareTo(o.value);
  }
}
