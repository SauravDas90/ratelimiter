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
 * Organization Entity
 * 
 * @author saurav.das
 *
 */
@Entity
@Table(name = "organizations")
public class Organization {

	@Id
	@GeneratedValue
	private Long id;

	@Column(name = "org_name")
	private String orgName;

	@Column(name = "org_address")
	private String orgAddress;

	@Column(name = "no_of_employees")
	private int noOfEmployees;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getOrgAddress() {
		return orgAddress;
	}

	public void setOrgAddress(String orgAddress) {
		this.orgAddress = orgAddress;
	}

	public int getNoOfEmployees() {
		return noOfEmployees;
	}

	public void setNoOfEmployees(int noOfEmployees) {
		this.noOfEmployees = noOfEmployees;
	}

	@Override
	public String toString() {
		return "Organization [orgName=" + orgName + ", orgAddress=" + orgAddress + ", noOfEmployees=" + noOfEmployees
				+ "]";
	}

}
