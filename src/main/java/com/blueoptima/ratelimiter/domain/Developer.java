/**
 * 
 */
package com.blueoptima.ratelimiter.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Developer entity
 * 
 * @author saurav.das
 *
 */
@Entity
@Table(name = "developers")
public class Developer {

	@Id
	@GeneratedValue
	private Long id;

	@Column(name = "dev_name")
	private String devName;

	@Column(name = "experience")
	private int experience;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDevName() {
		return devName;
	}

	public void setDevName(String devName) {
		this.devName = devName;
	}

	public int getExperience() {
		return experience;
	}

	public void setExperience(Integer experience) {
		this.experience = experience;
	}

	@Override
	public String toString() {
		return "Developer [id=" + id + ", devName=" + devName + ", experience=" + experience + "]";
	}

}
