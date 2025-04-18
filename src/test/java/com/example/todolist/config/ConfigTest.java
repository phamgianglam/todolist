package com.example.todolist.config;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class ConfigTest {
  @Autowired private PasswordEncoder passwordEncoder;

  @Autowired private SecurityFilterChain securityFilterChain;

  @Autowired private SpingDocConfiguration spingDocConfiguration;

  @Autowired StaticFileConfiguration staticFileConfiguration;

  @Test
  void contextLoads() {
    assertNotNull(passwordEncoder);
    assertNotNull(securityFilterChain);
    assertNotNull(spingDocConfiguration);
    assertNotNull(staticFileConfiguration);
  }
}
