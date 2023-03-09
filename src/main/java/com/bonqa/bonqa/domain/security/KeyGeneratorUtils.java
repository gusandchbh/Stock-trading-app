package com.bonqa.bonqa.domain.security;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import org.springframework.stereotype.Component;

/**
 * @author Joe Grandja
 * @since 0.1.0
 */
@Component
final class KeyGeneratorUtils {

  private KeyGeneratorUtils() {
  }

  static KeyPair generateRsaKey() {
    KeyPair keyPair;
    try {
      KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
      keyPairGenerator.initialize(2048);
      keyPair = keyPairGenerator.generateKeyPair();
    } catch (Exception ex) {
      throw new IllegalStateException(ex);
    }
    return keyPair;
  }
}
