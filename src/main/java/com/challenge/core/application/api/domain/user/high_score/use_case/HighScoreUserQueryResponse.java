package com.challenge.core.application.api.domain.user.high_score.use_case;

import com.challenge.core.domain.user.score.UserScore;

import java.util.Set;

public class HighScoreUserQueryResponse {

  private final String userScores;

  public HighScoreUserQueryResponse(String userScores) {
    this.userScores = userScores;
  }

  public static HighScoreUserQueryResponse of(Set<UserScore> userScores) {
    StringBuffer stringBuffer = new StringBuffer();
    for (UserScore userScore : userScores) {
      stringBuffer.append(userScore.userId().id());
      stringBuffer.append("=");
      stringBuffer.append(userScore.score().value());
      stringBuffer.append(",");
    }
    String bufferAsString = stringBuffer.toString();
    if (bufferAsString.length() > 0) {
      bufferAsString = bufferAsString.substring(0, bufferAsString.lastIndexOf(','));
    }
    return new HighScoreUserQueryResponse(bufferAsString);
  }

  public String userScores() {
    return userScores;
  }
}
