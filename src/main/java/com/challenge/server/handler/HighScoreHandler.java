package com.challenge.server.handler;

import com.challenge.core.application.api.domain.user.high_score.use_case.HighScoreUserQuery;
import com.challenge.core.application.api.domain.user.high_score.use_case.HighScoreUserQueryResponse;
import com.challenge.core.domain.user.score.exception.LevelException;
import com.challenge.server.handler.exception.InvalidRequestMethod;
import com.challenge.shared.cqs.QueryExecutionHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;

public class HighScoreHandler implements GenericHandler {

  private final QueryExecutionHandler<HighScoreUserQuery, HighScoreUserQueryResponse>
      queryExecutionHandler;

  public HighScoreHandler(
      QueryExecutionHandler<HighScoreUserQuery, HighScoreUserQueryResponse> queryExecutionHandler) {
    this.queryExecutionHandler = queryExecutionHandler;
  }

  @Override
  public void handle(HttpExchange httpExchange) throws IOException {

    final String exchangePath = path(httpExchange);
    OutputStream os = httpExchange.getResponseBody();

    try {
      checkMethodAllowed(httpExchange.getRequestMethod());

      int level =
          Integer.parseInt(exchangePath.substring(1, exchangePath.indexOf("/highscorelist")));

      HighScoreUserQuery query = HighScoreUserQuery.with((long) level);

      HighScoreUserQueryResponse queryResponse = queryExecutionHandler.execute(query);

      String usersScores = queryResponse.userScores();

      httpExchange.sendResponseHeaders(200, usersScores.length());
      os.write(usersScores.getBytes());

    } catch (NumberFormatException nfe) {
      handleError(
          httpExchange,
          "Invalid level with path '" + exchangePath + "'",
          httpExchange.getResponseBody());
    } catch (InvalidRequestMethod irm) {
      handleMethodNotAllowed(httpExchange, irm.getMessage(), os);
    } catch (LevelException exception) {
      handleError(httpExchange, exception.getMessage(), os);
    } catch (Exception e) {
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
