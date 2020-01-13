package com.estuate.esaip2_0.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.estuate.esaip2_0.dao.ApplicationDao;
import com.estuate.esaip2_0.entity.Association;
import com.estuate.esaip2_0.entity.Role;
import com.estuate.esaip2_0.entity.SuperEntity;
import com.estuate.esaip2_0.entity.UserDetail;

@Service
public class ApplicationServiceImpl implements ApplicationService {
	
	@Autowired
	private ApplicationDao appDao;

	@Transactional
	@Override
	public UserDetail validateEmailPassword(String emailId, String password) throws Exception {
		UserDetail user = appDao.validateEmailPassword(emailId, password);
		return user;
	}

	@Override
	@Transactional
	public boolean addEntity(SuperEntity entity) throws Exception {
		boolean value = appDao.addEntity(entity);
		return value;
	}

	@Override
	@Transactional
	public List<SuperEntity> getEntityList(Class<? extends SuperEntity> entityClass) throws Exception {
		List<SuperEntity> entityList = appDao.getEntityList(entityClass);
		return entityList;
	}

	@Override
	@Transactional
	public List<Role> getRolesByIdList(List<Integer> roleIdList) throws Exception {
		List<Role> roleList = appDao.getRolesByIdList(roleIdList);
		return roleList;
	}

	@Override
	@Transactional
	public boolean checkNameExistence(String className, String orgName) throws Exception {
		boolean value = appDao.checkNameExistence(className, orgName);
		return value;
	}

	@Override
	@Transactional
	public boolean checkEmailExistence(String className, String emailId) throws Exception {
		boolean value = appDao.checkEmailExistence(className, emailId);
		return value;
	}

	@Override
	@Transactional
	public SuperEntity getEntityById(Class<? extends SuperEntity> entityClass, int id) throws Exception {
		SuperEntity entity = appDao.getEntityById(entityClass, id);
		return entity;
	}

	@Override
	@Transactional
	public SuperEntity getEntityByName(Class<? extends SuperEntity> entityClass, String name) throws Exception {
		SuperEntity entity = appDao.getEntityByName(entityClass, name);
		return entity;
	}

	@Override
	@Transactional
	public SuperEntity getEntityByEmail(Class<? extends SuperEntity> entityClass, String mailId) throws Exception {
		SuperEntity entity = appDao.getEntityByEmail(entityClass, mailId);
		return entity;
	}

	@Override
	@Transactional
	public List<Integer> getAssociatedList(String associationType, int mainId, int associatedId) throws Exception {
		List<Integer> list = appDao.getAssociatedList(associationType, mainId, associatedId);
		return list;
	}

	@Override
	@Transactional
	public boolean updateEntity(SuperEntity entity) throws Exception {
		boolean value = appDao.updateEntity(entity);
		return value;
	}

	@Override
	@Transactional
	public boolean deleteEntityById(Class<? extends SuperEntity> entityClass, int id) throws Exception {
		boolean value = appDao.deleteEntityById(entityClass, id);
		return value;
	}

	@Override
	@Transactional
	public Association getAssociation(String associationType, int mainId, int associationId) throws Exception {
		Association association = appDao.getAssociation(associationType, mainId, associationId);
		return association;
	}

	@Override
	@Transactional
	public boolean deleteEntity(SuperEntity entity) throws Exception {
		boolean value = appDao.deleteEntity(entity);
		return value;
	}

	@Override
	@Transactional
	public boolean checkGroupExistence(String groupName) throws Exception {
		boolean value = appDao.checkGroupExistence(groupName);
		return value;
	}

	@Override
	@Transactional
	public List<SuperEntity> getAttemptByUserId(Class<? extends SuperEntity> entityClass, int id) throws Exception {
		List<SuperEntity> superEntityList = appDao.getAttemptByUserId(entityClass, id);
		return superEntityList;
	}

	@Override
	@Transactional
	public int getTotalLoginAttempts(Class<? extends SuperEntity> entityClass, UserDetail user) throws Exception {
		int count = appDao.getTotalLoginAttempts(entityClass, user);
		return count;
	}

	@Override
	@Transactional
	public SuperEntity getAttemptEntityBySessionId(Class<? extends SuperEntity> entityClass, String sessionId, int id)
			throws Exception {
		SuperEntity entity = appDao.getAttemptEntityBySessionId(entityClass, sessionId, id);
		return entity;
	}

	@Override
	@Transactional
	public boolean deleteEntityFromIdList(Class<? extends SuperEntity> entityClass, List<Integer> idList)
			throws Exception {
		boolean value = appDao.deleteEntityFromIdList(entityClass, idList);
		return value;
	}

	@Override
	@Transactional
	public boolean deleteAssociation(String associationType, int mainID, int associationID) throws Exception {
		boolean value = appDao.deleteAssociation(associationType, mainID, associationID);
		return value;
	}

	@Override
	@Transactional
	public List<Association> getAssociationById(String associationType, int mainID, int associationID)
			throws Exception {
		List<Association> list = appDao.getAssociationById(associationType, mainID, associationID);
		return list;
	}

	@Override
	@Transactional
	public SuperEntity checkEmailOnUpdate(String className, String emailId) throws Exception {
		SuperEntity entity = appDao.checkEmailOnUpdate(className, emailId);
		return entity;
	}

	@Override
	@Transactional
	public SuperEntity checkNameOnUpdate(String className, String name) throws Exception {
		SuperEntity entity = appDao.checkNameOnUpdate(className, name);
		return entity;
	}
}
