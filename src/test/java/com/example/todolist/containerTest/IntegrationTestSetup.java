package com.example.todolist.containerTest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.time.Duration;
import liquibase.integration.spring.SpringLiquibase;
import org.junit.jupiter.api.BeforeAll;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.output.Slf4jLogConsumer;
import org.testcontainers.junit.jupiter.Container;

public class IntegrationTestSetup {

  private static final Logger logger = LoggerFactory.getLogger(IntegrationTestSetup.class);

  private static final String DB_NAME = "test_db";
  private static final String DB_USERNAME = "user";
  private static final String DB_PASSWORD = "password";

  @Container
  static PostgreSQLContainer<?> postgres =
      new PostgreSQLContainer<>("postgres:15")
          .withDatabaseName(DB_NAME)
          .withUsername(DB_USERNAME)
          .withPassword(DB_PASSWORD)
          .withExposedPorts(5432)
          .withStartupTimeout(Duration.ofSeconds(60))
          .withLogConsumer(new Slf4jLogConsumer(logger).withPrefix("postgres"));

  @DynamicPropertySource
  static void registerProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.url", postgres::getJdbcUrl);
    registry.add("spring.datasource.username", postgres::getUsername);
    registry.add("spring.datasource.password", postgres::getPassword);
  }

  @BeforeAll
  static void setupDatabase() throws Exception {
    postgres.start();
    waitUntilDatabaseIsReady();
    runLiquibaseMigrations();
  }

  private static void waitUntilDatabaseIsReady() throws Exception {
    logger.info("Waiting for database {} to be ready...", DB_NAME);
    long timeoutMs = 30_000; // 30 seconds
    long start = System.currentTimeMillis();
    while (true) {
      try (Connection conn =
          DriverManager.getConnection(
              postgres.getJdbcUrl(), postgres.getUsername(), postgres.getPassword())) {
        if (conn.isValid(2)) {
          logger.info("Database is ready!");
          return;
        }
      } catch (Exception ex) {
        if (System.currentTimeMillis() - start > timeoutMs) {
          throw new RuntimeException("Database was not ready after 30 seconds", ex);
        }
        Thread.sleep(500); // wait and retry
      }
    }
  }

  private static void runLiquibaseMigrations() throws Exception {
    logger.info("Running Liquibase migrations for {}", DB_NAME);
    SpringLiquibase liquibase = new SpringLiquibase();
    liquibase.setChangeLog("classpath:db/changelog/db.changelog-master.yaml");
    liquibase.setDataSource(
        new org.springframework.jdbc.datasource.DriverManagerDataSource(
            postgres.getJdbcUrl(), postgres.getUsername(), postgres.getPassword()));
    liquibase.afterPropertiesSet();
  }
}
