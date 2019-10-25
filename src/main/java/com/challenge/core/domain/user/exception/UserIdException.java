package com.challenge.core.domain.user.exception;

public final class UserIdException extends RuntimeException {

  private UserIdException(String message) {
    super(message);
  }

  public static UserIdException notValid(String id) {
    return new UserIdException(String.format("The user id <%s> is not valid", id));
  }

  public static UserIdException notValid(Long id) {
    return notValid(String.valueOf(id));
  }
}
