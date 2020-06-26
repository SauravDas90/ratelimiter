package com.blueoptima.ratelimiter;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.blueoptima.ratelimiter.domain.Developer;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RatelimiterApplicationTests {

	@Test
	public void contextLoads() {
	}

	public List<Developer> testDeveloperData() {
		Developer devNewUser = new Developer();
		devNewUser.setDevName("Rahul");
		devNewUser.setId(1L);

		Developer devOldUser = new Developer();
		devOldUser.setDevName("Ram");
		devOldUser.setId(100L);

		return Stream.of(devNewUser, devOldUser).collect(Collectors.toList());

	}

}
