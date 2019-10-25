package com.challenge.core.application.api.domain.user.login.use_case;

import com.challenge.shared.cqs.Query;

public class UserLoginQuery extends Query<UserLoginQueryResponse> {

  private final Long userId;

  public UserLoginQuery(Long userId) {
    this.userId = userId;
  }

  public static UserLoginQuery with(Long userId) {
    return new UserLoginQuery(userId);
  }

  public Long userId() {
    return userId;
  }
}
