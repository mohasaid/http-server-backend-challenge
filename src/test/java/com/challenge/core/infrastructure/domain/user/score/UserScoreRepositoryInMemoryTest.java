package com.challenge.core.infrastructure.domain.user.score;

import com.challenge.core.domain.user.UserId;
import com.challenge.core.domain.user.score.Level;
import com.challenge.core.domain.user.score.Score;
import com.challenge.core.domain.user.score.UserScore;
import com.challenge.core.domain.user.score.UserScoreRepository;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

public class UserScoreRepositoryInMemoryTest {

  private final UserScoreRepository userScoreRepository = new UserScoreRepositoryInMemory();

  private UserScore existentUserScore;
  private Level existentLevel;
  private Long maxScores;

  @Before
  public void init() {
    existentUserScore = UserScore.with(UserId.with(9L), Score.with(2L));
    existentLevel = Level.with(1L);
    maxScores = 10L;
  }

  @Test
  public void GivenUserScoreRequestWithLevel_WhenCallSave_ThenSavesIt() {
    userScoreRepository.save(existentLevel, existentUserScore);

    Set<UserScore> scores = userScoreRepository.getHighScores(existentLevel, maxScores);
    assertNotNull(scores);
    assertFalse(scores.isEmpty());
    assertEquals(1, scores.size());
    assertTrue(scores.contains(existentUserScore));
  }

  @Test
  public void GivenGetHighScoreRequest_WhenCallIt_ThenItReturnsLimitedNumberOfUserScores() {
    List<UserScore> scores = new LinkedList<>();
    for (int i = 1; i < 21; ++i) {
      scores.add(new UserScore(UserId.with((long) i), Score.with(i + 10L)));
    }

    scores.forEach(us -> userScoreRepository.save(existentLevel, us));

    Set<UserScore> userScores = userScoreRepository.getHighScores(existentLevel, maxScores);
    assertNotNull(userScores);
    assertFalse(userScores.isEmpty());
    assertEquals(maxScores, Long.valueOf(userScores.size()));
  }
}
