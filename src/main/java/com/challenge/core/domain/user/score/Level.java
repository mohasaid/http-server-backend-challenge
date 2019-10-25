package com.challenge.core.domain.user.score;

import com.challenge.core.domain.user.score.exception.LevelException;

import java.util.Objects;

public class Level {

  private final Long value;

  public Level(Long value) {
    guard(value);
    this.value = value;
  }

  public static Level with(Long value) {
    return new Level(value);
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
    Level level = (Level) o;
    return Objects.equals(value, level.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(value);
  }

  private void guard(Long id) {
    if (null == id || id < 0) {
      throw LevelException.notValid(id);
    }
  }
}
