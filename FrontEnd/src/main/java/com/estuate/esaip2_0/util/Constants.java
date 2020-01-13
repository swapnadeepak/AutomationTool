/**
 * 
 */
package com.estuate.esaip2_0.util;

import java.net.Inet4Address;
import java.net.UnknownHostException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.estuate.esaip2_0.exception.ShowMessage;

/**
 * @author rgopalraj
 *
 */
public class Constants {
	/**
	 * The logger
	 */
	private static Logger logger = LoggerFactory.getLogger(Util.class);
	
	/**
	 * The esaipBaseLocation
	 */
	public static String esaipBaseLocation;
	/**
	 * The automationBaseLocation
	 */
	public static String automationBaseLocation;
	/**
	 * The serverIp
	 */
	public static String serverIp;
	/**
	 * The MAX_ATTEMPT_COUNT
	 */
	public static int MAX_ATTEMPT_COUNT;

	/**
	 * static block to load the location of generic automation folder and the location to where organization gets created 
	 */
	static {
		Util util = new Util();
		MAX_ATTEMPT_COUNT = Integer.parseInt(util.getProjectDataProperty("MAX_ATTEMPT_COUNT"));
		String baseLocationKey = "frameworkLocation";
		String baseLocation = util.getProjectDataProperty(baseLocationKey);
		automationBaseLocation = baseLocation;
		logger.info("Automation base location : " + automationBaseLocation);
		String folderKey = "organizationFolderKey";
		String folderLocation = util.getProjectDataProperty(folderKey);
		if (folderLocation != null && !folderLocation.trim().isEmpty()) {
			esaipBaseLocation = baseLocation.concat("\\" + folderLocation);
		} else {
			esaipBaseLocation = baseLocation;
		}
		logger.info("ESAIP base location : " + esaipBaseLocation);
		try {
			serverIp = Inet4Address.getLocalHost().getHostAddress();//"10.10.11.100";
			logger.info("*******************tqk : "+Inet4Address.getLocalHost().getHostAddress());
		} catch (UnknownHostException e) {
			logger.error("Exception : " + ShowMessage.getStackTrace(e));
		}
		logger.info("Server IP Address : " + serverIp);
	}
}
