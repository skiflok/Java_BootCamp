package edu.school21.exceptions;

public class MovementIsImpossibleException extends RuntimeException {

  public MovementIsImpossibleException() {
    super();
  }

  public MovementIsImpossibleException(String message) {
    super(message);
  }

  public MovementIsImpossibleException(String message, Exception e) {
    super(message, e);
  }
}
