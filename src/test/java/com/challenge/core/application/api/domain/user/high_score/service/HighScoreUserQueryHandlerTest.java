package com.challenge.core.application.api.domain.user.high_score.service;

import com.challenge.core.application.api.domain.user.high_score.use_case.HighScoreUserQuery;
import com.challenge.core.application.api.domain.user.high_score.use_case.HighScoreUserQueryResponse;
import com.challenge.core.domain.user.UserId;
import com.challenge.core.domain.user.score.Level;
import com.challenge.core.domain.user.score.Score;
import com.challenge.core.domain.user.score.UserScore;
import com.challenge.core.domain.user.score.UserScoreFinder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

import static com.challenge.core.helper.mockito.MockitoVerificationMode.once;
import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class HighScoreUserQueryHandlerTest {

  @InjectMocks private HighScoreUserQueryHandler highScoreUserQueryHandler;
  @Mock private UserScoreFinder userScoreFinder;

  private Level existingLevel;

  @Before
  public void init() {
    existingLevel = Level.with(1L);
  }

  @Test
  public void GivenExistingUserScores_WhenFindingHighScores_ThenReturnsExpectedList() {
    Set<UserScore> userScores = new ConcurrentSkipListSet<>();
    userScores.add(UserScore.with(UserId.with(1L), Score.with(100L)));
    userScores.add(UserScore.with(UserId.with(2L), Score.with(200L)));
    userScores.add(UserScore.with(UserId.with(3L), Score.with(300L)));
    when(userScoreFinder.findAll(existingLevel)).thenReturn(userScores);

    HighScoreUserQuery query = HighScoreUserQuery.with(existingLevel.value());
    HighScoreUserQueryResponse response = highScoreUserQueryHandler.internalExecute(query);

    String expectedResponse = "3=300,2=200,1=100";

    assertNotNull(response);
    assertTrue(response.userScores().length() > 0);
    assertEquals(expectedResponse, response.userScores());

    verify(userScoreFinder, once()).findAll(existingLevel);
  }

  @Test
  public void GivenNoUserScoresInLevel_WhenFindingHighScores_ThenReturnsEmptyString() {
    when(userScoreFinder.findAll(existingLevel)).thenReturn(new ConcurrentSkipListSet<>());

    HighScoreUserQuery query = HighScoreUserQuery.with(existingLevel.value());
    HighScoreUserQueryResponse response = highScoreUserQueryHandler.internalExecute(query);

    String expectedResponse = "";

    assertNotNull(response);
    assertEquals(0, response.userScores().length());
    assertEquals(expectedResponse, response.userScores());

    verify(userScoreFinder, once()).findAll(existingLevel);
  }
}
