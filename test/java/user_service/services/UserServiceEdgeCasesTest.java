package user_service.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import user_service.DAO.UserDAO;
import user_service.models.User;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceEdgeCasesTest {

    @Mock
    private UserDAO userDAO;

    @InjectMocks
    private UserService userService;

    @Test
    void testSaveUserWithNullUser() {
        // When & Then
        assertThrows(NullPointerException.class, () -> {
            userService.saveUser(null);
        });
    }

    @Test
    void testUpdateUserWithNullUser() {
        // When & Then
        assertThrows(NullPointerException.class, () -> {
            userService.updateUser(null);
        });
    }

    @Test
    void testDeleteUserWithNullUser() {
        // When & Then
        assertThrows(NullPointerException.class, () -> {
            userService.deleteUser(null);
        });
    }

    @Test
    void testFindUserWithNegativeId() {
        // Given
        when(userDAO.read(-1)).thenReturn(null);

        // When
        User result = userService.findUser(-1);

        // Then
        assertNull(result); // Теперь будет работать
        verify(userDAO, times(1)).read(-1);
    }

    @Test
    void testFindUserWithZeroId() {
        // Given
        when(userDAO.read(0)).thenReturn(null);

        // When
        User result = userService.findUser(0);

        // Then
        assertNull(result); // Теперь будет работать
        verify(userDAO, times(1)).read(0);
    }
}