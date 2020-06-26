package com.blueoptima.ratelimiter.api;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.blueoptima.ratelimiter.domain.Developer;
import com.blueoptima.ratelimiter.domain.Organization;
import com.blueoptima.ratelimiter.service.DeveloperDataService;
import com.blueoptima.ratelimiter.service.OrganizationDataService;

/**
 * {@code RestController} to expose the two api end points developers and
 * organizations
 * 
 * @author saurav.das
 *
 */
@RestController("/blueoptima")
public class RateLimiterController {

	/*
	 * @Autowired TokenRateLimiterInterceptor tokenRateLimiterSerice;
	 */
	private final static Logger LOGGER = LoggerFactory.getLogger(RateLimiterController.class);

	@Autowired
	private DeveloperDataService devDataService;

	@Autowired
	private OrganizationDataService orgDataService;

	/**
	 * @return ReponseEntity
	 * 
	 *         handle GET requests to /developers and returns a list of developer
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/developers")
	public ResponseEntity<List<Developer>> getDevelopers() {
		LOGGER.info("entering getDevelopers, allowed to enter");
		return ResponseEntity.ok(devDataService.getAllDevelopers());
	}

	/**
	 * @return ResponseEntity
	 * 
	 *         * Method to handle GET requests to /organizations and returns a list
	 *         of organizations names with
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/organizations")
	public ResponseEntity<List<Organization>> getOrganizations() {
		LOGGER.info("entering getOrganizations, allowed to enter");
		return ResponseEntity.ok(orgDataService.getAllOrganizations());
	}

}
