package com.example.todolist.util;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.Base64;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class JwtUltil {
  private final String secretKey;

  public JwtUltil(@Value("${jwt.secret:dev-test-jwt-secrets-placeholder}") String secretKey) {
    if (secretKey == null || secretKey.length() < 32) {
      throw new IllegalArgumentException("Secret key must be at least 32 characters long");
    }
    this.secretKey = secretKey;
  }

  public String createToken(UserDetails userDetails) {

    long currentTimeMillis = Instant.now().toEpochMilli();

    long validityInMilis = 3600000;
    long expiryTimeMillis = currentTimeMillis + validityInMilis;

    String payload = userDetails.getUsername() + "|" + expiryTimeMillis;

    String signature = generateHmacSha256(payload, secretKey);

    return Base64.getUrlEncoder()
        .encodeToString((payload + "|" + signature).getBytes(StandardCharsets.UTF_8));
  }

  public String getUsername(String token) throws IllegalArgumentException {
    String[] parts = decodeToken(token);
    validateToken(parts);
    return parts[0];
  }

  public boolean isExpired(String token) throws IllegalArgumentException {
    String[] parts = decodeToken(token);
    validateToken(parts);
    long expiryTimeMillis = Long.parseLong(parts[1]);
    return expiryTimeMillis < Instant.now().toEpochMilli();
  }

  private String[] decodeToken(String token) {
    try {
      String decoded = new String(Base64.getUrlDecoder().decode(token), StandardCharsets.UTF_8);
      return decoded.split("\\|");
    } catch (Exception e) {
      throw new IllegalArgumentException("Invalid token format");
    }
  }

  private void validateToken(String[] parts) {
    if (parts.length != 3) {
      throw new IllegalArgumentException("Invalid token structure");
    }
    String payload = parts[0] + "|" + parts[1]; // Reconstruct username|timestamp
    String providedSignature = parts[2];
    String expectedSignature = generateHmacSha256(payload, secretKey);
    if (!expectedSignature.equals(providedSignature)) {
      throw new IllegalArgumentException("Token signature is invalid");
    }
  }

  private String generateHmacSha256(String data, String key) {
    try {
      Mac mac = Mac.getInstance("HmacSHA256");
      SecretKeySpec secretKeySpec =
          new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
      mac.init(secretKeySpec);
      byte[] hash = mac.doFinal(data.getBytes(StandardCharsets.UTF_8));
      return Base64.getUrlEncoder().withoutPadding().encodeToString(hash);
    } catch (NoSuchAlgorithmException | InvalidKeyException e) {
      throw new RuntimeException("Error generating HMAC-SHA256", e);
    }
  }
}
