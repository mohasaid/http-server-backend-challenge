package com.challenge.server.handler;

import com.challenge.core.application.api.domain.user.score.use_case.RegisterUserScoreCommand;
import com.challenge.core.domain.user.score.exception.LevelException;
import com.challenge.core.domain.user.score.exception.UserScoreException;
import com.challenge.core.domain.user.session.exception.SessionDoesNotExistException;
import com.challenge.core.domain.user.session.exception.SessionExpiredException;
import com.challenge.core.domain.user.session.exception.SessionIdException;
import com.challenge.server.handler.exception.IOResourceException;
import com.challenge.server.handler.exception.InvalidRequestMethod;
import com.challenge.shared.cqs.CommandExecutionHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.*;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

public class UserScoreHandler implements GenericHandler {

  private final CommandExecutionHandler<RegisterUserScoreCommand> commandExecutionHandler;

  public UserScoreHandler(
      CommandExecutionHandler<RegisterUserScoreCommand> commandExecutionHandler) {
    this.commandExecutionHandler = commandExecutionHandler;
  }

  @Override
  public void handle(HttpExchange httpExchange) throws IOException {

    final String exchangePath = path(httpExchange);
    final String exchangeQuery = query(httpExchange);

    OutputStream os = httpExchange.getResponseBody();

    try {
      checkMethodAllowed(httpExchange.getRequestMethod());

      final int level = Integer.parseInt(exchangePath.substring(1, exchangePath.indexOf("/score")));
      final int score = score(httpExchange);
      final String sessionKey = sessionKey(exchangeQuery);

      RegisterUserScoreCommand registerUserScoreCommand =
          RegisterUserScoreCommand.with((long) level, sessionKey, (long) score);

      commandExecutionHandler.execute(registerUserScoreCommand);

      httpExchange.sendResponseHeaders(200, -1);

    } catch (NumberFormatException nfe) {
      handleError(httpExchange, "Invalid level with path '" + exchangePath + "'", os);
    } catch (InvalidRequestMethod irm) {
      handleMethodNotAllowed(httpExchange, irm.getMessage(), os);
    } catch (LevelException
        | UserScoreException
        | SessionIdException
        | SessionDoesNotExistException
        | SessionExpiredException exception) {
      handleError(httpExchange, exception.getMessage(), os);
    } catch (Exception ex) {
      handleError(httpExchange, "Internal server error", os);
    } finally {
      os.close();
    }
  }

  @Override
  public void checkMethodAllowed(String method) {
    if (!method.equalsIgnoreCase("POST")) {
      throw new InvalidRequestMethod(method);
    }
  }

  private String sessionKey(String query) {
    String sessionKey = null;
    String[] pairs = query.split("&");
    for (String pair : pairs) {
      int index = pair.indexOf("=");
      String field = URLDecoder.decode(pair.substring(0, index), StandardCharsets.UTF_8);
      if (field.equalsIgnoreCase("sessionkey")) {
        sessionKey = URLDecoder.decode(pair.substring(index + 1), StandardCharsets.UTF_8);
      }
    }
    return sessionKey;
  }

  private int score(HttpExchange httpExchange) {
    int score;
    InputStream is = httpExchange.getRequestBody();
    BufferedReader reader = new BufferedReader(new InputStreamReader(is));
    try {
      String value = reader.readLine();
      score = Integer.parseInt(value);
    } catch (NumberFormatException e) {
      throw UserScoreException.notValid(e.getMessage());
    } catch (IOException e) {
      throw new IOResourceException(e.getMessage());
    } finally {
      try {
        reader.close();
      } catch (IOException e) {
        throw new IOResourceException(e.getMessage());
      }
    }

    return score;
  }
}
