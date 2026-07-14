package com.ums.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ums.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {

	boolean existsByEmail(String email);

	Optional<UserEntity> findByEmail(String email);

	Optional<UserEntity> findByVerificationToken(String token);

	Optional<UserEntity> findByResetToken(String token);
}
