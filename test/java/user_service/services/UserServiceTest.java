package user_service.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import user_service.DAO.UserDAO;
import user_service.models.User;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserDAO userDAO;

    @InjectMocks
    private UserService userService;

    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = new User("Test Test", "test@test.com", 30, LocalDateTime.now());
    }

    @Test
    void testFindUser_Success() {
        // Given
        when(userDAO.read(1)).thenReturn(testUser);

        // When
        User foundUser = userService.findUser(1);

        // Then
        assertNotNull(foundUser);
        assertEquals("Test Test", foundUser.getName());
        assertEquals("test@test.com", foundUser.getEmail());
        assertEquals(30, foundUser.getAge());

        verify(userDAO, times(1)).read(1);
    }

    @Test
    void testFindUser_NotFound() {
        // Given
        when(userDAO.read(anyInt())).thenReturn(null);

        // When
        User foundUser = userService.findUser(999);

        // Then
        assertNull(foundUser);
        verify(userDAO, times(1)).read(999);
    }

    @Test
    void testSaveUser() {
        // Given
        doNothing().when(userDAO).save(any(User.class));

        // When
        userService.saveUser(testUser);

        // Then
        verify(userDAO, times(1)).save(testUser);
    }

    @Test
    void testUpdateUser() {
        // Given
        doNothing().when(userDAO).update(any(User.class));

        // When
        userService.updateUser(testUser);

        // Then
        verify(userDAO, times(1)).update(testUser);
    }

    @Test
    void testDeleteUser() {
        // Given
        doNothing().when(userDAO).delete(any(User.class));

        // When
        userService.deleteUser(testUser);

        // Then
        verify(userDAO, times(1)).delete(testUser);
    }

    @Test
    void testFindUserWithDifferentIds() {
        // Given
        User user1 = new User("Test1", "test1@test.com", 15, LocalDateTime.now());
        User user2 = new User("Test2", "test@test.com", 25, LocalDateTime.now());

        when(userDAO.read(1)).thenReturn(user1);
        when(userDAO.read(2)).thenReturn(user2);

        // When
        User foundUser1 = userService.findUser(1);
        User foundUser2 = userService.findUser(2);

        // Then
        assertEquals("Test1", foundUser1.getName());
        assertEquals("Test2", foundUser2.getName());

        verify(userDAO, times(1)).read(1);
        verify(userDAO, times(1)).read(2);
    }
}