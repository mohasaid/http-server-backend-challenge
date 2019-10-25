package com.challenge.core.application.api.domain.user.login.service;

import com.challenge.core.application.api.domain.user.login.use_case.UserLoginQuery;
import com.challenge.core.application.api.domain.user.login.use_case.UserLoginQueryResponse;
import com.challenge.core.domain.user.UserId;
import com.challenge.core.domain.user.session.UserSession;
import com.challenge.core.domain.user.session.UserSessionCreator;
import com.challenge.shared.application.service.DefaultQueryExecutionHandler;
import com.challenge.shared.cqs.QueryExecutionHandler;

public class UserLoginQueryHandler
    extends DefaultQueryExecutionHandler<UserLoginQuery, UserLoginQueryResponse>
    implements QueryExecutionHandler<UserLoginQuery, UserLoginQueryResponse> {

  private final UserSessionCreator userSessionCreator;

  public UserLoginQueryHandler(UserSessionCreator userSessionCreator) {
    this.userSessionCreator = userSessionCreator;
  }

  @Override
  protected UserLoginQueryResponse internalExecute(UserLoginQuery query) {
    UserId userId = UserId.with(query.userId());
    UserSession userSession = userSessionCreator.create(userId);
    return UserLoginQueryResponse.of(userSession);
  }
}
