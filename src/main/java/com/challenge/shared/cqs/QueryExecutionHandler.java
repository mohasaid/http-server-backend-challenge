package com.challenge.shared.cqs;

public interface QueryExecutionHandler<T extends Query<S>, S> {

  S execute(T query);
}
