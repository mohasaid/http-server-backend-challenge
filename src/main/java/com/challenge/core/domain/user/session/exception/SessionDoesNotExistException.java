package com.challenge.core.domain.user.session.exception;

import com.challenge.core.domain.user.session.SessionId;

public class SessionDoesNotExistException extends RuntimeException {

    private SessionDoesNotExistException(String message) {
        super(message);
    }

    public static SessionDoesNotExistException notValid(SessionId sessionKey) {
        return new SessionDoesNotExistException(
                String.format("The session key <%s> does not exist", sessionKey.value()));
    }
}
