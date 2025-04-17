package com.charniuk.bankcardmanagementsystem.exceptions;

public class CardLimitAlreadyExistsException extends RuntimeException {

  public CardLimitAlreadyExistsException(String message) {
    super(message);
  }

}
