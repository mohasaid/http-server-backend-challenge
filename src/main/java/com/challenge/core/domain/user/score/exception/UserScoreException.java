package com.challenge.core.domain.user.score.exception;

public class UserScoreException extends RuntimeException {

  private UserScoreException(String message) {
    super(message);
  }

  public static UserScoreException notValid(String id) {
    return new UserScoreException(String.format("The user score <%s> is not valid", id));
  }

  public static UserScoreException notValid(Long id) {
    return notValid(String.valueOf(id));
  }
}
