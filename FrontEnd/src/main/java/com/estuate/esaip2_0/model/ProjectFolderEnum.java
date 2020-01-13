package com.estuate.esaip2_0.model;

/**
 * <ul>
 * <li>Title: ProjectFolderEnum</li>
 * <li>Description:The ProjectFolderEnum enumarator, contains the folder names which are created during project creation.</li>
 * <li>Created By: Radhika GopalRaj</li>
 * </ul>
 */
public enum ProjectFolderEnum {
	MASTERPLAN_BACKUP("MasterPlan Backup"), SCREENSHOTS("Screenshots"),LOGS("Logs"), RESULTS("Results"), RUNPLANS("Runplans");
	/**
	 * The folderName
	 */
	private final String folderName;

	/**
	 * @return the folderName
	 */
	public String getFolderName() {
		return folderName;
	}

	/**
	 * @param folderName
	 */
	private ProjectFolderEnum(String folderName) {
		this.folderName = folderName;
	}
}
