package com.challenge.core.domain.user.session;

import java.util.UUID;

public class SessionIdCreator {

  public SessionId create() {
    return new SessionId(UUID.randomUUID().toString().replaceAll("-", ""));
  }
}
