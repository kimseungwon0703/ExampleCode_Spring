package com.example.demo.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<userEntity, Long> {
	Optional<userEntity> findByusername(String username);
}
