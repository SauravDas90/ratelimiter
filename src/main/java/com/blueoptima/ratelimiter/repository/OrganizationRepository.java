package com.blueoptima.ratelimiter.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blueoptima.ratelimiter.domain.Organization;

/**
 * JPA repository for Organization
 * 
 * @author saurav.das
 *
 */
public interface OrganizationRepository extends JpaRepository<Organization, String> {

}
