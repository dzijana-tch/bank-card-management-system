package com.charniuk.bankcardmanagementsystem.exceptions;

public class CardLimitExceededException extends RuntimeException {

  public CardLimitExceededException(String message) {
    super(message);
  }

}
