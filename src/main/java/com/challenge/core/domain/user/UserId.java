package com.challenge.core.domain.user;

import com.challenge.core.domain.user.exception.UserIdException;

import java.util.Objects;

public class UserId {

  private final Long uid;

  public UserId() {
    this.uid = null;
  }

  public UserId(Long uid) {
    guard(uid);
    this.uid = uid;
  }

  public static UserId from(String id) {
    try {
      Long parsedId = Long.parseLong(id);
      return new UserId(parsedId);
    } catch (NumberFormatException e) {
      throw UserIdException.notValid(id);
    }
  }

  public static UserId with(Long id) {
    return new UserId(id);
  }

  public Long id() {
    return uid;
  }

  public Boolean isEmpty() {
    return null == uid;
  }

  @Override
  public String toString() {
    return String.valueOf(uid);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    UserId userId = (UserId) o;
    return Objects.equals(uid, userId.uid);
  }

  @Override
  public int hashCode() {
    return Objects.hash(uid);
  }

  private void guard(Long id) {
    if (null == id || id <= 0) {
      throw UserIdException.notValid(id);
    }
  }
}
