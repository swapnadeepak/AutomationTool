package com.estuate.esaip2_0.service;

import java.util.List;

import com.estuate.esaip2_0.entity.Association;
import com.estuate.esaip2_0.entity.Role;
import com.estuate.esaip2_0.entity.SuperEntity;
import com.estuate.esaip2_0.entity.UserDetail;

public interface ApplicationService {
	/**
	 * checks the entered emailId and password are valid or not
	 * 
	 * @param emailId
	 * @param password
	 * @return
	 * @throws Exception
	 */
	public UserDetail validateEmailPassword(String emailId, String password) throws Exception;

	/**
	 * Retrives the entity based on the Id
	 * 
	 * @param entityClass
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public SuperEntity getEntityById(Class<? extends SuperEntity> entityClass, int id) throws Exception;

	/**
	 * Retrives the entity based on the name
	 * 
	 * @param entityClass
	 * @param name
	 * @return
	 * @throws Exception
	 */
	public SuperEntity getEntityByName(Class<? extends SuperEntity> entityClass, String name) throws Exception;

	/**
	 * Retrives the entity based on the email ID
	 * 
	 * @param entityClass
	 * @param mailId
	 * @return
	 * @throws Exception
	 */
	public SuperEntity getEntityByEmail(Class<? extends SuperEntity> entityClass, String mailId) throws Exception;

	/**
	 * To add an entity to the database
	 * 
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	public boolean addEntity(SuperEntity entity) throws Exception;

	/**
	 * To delete an entity from the database
	 * 
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	public boolean deleteEntity(SuperEntity entity) throws Exception;

	/**
	 * To delete an entity from the database based on Id
	 * 
	 * @param entityClass
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public boolean deleteEntityById(Class<? extends SuperEntity> entityClass, int id) throws Exception;

	/**
	 * Retrive all records of specifies <tt>entityClass</tt>
	 * 
	 * @param entityClass
	 * @return
	 * @throws Exception
	 */
	public List<SuperEntity> getEntityList(Class<? extends SuperEntity> entityClass) throws Exception;

	/**
	 * Retrieves all the Role corresponding to the <tt>roleIdList</tt>
	 * 
	 * @param roleIdList
	 * @return
	 * @throws Exception
	 */
	public List<Role> getRolesByIdList(List<Integer> roleIdList) throws Exception;

	/**
	 * checks whether the orgName already exists in the database or not
	 * 
	 * @param className
	 * @param orgName
	 * @return
	 * @throws Exception
	 */
	public boolean checkNameExistence(String className, String orgName) throws Exception;

	/**
	 * checks whether the groupName already exists in the database or not
	 * 
	 * @param groupName
	 * @return
	 * @throws Exception
	 */
	public boolean checkGroupExistence(String groupName) throws Exception;

	/**
	 * checks whether the emailId already exists in the database or not
	 * 
	 * @param className
	 * @param emailId
	 * @return
	 * @throws Exception
	 */
	public boolean checkEmailExistence(String className, String emailId) throws Exception;

	/**
	 * To get a list of Id's corresponding to the provided parameters.
	 * 
	 * @param associationType
	 * @param mainId
	 * @param associatedId
	 * @return
	 * @throws Exception
	 */
	public List<Integer> getAssociatedList(String associationType, int mainId, int associatedId) throws Exception;

	/**
	 * To update an entity in the database
	 * 
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	public boolean updateEntity(SuperEntity entity) throws Exception;

	/**
	 * To get an <tt>Association<tt> entity
	 * 
	 * @param associationType
	 * @param mainId
	 * @param associationId
	 * @return
	 * @throws Exception
	 */
	public Association getAssociation(String associationType, int mainId, int associationId) throws Exception;

	/**
	 * To get attempt record list based on the user id
	 * 
	 * @param entityClass
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public List<SuperEntity> getAttemptByUserId(Class<? extends SuperEntity> entityClass, int id) throws Exception;

	/**
	 * To get the total number of login attempts of a particular user
	 * 
	 * @param entityClass
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public int getTotalLoginAttempts(Class<? extends SuperEntity> entityClass, UserDetail user) throws Exception;

	/**
	 * To get attempt record based on the session id
	 * 
	 * @param entityClass
	 * @param sessionId
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public SuperEntity getAttemptEntityBySessionId(Class<? extends SuperEntity> entityClass, String sessionId, int id)
			throws Exception;

	/**
	 * To delete an entity whose Id's are provided in the idList
	 * 
	 * @param entityClass
	 * @param idList
	 * @return
	 * @throws Exception
	 */
	public boolean deleteEntityFromIdList(Class<? extends SuperEntity> entityClass, List<Integer> idList)
			throws Exception;

	/**
	 * To delete <tt>Association</tt> entity from the database
	 * 
	 * @param associationType
	 * @param mainID
	 * @param associationID
	 * @return
	 * @throws Exception
	 */
	public boolean deleteAssociation(String associationType, int mainID, int associationID) throws Exception;

	/**
	 * To get Association based on the id
	 * 
	 * @param associationType
	 * @param mainID
	 * @param associationID
	 * @return
	 * @throws Exception
	 */
	public List<Association> getAssociationById(String associationType, int mainID, int associationID) throws Exception;

	/**
	 * To check email existence on update functionality
	 * 
	 * @param className
	 * @param emailId
	 * @return
	 * @throws Exception
	 */
	public SuperEntity checkEmailOnUpdate(String className, String emailId) throws Exception;
	/**
	 *  To check name existence on update functionality
	 * @param className
	 * @param name
	 * @return
	 * @throws Exception
	 */
	public SuperEntity checkNameOnUpdate(String className, String name) throws Exception;
}
