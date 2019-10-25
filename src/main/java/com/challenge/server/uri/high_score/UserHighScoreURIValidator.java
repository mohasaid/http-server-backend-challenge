package com.challenge.server.uri.high_score;

import com.challenge.server.uri.URIValidator;

import java.net.URI;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserHighScoreURIValidator implements URIValidator {

  private static final Pattern PATTERN =
      Pattern.compile("/\\d+/highscorelist", Pattern.CASE_INSENSITIVE);

  @Override
  public boolean isValid(URI uri) {
    Matcher matcher = PATTERN.matcher(uri.toString());
    return matcher.matches();
  }
}
