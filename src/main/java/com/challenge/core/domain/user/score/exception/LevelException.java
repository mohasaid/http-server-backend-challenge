package com.challenge.core.domain.user.score.exception;

public class LevelException extends RuntimeException {

  private LevelException(String message) {
    super(message);
  }

  public static LevelException notValid(String id) {
    return new LevelException(String.format("The level <%s> is not valid", id));
  }

  public static LevelException notValid(Long id) {
    return notValid(String.valueOf(id));
  }
}
