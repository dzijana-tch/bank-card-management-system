package com.charniuk.bankcardmanagementsystem.exceptions;

public class CardNotFoundException extends RuntimeException {

  public CardNotFoundException(String message) {
    super(message);
  }
}
