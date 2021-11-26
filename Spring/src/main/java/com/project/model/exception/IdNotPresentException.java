package com.project.model.exception;

public class IdNotPresentException extends RuntimeException{
  public IdNotPresentException(String message) {
    super(message);
  }
}
