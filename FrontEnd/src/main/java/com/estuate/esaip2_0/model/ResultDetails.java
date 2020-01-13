package com.estuate.esaip2_0.model;

import java.io.Serializable;

/**
 * <ul>
 * <li>Title: RunPlan</li>
 * <li>Description:This class is for the results tab of excel.</li>
 * <li>Created by: Nemmar Rajath Bhat</li>
 * <li>Modified By: Radhika GopalRaj</li>
 * </ul>
 */
public class ResultDetails implements Serializable {
	/**
	 * The serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * The resultkey
	 */
	private String resultkey;
	/**
	 * The resultvalue
	 */
	private String resultvalue;
	/**
	 * The fileName
	 */
	private String fileName;
	/**
	 * The fileLocation
	 */
	private String fileLocation;

	/**
	 * @return the resultkey
	 */
	public String getResultkey() {
		return resultkey;
	}

	/**
	 * @param resultkey
	 *            the resultkey to set
	 */
	public void setResultkey(String resultkey) {
		this.resultkey = resultkey;
	}

	/**
	 * @return the resultvalue
	 */
	public String getResultvalue() {
		return resultvalue;
	}

	/**
	 * @param resultvalue
	 *            the resultvalue to set
	 */
	public void setResultvalue(String resultvalue) {
		this.resultvalue = resultvalue;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ResultDetails [resultkey=" + resultkey + ", resultvalue=" + resultvalue + ", fileName=" + fileName
				+ ", fileLocation=" + fileLocation + "]";
	}
}
