package com.medicine.course.Medicine.services;

import com.medicine.course.Medicine.entities.UserApi;
import com.medicine.course.Medicine.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class AuthenticationServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AuthenticationService authenticationService;

    private UserApi testUser;

    @BeforeEach
    public void setup() {
        testUser = new UserApi();
        testUser.setId(1L);
        testUser.setLogin("testUser");
        testUser.setPassword("password");
    }

    @Test
    public void testLoadUserByUsername_WhenUserExists_ShouldReturnUserDetails() {
        when(userRepository.findByLogin("testUser")).thenReturn(testUser);

        UserDetails userDetails = authenticationService.loadUserByUsername("testUser");

        assertEquals(testUser.getLogin(), userDetails.getUsername());
        assertEquals(testUser.getPassword(), userDetails.getPassword());
    }

    @Test
    public void testLoadUserByUsername_WhenUserDoesNotExist_ShouldThrowUsernameNotFoundException() {
        when(userRepository.findByLogin("notExistentUser")).thenReturn(null);

        try {
            authenticationService.loadUserByUsername("notExistentUser");
        } catch (UsernameNotFoundException ex) {
            assertEquals("User not found with username: notExistentUser", ex.getMessage());
        }
    }
}
