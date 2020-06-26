/**
 * 
 */
package com.blueoptima.ratelimiter.domain;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * UserApiKey entity
 * 
 * @author saurav.das
 *
 */

@Entity
@Table(name = "usersapikey")
public class UserApiKey {

	@Id
	@Column(name = "user_api_key")
	private String userApiKey;

	@Column(name = "user_name")
	private String userName;

	@Column(name = "api_name")
	private String apiName;

	@Column(name = "rate_limit")
	private int rateLimit;

	public String getUserApiKey() {
		return userApiKey;
	}

	public void setUserApiKey(String userApiKey) {
		this.userApiKey = userApiKey;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getApiName() {
		return apiName;
	}

	public void setApiName(String apiName) {
		this.apiName = apiName;
	}

	public int getRateLimit() {
		return rateLimit;
	}

	public void setRateLimit(int rateLimit) {
		this.rateLimit = rateLimit;
	}

	@Override
	public int hashCode() {
		return Objects.hash(apiName, rateLimit, userName, userApiKey);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof UserApiKey)) {
			return false;
		}
		UserApiKey other = (UserApiKey) obj;
		if (apiName == null) {
			if (other.apiName != null) {
				return false;
			}
		} else if (!apiName.equals(other.apiName)) {
			return false;
		}
		if (rateLimit != other.rateLimit) {
			return false;
		}
		if (userApiKey == null) {
			if (other.userApiKey != null) {
				return false;
			}
		} else if (!userApiKey.equals(other.userApiKey)) {
			return false;
		}
		if (userName == null) {
			if (other.userName != null) {
				return false;
			}
		} else if (!userName.equals(other.userName)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "UserApiKey [userApiKey=" + userApiKey + ", userName=" + userName + ", apiName=" + apiName
				+ ", rateLimit=" + rateLimit + "]";
	}

}
