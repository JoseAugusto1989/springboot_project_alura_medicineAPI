package com.medicine.course.Medicine.services;

import com.medicine.course.Medicine.models.request.Address;
import com.medicine.course.Medicine.models.request.UserDto;
import com.medicine.course.Medicine.entities.UserApi;
import com.medicine.course.Medicine.exception.ObjectNotFoundException;
import com.medicine.course.Medicine.repositories.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncryptionService passwordEncrypt;
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    public UserApi registerUser(@RequestBody @Valid UserDto userDto) {
        try {
            UserApi user = new UserApi();
            user.setName(userDto.getName());
            user.setLogin(userDto.getLogin());
            user.setEmail(userDto.getEmail());
            user.setCpf(userDto.getCpf());
            user.setActive(true);

            String encode = passwordEncrypt.getPasswordEncoder(userDto.getPassword());
            user.setPassword(encode);

            Address address = new Address();
            address.setCity(userDto.getAddress().getCity());
            address.setDistrict(userDto.getAddress().getDistrict());
            address.setUf(userDto.getAddress().getUf());
            address.setNumber(userDto.getAddress().getNumber());
            address.setPostalCode(userDto.getAddress().getPostalCode());
            address.setComplement(userDto.getAddress().getComplement());

            user.setAddress(address);
            user = userRepository.save(user);
            return user;

        } catch (ObjectNotFoundException e) {
            logger.error("Error saving user: {}", e.getMessage());
            throw e;
        }
    }

}
