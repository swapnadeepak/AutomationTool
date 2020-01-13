package com.estuate.esaip2_0.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.estuate.esaip2_0.dto.UserDetailDTO;
import com.estuate.esaip2_0.entity.Association;
import com.estuate.esaip2_0.entity.AttemptCount;
import com.estuate.esaip2_0.entity.AttemptHistory;
import com.estuate.esaip2_0.entity.Group;
import com.estuate.esaip2_0.entity.Organization;
import com.estuate.esaip2_0.entity.Project;
import com.estuate.esaip2_0.entity.SuperEntity;
import com.estuate.esaip2_0.entity.UserDetail;
import com.estuate.esaip2_0.entity.UserType;
import com.estuate.esaip2_0.exception.ShowMessage;
import com.estuate.esaip2_0.model.AssociationTypeEnum;
import com.estuate.esaip2_0.model.ProjectFolderEnum;
import com.estuate.esaip2_0.model.PropertyFileEnum;
import com.estuate.esaip2_0.service.ApplicationService;

import static com.estuate.esaip2_0.util.Constants.*;

/**
 * <ul>
 * <li>Title: Util</li>
 * <li>Description:</li>
 * <li>Created by: Radhika Gopalraj</li>
 * </ul>
 */
@Component
public class Util {
	/**
	 * The logger
	 */
	private static Logger logger = LoggerFactory.getLogger(Util.class);
	
	@Autowired
	private ApplicationService applicationService;
	
	@Autowired
	private Execute execute;

	/**
	 * To get the property object for the given property file name
	 * 
	 * @param fileName
	 * @return
	 */
	public Properties getProperties(String fileName) {
		Properties properties = null;
		try {
			if (fileName != null && !fileName.isEmpty()) {
				properties = new Properties();
				InputStream inStr = getClass().getClassLoader().getResourceAsStream(fileName);
				properties.load(inStr);
			} else {
				logger.error("File name is empty in getProperties method");
			}
		} catch (IOException e) {
			logger.error("Exception: " + ShowMessage.getStackTrace(e));
		}
		return properties;
	}

	/**
	 * To create the folder path
	 * 
	 * @param folderName
	 * @return
	 * @throws Exception
	 */
	public String createFolder(String folderName) throws Exception {
		File folder = null;
		if (folderName != null && !folderName.trim().isEmpty()) {
			folder = new File(folderName);
		}
		if (!folder.exists()) {
			folder.mkdirs();
		}
		return folder.getAbsolutePath();
	}

	/**
	 * To copy files from <tt>fileSource</tt> to <tt>fileDestination</tt>
	 * 
	 * @param fileSource
	 * @param fileDestination
	 */
	public void copyFile(String fileSource, String fileDestination) {
		InputStream inputStream = null;
		OutputStream outputStream = null;
		try {
			if (fileSource != null && !fileSource.trim().isEmpty() && fileDestination != null
					&& !fileDestination.trim().isEmpty()) {
				inputStream = new FileInputStream(fileSource);
				outputStream = new FileOutputStream(fileDestination);

				byte[] buf = new byte[1024];
				int len;
				while ((len = inputStream.read(buf)) > 0) {
					outputStream.write(buf, 0, len);
				}
			} else {
				logger.error("File source path or File destination path is empty in copyFile()");
			}
		} catch (Exception e) {
			logger.error("Exception : " + ShowMessage.getStackTrace(e));
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					logger.error("Exception :" + ShowMessage.getStackTrace(e));
				}
			}
			if (outputStream != null) {
				try {
					outputStream.close();
				} catch (IOException e) {
					logger.error("Exception :" + ShowMessage.getStackTrace(e));
				}
			}
		}
	}

	/**
	 * To copy the necessary files when user is created
	 * 
	 * @param destination
	 * @param port
	 * @return
	 * @throws Exception
	 */
	public boolean copyUserFiles(HttpServletRequest request, String destination, int port) throws Exception {
		logger.info("Entered copyFiles method" + destination);
		if (destination != null && !destination.isEmpty()) {
			copyFile(automationBaseLocation + "\\src\\TestDriver.jar", destination + "\\TestDriver.jar");
			copyFile(automationBaseLocation + "\\src\\LogDefects.jar", destination + "\\LogDefects.jar");
			copyFile(automationBaseLocation + "\\src\\convertResultToHtml.vbs",
					destination + "\\convertResultToHtml.vbs");
			copyFile(automationBaseLocation + "\\src\\convertResultToPdf.vbs",
					destination + "\\convertResultToPdf.vbs");
			copyFile(
					automationBaseLocation + "\\src\\Progress\\"
							+ PropertyFileEnum.MESSAGE_PROPERTY_FILENAME.getProperty() + ".properties",
					destination + "\\" + PropertyFileEnum.MESSAGE_PROPERTY_FILENAME.getProperty() + ".properties");
			copyFile(
					automationBaseLocation + "\\src\\" + PropertyFileEnum.PROGRESS_PROPERTY_FILENAME.getProperty()
							+ ".properties",
					destination + "\\" + PropertyFileEnum.PROGRESS_PROPERTY_FILENAME.getProperty() + ".properties");

			String folderPath = createNodeServerFolder(request, destination, port);
			if (folderPath != null && !folderPath.isEmpty()) {
				compressFile(folderPath);
				deleteCopiedFolder(folderPath);
			}
			logger.info("Copied successfuly ");
			return true;
		}
		return false;
	}

	/**
	 * To create folders when project is created
	 * 
	 * @param folderName
	 * @return
	 * @throws Exception
	 */
	public boolean createFoldersForProject(String folderName) throws Exception {
		logger.info(" entered createFoldersForProject method");
		if (folderName != null && !folderName.isEmpty()) {
			createFolder(folderName.concat("\\" + ProjectFolderEnum.MASTERPLAN_BACKUP.getFolderName()));
			createFolder(folderName.concat("\\" + ProjectFolderEnum.RESULTS.getFolderName()));
			createFolder(folderName.concat("\\" + ProjectFolderEnum.RUNPLANS.getFolderName()));
			createFolder(folderName.concat("\\" + ProjectFolderEnum.SCREENSHOTS.getFolderName()));
			createFolder(folderName.concat("\\" + ProjectFolderEnum.LOGS.getFolderName()));
			return true;
		} else {
			logger.error("Folder name is empty in createFoldersForProject method");
			return false;
		}
	}

	/**
	 * To get a folder structure when organization is created
	 * 
	 * @param orgName
	 * @return
	 */
	public String getFolderNameForOrganization(String orgName) throws Exception {
		String base = "";
		if (orgName != null && !orgName.trim().isEmpty()) {
			base = esaipBaseLocation.concat("\\" + orgName);
		}
		return base;
	}

	/**
	 * To get a folder structure when user is created
	 * 
	 * @param orgName
	 * @return
	 */
	public String getFolderNameForUsers(String orgName) throws Exception {
		String base = "";
		if (orgName != null && !orgName.trim().isEmpty()) {
			base = esaipBaseLocation.concat("\\" + orgName);
			String userFolder = getProjectDataProperty("userFolderKey");
			base = base.concat("\\" + userFolder);
		}
		return base;
	}

	/**
	 * To get a folder structure when project is created
	 * 
	 * @param orgName
	 * @return
	 */
	public String getFolderNameForProjects(String orgName) throws Exception {
		String base = "";
		if (orgName != null && !orgName.trim().isEmpty()) {
			base = esaipBaseLocation.concat("\\" + orgName);
			String projectFolder = getProjectDataProperty("projectFolderKey");
			base = base.concat("\\" + projectFolder);
		}
		return base;
	}

	/**
	 * To fetch the folder location of src folder of the generic automation folder
	 * 
	 * @param key
	 * @return
	 */
	public String getProjectDataProperty(String key) {
		Properties prop = null;
		try {
			if (key != null && !key.trim().isEmpty()) {
				prop = new Properties();
				InputStream inStr = this.getClass().getClassLoader()
						.getResourceAsStream(PropertyFileEnum.PROJECT_DATA_PROPERTY_FILE.getProperty() + ".properties");
				prop.load(inStr);
			} else {
				logger.error("Key value is empty in getFolderLocation method");
			}
		} catch (IOException e) {
			logger.error("Error fetching the properties file");
		}
		return prop.getProperty(key);
	}

	/**
	 * To create a node server folder which is needed to zip whuile user creation
	 * 
	 * @param destination
	 * @param userPort
	 * @param windowsPort
	 * @return
	 * @throws Exception
	 */
	private String createNodeServerFolder(HttpServletRequest request, String destination, int port) throws Exception {
		String folderPath = "";
		String folderName = "";
		if (destination == null || destination.isEmpty()) {
			return folderPath;
		}
		folderName = destination.concat("\\NodeserverBundle1.0");
		folderPath = createFolder(folderName);

		if (folderPath != null && !folderPath.trim().isEmpty()) {
			copyFile(automationBaseLocation + "\\src\\Repository\\resources\\chromedriver.exe",
					folderPath + "\\chromedriver.exe");
			copyFile(automationBaseLocation + "\\src\\Repository\\resources\\NodeServer.jar",
					folderPath + "\\NodeServer.jar");
			logger.info("Server Ip in createNodeServerFolder is : "+serverIp);
			createNodeServerBatFile(request, serverIp, folderPath, port);
		}
		return folderPath;
	}

	/**
	 * To create nodeserver.bat file while user creation
	 * 
	 * @param userPort
	 * @param windowsPort
	 * @param folder
	 * @return
	 */
	private String createNodeServerBatFile(HttpServletRequest request, String hubIP,String folder, int port) {
		String data = "";
		if (folder == null || folder.isEmpty()) {
			return data;
		}
		String userPort = String.valueOf(port);
		data = "@echo off \n set SCRIPT=\"%TEMP%\\%RANDOM%-%RANDOM%-%RANDOM%-%RANDOM%o.vbs\" "
				+ " \n echo Set oWS = WScript.CreateObject(\"WScript.Shell\") >> %SCRIPT%  "
				+ "\n echo location = oWS.currentdirectory >> %SCRIPT%"
				+ " \n echo Set fso = CreateObject(\"Scripting.FileSystemObject\") >> %SCRIPT% "
				+ "\n echo If Not fso.FileExists(\"%USERPROFILE%\\Estuatelogo.ico\") Then fso.CopyFile location+\"\\Estuatelogo.ico\", \"%USERPROFILE%\\Estuatelogo.ico\", True  End If >> %SCRIPT% "
				+ "\n echo sLinkFile = \"%USERPROFILE%\\Desktop\\NodeServer.lnk\" >> %SCRIPT% "
				+ "\n echo Set oLink = oWS.CreateShortcut(sLinkFile) >> %SCRIPT% "
				+ "\n echo oLink.TargetPath = location+\"\\NodeServer.exe\" >> %SCRIPT% "
				+ "\n echo oLink.IconLocation = \"%USERPROFILE%\\Estuatelogo.ico,0\" >> %SCRIPT% "
				+ "\n echo oLink.Save >> %SCRIPT% " + "\n cscript /nologo %SCRIPT% " + "\n del %SCRIPT% "
				+ "\n java -Dwebdriver.chrome.driver=\"%~dp0\\chromedriver.exe\" -jar \"%~dp0\\NodeServer.jar\" -role node -hub http://"
				+ hubIP + ":" + userPort + "/grid/register -browser browserName=chrome, platform=WINDOWS & pause";
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(folder.concat("\\NodeServer.bat"));
			out.write(data.getBytes());
		} catch (Exception e) {
			logger.error("Exception :" + ShowMessage.getStackTrace(e));
		} finally {
			try {
				out.close();
			} catch (IOException e) {
				logger.error("Exception :" + ShowMessage.getStackTrace(e));
			}
		}
		return data;
	}

	/**
	 * Used to zip the folder
	 * 
	 * @param dirPath
	 * @return
	 * @throws Exception
	 */
	private String compressFile(String dirPath) throws Exception {
		String zipDirName = "";
		if (dirPath == null || dirPath.trim().isEmpty()) {
			return zipDirName;
		}
		File dir = new File(dirPath);
		zipDirName = dir.getAbsolutePath().concat(".zip");
		FileOutputStream fos = new FileOutputStream(zipDirName);
		ZipOutputStream zos = new ZipOutputStream(fos);
		if (dir.isFile()) {
			zos = new ZipOutputStream(new BufferedOutputStream(fos));
			InputStream fis = new FileInputStream(dir);
			ZipEntry ze = new ZipEntry(dir.getName());
			logger.info("Zipping the file: " + dir.getName());
			zos.putNextEntry(ze);
			byte[] tmp = new byte[4 * 1024];
			int size = 0;
			while ((size = fis.read(tmp)) != -1) {
				zos.write(tmp, 0, size);
			}
			zos.flush();
			fis.close();
		} else {
			List<String> subFiles = populateFilesList(dir);
			for (String filePath : subFiles) {
				// use the relative path
				ZipEntry ze = new ZipEntry(filePath.substring(dir.getAbsolutePath().length() + 1, filePath.length()));
				zos.putNextEntry(ze);
				InputStream fis = new FileInputStream(filePath);
				byte[] buffer = new byte[1024];
				int len;
				while ((len = fis.read(buffer)) > 0) {
					zos.write(buffer, 0, len);
				}
				zos.closeEntry();
				fis.close();
			}
		}
		zos.close();
		fos.close();
		return zipDirName;
	}

	/**
	 * Populate all the files in a directory to a List
	 * 
	 * @param dir
	 * @throws Exception
	 */
	private List<String> populateFilesList(File dir) throws Exception {
		if (dir == null) {
			return null;
		}
		File[] files = dir.listFiles();
		List<String> subFiles = new ArrayList<String>();
		if (files != null && files.length > 0) {
			for (File file : files) {
				if (file.isFile())
					subFiles.add(file.getAbsolutePath());
				else
					populateFilesList(file);
			}
		}
		return subFiles;
	}

	/**
	 * To delete the folder after it is compressed
	 * 
	 * @param filePath
	 * @throws Exception
	 */
	public boolean deleteCopiedFolder(String filePath) throws Exception {  //TODO
		if (filePath == null || filePath.trim().isEmpty()) {
			return false;
		}
		File folder = new File(filePath);
		if (folder.exists()) {
			File[] listOfFiles = folder.listFiles();
			if (listOfFiles != null) {
				for (File file : listOfFiles) {
					if (file.isDirectory()) {
//						File[] subFiles = file.listFiles();
//						if (subFiles != null) {
//							for (File subFile : subFiles) {
//								if (subFile.isFile()) {
//									subFile.delete();
//								}
//							}
//							file.delete();
//						}
						deleteCopiedFolder(file.getAbsolutePath());
						
					} else if (file.isFile()) {
						System.out.println(" File to be deleted >>>>>>>>>>> : "+ file.getName());
						file.delete();
						System.out.println(" File deleted >>>>>>>>>>>> : "+ file.getName());
					}
				}
			}
			System.out.println("Folder length >>> "+folder.length() + ">>> length >>>" +folder.listFiles().length);
			System.out.println("Folder >>> "+folder.getName() + "exists >>> "+ folder.exists() );
			if (folder.exists()) {
				System.out.println("Enetred if block " + folder.exists());
				folder.delete();
				System.out.println(" if block " + folder.exists());
				if(folder.exists()){
					for(File file : folder.listFiles()){
						if(file.isFile()){
							file.delete();
						}
					}
					System.out.println("************* folder length "+ folder.length());
					if(folder.length() == 0){
						folder.delete();
					}
				}
				System.out.println("************** exists"+folder.exists());
			}
		}
		if (folder.exists()) {
			System.out.println("Enetred 2nd if block " + folder.exists());
			return false;
		} else {
			System.out.println("Enetred 2nd else block " + folder.exists());
			return true;
		}
	}

	/**
	 * 
	 * @param src
	 * @param dest
	 * @throws IOException
	 */
	public void copyDirectory(File src, File dest) throws Exception {
		if (src.isDirectory()) {
			// if directory not exists, create it
			if (!dest.exists()) {
				dest.mkdir();
				System.out.println("Directory copied from " + src + "  to " + dest);
			}
			// list all the directory contents
			String files[] = src.list();
			for (String file : files) {
				// construct the src and dest file structure
				File srcFile = new File(src, file);
				File destFile = new File(dest, file);
				// recursive copy
				copyDirectory(srcFile, destFile);
			}
		} else {
			// if file, then copy it
			// Use bytes stream to support all file types
			InputStream in = new FileInputStream(src);
			OutputStream out = new FileOutputStream(dest);
			byte[] buffer = new byte[1024];
			int length;
			// copy the file content in bytes
			while ((length = in.read(buffer)) > 0) {
				out.write(buffer, 0, length);
			}
			in.close();
			out.close();
			System.out.println("File copied from " + src + " to " + dest);
		}
	}

	/**
	 * To get the property file value provided file name and the key name
	 * 
	 * @param propertyFileName
	 * @param key
	 * @return
	 */
	public String getPropertyValue(String propertyFileName, String key) throws Exception { // unused method
		Properties prop = null;
		try {
			if (key != null && !key.trim().isEmpty()) {
				prop = new Properties();
				InputStream inStr = this.getClass().getClassLoader()
						.getResourceAsStream(propertyFileName + ".properties");
				prop.load(inStr);
			} else {
				logger.error("Key value is empty in getFolderLocation method");
			}
		} catch (IOException e) {
			logger.error("Exception : " + ShowMessage.getStackTrace(e));
		}
		return prop.getProperty(key);
	}
	
	/**
	 * 
	 * @param loginAttempts
	 * @param user
	 * @param request
	 * @return
	 */
	public int updateAttemptCount(UserDetail userToUpdate, HttpServletRequest request) throws Exception {
		List<SuperEntity> attemptCountEntityList = null;
		AttemptHistory attemptHistoryEntity = null;
		AttemptCount attemptCountEntity = null;
		int returnCount = 0;
		if (userToUpdate != null) {
			// checks whether the user already made an attempt
			attemptCountEntityList = (List<SuperEntity>) applicationService.getAttemptByUserId(AttemptCount.class,
					userToUpdate.getId());

			if (attemptCountEntityList == null || attemptCountEntityList.isEmpty()) {
				attemptCountEntity = getAttemptCountEntity(request.getSession().getId(), userToUpdate, 1);
				if (attemptCountEntity != null) {
					applicationService.addEntity(attemptCountEntity);
				} else {
					logger.error(ShowMessage.ATTEMPTCOUNT_OBJECT_EMPTY);
					returnCount = 0;
				}
				attemptHistoryEntity = getAttemptHistoryEntity(request.getSession().getId(), userToUpdate, 1);
				if (attemptHistoryEntity != null) {
					applicationService.addEntity(attemptHistoryEntity);
				} else {
					logger.error(ShowMessage.ATTEMPTHISTORY_OBJECT_EMPTY);
					returnCount = 0;
				}
				return returnCount = MAX_ATTEMPT_COUNT - 1;

			} else {
				String rsId = request.getSession().getId();
				int loginAttempts = applicationService.getTotalLoginAttempts(AttemptCount.class, userToUpdate);

				// Getting attempt count based on session ID
				AttemptCount attemptCount = (AttemptCount) applicationService
						.getAttemptEntityBySessionId(AttemptCount.class, rsId, userToUpdate.getId());
				if (loginAttempts != 0 && loginAttempts < MAX_ATTEMPT_COUNT) {
					if (attemptCount == null) {
						attemptCountEntity = getAttemptCountEntity(rsId, userToUpdate, 1);
						applicationService.addEntity(attemptCountEntity);
					} else {
						// Updating attemptCountEntity
						attemptCount.setAttemptCount(attemptCount.getAttemptCount() + 1);
						attemptCount.setClientDate(new Date());
						attemptCount.setServerDate(new Date());
						applicationService.updateEntity(attemptCount);
					}
					// Getting attempt history based on session ID
					AttemptHistory attemptHistory = (AttemptHistory) applicationService
							.getAttemptEntityBySessionId(AttemptHistory.class, rsId, userToUpdate.getId());
					if (attemptHistory == null) {
						// add new row to attempt count and history
						attemptHistoryEntity = getAttemptHistoryEntity(rsId, userToUpdate, 1);
						applicationService.addEntity(attemptHistoryEntity);
					} else {
						// Adding to attempt History table
						attemptHistory.setAttemptCount(attemptHistory.getAttemptCount() + 1);
						attemptHistory.setClientDate(new Date());
						attemptHistory.setServerDate(new Date());
						applicationService.updateEntity(attemptHistory);
					}
					for (int i = 1; i < MAX_ATTEMPT_COUNT; i++) {
						if (i == loginAttempts) {
							returnCount = MAX_ATTEMPT_COUNT - (i + 1);
						}
					}
				}
				if (loginAttempts == MAX_ATTEMPT_COUNT) {
					return MAX_ATTEMPT_COUNT;
				}
			}
		}
		return returnCount;
	}

	/**
	 * 
	 * @param sessionId
	 * @param user
	 * @param loginAttempts
	 * @return
	 */
	private AttemptCount getAttemptCountEntity(String sessionId, UserDetail user, int loginAttempts) throws Exception {
		AttemptCount attemptCountEntity = null;
		if (sessionId == null || sessionId.trim().isEmpty() || user == null || loginAttempts <= 0) {
			return attemptCountEntity;
		}
		attemptCountEntity = new AttemptCount();
		attemptCountEntity.setSessionId(sessionId);
		attemptCountEntity.setAttemptCount(loginAttempts);
		attemptCountEntity.setClientDate(new Date());
		attemptCountEntity.setServerDate(new Date());
		attemptCountEntity.setUser(user);
		return attemptCountEntity;

	}

	/**
	 * 
	 * @param sessionId
	 * @param user
	 * @param loginAttempts
	 * @return
	 */
	private AttemptHistory getAttemptHistoryEntity(String sessionId, UserDetail user, int loginAttempts)
			throws Exception {
		Organization org = null;
		AttemptHistory attemptHistory = null;
		if (sessionId == null || sessionId.trim().isEmpty() || user == null || loginAttempts <= 0) {
			return attemptHistory;
		}
		List<Integer> orgList = applicationService.getAssociatedList(AssociationTypeEnum.UOA.toString(), user.getId(),
				0);
		if (orgList != null && !orgList.isEmpty()) {
			org = (Organization) applicationService.getEntityById(Organization.class, orgList.get(0));
		} else {
			logger.warn(ShowMessage.ORGANIZATION_LIST_NULL);
		}
		attemptHistory = new AttemptHistory();
		attemptHistory.setSessionId(sessionId);
		attemptHistory.setAttemptCount(loginAttempts);
		attemptHistory.setOrganization(org);
		attemptHistory.setClientDate(new Date());
		attemptHistory.setServerDate(new Date());
		attemptHistory.setUser(user);
		return attemptHistory;
	}

	/**
	 * 
	 * @param user
	 * @return
	 */
	public String deleteAttemptCount(UserDetail user) throws Exception {
		String result = "";
		boolean deleted = false;
		if (user == null) {
			return result;
		}
		List<SuperEntity> attemptCountEntityList = (List<SuperEntity>) applicationService
				.getAttemptByUserId(AttemptCount.class, user.getId());
		List<Integer> attemptCountIdList = new ArrayList<Integer>();
		if (attemptCountEntityList != null && !attemptCountEntityList.isEmpty()) {
			for (SuperEntity attemptCount : attemptCountEntityList) {
				AttemptCount entity = (AttemptCount) attemptCount;
				// Delete in attemptCount table but not in attempt history table
				attemptCountIdList.add(entity.getUser().getId());
			}
			deleted = applicationService.deleteEntityFromIdList(AttemptCount.class, attemptCountIdList);
			if (deleted) {
				result = "success";
			} else {
				result = "failure";
			}
			return result;
		}
		return result;
	}

	/**
	 * 
	 * @param orgName
	 * @param userName
	 * @param userID
	 * @param request
	 * @throws Exception
	 */
	public void runHubServer(String orgName, String userName, int userID, HttpServletRequest request)
			throws Exception {
		logger.info("Entered runHubServer method");
		String drive = automationBaseLocation.substring(0, automationBaseLocation.indexOf("\\"));
		String folder = automationBaseLocation + "\\src\\Repository\\resources";
		String port = (String) request.getSession().getAttribute("userPort");
		execute.updateProgressProperty(orgName, userName, "ServerPort", port, request);
		String cmd = "cmd.exe /c cd\\ & " + drive + " & cd " + folder + " & java -jar HubServer.jar -port " + port
				+ " -role hub -nodeTimeout 5000 &  pause";   
		try {
			Runtime.getRuntime().exec(cmd);
			logger.info("Executed runHubServer method successfully.");
		} catch (Exception e) {
			logger.error("Exception : " + ShowMessage.getStackTrace(e));
		}
	}
	

	/**
	 * 
	 * @param emailId
	 * @param resp
	 * @throws IOException
	 */
	public Map<String, List<String>> getOrgDropDownValues() throws Exception {
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		List<String> orgTypeList = null;
		List<String> billingRateList = null;
		List<String> billingPeriodList = null;
		List<String> billingTypeList = null;
		List<String> billingCurrencyList = null;
		List<String> licenceNumList = null;
		try {
			Properties prop = getProperties(PropertyFileEnum.UTILITY_PROPERTY_FILENAME.getProperty() + ".properties");
			if (prop.entrySet() != null && !prop.entrySet().isEmpty()) {
				for (Map.Entry<Object, Object> entry : prop.entrySet()) {
					if (entry.getKey().equals("organizationType")) {
						String str = (String) entry.getValue();
						orgTypeList = Arrays.asList(str.split("\\s*,\\s*"));
					}
					if (entry.getKey().equals("billingRate")) {
						String str = (String) entry.getValue();
						billingRateList = Arrays.asList(str.split("\\s*,\\s*"));
					}
					if (entry.getKey().equals("billingPeriod")) {
						String str = (String) entry.getValue();
						billingPeriodList = Arrays.asList(str.split("\\s*,\\s*"));
					}
					if (entry.getKey().equals("billingType")) {
						String str = (String) entry.getValue();
						billingTypeList = Arrays.asList(str.split("\\s*,\\s*"));
					}
					if (entry.getKey().equals("billingCurrency")) {
						String str = (String) entry.getValue();
						billingCurrencyList = Arrays.asList(str.split("\\s*,\\s*"));
					}
					if (entry.getKey().equals("totalNumOfLicences")) {
						String str = (String) entry.getValue();
						licenceNumList = Arrays.asList(str.split("\\s*,\\s*"));
					}
				}
				map.put("orgTypeList", orgTypeList);
				map.put("billingRateList", billingRateList);
				map.put("billingPeriodList", billingPeriodList);
				map.put("billingTypeList", billingTypeList);
				map.put("billingCurrencyList", billingCurrencyList);
				map.put("licenceNumList", licenceNumList);
			}
		} catch (Exception e) {
			logger.error("Exception : " + ShowMessage.getStackTrace(e));
		}
		return map;
	}

	/**
	 * 
	 * @param userDto
	 * @param request
	 * @return
	 */
	public UserDetail toUserDetailEntity(UserDetailDTO userDto, HttpServletRequest request) throws Exception {
		UserDetail userDetail = null;
		try {
			userDetail = new UserDetail();
			if (userDto != null) {
				UserType userType = null;
				if (userDto.getUserType() != 0) {
					userType = (UserType) applicationService.getEntityById(UserType.class, userDto.getUserType());
				}
				if (userDto.getUserName() == null || userDto.getUserName().trim().isEmpty()) {
					userDto.setUserName(userDto.getFirstName().concat(" " + userDto.getLastName()));
				}
				userDetail.setId(userDto.getId());
				userDetail.setFirstName(userDto.getFirstName());
				userDetail.setLastName(userDto.getLastName());
				userDetail.setUserName(userDto.getUserName());
				userDetail.setEmailId(userDto.getEmailId());
				userDetail.setPassword(userDto.getPassword());
				userDetail.setStatus(userDto.getStatus());
				userDetail.setUserType(userType);
				userDetail.setOrganization(userDto.getOrganization());
				userDetail.setProject(userDto.getProject());
				userDetail.setAvailableGroup(userDto.getAvailableGroup());
				userDetail.setAssignedGroup(userDto.getAssignedGroup());
			}
		} catch (Exception e) {
			logger.error("Exception : " + ShowMessage.getStackTrace(e));
		}
		return userDetail;
	}

	/**
	 * 
	 * @param userDetailDto
	 * @param request
	 * @return
	 */
	public UserDetail mapDtoWithCommonFeildsOnCreate(UserDetailDTO userDetailDto, HttpServletRequest request)
			throws Exception {
		UserDetail userDetail = null;
		if (userDetailDto != null) {
			try {
				userDetail = toUserDetailEntity(userDetailDto, request);
				if (userDetail != null) {
					userDetail.setCreatedBy((String) request.getSession().getAttribute("userName"));
					userDetail.setCreatedDate(new Date());
					userDetail.setModifiedBy((String) request.getSession().getAttribute("userName"));
					userDetail.setModifiedDate(new Date());
				}
			} catch (Exception e) {
				logger.error("Exception : " + ShowMessage.getStackTrace(e));
			}
		}
		return userDetail;
	}

	/**
	 * 
	 * @param userDetailDto
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public UserDetail mapDtoWithCommonFeildsOnUpdate(UserDetailDTO userDetailDto, HttpServletRequest request)
			throws Exception {
		UserDetail userDetail = null;
		if (userDetailDto != null) {
			try {
				userDetail = toUserDetailEntity(userDetailDto, request);
				if (userDetail != null) {
					userDetail.setCreatedBy(userDetailDto.getCreatedBy());
					userDetail.setCreatedDate(userDetailDto.getCreatedDate());
					userDetail.setModifiedBy((String) request.getSession().getAttribute("userName"));
					userDetail.setModifiedDate(new Date());
				}
			} catch (Exception e) {
				logger.error("Exception : " + ShowMessage.getStackTrace(e));
			}
		}
		return userDetail;
	}

	/**
	 * 
	 * @param user
	 * @param userId
	 * @return
	 */
	public Map<String, Object> addUOA(UserDetailDTO user, int userId) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		boolean isUOACreated = false;
		boolean isCompleteCreateUser = true;
		List<Association> associationListToremove = new ArrayList<Association>();
		try {
			Organization org = (Organization) applicationService.getEntityByName(Organization.class, user.getOrganization());
			Association association = null;
			if (org != null) {
				association = new Association(AssociationTypeEnum.UOA.toString(), org.getId(), userId);
				isUOACreated = applicationService.addEntity(association);
				if (isUOACreated) {
					associationListToremove.add(association);
				} else {
					isCompleteCreateUser = false;
				}
				logger.info("Association UOA added");
			}
			resultMap.put("ASSOCIATION_TO_BE_DELETED", associationListToremove);
			resultMap.put("PROCEED_CREATE_USER", isCompleteCreateUser);
		} catch (Exception e) {
			logger.error("Exception : " + ShowMessage.getStackTrace(e));
		}
		return resultMap;
	}

	/**
	 * 
	 * @param userDto
	 * @param userId
	 * @param associationListToremove
	 * @return
	 */
	public Map<String, Object> addUGA(UserDetailDTO userDto, int userId, List<Association> associationListToremove)
			throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		boolean isUGACreated = false;
		boolean isCompleteCreateUser = true;
		try {
			List<String> assignedGroupList = userDto.getAssignedGroup();
			Association association = null;
			if (assignedGroupList != null && !assignedGroupList.isEmpty()) {
				for (String groupName : assignedGroupList) {
					Group group = (Group) applicationService.getEntityByName(Group.class, groupName);
					if (group != null) {
						association = new Association(AssociationTypeEnum.UGA.toString(), group.getId(), userId);
						isUGACreated = applicationService.addEntity(association);
					}
					if (isUGACreated) {
						if (associationListToremove == null) {
							associationListToremove = new ArrayList<Association>();
							associationListToremove.add(association);
						}
						associationListToremove.add(association);
					} else {
						isCompleteCreateUser = false;
					}
					logger.info("association UGA added");
				}
			}
			resultMap.put("ASSOCIATION_TO_BE_DELETED", associationListToremove);
			resultMap.put("PROCEED_CREATE_USER", isCompleteCreateUser);
		} catch (Exception e) {
			logger.error("Exception : " + ShowMessage.getStackTrace(e));
		}
		return resultMap;
	}

	/**
	 * 
	 * @param userDto
	 * @param userId
	 * @param associationListToremove
	 * @return
	 */
	public Map<String, Object> addUPA(UserDetailDTO userDto, int userId, List<Association> associationListToremove)
			throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		boolean isUPACreated = false;
		boolean isCompleteCreateUser = true;
		Association association = null;
		try {
			List<String> projectList = userDto.getProject();
			if (projectList != null && !projectList.isEmpty()) {
				for (String projectName : projectList) {
					Project project = (Project) applicationService.getEntityByName(Project.class, projectName);
					if (project != null) {
						association = new Association(AssociationTypeEnum.UPA.toString(), project.getId(), userId);
						isUPACreated = applicationService.addEntity(association);
					}
					if (isUPACreated) {
						if (associationListToremove == null) {
							associationListToremove = new ArrayList<Association>();
							associationListToremove.add(association);
						}
						associationListToremove.add(association);
					} else {
						isCompleteCreateUser = false;
					}
					logger.info("Association UPA added");
				}
			}
			resultMap.put("ASSOCIATION_TO_BE_DELETED", associationListToremove);
			resultMap.put("PROCEED_CREATE_USER", isCompleteCreateUser);
		} catch (Exception e) {
			logger.error("Exception : " + ShowMessage.getStackTrace(e));
		}
		return resultMap;
	}

	/**
	 * 
	 * @param user
	 * @return
	 */
	public Map<String, Object> checkForUpdateUOA(UserDetailDTO user) throws Exception {
		Organization existingOrg = null;
		Map<String, Object> resultMap = new HashMap<String, Object>();
		boolean updateUOA = false;
		try {
			List<Integer> orgList = applicationService.getAssociatedList(AssociationTypeEnum.UOA.toString(), user.getId(), 0);
			if (orgList != null && !orgList.isEmpty()) {
				existingOrg = (Organization) applicationService.getEntityById(Organization.class, orgList.get(0));
				if (existingOrg.getName() != null && !existingOrg.getName().trim().isEmpty()) {
					if (!existingOrg.getName().equalsIgnoreCase(user.getOrganization())) {
						updateUOA = true;
					}
				}
			}
			resultMap.put("EXISTING_ORG", existingOrg);
			resultMap.put("IS_UPDATE_UOA", updateUOA);
		} catch (Exception e) {
			logger.error("Exception : " + ShowMessage.getStackTrace(e));
		}
		return resultMap;
	}

	/**
	 * 
	 * @param user
	 * @return
	 */
	public Map<String, Object> checkForUpdateUGA(UserDetailDTO user) throws Exception {
		List<Group> currentGroupList = new ArrayList<Group>();
		List<Group> existingGroupList = new ArrayList<Group>();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		boolean updateUGA = false;
		try {
			List<Integer> existingGroupIdList = applicationService.getAssociatedList(AssociationTypeEnum.UGA.toString(),
					user.getId(), 0);
			if (existingGroupIdList != null && !existingGroupIdList.isEmpty()) {
				for (int id : existingGroupIdList) {
					Group group = (Group) applicationService.getEntityById(Group.class, id);
					existingGroupList.add(group);
				}
			}
			if (user.getAssignedGroup() != null && !user.getAssignedGroup().isEmpty()) {
				for (String groupName : user.getAssignedGroup()) {
					Group group = (Group) applicationService.getEntityByName(Group.class, groupName);
					currentGroupList.add(group);
				}
			}
			if (!existingGroupList.equals(currentGroupList)) {
				updateUGA = true;
			}
			resultMap.put("EXISTING_GROUP_LIST", existingGroupList);
			resultMap.put("CURRENT_GROUP_LIST", currentGroupList);
			resultMap.put("IS_UPDATE_UGA", updateUGA);
		} catch (Exception e) {
			logger.error("Exception : " + ShowMessage.getStackTrace(e));
		}
		return resultMap;
	}

	/**
	 * 
	 * @param user
	 * @return
	 */
	public Map<String, Object> checkForUpdateUPA(UserDetailDTO user) throws Exception {
		List<Project> currentProjectList = new ArrayList<Project>();
		List<Project> existingProjectList = new ArrayList<Project>();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		boolean updateUPA = false;
		try {
			List<Integer> existingProjectIdList = applicationService.getAssociatedList(AssociationTypeEnum.UPA.toString(),
					user.getId(), 0);
			if (existingProjectIdList != null && !existingProjectIdList.isEmpty()) {
				for (int id : existingProjectIdList) {
					Project group = (Project) applicationService.getEntityById(Project.class, id);
					existingProjectList.add(group);
				}
			}
			if (user.getProject() != null && !user.getProject().isEmpty()) {
				for (String projectName : user.getProject()) {
					Project project = (Project) applicationService.getEntityByName(Project.class, projectName);
					currentProjectList.add(project);
				}
			}
			if (!existingProjectList.equals(currentProjectList)) {
				updateUPA = true;
			}
			resultMap.put("EXISTING_PROJECT_LIST", existingProjectList);
			resultMap.put("CURRENT_PROJECT_LIST", currentProjectList);
			resultMap.put("IS_UPDATE_UPA", updateUPA);
		} catch (Exception e) {
			logger.error("Exception : " + ShowMessage.getStackTrace(e));
		}
		return resultMap;
	}

	/**
	 * 
	 * @param user
	 * @param existingOrg
	 * @return
	 */
	public Map<String, Object> updateUOA(UserDetailDTO user, Organization existingOrg) throws Exception {
		boolean isUOADeleted = false;
		boolean proceedUpdateUser = true;
		boolean isUOAUpdated = false;
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<Association> associationTobeDeletedList = new ArrayList<Association>();
		List<Association> associationTobeAddedList = new ArrayList<Association>();
		try {
			Association association = applicationService.getAssociation(AssociationTypeEnum.UOA.toString(), user.getId(),
					existingOrg.getId());
			if (association != null) {
				isUOADeleted = applicationService.deleteEntity(association);
				if (isUOADeleted) {
					associationTobeAddedList.add(association);
				} else {
					proceedUpdateUser = false;
				}
				Organization org = (Organization) applicationService.getEntityByName(Organization.class,
						user.getOrganization());
				association = new Association(AssociationTypeEnum.UOA.toString(), org.getId(), user.getId());
				isUOAUpdated = applicationService.addEntity(association);
				if (isUOAUpdated) {
					associationTobeDeletedList.add(association);
				} else {
					proceedUpdateUser = false;
				}
				logger.info("association UOA Updated");
			}
			resultMap.put("ASSOCIATION_TO_BE_ADDED", associationTobeAddedList);
			resultMap.put("ASSOCIATION_TO_BE_DELETED", associationTobeDeletedList);
			resultMap.put("PROCEED_UPDATE_USER", proceedUpdateUser);
		} catch (Exception e) {
			logger.error("Exception : " + ShowMessage.getStackTrace(e));
		}
		return resultMap;
	}

	/**
	 * 
	 * @param user
	 * @param currentGroupList
	 * @param existingGroupList
	 * @param associationTobeDeletedList
	 * @param associationTobeAddedList
	 * @return
	 */
	public Map<String, Object> updateUGA(UserDetailDTO user, List<Group> currentGroupList,
			List<Group> existingGroupList, List<Association> associationTobeDeletedList,
			List<Association> associationTobeAddedList) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		boolean isUGADeleted = false;
		boolean proceedUpdateUser = true;
		boolean isUGAUpdated = false;
		try {
			if (existingGroupList != null && !existingGroupList.isEmpty()) {
				for (Group group : existingGroupList) {
					Association association = applicationService.getAssociation(AssociationTypeEnum.UGA.toString(),
							user.getId(), group.getId());
					isUGADeleted = applicationService.deleteEntity(association);
					if (isUGADeleted) {
						if (associationTobeAddedList == null) {
							associationTobeAddedList = new ArrayList<Association>();
							associationTobeAddedList.add(association);
						}
						associationTobeAddedList.add(association);
					} else {
						proceedUpdateUser = false;
					}
				}
			}
			if (currentGroupList != null && !currentGroupList.isEmpty()) {
				for (Group group : currentGroupList) {
					Association association = new Association(AssociationTypeEnum.UGA.toString(), group.getId(),
							user.getId());
					isUGAUpdated = applicationService.addEntity(association);
					if (isUGAUpdated) {
						if (associationTobeDeletedList == null) {
							associationTobeDeletedList = new ArrayList<Association>();
							associationTobeDeletedList.add(association);
						}
						associationTobeDeletedList.add(association);
					} else {
						proceedUpdateUser = false;
					}
					logger.info("association UGA added in update");
				}
			}
			resultMap.put("ASSOCIATION_TO_BE_ADDED", associationTobeAddedList);
			resultMap.put("ASSOCIATION_TO_BE_DELETED", associationTobeDeletedList);
			resultMap.put("PROCEED_UPDATE_USER", proceedUpdateUser);
		} catch (Exception e) {
			logger.error("Exception : " + ShowMessage.getStackTrace(e));
		}
		return resultMap;
	}

	/**
	 * 
	 * @param user
	 * @param currentProjectList
	 * @param existingProjectList
	 * @param associationTobeDeletedList
	 * @param associationTobeAddedList
	 * @return
	 */
	public Map<String, Object> updateUPA(UserDetailDTO user, List<Project> currentProjectList,
			List<Project> existingProjectList, List<Association> associationTobeDeletedList,
			List<Association> associationTobeAddedList) throws Exception {
		boolean isUPADeleted = false;
		boolean proceedUpdateUser = true;
		boolean isUPAUpdated = false;
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			if (existingProjectList != null && !existingProjectList.isEmpty()) {
				for (Project project : existingProjectList) {
					Association association = applicationService.getAssociation(AssociationTypeEnum.UPA.toString(),
							user.getId(), project.getId());
					isUPADeleted = applicationService.deleteEntity(association);
					if (isUPADeleted) {
						if (associationTobeAddedList == null) {
							associationTobeAddedList = new ArrayList<Association>();
							associationTobeAddedList.add(association);
						}
						associationTobeAddedList.add(association);
					} else {
						proceedUpdateUser = false;
					}
				}
			}
			if (currentProjectList != null && !currentProjectList.isEmpty()) {
				for (Project project : currentProjectList) {
					Association association = new Association(AssociationTypeEnum.UPA.toString(), project.getId(),
							user.getId());
					isUPAUpdated = applicationService.addEntity(association);
					if (isUPAUpdated) {
						if (associationTobeDeletedList == null) {
							associationTobeDeletedList = new ArrayList<Association>();
							associationTobeDeletedList.add(association);
						}
						associationTobeDeletedList.add(association);
					} else {
						proceedUpdateUser = false;
					}
					logger.info("association UPA added in update");
				}
			}
			resultMap.put("ASSOCIATION_TO_BE_ADDED", associationTobeAddedList);
			resultMap.put("ASSOCIATION_TO_BE_DELETED", associationTobeDeletedList);
			resultMap.put("PROCEED_UPDATE_USER", proceedUpdateUser);
		} catch (Exception e) {
			logger.error("Exception : " + ShowMessage.getStackTrace(e));
		}
		return resultMap;
	}

	/**
	 * 
	 * @param request
	 * @throws Exception
	 */
	public void updatePropertyOnUserCreate(String orgName, String userName, HttpServletRequest request)
			throws Exception {
		if (orgName != null && !orgName.trim().isEmpty() && userName != null && !userName.trim().isEmpty()) {
			execute.updateProgressProperty(orgName, userName, "frameworkLocation",
					getProjectDataProperty("frameworkLocation"), request);
			execute.updateProgressProperty(orgName, userName, "organizationFolderKey",
					getProjectDataProperty("organizationFolderKey"), request);
			execute.updateProgressProperty(orgName, userName, "projectFolderKey",
					getProjectDataProperty("projectFolderKey"), request);
			execute.updateProgressProperty(orgName, userName, "userFolderKey",
					getProjectDataProperty("userFolderKey"), request);
			execute.updateProgressProperty(orgName, userName, "orgName", orgName, request);
			execute.updateProgressProperty(orgName, userName, "userName", userName, request);
			logger.info("Updating the property file on user creation is successfull.");
		}
	}
	/**
	 * To convert all the first letter of each word in the given string to capital case
	 * @param str
	 * @return
	 */
	public String stringCapital(String str){
		StringBuilder result = new StringBuilder(str.length());
		String words[] = str.split("\\ "); 
		for (int i = 0; i < words.length; i++)
		{			
			result.append(Character.toUpperCase(words[i].charAt(0))).append(words[i].substring(1)).append(" ");
			
		}
		return result.toString().trim();
	}
}
