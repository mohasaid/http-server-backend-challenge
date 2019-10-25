package com.challenge.shared.application.service;

import com.challenge.shared.cqs.Command;
import com.challenge.shared.cqs.CommandExecutionHandler;

public class DefaultCommandExecutionHandler<T extends Command>
    implements CommandExecutionHandler<T> {

  public DefaultCommandExecutionHandler() {}

  protected void internalExecute(T command) {
    throw new UnsupportedOperationException("Not implemented!");
  }

  @Override
  public void execute(T command) {
    internalExecute(command);
  }
}
