package com.estuate.esaip2_0.model;

/**
 * <ul>
 * <li>Title: UserTypeEnum</li>
 * <li>Description: The UserTypeEnum enumarator.</li>
 * <li>Created By: Radhika GopalRaj</li>
 * </ul>
 */
public enum UserTypeEnum {
	SUPER_ADMIN(1), ADMIN(2), USER(3);
	/**
	 * The id
	 */
	private final int id;

	/**
	 * @param id
	 */
	private UserTypeEnum(int id) {
		this.id = id;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
}
