package com.estuate.esaip2_0.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.estuate.esaip2_0.entity.Association;
import com.estuate.esaip2_0.entity.Group;
import com.estuate.esaip2_0.entity.Role;
import com.estuate.esaip2_0.entity.SuperEntity;
import com.estuate.esaip2_0.entity.UserDetail;
import com.estuate.esaip2_0.exception.ShowMessage;

@Repository
@SuppressWarnings("rawtypes")
public class ApplicationDaoImpl implements ApplicationDao {
	
	/**
	 * The logger
	 */
	private static final Logger logger = LoggerFactory.getLogger(ApplicationDaoImpl.class);
	
	@Autowired
	private EntityManager entityManager;

	@Override
	public UserDetail validateEmailPassword(String emailId, String password) throws Exception {
		UserDetail user = null;
		String hqlQuery = null;
		if (emailId == null || emailId.trim().isEmpty() || password == null || password.trim().isEmpty()) {
			return user;
		}
		hqlQuery = "from UserDetail where emailId ='" + emailId + "' and password='" + password + "'";
		List results = entityManager.createQuery(hqlQuery).getResultList();
		if(!results.isEmpty()){
			user = (UserDetail) results.get(0);
		}
		return user;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Role> getRolesByIdList(List<Integer> roleIdList) throws Exception {
		String hqlQuery = null;
		List<Role> roleList = null;
		if (roleIdList == null || roleIdList.isEmpty()) {
			return roleList;
		}
		hqlQuery = "from Role where roleID IN (:roleIdList)";
		roleList = entityManager.createQuery(hqlQuery).setParameter("roleIdList", roleIdList).getResultList();
		return roleList;
	}

	@Override
	public boolean addEntity(SuperEntity entity) throws Exception {
		if (entity != null) {
			entityManager.persist(entity);
			return true;
		}
		return false;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<SuperEntity> getEntityList(Class<? extends SuperEntity> entityClass) throws Exception {
		List<SuperEntity> entityList = new ArrayList<SuperEntity>();
		if (entityClass == null) {
			return entityList;
		}
		entityList = entityManager.createQuery("from " + entityClass.getSimpleName()).getResultList();
		return entityList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean checkNameExistence(String className, String name) throws Exception {
		javax.persistence.Query query = null;
		if (className == null || className.trim().isEmpty() || name == null || name.trim().isEmpty()) {
			return false;
		}
		if (className.equalsIgnoreCase("Project")) {
			query = entityManager.createQuery("from Project where name='" + name + "'");
		}
		if (className.equalsIgnoreCase("Organization")) {
			query = entityManager.createQuery("from Organization where name='" + name + "'");
		}
		if (className.equalsIgnoreCase("Group")) {
			query = entityManager.createQuery("from Group where name='" + name + "'");
		}
		List<SuperEntity> entity = query.getResultList();
		if (entity == null || entity.isEmpty())
			return false;
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean checkEmailExistence(String className, String emailId) throws Exception {
		javax.persistence.Query query = null;
		if (className == null || className.trim().isEmpty() || emailId == null || emailId.trim().isEmpty()) {
			return false;
		}
		if (className.equals("UserDetail")) {
			 query = entityManager.createQuery("from UserDetail where emailId='" + emailId + "'");
		}
		if (className.equals("Organization")) {
			query = entityManager.createQuery("from Organization where contactEmailId='" + emailId + "'");
		}
		List<SuperEntity> entity = query.getResultList();
		if (entity == null || entity.isEmpty())
			return false;
		return true;
	}

	@Override
	public SuperEntity getEntityById(Class<? extends SuperEntity> entityClass, int id) throws Exception {
		SuperEntity entity = null;
		String hqlQuery = null;
		if (entityClass == null || id == 0) {
			return entity;
		}
		if (entityClass.getSimpleName().equalsIgnoreCase("Port") || entityClass.getSimpleName().equalsIgnoreCase("AttemptHistory")) {
			hqlQuery = "from " + entityClass.getSimpleName() + " where UserID='" + id + "'";
		} else {
			hqlQuery = "from " + entityClass.getSimpleName() + " where id='" + id + "'";
		}
		List results = entityManager.createQuery(hqlQuery).getResultList();
		if(!results.isEmpty()){
			entity = (SuperEntity) results.get(0);
		}
		return entity;
	}

	@Override
	public SuperEntity getEntityByName(Class<? extends SuperEntity> entityClass, String name) throws Exception {
		SuperEntity entity = null;
		if (entityClass == null || name == null || name.trim().isEmpty()) {
			return entity;
		}
		String hqlQuery = "from " + entityClass.getSimpleName() + " where name='" + name + "'";
		List results = entityManager.createQuery(hqlQuery).getResultList();
		if(!results.isEmpty()){
			entity = (SuperEntity) results.get(0);
		}
		return entity;
	}

	@Override
	public SuperEntity getEntityByEmail(Class<? extends SuperEntity> entityClass, String mailId) throws Exception {
		SuperEntity entity = null;
		if (entityClass == null || mailId == null || mailId.trim().isEmpty()) {
			return entity;
		}
		String hqlQuery = "from " + entityClass.getSimpleName() + " where emailId='" + mailId + "'";
		List results = entityManager.createQuery(hqlQuery).getResultList();
		if(!results.isEmpty()){
			entity = (SuperEntity) results.get(0);
		}
		return entity;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Integer> getAssociatedList(String associationType, int mainId, int associatedId) throws Exception {
		List<Integer> idList = null;
		if (associationType == null || associationType.trim().isEmpty()) {
			return idList;
		}
		String hqlQuery = null;
		if (associatedId == 0 && mainId != 0) {
			hqlQuery = "Select compositeId.associationID from Association where compositeId.associationType='"
					+ associationType + "' and compositeId.mainID=" + mainId;
		}
		if (mainId == 0 && associatedId != 0) {
			hqlQuery = "Select compositeId.mainID from Association where compositeId.associationType='"
					+ associationType + "' and compositeId.associationID=" + associatedId;
		}
		idList = entityManager.createQuery(hqlQuery).getResultList();
		return idList;
	}
	@Override
	public boolean updateEntity(SuperEntity entity) throws Exception {
		if (entity == null) {
			return false;
		}
		try {
			entityManager.merge(entity);
			return true;
		} catch (Exception e) {
			logger.error("Exception : " + ShowMessage.getStackTrace(e));
		}
		return false;
	}

	@Override
	public boolean deleteEntityById(Class<? extends SuperEntity> entityClass, int id) throws Exception {
		if (entityClass == null || id == 0) {
			return false;
		}
		SuperEntity entity = getEntityById(entityClass, id);
		if (entity != null) {
			try {
				entityManager.remove(entity);
				return true;
			} catch (Exception e) {
				logger.error("Exception : " + ShowMessage.getStackTrace(e));
			}
		}
		return false;
	}

	@Override
	public Association getAssociation(String associationType, int mainId, int associationId) throws Exception {
		Association association = null;
		if (mainId == 0 || associationId == 0 || associationType == null || associationType.trim().isEmpty()) {
			return association;
		}
		String hqlQuery = null;
		hqlQuery = "from Association where compositeId.associationType='" + associationType
				+ "' and compositeId.mainID=" + mainId + "and compositeId.associationID=" + associationId;
		List results = entityManager.createQuery(hqlQuery).getResultList();
		if(!results.isEmpty()){
			association = (Association) results.get(0);
		}
		return association;
	}

	@Override
	public boolean deleteEntity(SuperEntity entity) throws Exception {
		if (entity != null) {
			try {
				entityManager.remove(entity);
				return true;
			} catch (Exception e) {
				logger.error("Exception : " + ShowMessage.getStackTrace(e));
			}
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean checkGroupExistence(String groupName) throws Exception {
		if (groupName == null || groupName.trim().isEmpty()) {
			return false;
		}
		List<Group> group = entityManager.createQuery("from Group where name='" + groupName + "'").getResultList();
		if (group == null || group.isEmpty())
			return false;
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SuperEntity> getAttemptByUserId(Class<? extends SuperEntity> entityClass, int id) throws Exception {
		List<SuperEntity> superEntityList = null;
		if (entityClass == null || id == 0) {
			return superEntityList;
		}
		String hqlQuery = null;
		hqlQuery = "from " + entityClass.getSimpleName() + " where UserID=" + id;
		superEntityList = entityManager.createQuery(hqlQuery).getResultList();
		return superEntityList;
	}

	@Override
	public int getTotalLoginAttempts(Class<? extends SuperEntity> entityClass, UserDetail user) throws Exception {  
		int sum = 0;
		Long sumInLong = 0L;
		String hqlQuery = null;
		hqlQuery = "select sum(attemptCount) from " + entityClass.getSimpleName() + " where UserID=" + user.getId();
		sumInLong = (Long) entityManager.createQuery(hqlQuery).getSingleResult();
		if(sumInLong != null){
			sum = sumInLong.intValue();
		}
		return sum;
	}

	@Override
	public SuperEntity getAttemptEntityBySessionId(Class<? extends SuperEntity> entityClass, String sessionId, int id)
			throws Exception {
		SuperEntity superEntity = null;
		if (sessionId == null || sessionId.trim().isEmpty() || entityClass == null || id == 0) {
			return superEntity;
		}
		String hqlQuery = null;
		hqlQuery = "from " + entityClass.getSimpleName() + " where UserID=" + id + " and sessionId='" + sessionId + "'";
		List results = entityManager.createQuery(hqlQuery).getResultList();
		if(!results.isEmpty()){
			superEntity =  (SuperEntity) results.get(0);
		}
		return superEntity;
	}

	@Override
	public boolean deleteEntityFromIdList(Class<? extends SuperEntity> entityClass, List<Integer> idList)
			throws Exception {
		if (entityClass == null || idList == null || idList.isEmpty()) {
			return false;
		}
		String hqlQuery = null;
		hqlQuery = "delete from " + entityClass.getSimpleName() + " where UserID in :idList";
		javax.persistence.Query query = entityManager.createQuery(hqlQuery).setParameter("idList", idList);
		if (query.executeUpdate() > 0) {
			return true;
		}
		return false;
	}

	@Override
	public boolean deleteAssociation(String associationType, int mainID, int associationID) throws Exception {
		if (associationType == null || associationType.trim().isEmpty()) {
			return false;
		}
		String hqlQuery = null;
		if (associationID == 0) {
			hqlQuery = "delete from Association where compositeId.associationType='" + associationType
					+ "' and compositeId.mainID=" + mainID;
		}
		if (mainID == 0) {
			hqlQuery = "delete from Association where compositeId.associationType='" + associationType
					+ "' and compositeId.associationID=" + associationID;
		}
		javax.persistence.Query query = entityManager.createQuery(hqlQuery);
		int res = query.executeUpdate();
		if (res > 0) {
			return true;
		}
		return false;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Association> getAssociationById(String associationType, int mainID, int associationID)
			throws Exception {
		List<Association> associationList = null;
		if (associationType == null || associationType.trim().isEmpty()) {
			return associationList;
		}
		String hqlQuery = null;
		if (associationID == 0) {
			hqlQuery = "from Association where compositeId.associationType='" + associationType
					+ "' and compositeId.mainID=" + mainID;
		}
		if (mainID == 0) {
			hqlQuery = "from Association where compositeId.associationType='" + associationType
					+ "' and compositeId.associationID=" + associationID;
		}
		javax.persistence.Query query = entityManager.createQuery(hqlQuery);
		associationList = query.getResultList();
		return associationList;
	}

	@Override
	public SuperEntity checkEmailOnUpdate(String className, String emailId) throws Exception {
		SuperEntity entity = null;
		javax.persistence.Query query = null;
		if (className == null || className.trim().isEmpty() || emailId == null || emailId.trim().isEmpty()) {
			return entity;
		}
		if (className.equals("UserDetail")) {
			query = entityManager.createQuery("from UserDetail where emailId='" + emailId + "'");
		}
		if (className.equals("Organization")) {
			query = entityManager.createQuery("from Organization where contactEmailId='" + emailId + "'");
		}
		List results = query.getResultList();
		if(!results.isEmpty()){
			entity =  (SuperEntity) results.get(0);
		}
		return entity;
	}

	@Override
	public SuperEntity checkNameOnUpdate(String className, String name) throws Exception {
		SuperEntity entity = null;
		javax.persistence.Query query = null;
		if (className == null || className.trim().isEmpty() || name == null || name.trim().isEmpty()) {
			return entity;
		}
		if (className.equalsIgnoreCase("Project")) {
			query = entityManager.createQuery("from Project where name='" + name + "'");
		}
		if (className.equalsIgnoreCase("Organization")) {
			query = entityManager.createQuery("from Organization where name='" + name + "'");
		}
		if (className.equalsIgnoreCase("Group")) {
			query = entityManager.createQuery("from Group where name='" + name + "'");
		}
		List results = query.getResultList();
		if(!results.isEmpty()){
			entity = (SuperEntity) results.get(0);
		}
		return entity;
	}
}
