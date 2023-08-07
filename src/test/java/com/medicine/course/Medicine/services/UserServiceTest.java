package com.medicine.course.Medicine.services;

import com.medicine.course.Medicine.entities.UserApi;
import com.medicine.course.Medicine.exception.ObjectNotFoundException;
import com.medicine.course.Medicine.models.request.UserDto;
import com.medicine.course.Medicine.repositories.UserRepository;
import com.medicine.course.Medicine.template.UserApiTemplate;
import com.medicine.course.Medicine.template.UserDtoTemplate;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncryptionService passwordEncrypt;

    @InjectMocks
    private UserService userService;

    @Test
    public void testRegisterUser_shouldSaveUser() {
        UserDto userDto = UserDtoTemplate.buildUserDto();
        String encodedPassword = "encodedPassword";
        UserApi savedUser = UserApiTemplate.buildUserApi();

        when(passwordEncrypt.getPasswordEncoder(userDto.getPassword())).thenReturn(encodedPassword);
        when(userRepository.save(any(UserApi.class))).thenReturn(savedUser);

        UserApi result = userService.registerUser(userDto);

        assertNotNull(result);
        assertEquals(savedUser, result);
//
        verify(passwordEncrypt, times(1)).getPasswordEncoder(userDto.getPassword());
        verify(userRepository, times(1)).save(any(UserApi.class));
    }

    @Test
    public void testRegisterUser_whenObjectNotFoundException_shouldThrowException() {
        UserDto userDto = UserDtoTemplate.buildUserDto();

        when(passwordEncrypt.getPasswordEncoder(userDto.getPassword())).thenReturn("encodedPassword");
        when(userRepository.save(any(UserApi.class))).thenThrow(new ObjectNotFoundException("Error saving user", null));

        assertThrows(ObjectNotFoundException.class, () -> userService.registerUser(userDto));

        verify(passwordEncrypt, times(1)).getPasswordEncoder(userDto.getPassword());
        verify(userRepository, times(1)).save(any(UserApi.class));
    }
}
