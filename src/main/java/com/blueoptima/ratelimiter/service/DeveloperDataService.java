package com.blueoptima.ratelimiter.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blueoptima.ratelimiter.domain.Developer;
import com.blueoptima.ratelimiter.repository.DeveloperRepository;

/**
 * DataService class for Developer with bean id as devDataService
 * 
 * @author saurav.das
 *
 */
@Service("devDataService")
public class DeveloperDataService {

	@Autowired
	private DeveloperRepository devRepository;

	/**
	 * @return List
	 * 
	 *         fetches the List of Developers
	 */
	public List<Developer> getAllDevelopers() {
		return devRepository.findAll();
	}

}
