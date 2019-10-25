package com.challenge.server.handler;

import com.challenge.core.application.api.domain.user.login.use_case.UserLoginQuery;
import com.challenge.core.application.api.domain.user.login.use_case.UserLoginQueryResponse;
import com.challenge.server.handler.exception.InvalidRequestMethod;
import com.challenge.shared.cqs.QueryExecutionHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;

public class UserLoginHandler implements GenericHandler {

  private final QueryExecutionHandler<UserLoginQuery, UserLoginQueryResponse> queryExecutionHandler;

  public UserLoginHandler(
      QueryExecutionHandler<UserLoginQuery, UserLoginQueryResponse> queryExecutionHandler) {
    this.queryExecutionHandler = queryExecutionHandler;
  }

  @Override
  public void handle(HttpExchange httpExchange) throws IOException {

    final String exchangePath = path(httpExchange);

    OutputStream os = httpExchange.getResponseBody();

    try {
      checkMethodAllowed(httpExchange.getRequestMethod());

      final int userId =
          Integer.parseInt(exchangePath.substring(1, exchangePath.indexOf("/login")));

      UserLoginQuery query = UserLoginQuery.with((long) userId);
      UserLoginQueryResponse queryResponse = queryExecutionHandler.execute(query);

      httpExchange.sendResponseHeaders(200, queryResponse.sessionId().length());
      os.write(queryResponse.sessionId().getBytes());

    } catch (NumberFormatException exception) {
      handleError(httpExchange, "Invalid userId with path '" + exchangePath + "'", os);
    } catch (InvalidRequestMethod irm) {
      handleMethodNotAllowed(httpExchange, irm.getMessage(), os);
    } catch (Exception exception) {
      handleError(httpExchange, "Internal server error", os);
    } finally {
      os.close();
    }
  }

  @Override
  public void checkMethodAllowed(String method) {
    if (!method.equalsIgnoreCase("GET")) {
      throw new InvalidRequestMethod(method);
    }
  }
}
