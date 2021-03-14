package com.capestart.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.capestart.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

	public User findByUsername(String username);
}
