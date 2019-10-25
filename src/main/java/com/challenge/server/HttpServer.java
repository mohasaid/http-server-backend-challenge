package com.challenge.server;

import com.challenge.server.uri.URIValidator;
import com.sun.net.httpserver.HttpHandler;

import java.util.Map;

public interface HttpServer {

  void start();

  void stop();

  void addExecutor();

  void addShutdownHook();

  void createContext(Map<URIValidator, HttpHandler> handlers);
}
