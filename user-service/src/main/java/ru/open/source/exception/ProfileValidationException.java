package ru.open.source.exception;

public class ProfileValidationException extends RuntimeException {
  public ProfileValidationException(String message) {
    super(message);
  }
}
