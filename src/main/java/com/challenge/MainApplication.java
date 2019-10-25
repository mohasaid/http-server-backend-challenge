package com.challenge;

import com.challenge.core.application.api.domain.user.high_score.service.HighScoreUserQueryHandler;
import com.challenge.core.application.api.domain.user.high_score.use_case.HighScoreUserQuery;
import com.challenge.core.application.api.domain.user.high_score.use_case.HighScoreUserQueryResponse;
import com.challenge.core.application.api.domain.user.login.service.UserLoginQueryHandler;
import com.challenge.core.application.api.domain.user.login.use_case.UserLoginQuery;
import com.challenge.core.application.api.domain.user.login.use_case.UserLoginQueryResponse;
import com.challenge.core.application.api.domain.user.score.service.RegisterUserScoreCommandHandler;
import com.challenge.core.application.api.domain.user.score.use_case.RegisterUserScoreCommand;
import com.challenge.core.domain.user.score.UserScoreCreator;
import com.challenge.core.domain.user.score.UserScoreFinder;
import com.challenge.core.domain.user.score.UserScoreRepository;
import com.challenge.core.domain.user.session.*;
import com.challenge.core.infrastructure.domain.user.score.UserScoreRepositoryInMemory;
import com.challenge.core.infrastructure.domain.user.session.UserSessionRepositoryInMemory;
import com.challenge.server.HttpServer;
import com.challenge.server.SimpleHttpServerBuilder;
import com.challenge.server.handler.HighScoreHandler;
import com.challenge.server.handler.UserLoginHandler;
import com.challenge.server.handler.UserScoreHandler;
import com.challenge.server.uri.high_score.UserHighScoreURIValidator;
import com.challenge.server.uri.login.UserLoginURIValidator;
import com.challenge.server.uri.score.UserScoreURIValidator;
import com.challenge.shared.cqs.CommandExecutionHandler;
import com.challenge.shared.cqs.QueryExecutionHandler;

import java.io.IOException;

public class MainApplication {

  public static void main(String[] args) throws IOException {
    // - REPOSITORIES -
    UserSessionRepository userSessionRepository = new UserSessionRepositoryInMemory();
    UserScoreRepository userScoreRepository = new UserScoreRepositoryInMemory();

    // - SERVICES -
    // session
    UserSessionCreator userSessionCreator =
        new UserSessionCreator(new SessionIdCreator(), userSessionRepository);

    UserSessionFinder userSessionFinder = new UserSessionFinder(userSessionRepository);

    UserSessionValidator userSessionValidator = new UserSessionValidator();

    // score
    UserScoreCreator userScoreCreator = new UserScoreCreator(userScoreRepository);
    UserScoreFinder userScoreFinder = new UserScoreFinder(userScoreRepository);

    // - USE CASES -
    QueryExecutionHandler<UserLoginQuery, UserLoginQueryResponse> userLoginQueryHandler =
        new UserLoginQueryHandler(userSessionCreator);

    CommandExecutionHandler<RegisterUserScoreCommand> registerUserScoreCommandHandler =
        new RegisterUserScoreCommandHandler(
            userScoreCreator, userSessionFinder, userSessionValidator);

    QueryExecutionHandler<HighScoreUserQuery, HighScoreUserQueryResponse> highScoreQueryHandler =
        new HighScoreUserQueryHandler(userScoreFinder);

    // - SERVER -
    HttpServer simpleHttpServer =
        SimpleHttpServerBuilder.aSimpleHttpServer()
            .withHandler(new UserLoginURIValidator(), new UserLoginHandler(userLoginQueryHandler))
            .withHandler(
                new UserScoreURIValidator(), new UserScoreHandler(registerUserScoreCommandHandler))
            .withHandler(
                new UserHighScoreURIValidator(), new HighScoreHandler(highScoreQueryHandler))
            .build();

    simpleHttpServer.start();

    System.out.println("Server started.");
  }
}
