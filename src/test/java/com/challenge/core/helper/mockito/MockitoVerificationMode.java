package com.challenge.core.helper.mockito;

import org.mockito.verification.VerificationMode;

import static org.mockito.internal.verification.VerificationModeFactory.times;

public final class MockitoVerificationMode {
  public static VerificationMode once() {
    return times(1);
  }
  public static VerificationMode never(){
    return times(0);
  }
}
