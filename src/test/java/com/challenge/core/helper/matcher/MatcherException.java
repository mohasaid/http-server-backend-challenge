package com.challenge.core.helper.matcher;

import static java.lang.String.format;
import static org.junit.Assert.fail;

public class MatcherException {
  public static <T extends Throwable> void assertThrows(Runnable runnable, Class<T> exception) {
    try {
      runnable.run();
    } catch (Throwable e) {
      if (exception.isAssignableFrom(e.getClass())) {
        return;
      }
      throw e;
    }

    fail(format("Expected a %s to be thrown", exception.getSimpleName()));
  }
}
