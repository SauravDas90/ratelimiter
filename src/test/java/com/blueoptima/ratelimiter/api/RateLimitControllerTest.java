package com.blueoptima.ratelimiter.api;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.blueoptima.ratelimiter.RatelimiterApplicationTests;
import com.blueoptima.ratelimiter.service.DeveloperDataService;
import com.blueoptima.ratelimiter.service.OrganizationDataService;

//@WebMvcTest(RateLimiterController.class)
public class RateLimitControllerTest extends RatelimiterApplicationTests {

	private MockMvc mockMvc;

	@MockBean
	private DeveloperDataService devDataService;

	@MockBean
	private OrganizationDataService orgDataService;

	@InjectMocks
	private RateLimiterController rateLimitController;

	@Before
	public void setup() {

		MockitoAnnotations.initMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(rateLimitController).build();
		Mockito.when(devDataService.getAllDevelopers()).thenReturn(testDeveloperData());

	}

	/**
	 * calls the /developer endpoint
	 * 
	 * @throws Exception
	 */
	@Test
	public void testNormalUsage() throws Exception {

		MvcResult result = this.mockMvc.perform(get("/developers")).andExpect(status().isOk()).andReturn();
		String content = result.getResponse().getContentAsString();
		assertNotNull(content);

	}
}
