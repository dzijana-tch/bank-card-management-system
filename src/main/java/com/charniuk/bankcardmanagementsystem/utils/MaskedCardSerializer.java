package com.charniuk.bankcardmanagementsystem.utils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;

public class MaskedCardSerializer extends JsonSerializer<String> {

  @Override
  public void serialize(String cardNumber, JsonGenerator jsonGenerator,
      SerializerProvider serializerProvider) throws IOException {

    String maskedNumber = "************" + cardNumber.substring(cardNumber.length() - 4);
    jsonGenerator.writeString(maskedNumber);
  }
}
