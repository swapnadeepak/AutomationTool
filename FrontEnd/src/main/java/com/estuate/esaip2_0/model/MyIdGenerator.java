package com.estuate.esaip2_0.model;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.estuate.esaip2_0.exception.ShowMessage;


/**
 * <ul>
 * <li>Title: MyIdGenerator</li>
 * <li>Description: To generate custom ID for the Port entity</li>
 * <li>Created By: Radhika GopalRaj</li>
 * </ul>
 */
public class MyIdGenerator implements IdentifierGenerator {
	/**
	 * The logger
	 */
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public Serializable generate(SharedSessionContractImplementor si, Object obj) throws HibernateException {
		int prefix = 2999;
		int generatedId = 0;
		Connection connection = si.connection();
		try {
			java.sql.PreparedStatement preparedStatement = connection
					.prepareStatement("select max(Port) as port from est_userport");
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				int id = resultSet.getInt(1) + 1;
				if (id == 1) {
					generatedId = prefix + new Integer(id);
				} else {
					generatedId = new Integer(id);
				}
				return generatedId;
			}
		} catch (SQLException e) {
			logger.error("Exception : " + ShowMessage.getStackTrace(e));
		}
		return generatedId;
	}



}
