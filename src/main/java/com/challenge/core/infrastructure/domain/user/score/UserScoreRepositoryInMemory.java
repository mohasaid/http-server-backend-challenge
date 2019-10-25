package com.challenge.core.infrastructure.domain.user.score;

import com.challenge.core.domain.user.UserId;
import com.challenge.core.domain.user.score.Level;
import com.challenge.core.domain.user.score.Score;
import com.challenge.core.domain.user.score.UserScore;
import com.challenge.core.domain.user.score.UserScoreRepository;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.stream.Collectors;

public class UserScoreRepositoryInMemory implements UserScoreRepository {

  private final Map<Level, Map<UserId, Score>> repository = new ConcurrentHashMap<>();

  @Override
  public void save(Level level, UserScore userScore) {
    Map<UserId, Score> scoresFromLevel = repository.get(level);
    if (scoresFromLevel == null) {
      Map<UserId, Score> scores = new ConcurrentHashMap<>();
      scores.put(userScore.userId(), userScore.score());
      repository.put(level, scores);
    } else {
      Score currentScore = scoresFromLevel.get(userScore.userId());
      if (currentScore != null) {
        if (currentScore.value() < userScore.score().value()) {
          scoresFromLevel.remove(userScore.userId());
          scoresFromLevel.put(userScore.userId(), userScore.score());
          repository.put(level, scoresFromLevel);
        }
      } else {
        Map<UserId, Score> scoreMap = repository.get(level);
        scoreMap.put(userScore.userId(), userScore.score());
        repository.put(level, scoreMap);
      }
    }
  }

  @Override
  public Set<UserScore> getHighScores(Level level, Long maxScores) {
    if (repository.get(level) != null) {
      return repository.get(level).entrySet().stream()
          .map(entry -> UserScore.with(entry.getKey(), entry.getValue()))
          .limit(maxScores)
          .collect(Collectors.toCollection(ConcurrentSkipListSet::new));
    }
    return new ConcurrentSkipListSet<>();
  }
}
