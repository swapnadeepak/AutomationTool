package com.estuate.esaip2_0.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.estuate.esaip2_0.exception.ShowMessage;
import com.estuate.esaip2_0.model.PropertyFileEnum;
import static com.estuate.esaip2_0.util.Constants.*;





/**
 * <ul>
 * <li>Title: ExcelUtil</li>
 * <li>Description: This Util class is used to fetch the properties from
 * properties file</li>
 * <li>Created by: Nemmar Rajath Bhat</li>
 * <li>Modified By: Radhika GopalRaj</li>
 * </ul>
 */
@Component
public class ExcelUtil{
	/**
	 * The logger
	 */
	private static final Logger logger = LoggerFactory.getLogger(ExcelUtil.class);
	
	@Autowired
	Util util;

	/**
	 * This is used to set the values required for selenium testing
	 * 
	 * @param property
	 * @param value
	 * @param request
	 * @throws Exception
	 */
	public void setProgressProperty(String orgFolder, String userFolder, String property, String value,
			HttpServletRequest request) throws Exception {
		if (property != null && !property.trim().isEmpty() && value != null && !value.trim().isEmpty()) {
			FileInputStream fis = new FileInputStream(
					esaipBaseLocation + "\\" + orgFolder + "\\" + util.getProjectDataProperty("userFolderKey") + "\\" + userFolder
							+ "\\" + PropertyFileEnum.PROGRESS_PROPERTY_FILENAME.getProperty() + ".properties");
			Properties prop = null;
			prop = new Properties();
			prop.load(fis);
			fis.close();
			FileOutputStream fos = new FileOutputStream(
					esaipBaseLocation + "\\" + orgFolder + "\\" + util.getProjectDataProperty("userFolderKey") + "\\" + userFolder
							+ "\\" + PropertyFileEnum.PROGRESS_PROPERTY_FILENAME.getProperty() + ".properties");
			prop.setProperty(property, value);
			prop.store(fos, null);
		}
	}

	/**
	 * This is used to fetch the values set by selenium testing
	 * 
	 * @param propertyName
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public String getProgressProperty(String propertyName, HttpServletRequest request) throws Exception {
		Properties prop = null;
		if (propertyName != null && !propertyName.trim().isEmpty()) {
			prop = new Properties();
			String orgFolder = (String) request.getSession().getAttribute("userOrganization");
			String userFolder = (String) request.getSession().getAttribute("userName");
			prop.load(new FileInputStream(
					esaipBaseLocation + "\\" + orgFolder + "\\" + util.getProjectDataProperty("userFolderKey") + "\\" + userFolder
							+ "\\" + PropertyFileEnum.PROGRESS_PROPERTY_FILENAME.getProperty() + ".properties"));
		}
		return prop.getProperty(propertyName);
	}

	/**
	 * This is used to fetch the values set by selenium testing
	 * 
	 * @param propertyName
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public String getMessage(String propertyName, HttpServletRequest request) throws Exception {
		Properties prop = null;
		prop = new Properties();
		if (propertyName != null && !propertyName.trim().isEmpty()) {
			String orgFolder = (String) request.getSession().getAttribute("userOrganization");
			String userFolder = (String) request.getSession().getAttribute("userName");
			prop.load(new FileInputStream(
					esaipBaseLocation + "\\" + orgFolder + "\\" + util.getProjectDataProperty("userFolderKey") + "\\" + userFolder
							+ "\\" + PropertyFileEnum.MESSAGE_PROPERTY_FILENAME.getProperty() + ".properties"));
		}
		return prop.getProperty(propertyName);
	}

	/**
	 * This is used to fetch the values from MasterPlan.properties for master plan
	 * upload validations
	 * 
	 * @param propertyName
	 * @return
	 */
	public String getValue(String propertyName) throws Exception {
		Properties prop = null;
		prop = new Properties();
		try {
			prop.load(new FileInputStream(automationBaseLocation + "\\src\\"
					+ PropertyFileEnum.MASTERPLAN_PROPERTY_FILENAME.getProperty() + ".properties"));
		} catch (Exception e) {
			logger.error("Exception : " + ShowMessage.getStackTrace(e));
		}
		return prop.getProperty(propertyName);
	}
	/**
	 * 
	 * @param propertyName
	 * @return
	 */
	public static List<String> getDropDownValues(String propertyName) {
		ArrayList<String> dropdownValues = new ArrayList<>();
		Properties prop = new Properties();
		try {
			prop.load(new FileInputStream(automationBaseLocation + "\\src\\Dropdown.properties"));
		} catch (IOException e) {
			logger.error("Error fetching dropdown " + propertyName + ": " + e);
		}
		String dropdown = prop.getProperty(propertyName);
		StringTokenizer st = new StringTokenizer(dropdown, ",");
		while (st.hasMoreTokens()) {
			dropdownValues.add(st.nextToken().trim());
		}
		return dropdownValues;
	}

	
	
}
