package com.blueoptima.ratelimiter.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blueoptima.ratelimiter.domain.Organization;
import com.blueoptima.ratelimiter.repository.OrganizationRepository;

/**
 * DataService class for Organization with bean id as orgDataService
 * 
 * @author saurav.das
 *
 */
@Service("orgDataService")
public class OrganizationDataService {

	@Autowired
	private OrganizationRepository repository;

	/**
	 * @return List
	 * 
	 *         fetches the List of Organizations
	 */
	public List<Organization> getAllOrganizations() {
		return repository.findAll();
	}
}
