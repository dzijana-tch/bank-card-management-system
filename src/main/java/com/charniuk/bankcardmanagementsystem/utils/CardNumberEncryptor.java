package com.charniuk.bankcardmanagementsystem.utils;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

@Converter
public class CardNumberEncryptor implements AttributeConverter<String, String> {

  private static final String ALGORITHM = "AES/ECB/PKCS5Padding";
  private static final String SECRET = "your-encryption-secret";

  @Override
  public String convertToDatabaseColumn(String attribute) {
    if (attribute == null) return null;

    try {
      Cipher cipher = Cipher.getInstance(ALGORITHM);
      cipher.init(Cipher.ENCRYPT_MODE, generateSecretKey());
      return Base64.getEncoder().encodeToString(cipher.doFinal(attribute.getBytes()));
    } catch (Exception e) {
      throw new RuntimeException("Error encrypting card number", e);
    }
  }

  @Override
  public String convertToEntityAttribute(String dbData) {
    if (dbData == null) return null;

    try {
      Cipher cipher = Cipher.getInstance(ALGORITHM);
      cipher.init(Cipher.DECRYPT_MODE, generateSecretKey());
      return new String(cipher.doFinal(Base64.getDecoder().decode(dbData)));
    } catch (Exception e) {
      throw new RuntimeException("Error decrypting card number", e);
    }
  }

  private SecretKey generateSecretKey() {
    return new SecretKeySpec(SECRET.getBytes(), "AES");
  }
}
