package com.challenge.shared.application.service;

import com.challenge.shared.cqs.Query;
import com.challenge.shared.cqs.QueryException;
import com.challenge.shared.cqs.QueryExecutionHandler;

public class DefaultQueryExecutionHandler<T extends Query<S>, S>
    implements QueryExecutionHandler<T, S> {

  public DefaultQueryExecutionHandler() {}

  protected S internalExecute(T query) {
    throw new UnsupportedOperationException("Not implemented!");
  }

  @Override
  public S execute(T query) {
    try {
      S queryResult = internalExecute(query);
      return queryResult;
    } catch (Exception e) {
      throw new QueryException(e);
    }
  }
}
