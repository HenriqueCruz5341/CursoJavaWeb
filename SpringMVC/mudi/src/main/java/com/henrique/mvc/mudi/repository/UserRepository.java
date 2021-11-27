package com.henrique.mvc.mudi.repository;

import com.henrique.mvc.mudi.model.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
    User findByUsername(String username);
}
