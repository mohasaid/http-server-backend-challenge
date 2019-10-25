package com.challenge.shared.cqs;

public interface CommandExecutionHandler<T extends Command> {

  void execute(T command);
}
