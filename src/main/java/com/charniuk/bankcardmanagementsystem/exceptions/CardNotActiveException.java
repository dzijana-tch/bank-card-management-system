package com.charniuk.bankcardmanagementsystem.exceptions;

public class CardNotActiveException extends RuntimeException {

  public CardNotActiveException(String message) {
    super(message);
  }

}