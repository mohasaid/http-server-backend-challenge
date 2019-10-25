package com.challenge.server.handler;

import com.challenge.server.uri.URIValidator;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.net.URI;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class HttpHandlerFactory implements HttpHandler {

  private Map<URIValidator, HttpHandler> map;

  public HttpHandlerFactory(Map<URIValidator, HttpHandler> handlers) {
    map = new ConcurrentHashMap<>(handlers);
  }

  @Override
  public void handle(HttpExchange httpExchange) throws IOException {
    URI currentUri = httpExchange.getRequestURI();

    HttpHandler handler = null;
    boolean matches = false;
    for (Map.Entry<URIValidator, HttpHandler> entry : map.entrySet()) {
      if (entry.getKey().isValid(currentUri) && !matches) {
        handler = entry.getValue();
        matches = true;
      }
    }

    if (handler != null) {
      handler.handle(httpExchange);
    } else {
      System.out.println(String.format("no handler for this uri <%s>", currentUri.toString()));
      httpExchange.getResponseBody().close();
    }
  }
}
