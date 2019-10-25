package com.challenge.server.handler.exception;

public class InvalidRequestMethod extends RuntimeException {

  public InvalidRequestMethod(String method) {
    super(String.format("Invalid <%s> method", method));
  }
}
