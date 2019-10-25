package com.challenge.core.domain.user.session;

import com.challenge.core.domain.user.session.exception.SessionIdException;

import java.util.Objects;

public class SessionId {

  private final String value;

  public SessionId() {
    this.value = null;
  }

  public SessionId(String value) {
    guard(value);
    this.value = value;
  }

  public static SessionId with(String value) {
    return new SessionId(value);
  }

  public String value() {
    return value;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    SessionId that = (SessionId) o;
    return Objects.equals(value, that.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(value);
  }

  private void guard(String value) {
    if (!isAlphanumeric(value)) {
      throw SessionIdException.notValid(value);
    }
  }

  private boolean isAlphanumeric(String session) {
    return session.matches("[A-Za-z0-9]+");
  }
}
