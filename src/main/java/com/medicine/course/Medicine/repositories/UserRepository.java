package com.medicine.course.Medicine.repositories;

import com.medicine.course.Medicine.entities.UserApi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserApi, Long> {

    UserDetails findByLogin(String login);
}
