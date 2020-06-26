package com.blueoptima.ratelimiter.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blueoptima.ratelimiter.domain.Developer;

/**
 * JPA repository for Developer
 * 
 * @author saurav.das
 *
 */
public interface DeveloperRepository extends JpaRepository<Developer, String> {

}
