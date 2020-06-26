package com.blueoptima.ratelimiter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.blueoptima.ratelimiter.domain.UserApiKey;

/**
 * JPA repository for UserApiKeyRepository
 * 
 * @author saurav.das
 *
 */
public interface UserApiKeyRepository extends JpaRepository<UserApiKey, String> {

}
