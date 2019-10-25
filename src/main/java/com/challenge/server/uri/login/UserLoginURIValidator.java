package com.challenge.server.uri.login;

import com.challenge.server.uri.URIValidator;

import java.net.URI;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserLoginURIValidator implements URIValidator {

  private static final Pattern PATTERN = Pattern.compile("/\\d+/login", Pattern.CASE_INSENSITIVE);

  @Override
  public boolean isValid(URI uri) {
    Matcher matcher = PATTERN.matcher(uri.toString());
    return matcher.matches();
  }
}
