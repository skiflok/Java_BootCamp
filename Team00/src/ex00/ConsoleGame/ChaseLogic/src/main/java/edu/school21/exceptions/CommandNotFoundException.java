package edu.school21.exceptions;

public class CommandNotFoundException extends RuntimeException {

  public CommandNotFoundException() {
    super();
  }

  public CommandNotFoundException(String message) {
    super(message);
  }

  public CommandNotFoundException(String message, Exception e) {
    super(message, e);
  }
}
