package com.example.todolist.unittest.config;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.example.todolist.config.SpingDocConfiguration;
import com.example.todolist.config.StaticFileConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class ConfigTest {
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
