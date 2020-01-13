package com.estuate.esaip2_0.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

/**
 * <ul>
 * <li>Title: Organization</li>
 * <li>Description: The Organization entity</li>
 * <li>Created By: Radhika GopalRaj</li>
 * </ul>
 */
@Entity
@Component
@Table(name = "est_orgdetails")
public class Organization extends SuperEntity {
	/**
	 * The orgID
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "OrgID")
	private int id;
	/**
	 * The name
	 */
	@Column(name = "OrgName")
	private String name;
	/**
	 * The type
	 */
	@Column(name = "OrgType")
	private String orgType;
	/**
	 * The status
	 */
	@Column(name = "OrgStatus")
	private String orgStatus;
	/**
	 * The address
	 */
	@Column(name = "OrgAddress")
	private String orgAddress;
	/**
	 * The contactPerson
	 */
	@Column(name = "PointOfContact")
	private String contactPerson;
	/**
	 * The contactNumber
	 */
	@Column(name = "Contact_PhoneNumber")
	private String contactNumber;
	/**
	 * The contactEmailId
	 */
	@Column(name = "Contact_EmailId")
	private String contactEmailId;
	/**
	 * The billingAddress
	 */
	@Column(name = "BillingAddress")
	private String billingAddress;
	/**
	 * The billingRate
	 */
	@Column(name = "BillingRate")
	private int billingRate;
	/**
	 * The billingRate
	 */
	@Column(name = "BillingPeriod")
	private String billingPeriod;
	/**
	 * The billingRate
	 */
	@Column(name = "BillingType")
	private String billingType;
	/**
	 * The billingCurrency
	 */
	@Column(name = "BillingCurrency")
	private String billingCurrency;
	/**
	 * The totalLicense
	 */
	@Column(name = "NumberOfLicences")
	private int numberOfLicences;
	/**
	 * The licenseNumber
	 */
	@Column(name = "LicenseNumber")
	private String licenseNumber;
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the orgType
	 */
	public String getOrgType() {
		return orgType;
	}
	/**
	 * @param orgType the orgType to set
	 */
	public void setOrgType(String orgType) {
		this.orgType = orgType;
	}
	/**
	 * @return the orgStatus
	 */
	public String getOrgStatus() {
		return orgStatus;
	}
	/**
	 * @param orgStatus the orgStatus to set
	 */
	public void setOrgStatus(String orgStatus) {
		this.orgStatus = orgStatus;
	}
	/**
	 * @return the orgAddress
	 */
	public String getOrgAddress() {
		return orgAddress;
	}
	/**
	 * @param orgAddress the orgAddress to set
	 */
	public void setOrgAddress(String orgAddress) {
		this.orgAddress = orgAddress;
	}
	/**
	 * @return the contactPerson
	 */
	public String getContactPerson() {
		return contactPerson;
	}
	/**
	 * @param contactPerson the contactPerson to set
	 */
	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}
	/**
	 * @return the contactNumber
	 */
	public String getContactNumber() {
		return contactNumber;
	}
	/**
	 * @param contactNumber the contactNumber to set
	 */
	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}
	/**
	 * @return the contactEmailId
	 */
	public String getContactEmailId() {
		return contactEmailId;
	}
	/**
	 * @param contactEmailId the contactEmailId to set
	 */
	public void setContactEmailId(String contactEmailId) {
		this.contactEmailId = contactEmailId;
	}
	/**
	 * @return the billingAddress
	 */
	public String getBillingAddress() {
		return billingAddress;
	}
	/**
	 * @param billingAddress the billingAddress to set
	 */
	public void setBillingAddress(String billingAddress) {
		this.billingAddress = billingAddress;
	}
	/**
	 * @return the billingRate
	 */
	public int getBillingRate() {
		return billingRate;
	}
	/**
	 * @param billingRate the billingRate to set
	 */
	public void setBillingRate(int billingRate) {
		this.billingRate = billingRate;
	}
	/**
	 * @return the billingPeriod
	 */
	public String getBillingPeriod() {
		return billingPeriod;
	}
	/**
	 * @param billingPeriod the billingPeriod to set
	 */
	public void setBillingPeriod(String billingPeriod) {
		this.billingPeriod = billingPeriod;
	}
	/**
	 * @return the billingType
	 */
	public String getBillingType() {
		return billingType;
	}
	/**
	 * @param billingType the billingType to set
	 */
	public void setBillingType(String billingType) {
		this.billingType = billingType;
	}
	/**
	 * @return the billingCurrency
	 */
	public String getBillingCurrency() {
		return billingCurrency;
	}
	/**
	 * @param billingCurrency the billingCurrency to set
	 */
	public void setBillingCurrency(String billingCurrency) {
		this.billingCurrency = billingCurrency;
	}
	/**
	 * @return the numberOfLicences
	 */
	public int getNumberOfLicences() {
		return numberOfLicences;
	}
	/**
	 * @param numberOfLicences the numberOfLicences to set
	 */
	public void setNumberOfLicences(int numberOfLicences) {
		this.numberOfLicences = numberOfLicences;
	}
	/**
	 * @return the licenseNumber
	 */
	public String getLicenseNumber() {
		return licenseNumber;
	}
	/**
	 * @param licenseNumber the licenseNumber to set
	 */
	public void setLicenseNumber(String licenseNumber) {
		this.licenseNumber = licenseNumber;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Organization [id=" + id + ", name=" + name + ", orgType=" + orgType + ", orgStatus=" + orgStatus
				+ ", orgAddress=" + orgAddress + ", contactPerson=" + contactPerson + ", contactNumber=" + contactNumber
				+ ", contactEmailId=" + contactEmailId + ", billingAddress=" + billingAddress + ", billingRate="
				+ billingRate + ", billingPeriod=" + billingPeriod + ", billingType=" + billingType
				+ ", billingCurrency=" + billingCurrency + ", numberOfLicences=" + numberOfLicences + ", licenseNumber="
				+ licenseNumber + "]";
	}
}
