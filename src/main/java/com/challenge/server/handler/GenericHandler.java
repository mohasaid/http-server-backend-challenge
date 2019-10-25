package com.challenge.server.handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;

public interface GenericHandler extends HttpHandler {

  default String path(HttpExchange httpExchange) {
    return httpExchange.getRequestURI().getPath().toLowerCase();
  }

  default String query(HttpExchange httpExchange) {
    return httpExchange.getRequestURI().getQuery().toLowerCase();
  }

  default void handleError(HttpExchange httpExchange, String message, OutputStream os)
      throws IOException {
    httpExchange.sendResponseHeaders(500, message.length());
    os.write(message.getBytes());
  }

  default void handleMethodNotAllowed(HttpExchange httpExchange, String message, OutputStream os)
      throws IOException {
    httpExchange.sendResponseHeaders(405, message.length());
    os.write(message.getBytes());
  }

  void checkMethodAllowed(String method);
}
