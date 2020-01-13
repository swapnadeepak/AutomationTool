package com.estuate.esaip2_0.model;

import java.io.Serializable;

/**
 * <ul>
 * <li>Title: RunPlan</li>
 * <li>Description: This class is for master plan tab of excel.</li>
 * <li>Created by: Nemmar Rajath Bhat</li>
 * <li>Modified By: Radhika GopalRaj</li>
 * </ul>
 */
public class MasterPlan implements Serializable {
	/**
	 * The serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * The index
	 */
	private int index;
	/**
	 * The id
	 */
	private String id;
	/**
	 * The businessRequirement
	 */
	private String businessRequirement;
	/**
	 * The description
	 */
	private String description;
	/**
	 * The impactedBRs
	 */
	private String impactedBRs;
	/**
	 * The includeImpactedBRs
	 */
	private String includeImpactedBRs;
	/**
	 * The modules
	 */
	private String modules;
	/**
	 * The modulesInclude
	 */
	private String modulesInclude;
	/**
	 * The testCases
	 */
	private String testCases;
	/**
	 * The testCasesInclude
	 */
	private String testCasesInclude;
	/**
	 * The TestCaseId
	 */
	private String TestCaseId;
	/**
	 * The criticality
	 */
	private String criticality;
	/**
	 * The repeatablity
	 */
	private String repeatablity;
	/**
	 * The fileLocation
	 */
	private String fileLocation;
	/**
	 * The fileName
	 */
	private String fileName;

	/**
	 * @return the index
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * @param index
	 *            the index to set
	 */
	public void setIndex(int index) {
		this.index = index;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the businessRequirement
	 */
	public String getBusinessRequirement() {
		return businessRequirement;
	}

	/**
	 * @param businessRequirement
	 *            the businessRequirement to set
	 */
	public void setBusinessRequirement(String businessRequirement) {
		this.businessRequirement = businessRequirement;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the impactedBRs
	 */
	public String getImpactedBRs() {
		return impactedBRs;
	}

	/**
	 * @param impactedBRs
	 *            the impactedBRs to set
	 */
	public void setImpactedBRs(String impactedBRs) {
		this.impactedBRs = impactedBRs;
	}

	/**
	 * @return the includeImpactedBRs
	 */
	public String getIncludeImpactedBRs() {
		return includeImpactedBRs;
	}

	/**
	 * @param includeImpactedBRs
	 *            the includeImpactedBRs to set
	 */
	public void setIncludeImpactedBRs(String includeImpactedBRs) {
		this.includeImpactedBRs = includeImpactedBRs;
	}

	/**
	 * @return the modules
	 */
	public String getModules() {
		return modules;
	}

	/**
	 * @param modules
	 *            the modules to set
	 */
	public void setModules(String modules) {
		this.modules = modules;
	}

	/**
	 * @return the modulesInclude
	 */
	public String getModulesInclude() {
		return modulesInclude;
	}

	/**
	 * @param modulesInclude
	 *            the modulesInclude to set
	 */
	public void setModulesInclude(String modulesInclude) {
		this.modulesInclude = modulesInclude;
	}

	/**
	 * @return the testCases
	 */
	public String getTestCases() {
		return testCases;
	}

	/**
	 * @param testCases
	 *            the testCases to set
	 */
	public void setTestCases(String testCases) {
		this.testCases = testCases;
	}

	/**
	 * @return the testCasesInclude
	 */
	public String getTestCasesInclude() {
		return testCasesInclude;
	}

	/**
	 * @param testCasesInclude
	 *            the testCasesInclude to set
	 */
	public void setTestCasesInclude(String testCasesInclude) {
		this.testCasesInclude = testCasesInclude;
	}

	/**
	 * @return the testCaseId
	 */
	public String getTestCaseId() {
		return TestCaseId;
	}

	/**
	 * @param testCaseId
	 *            the testCaseId to set
	 */
	public void setTestCaseId(String testCaseId) {
		TestCaseId = testCaseId;
	}

	/**
	 * @return the criticality
	 */
	public String getCriticality() {
		return criticality;
	}

	/**
	 * @param criticality
	 *            the criticality to set
	 */
	public void setCriticality(String criticality) {
		this.criticality = criticality;
	}

	/**
	 * @return the repeatablity
	 */
	public String getRepeatablity() {
		return repeatablity;
	}

	/**
	 * @param repeatablity
	 *            the repeatablity to set
	 */
	public void setRepeatablity(String repeatablity) {
		this.repeatablity = repeatablity;
	}

	/**
	 * @return the fileLocation
	 */
	public String getFileLocation() {
		return fileLocation;
	}

	/**
	 * @param fileLocation
	 *            the fileLocation to set
	 */
	public void setFileLocation(String fileLocation) {
		this.fileLocation = fileLocation;
	}

	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * @param fileName
	 *            the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "MasterPlan [index=" + index + ", id=" + id + ", businessRequirement=" + businessRequirement
				+ ", description=" + description + ", impactedBRs=" + impactedBRs + ", includeImpactedBRs="
				+ includeImpactedBRs + ", modules=" + modules + ", modulesInclude=" + modulesInclude + ", testCases="
				+ testCases + ", testCasesInclude=" + testCasesInclude + ", TestCaseId=" + TestCaseId + ", criticality="
				+ criticality + ", repeatablity=" + repeatablity + ", fileLocation=" + fileLocation + ", fileName="
				+ fileName + "]";
	}

}
