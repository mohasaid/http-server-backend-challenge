package com.challenge.server;

import com.challenge.server.handler.HttpHandlerFactory;
import com.challenge.server.uri.URIValidator;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Map;
import java.util.concurrent.Executors;

public class SimpleHttpServer implements com.challenge.server.HttpServer {

  private static final int DEFAULT_SERVER_PORT = 8081;
  private static final int DEFAULT_SOCKET_BACKLOG = 0;
  private HttpServer httpServer;

  public SimpleHttpServer() throws IOException {
    this(DEFAULT_SERVER_PORT, DEFAULT_SOCKET_BACKLOG);
  }

  public SimpleHttpServer(final int port, final int backlog) throws IOException {
    this(HttpServer.create(new InetSocketAddress(port), backlog));
  }

  public SimpleHttpServer(HttpServer httpServer) {
    this.httpServer = httpServer;
  }

  @Override
  public void start() {
    httpServer.start();
  }

  @Override
  public void stop() {
    httpServer.stop(0);
  }

  @Override
  public void createContext(Map<URIValidator, HttpHandler> handlers) {
    httpServer.createContext("/", new HttpHandlerFactory(handlers));
  }

  @Override
  public void addExecutor() {
    httpServer.setExecutor(
        Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()));
  }

  @Override
  public void addShutdownHook() {
    Runtime.getRuntime().addShutdownHook(new Thread(this::stop));
  }
}
