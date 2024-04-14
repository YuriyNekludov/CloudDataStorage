package edu.spring.clouddatastorage;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.condition.DisabledIfSystemProperty;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;

@SpringBootTest
@ActiveProfiles("test")
@DisabledIfSystemProperty(named = "skipTestContainers", matches = "true")
public abstract class CloudDataStorageApplicationTest {

    private static final MySQLContainer<?> container = new MySQLContainer<>("mysql");

    @BeforeAll
    static void runContainer() {
        container.start();
    }

    @DynamicPropertySource
    static void mysqlProperties(DynamicPropertyRegistry registry) {
        registry. add("spring.datasource.url", container::getJdbcUrl);
    }
}
