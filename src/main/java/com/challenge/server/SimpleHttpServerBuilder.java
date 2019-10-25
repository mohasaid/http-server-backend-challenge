package com.challenge.server;

import com.challenge.server.uri.URIValidator;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SimpleHttpServerBuilder {

  private static Map<URIValidator, HttpHandler> handlers;

  public static SimpleHttpServerBuilder aSimpleHttpServer() {
    handlers = new ConcurrentHashMap<>();
    return new SimpleHttpServerBuilder();
  }

  public SimpleHttpServerBuilder withHandler(URIValidator validator, HttpHandler handler) {
    handlers.put(validator, handler);
    return this;
  }

  public SimpleHttpServer build() throws IOException {
    SimpleHttpServer server = new SimpleHttpServer();
    server.createContext(handlers);
    server.addExecutor();
    server.addShutdownHook();
    return server;
  }
}
