package com.challenge.core.application.api.domain.user.score.use_case;

import com.challenge.shared.cqs.Command;

public class RegisterUserScoreCommand extends Command {

  private Long level;
  private Long score;
  private String sessionKey;

  public RegisterUserScoreCommand(Long level, String sessionKey, Long score) {
    this.level = level;
    this.score = score;
    this.sessionKey = sessionKey;
  }

  public static RegisterUserScoreCommand with(Long level, String sessionKey, Long score) {
    return new RegisterUserScoreCommand(level, sessionKey, score);
  }

  public Long level() {
    return level;
  }

  public Long score() {
    return score;
  }

  public String sessionKey() {
    return sessionKey;
  }
}
