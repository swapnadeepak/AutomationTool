package com.estuate.esaip2_0.model;

/**
 * <ul>
 * <li>Title: ProjectFolderEnum</li>
 * <li>Description:The PropertyFileEnum enumarator, contains the property file names.</li>
 * <li>Created By: Radhika GopalRaj</li>
 * </ul>
 */
public enum PropertyFileEnum {
	PROGRESS_PROPERTY_FILENAME("Generic"), MESSAGE_PROPERTY_FILENAME("Messages"), MASTERPLAN_XLSX_FILENAME(
			"MasterPlan"), UTILITY_PROPERTY_FILENAME("utility"), MASTERPLAN_PROPERTY_FILENAME(
					"MasterPlan"), PROJECT_DATA_PROPERTY_FILE("project_data");
	/**
	 * The property
	 */
	private final String property;

	/**
	 * @return the property
	 */
	public String getProperty() {
		return property;
	}

	/**
	 * @param property
	 */
	private PropertyFileEnum(String property) {
		this.property = property;
	}
}
