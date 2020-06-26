package com.blueoptima.ratelimiter.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blueoptima.ratelimiter.domain.User;

public interface UserRepository extends JpaRepository<User, String> {

}
