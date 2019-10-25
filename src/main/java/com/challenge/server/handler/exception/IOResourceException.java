package com.challenge.server.handler.exception;

public class IOResourceException extends RuntimeException {

  public IOResourceException(String message) {
    super(String.format("There was an error closing resource, message => <%s> ", message));
  }
}
