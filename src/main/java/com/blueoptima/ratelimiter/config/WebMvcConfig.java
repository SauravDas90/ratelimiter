/**
 * 
 */
package com.blueoptima.ratelimiter.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.blueoptima.ratelimiter.service.TokenRateLimiterInterceptor;

/**
 * {@code Configuration} for adding the {@code HandlerInterceptorAdapter}
 * 
 * @author saurav.das
 * 
 */

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

	/**
	 * @return TokenRateLimiterInterceptor Defining as a Bean, so that Spring
	 *         manages its life cycle
	 */
	@Bean
	public TokenRateLimiterInterceptor tokenLimiterInterceptor() {
		return new TokenRateLimiterInterceptor();
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(tokenLimiterInterceptor());
	}

}
