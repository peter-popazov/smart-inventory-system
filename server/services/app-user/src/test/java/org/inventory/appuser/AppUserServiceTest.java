package org.inventory.appuser;

import org.inventory.appuser.exception.UserNotFoundException;
import org.inventory.appuser.user.AppUserService;
import org.inventory.appuser.user.helpers.AppUserResponse;
import org.inventory.appuser.user.helpers.UpdateUserRequest;
import org.inventory.appuser.user.model.AppUser;
import org.inventory.appuser.user.model.Role;
import org.inventory.appuser.user.repos.AppUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AppUserServiceTest {

    @Mock
    private AppUserRepository repository;

    @InjectMocks
    private AppUserService appUserService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegisterUser() {
        Integer userId = 1;
        String email = "test@example.com";

        appUserService.registerUser(userId, email);

        verify(repository, times(1)).save(any(AppUser.class));
    }

    @Test
    void testUpdateUser_Success() {
        String userId = "1";
        UpdateUserRequest request = new UpdateUserRequest("John", "Doe");

        AppUser existingUser = AppUser.builder()
                .registeredUserId(1)
                .firstName("Old")
                .lastName("Name")
                .build();

        when(repository.findByRegisteredUserId(Integer.parseInt(userId)))
                .thenReturn(Optional.of(existingUser));

        appUserService.updateUser(request, userId);

        verify(repository, times(1)).save(existingUser);
        assertEquals("John", existingUser.getFirstName());
        assertEquals("Doe", existingUser.getLastName());
    }

    @Test
    void testUpdateUser_UserNotFound() {
        String userId = "1";
        UpdateUserRequest request = new UpdateUserRequest("John", "Doe");

        when(repository.findByRegisteredUserId(Integer.parseInt(userId)))
                .thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> appUserService.updateUser(request, userId));
    }

    @Test
    void testGetUserByEmail_Success() {
        String email = "test@example.com";

        AppUser appUser = AppUser.builder()
                .email(email)
                .firstName("John")
                .lastName("Doe")
                .build();

        Role role = Role.builder().name("ADMIN").build();

        when(repository.findByEmail(email)).thenReturn(Optional.of(appUser));
        when(repository.findUserRole(appUser.getUserId())).thenReturn(Optional.of(role));

        AppUserResponse response = appUserService.getUserByEmail(email);

        assertNotNull(response);
        assertEquals("John", response.firstName());
        assertEquals("ADMIN", response.role());
    }

    @Test
    void testGetUserByEmail_UserNotFound() {
        String email = "notfound@example.com";

        when(repository.findByEmail(email)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> appUserService.getUserByEmail(email));
    }

    @Test
    void testExistsByEmail_True() {
        String email = "exists@example.com";

        when(repository.findByEmail(email)).thenReturn(Optional.of(mock(AppUser.class)));
        Boolean exists = appUserService.existsByEmail(email);
        assertTrue(exists);
    }

    @Test
    void testExistsByEmail_False() {
        String email = "notexists@example.com";

        when(repository.findByEmail(email)).thenReturn(Optional.empty());
        Boolean exists = appUserService.existsByEmail(email);
        assertFalse(exists);
    }

    @Test
    void testDeleteUser_Success() {
        String email = "delete@example.com";
        String userId = "1";

        AppUser appUser = AppUser.builder()
                .registeredUserId(1)
                .email(email)
                .build();

        when(repository.findByEmail(email)).thenReturn(Optional.of(appUser));

        Boolean result = appUserService.deleteUser(email, userId);

        assertTrue(result);
        verify(repository, times(1)).delete(appUser);
    }

    @Test
    void testDeleteUser_InsufficientPermissions() {
        String email = "delete@example.com";
        String userId = "2";

        AppUser appUser = AppUser.builder()
                .registeredUserId(1)
                .email(email)
                .build();

        when(repository.findByEmail(email)).thenReturn(Optional.of(appUser));

        assertThrows(RuntimeException.class, () -> appUserService.deleteUser(email, userId));
    }
}
