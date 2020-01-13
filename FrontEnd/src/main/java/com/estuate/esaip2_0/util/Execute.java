package com.estuate.esaip2_0.util;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import static com.estuate.esaip2_0.util.Constants.*;

/**
 * <ul>
 * <li>Title: Execute</li>
 * <li>Description: This Util class is used to execute the jar files for running
 * selenium code and Mantis defect logging code.</li>
 * <li>Created by: Nemmar Rajath Bhat</li>
 * <li>Modified By: Radhika GopalRaj</li>
 * </ul>
 */
@Component
public class Execute{
	/**
	 * The logger
	 */
	private static final Logger logger = LoggerFactory.getLogger(Execute.class);
	/**
	 * The excelUtil
	 */
	@Autowired
	private ExcelUtil excelUtil;
	
	@Autowired
	Util util;
	

	/**
	 * This method executeTestCase() is used to start the selenium testing for given
	 * run plan
	 * 
	 * @param fileLocation
	 * @param request
	 * @throws Exception
	 */
	public void executeTestCase(String fileLocation, HttpServletRequest request) throws Exception {
		if (fileLocation != null && !fileLocation.trim().isEmpty()) {
			logger.info("Execute the Run Plans of file: " + fileLocation);
			String orgFolder = (String) request.getSession().getAttribute("userOrganization");
			String userFolder = (String) request.getSession().getAttribute("userName");
			String cmd = "	cmd.exe /c cd\\ & Taskkill /im excel.exe & "
					+ esaipBaseLocation.substring(0, esaipBaseLocation.indexOf("\\")) + " & cd " + esaipBaseLocation
					+ " & cd " + orgFolder + " & cd " + util.getProjectDataProperty("userFolderKey") + " & cd " + userFolder
					+ " & java -jar TestDriver.jar & pause";
			logger.info("command >>>>>>>> : "+cmd);
			Runtime.getRuntime().exec(cmd);
			logger.info("Execution of executeTestCase method completed");
		} else {
			logger.error("fileLocation is Empty or null");
		}
	}

	/**
	 * This method FetchdefectId() is used to log the defect id of the mantis
	 * 
	 * @param fileLocation
	 * @param request
	 * @throws Exception
	 */
	public void logDefect(String fileLocation, HttpServletRequest request) throws Exception {
		if (fileLocation != null && !fileLocation.trim().isEmpty()) {
			logger.info("Defect id logged for file: " + fileLocation);
			String orgFolder = (String) request.getSession().getAttribute("userOrganization");
			String userFolder = (String) request.getSession().getAttribute("userName");
			String defectJarFile = getProperty("DefectJarFilename", request);
			String cmd = "	cmd.exe /c cd\\ & Taskkill /im excel.exe & "
					+ esaipBaseLocation.substring(0, esaipBaseLocation.indexOf("\\")) + " & cd " + esaipBaseLocation
					+ " & cd " + orgFolder + " & cd " + util.getProjectDataProperty("userFolderKey") + " & cd " + userFolder
					+ " & java -jar "+ defectJarFile+".jar & pause";
			Runtime.getRuntime().exec(cmd);
		}
	}

	/**
	 * Returns value of the defined property
	 * 
	 * @param propertyName
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public String getProperty(String propertyName, HttpServletRequest request) throws Exception {
		String status = null;
		if (propertyName != null && !propertyName.trim().isEmpty()) {
			status = excelUtil.getProgressProperty(propertyName, request);
		}
		return status;
	}

	/**
	 * This is used to update the property for the selenium execution
	 * 
	 * @param property
	 * @param value
	 * @param request
	 * @throws Exception
	 */
	public void updateProgressProperty(String orgName, String userName, String property, String value,
			HttpServletRequest request) throws Exception {
		if (property != null && !property.trim().isEmpty()) {
			excelUtil.setProgressProperty(orgName, userName, property, value, request);
			logger.info(property + " is  set with value: " + value);
		}
	}

	/**
	 * This is used to fetch the property value from the message property file
	 * 
	 * @param propertyName
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public String getMessage(String propertyName, HttpServletRequest request) throws Exception {
		String message = null;
		if (propertyName != null && !propertyName.trim().isEmpty()) {
			message = excelUtil.getMessage(propertyName, request);
		}
		return message;
	}
}
