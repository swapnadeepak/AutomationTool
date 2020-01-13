package com.estuate.esaip2_0.model;

import java.io.Serializable;

/**
 * <ul>
 * <li>Title: RunPlan</li>
 * <li>Description: This class is for the data of defects tab of excel.</li>
 * <li>Created by: Nemmar Rajath Bhat</li>
 * <li>Modified By: Radhika GopalRaj</li>
 * </ul>
 */
public class Defects implements Serializable {
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
	 * The testCaseid
	 */
	private String testCaseid;
	/**
	 * The testCaseName
	 */
	private String testCaseName;
	/**
	 * The logDefect
	 */
	private String logDefect;
	/**
	 * The summary
	 */
	private String summary;
	/**
	 * The description
	 */
	private String description;
	/**
	 * The reproducibility
	 */
	private String reproducibility;
	/**
	 * The Severity
	 */
	private String Severity;
	/**
	 * The priority
	 */
	private String priority;
	/**
	 * The assignTo
	 */
	private String assignTo;
	/**
	 * The stepsToReproduce
	 */
	private String stepsToReproduce;
	/**
	 * The additionalInformation
	 */
	private String additionalInformation;
	/**
	 * The uploadFilePath
	 */
	private String uploadFilePath;
	/**
	 * The defectID
	 */
	private String defectID;
	/**
	 * The loggedDate
	 */
	private String loggedDate;
	/**
	 * The defectUrl
	 */
	private String defectUrl;
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
	 * @return the testCaseid
	 */
	public String getTestCaseid() {
		return testCaseid;
	}

	/**
	 * @param testCaseid
	 *            the testCaseid to set
	 */
	public void setTestCaseid(String testCaseid) {
		this.testCaseid = testCaseid;
	}

	/**
	 * @return the testCaseName
	 */
	public String getTestCaseName() {
		return testCaseName;
	}

	/**
	 * @param testCaseName
	 *            the testCaseName to set
	 */
	public void setTestCaseName(String testCaseName) {
		this.testCaseName = testCaseName;
	}

	/**
	 * @return the logDefect
	 */
	public String getLogDefect() {
		return logDefect;
	}

	/**
	 * @param logDefect
	 *            the logDefect to set
	 */
	public void setLogDefect(String logDefect) {
		this.logDefect = logDefect;
	}

	/**
	 * @return the summary
	 */
	public String getSummary() {
		return summary;
	}

	/**
	 * @param summary
	 *            the summary to set
	 */
	public void setSummary(String summary) {
		this.summary = summary;
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
	 * @return the reproducibility
	 */
	public String getReproducibility() {
		return reproducibility;
	}

	/**
	 * @param reproducibility
	 *            the reproducibility to set
	 */
	public void setReproducibility(String reproducibility) {
		this.reproducibility = reproducibility;
	}

	/**
	 * @return the severity
	 */
	public String getSeverity() {
		return Severity;
	}

	/**
	 * @param severity
	 *            the severity to set
	 */
	public void setSeverity(String severity) {
		Severity = severity;
	}

	/**
	 * @return the priority
	 */
	public String getPriority() {
		return priority;
	}

	/**
	 * @param priority
	 *            the priority to set
	 */
	public void setPriority(String priority) {
		this.priority = priority;
	}

	/**
	 * @return the assignTo
	 */
	public String getAssignTo() {
		return assignTo;
	}

	/**
	 * @param assignTo
	 *            the assignTo to set
	 */
	public void setAssignTo(String assignTo) {
		this.assignTo = assignTo;
	}

	/**
	 * @return the stepsToReproduce
	 */
	public String getStepsToReproduce() {
		return stepsToReproduce;
	}

	/**
	 * @param stepsToReproduce
	 *            the stepsToReproduce to set
	 */
	public void setStepsToReproduce(String stepsToReproduce) {
		this.stepsToReproduce = stepsToReproduce;
	}

	/**
	 * @return the additionalInformation
	 */
	public String getAdditionalInformation() {
		return additionalInformation;
	}

	/**
	 * @param additionalInformation
	 *            the additionalInformation to set
	 */
	public void setAdditionalInformation(String additionalInformation) {
		this.additionalInformation = additionalInformation;
	}

	/**
	 * @return the uploadFilePath
	 */
	public String getUploadFilePath() {
		return uploadFilePath;
	}

	/**
	 * @param uploadFilePath
	 *            the uploadFilePath to set
	 */
	public void setUploadFilePath(String uploadFilePath) {
		this.uploadFilePath = uploadFilePath;
	}

	/**
	 * @return the defectID
	 */
	public String getDefectID() {
		return defectID;
	}

	/**
	 * @param defectID
	 *            the defectID to set
	 */
	public void setDefectID(String defectID) {
		this.defectID = defectID;
	}

	/**
	 * @return the loggedDate
	 */
	public String getLoggedDate() {
		return loggedDate;
	}

	/**
	 * @param loggedDate
	 *            the loggedDate to set
	 */
	public void setLoggedDate(String loggedDate) {
		this.loggedDate = loggedDate;
	}

	/**
	 * @return the defectUrl
	 */
	public String getDefectUrl() {
		return defectUrl;
	}

	/**
	 * @param defectUrl
	 *            the defectUrl to set
	 */
	public void setDefectUrl(String defectUrl) {
		this.defectUrl = defectUrl;
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
		return "Defects [index=" + index + ", id=" + id + ", testCaseid=" + testCaseid + ", testCaseName="
				+ testCaseName + ", logDefect=" + logDefect + ", summary=" + summary + ", description=" + description
				+ ", reproducibility=" + reproducibility + ", Severity=" + Severity + ", priority=" + priority
				+ ", assignTo=" + assignTo + ", stepsToReproduce=" + stepsToReproduce + ", additionalInformation="
				+ additionalInformation + ", uploadFilePath=" + uploadFilePath + ", defectID=" + defectID
				+ ", loggedDate=" + loggedDate + ", defectUrl=" + defectUrl + ", fileLocation=" + fileLocation
				+ ", fileName=" + fileName + "]";
	}

}
