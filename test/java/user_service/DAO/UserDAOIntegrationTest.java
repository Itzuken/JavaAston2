package user_service.DAO;

import org.junit.jupiter.api.*;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import user_service.models.User;
import user_service.utils.HibernateSessionFactoryUtil;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
@Disabled
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserDAOIntegrationTest {

    @Container
    private static final PostgreSQLContainer<?> postgresqlContainer =
            new PostgreSQLContainer<>("postgres:15-alpine")
                    .withDatabaseName("testdb")
                    .withUsername("test")
                    .withPassword("test");

    private static UserDAO userDAO;

    @BeforeAll
    static void setUp() {
        System.setProperty("hibernate.connection.url", postgresqlContainer.getJdbcUrl());
        System.setProperty("hibernate.connection.username", postgresqlContainer.getUsername());
        System.setProperty("hibernate.connection.password", postgresqlContainer.getPassword());

        userDAO = new UserDAO();
    }

    @AfterAll
    static void tearDown() {
        HibernateSessionFactoryUtil.shutdown();
    }

    @Test
    @Order(1)
    void testSaveUser() {
        User user = new User("User1", "user1.test@test.com", 30, LocalDateTime.now());
        userDAO.save(user);
        assertTrue(user.getId() > 0);
    }
    @Test
    @Order(2)
    void testReadUser() {
        // Given
        User user = new User("User2", "user2.test@test.com", 25, LocalDateTime.now());
        userDAO.save(user);
        int userId = user.getId();

        // When
        User foundUser = userDAO.read(userId);

        // Then
        assertNotNull(foundUser, "User should be found");
        assertEquals("User2", foundUser.getName());
        assertEquals("user2.test@test.com", foundUser.getEmail());
        assertEquals(25, foundUser.getAge());
    }

    @Test
    @Order(3)
    void testUpdateUser() {
        // Given
        User user = new User("User", "user@test.com", 15, LocalDateTime.now());
        userDAO.save(user);
        int userId = user.getId();

        // When
        user.setName("UpdatedUser");
        user.setEmail("updated.user@test.com");
        user.setAge(25);
        userDAO.update(user);

        // Then
        User updatedUser = userDAO.read(userId);
        assertEquals("UpdatedUser", updatedUser.getName());
        assertEquals("updated.user@test.com", updatedUser.getEmail());
        assertEquals(25, updatedUser.getAge());
    }

    @Test
    @Order(4)
    void testDeleteUser() {
        // Given
        User user = new User("DeleteUser", "delete.user@test.com", 20, LocalDateTime.now());
        userDAO.save(user);
        int userId = user.getId();

        // When
        userDAO.delete(user);

        // Then
        User deletedUser = userDAO.read(userId);
        assertNull(deletedUser, "User should be deleted");
    }

    @Test
    @Order(5)
    void testReadNonExistentUser() {
        // When
        User user = userDAO.read(-1);

        // Then
        assertNull(user, "Non-existent user should return null");
    }
}