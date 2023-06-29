package edu.school21.exceptions;

public class IllegalParametersException extends RuntimeException {

  public IllegalParametersException() {
    super();
  }

  public IllegalParametersException(String message) {
    super(message);
  }

  public IllegalParametersException(String message, Exception e) {
    super(message, e);
  }

}
