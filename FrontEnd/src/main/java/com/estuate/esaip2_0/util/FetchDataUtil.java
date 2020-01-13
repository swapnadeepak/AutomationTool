package com.estuate.esaip2_0.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.estuate.esaip2_0.entity.Project;
import com.estuate.esaip2_0.exception.ShowMessage;
import com.estuate.esaip2_0.model.Defects;
import com.estuate.esaip2_0.model.MasterPlan;
import com.estuate.esaip2_0.model.ProjectFolderEnum;
import com.estuate.esaip2_0.model.PropertyFileEnum;
import com.estuate.esaip2_0.model.ResultDetails;
import com.estuate.esaip2_0.model.RunPlan;
import static com.estuate.esaip2_0.util.Constants.*;


/**
 * <ul>
 * <li>Title: FetchDataUtil</li>
 * <li>This Util class is used to fetch data from the excel sheet at specified
 * locations</li>
 * <li>Created by: Nemmar Rajath Bhat</li>
 * <li>Modified By: Radhika GopalRaj</li>
 * </ul>
 */
@Component
public class FetchDataUtil{
	/**
	 * The logger
	 */
	private static final Logger logger = LoggerFactory.getLogger(FetchDataUtil.class);
	/**
	 * The excelUtil
	 */
	@Autowired
	private ExcelUtil excelUtil;
	
	@Autowired
	Util util;

	/**
	 * To fetch the Master Plan details
	 * 
	 * @param request
	 * @return
	 */
	public List<MasterPlan> fetchMasterplan(HttpServletRequest request) {
		String fileName = "";
		FileInputStream fis = null;
		XSSFWorkbook workbook = null;
		List<MasterPlan> masterPlans = new ArrayList<MasterPlan>();
		try {
			String orgFolder = (String) request.getSession().getAttribute("userOrganization");
			Project project = (Project) request.getSession().getAttribute("userProject");
			if (orgFolder != null && !orgFolder.trim().isEmpty() && project != null) {
				fileName = esaipBaseLocation + "\\" + orgFolder + "\\" + util.getProjectDataProperty("projectFolderKey")
						+ "\\" + project.getName() + "\\" + PropertyFileEnum.MASTERPLAN_XLSX_FILENAME.getProperty()
						+ ".xlsx";
			}
			if (fileName != null && !fileName.trim().isEmpty()) {
				File file = new File(fileName);
				if (!file.exists()) {
					return null;
				}
				fis = new FileInputStream(fileName);
				workbook = new XSSFWorkbook(fis);
				XSSFSheet sheet = workbook.getSheet("MasterSheet");
				for (int i = 1; i < sheet.getLastRowNum() + 1; i++) {
					String rowData = "";
					for (int j = 0; j < 12; j++) {
						DataFormatter formatter = new DataFormatter();
						XSSFCell cell = sheet.getRow(i).getCell(j);
						rowData = rowData + formatter.formatCellValue(cell) + " |";
					}

					StringTokenizer st = new StringTokenizer(rowData, "|");
					MasterPlan plan = new MasterPlan();
					plan.setIndex(i);
					plan.setId(st.nextToken().trim());
					plan.setBusinessRequirement(st.nextToken().trim());
					plan.setDescription(st.nextToken().trim());
					plan.setImpactedBRs(st.nextToken().trim());
					plan.setIncludeImpactedBRs(st.nextToken().trim());
					plan.setModules(st.nextToken().trim());
					plan.setModulesInclude(st.nextToken().trim());
					plan.setTestCases(st.nextToken().trim());
					plan.setTestCasesInclude(st.nextToken().trim());
					plan.setTestCaseId(st.nextToken().trim());
					plan.setCriticality(st.nextToken().trim());
					plan.setRepeatablity(st.nextToken().trim());
					masterPlans.add(plan);
				}
				logger.info("Masterplan Fetched from location " + fileName);
			} else {
				logger.error("File name is empty");
			}
		} catch (IOException e) {
			logger.error("Exception :  " + ShowMessage.getStackTrace(e));
		} finally {
			if (workbook != null) {
				try {
					workbook.close();
				} catch (IOException e) {
					logger.error("Exception : " + ShowMessage.getStackTrace(e));
				}
			}
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					logger.error("Exception : " + ShowMessage.getStackTrace(e));
				}
			}
		}
		return masterPlans;
	}

	/**
	 * To fetch the Master Plan details
	 * 
	 * @param fileLocation
	 * @return
	 */
	public List<RunPlan> fetchExcecutionPlan(String fileLocation) {
		List<RunPlan> runPlans = new ArrayList<>();
		XSSFWorkbook workbook = null;
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(fileLocation);
			workbook = new XSSFWorkbook(fis);
			XSSFSheet sheet = workbook.getSheet("RunplanSheet");
			for (int i = sheet.getFirstRowNum() + 1; i < sheet.getLastRowNum() + 1; i++) {
				StringBuilder rowData = new StringBuilder();
				for (int j = 0; j < 10; j++) {
					DataFormatter formatter = new DataFormatter();
					XSSFCell cell = sheet.getRow(i).getCell(j);
					rowData.append(formatter.formatCellValue(cell) + " |");
				}

				StringTokenizer st = new StringTokenizer(rowData.toString(), "|");
				RunPlan plan = new RunPlan();
				plan.setId(st.nextToken().trim());
				plan.setBR(st.nextToken().trim());
				plan.setModule(st.nextToken().trim());
				plan.setTestCase(st.nextToken().trim());
				plan.setTestCaseId(st.nextToken().trim());
				plan.setCriticality(st.nextToken().trim());
				plan.setRepeatablity(st.nextToken().trim());
				plan.setResult(st.nextToken().trim());
				plan.setTimeTaken(st.nextToken().trim());
				plan.setFileName(fileLocation.substring(fileLocation.lastIndexOf('\\') + 1));
				plan.setFileLocation(fileLocation);
				runPlans.add(plan);
			}
		} catch (IOException e) {
			logger.error("Error while fetching Execution Plan" + ShowMessage.getStackTrace(e));
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					logger.error("Exception : " + ShowMessage.getStackTrace(e));
				}
			}
			if (workbook != null) {
				try {
					workbook.close();
				} catch (IOException e) {
					logger.error("Error while closing workbook in fetch execution plan" + ShowMessage.getStackTrace(e));
				}
			}
		}
		logger.info("fetched Executionplan from file: " + fileLocation);
		return runPlans;
	}

	/**
	 * This method fetchRunPlan() is used to fetch the Run Plan from specified
	 * location
	 * 
	 * @param fileLocation
	 * @return
	 */
	public List<RunPlan> fetchRunPlan(String fileLocation) {
		List<RunPlan> runPlans = new ArrayList<>();
		XSSFWorkbook workbook = null;
		try (FileInputStream fis = new FileInputStream(fileLocation)) {
			workbook = new XSSFWorkbook(fis);
			XSSFSheet sheet = workbook.getSheet("RunPlanForDisplay");
			for (int i = sheet.getFirstRowNum() + 1; i < sheet.getLastRowNum() + 1; i++) {
				StringBuilder rowData = new StringBuilder();
				for (int j = 0; j < 10; j++) {
					DataFormatter formatter = new DataFormatter();
					XSSFCell cell = sheet.getRow(i).getCell(j);
					rowData.append(formatter.formatCellValue(cell) + " |");
				}

				StringTokenizer st = new StringTokenizer(rowData.toString(), "|");
				RunPlan plan = new RunPlan();
				plan.setId(st.nextToken().trim());
				plan.setBR(st.nextToken().trim());
				plan.setModule(st.nextToken().trim());
				plan.setTestCase(st.nextToken().trim());
				plan.setTestCaseId(st.nextToken().trim());
				plan.setCriticality(st.nextToken().trim());
				plan.setRepeatablity(st.nextToken().trim());
				plan.setResult(st.nextToken().trim());
				plan.setTimeTaken(st.nextToken().trim());
				plan.setFileName(fileLocation.substring(fileLocation.lastIndexOf('\\') + 1));
				plan.setFileLocation(fileLocation);
				runPlans.add(plan);
			}
		} catch (IOException e) {
			logger.error("Error while fetching Execution Plan" + ShowMessage.getStackTrace(e));
		} finally {
			if (workbook != null) {
				try {
					workbook.close();
				} catch (IOException e) {
					logger.error(
							"Error while closing workbook in fetch runplan method " + ShowMessage.getStackTrace(e));
				}
			}
		}
		logger.info("fetched Executionplan from file: " + fileLocation);
		return runPlans;
	}

	/**
	 * To fetch the Result data from specified location
	 * 
	 * @param fileLocation
	 * @return
	 */
	public List<ResultDetails> fetchResultData(String fileLocation) {
		List<ResultDetails> result = new ArrayList<ResultDetails>();
		FileInputStream fis = null;
		XSSFWorkbook workbook = null;
		try {
			if (fileLocation != null && !fileLocation.trim().isEmpty()) {
				fis = new FileInputStream(fileLocation);
				workbook = new XSSFWorkbook(fis);
				XSSFSheet sheet = workbook.getSheet("Results");
				for (int i = 3; i < sheet.getLastRowNum() + 1; i++) {
					String rowData = "";
					for (int j = 1; j < 3; j++) {
						DataFormatter formatter = new DataFormatter();
						XSSFCell cell = sheet.getRow(i).getCell(j);
						rowData = rowData + formatter.formatCellValue(cell) + " |";
					}

					StringTokenizer st = new StringTokenizer(rowData, "|");
					ResultDetails resultDetail = new ResultDetails();
					resultDetail.setResultkey(st.nextToken().trim());
					resultDetail.setResultvalue(st.nextToken().trim());
					resultDetail.setFileLocation(fileLocation);
					resultDetail.setFileName(fileLocation.substring(fileLocation.lastIndexOf("\\") + 1));
					result.add(resultDetail);
				}
			} else {
				logger.error("File location is empty in fetchResultData()");
			}
		} catch (Exception e) {
			logger.error("Exception : " + ShowMessage.getStackTrace(e));

		} finally {
			if (workbook != null) {
				try {
					workbook.close();
				} catch (IOException e) {
					logger.error("Exception : " + ShowMessage.getStackTrace(e));
				}
			}
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					logger.error("Exception : " + ShowMessage.getStackTrace(e));
				}
			}
		}
		return result;
	}

	/**
	 * To fetch the Defect data from specified location
	 * 
	 * @param fileLocation
	 * @return
	 */
	public List<Defects> fetchDefectData(String fileLocation) {
		List<Defects> defectData = new ArrayList<Defects>();
		InputStream fis = null;
		XSSFWorkbook workbook = null;
		try {
			if (fileLocation != null && !fileLocation.trim().isEmpty()) {
				fis = new FileInputStream(fileLocation);
				workbook = new XSSFWorkbook(fis);
				XSSFSheet sheet = workbook.getSheet("Defects");
				for (int i = 1; i < sheet.getLastRowNum() + 1; i++) {
					String rowData = "";
					for (int j = 0; j < 16; j++) {
						DataFormatter formatter = new DataFormatter();
						XSSFCell cell = sheet.getRow(i).getCell(j);
						rowData = rowData + formatter.formatCellValue(cell) + " |";
					}
					StringTokenizer st = new StringTokenizer(rowData, "|");
					Defects defect = new Defects();
					defect.setIndex(i);
					defect.setId(st.nextToken().trim());
					defect.setTestCaseid(st.nextToken().trim());
					defect.setTestCaseName(st.nextToken().trim());
					defect.setLogDefect(st.nextToken().trim());
					defect.setSummary(st.nextToken().trim());
					defect.setDescription(st.nextToken().trim());
					defect.setReproducibility(st.nextToken().trim());
					defect.setSeverity(st.nextToken().trim());
					defect.setPriority(st.nextToken().trim());
					defect.setAssignTo(st.nextToken().trim());
					defect.setStepsToReproduce(st.nextToken().trim());
					defect.setAdditionalInformation(st.nextToken().trim());
					defect.setUploadFilePath(st.nextToken().trim());
					defect.setDefectID(st.nextToken().trim());
					defect.setLoggedDate(st.nextToken().trim());
					defect.setDefectUrl(st.nextToken().trim());
					defect.setFileLocation(fileLocation);
					defect.setFileName(fileLocation.substring(fileLocation.lastIndexOf("\\") + 1));
					defectData.add(defect);
				}
				logger.info("Defect data fetcted from file: " + fileLocation);
			} else {
				logger.error("File location is empty in fetchDefectData()");
			}
		} catch (Exception e) {
			logger.error("Exception : " + ShowMessage.getStackTrace(e));

		} finally {
			if (workbook != null) {
				try {
					workbook.close();
				} catch (IOException e) {
					logger.error("Exception : " + ShowMessage.getStackTrace(e));
				}
			}
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					logger.error("Exception : " + ShowMessage.getStackTrace(e));
				}
			}
		}
		return defectData;
	}

	/**
	 * To fetch defect table drop down values
	 * 
	 * @param fileLocation
	 * @param column
	 * @return
	 */
	public Map<String, String> fetchDefectDropdown(String fileLocation, int column) {
		Map<String, String> values = new TreeMap<String, String>();
		FileInputStream fis = null;
		XSSFWorkbook workbook = null;
		try {
			if (fileLocation != null && !fileLocation.trim().isEmpty()) {
				fis = new FileInputStream(fileLocation);
				workbook = new XSSFWorkbook(fis);
				XSSFSheet sheet = workbook.getSheet("ListValues");
				for (int i = 1; i < sheet.getLastRowNum() + 1; i++) {
					String rowData = "";
					DataFormatter formatter = new DataFormatter();
					XSSFCell cell = sheet.getRow(i).getCell(column);
					rowData = formatter.formatCellValue(cell);
					if (rowData.trim().length() != 0) {
						values.put(rowData, rowData);
					}
				}
			} else {
				logger.error("File location is empty in fetchDefectDropdown()");
			}
		} catch (IOException e) {
			logger.error("Exception : " + ShowMessage.getStackTrace(e));
		} finally {
			if (workbook != null) {
				try {
					workbook.close();
				} catch (IOException e) {
					logger.error("Exception : " + ShowMessage.getStackTrace(e));
				}
			}
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					logger.error("Exception : " + ShowMessage.getStackTrace(e));
				}
			}
		}
		return values;
	}

	/**
	 * This method getFilesList() is used to fetch the list of files from the
	 * directory
	 * 
	 * @param folderPath
	 * @param request
	 * @return
	 */
	/*public Map<String, Map<String, String>> getFilesListForResults(String folderPath, HttpServletRequest request)
			throws Exception {
		String folderLocation = "";
		String orgFolder = (String) request.getSession().getAttribute("userOrganization");
		Project project = (Project) request.getSession().getAttribute("userProject");
		if (orgFolder != null && !orgFolder.trim().isEmpty() && project != null) {
			folderLocation = esaipBaseLocation + "\\" + orgFolder + "\\" + getProjectDataProperty("projectFolderKey")
					+ "\\" + project.getName() + "\\" + folderPath;
			logger.info("folder location : >>> : : " + folderLocation);
		}
		File[] folderList = null;
		File folder = new File(folderLocation);
		if (folder != null) {
			folderList = folder.listFiles();
		}

		Map<String, Map<String, String>> resultMap = new TreeMap<String, Map<String, String>>(
				Collections.reverseOrder());
		Map<String, String> fileMap = null;
		if (folderList != null && folderList.length != 0) {
			for (File dir : folderList) {
				if (dir.isDirectory()) {
					String folderName = dir.getName();
					File[] fileList = dir.listFiles();
					if (fileList != null && fileList.length != 0) {
						fileMap = new TreeMap<String, String>(Collections.reverseOrder());
						for (File file : fileList) {
							if (file.isFile()) {
								String fileName = file.getName();
								if (fileName.substring(fileName.lastIndexOf(".")).equalsIgnoreCase(".xlsx")) {
									fileMap.put(fileName, folderLocation + "\\" + folderName + "\\" + fileName);
									logger.info("file path : " + folderLocation + "\\" + folderName + "\\" + fileName);
								}
							}
						}
					}
					resultMap.put(folderName, fileMap);
				}
			}
			logger.info("List of " + folderLocation + " fetched from " + folderLocation);
		}
		return resultMap;
	}*/
	
	public Map<String, String> getFilesListForResults(String folderPath, HttpServletRequest request)
			throws Exception {
		Map<String, String> fileMap = null;
		if (folderPath != null && !folderPath.trim().isEmpty()) {
		File[] fileList = null;
		File folder = new File(folderPath);
		if (folder != null) {
			fileList = folder.listFiles();
		}
		 fileMap = new TreeMap<String, String>(Collections.reverseOrder());
		if (fileList != null && fileList.length != 0) {
			for (File file : fileList) {
				if (file.isFile()) {
					String fileName = file.getName();
					if (fileName.substring(fileName.lastIndexOf(".")).equalsIgnoreCase(".xlsx")) {
						fileMap.put(fileName, folderPath + "\\" + fileName);
					}
				}
			}
			}
		}
		return fileMap;
	}

	/**
	 * 
	 * @param folderPath
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> getFilesList(String folderPath, HttpServletRequest request) throws Exception {
		String folderLocation = "";
		String orgFolder = (String) request.getSession().getAttribute("userOrganization");
		Project project = (Project) request.getSession().getAttribute("userProject");
		if (orgFolder != null && !orgFolder.trim().isEmpty() && project != null) {
			folderLocation = esaipBaseLocation + "\\" + orgFolder + "\\" + util.getProjectDataProperty("projectFolderKey")
					+ "\\" + project.getName() + "\\" + folderPath;
		}
		File[] fileList = null;
		File folder = new File(folderLocation);
		if (folder != null) {
			fileList = folder.listFiles();
		}
		Map<String, String> fileMap = new TreeMap<String, String>(Collections.reverseOrder());
		if (fileList != null && fileList.length != 0) {
			for (File file : fileList) {
				if(folderPath.equals(ProjectFolderEnum.RESULTS.getFolderName())) {
				if (file.isDirectory()) {
					String fileName = file.getName();
						fileMap.put(fileName, folderLocation + "\\" + fileName);
				}
			}else {
				if (file.isFile()) {
					String fileName = file.getName();
					if (fileName.substring(fileName.lastIndexOf(".")).equalsIgnoreCase(".xlsx")) {
						fileMap.put(fileName, folderLocation + "\\" + fileName);
					}
				}
			}
			}
		}
		logger.info("List of " + folderLocation + " fetched from " + folderLocation);
		return fileMap;
	}

	/**
	 * To get the respective log file for given Result File
	 * 
	 * @param resultLocation
	 * @param request
	 * @return
	 */
	public String getLogFile(String resultLocation, HttpServletRequest request) throws Exception {
		String logsFolder = "";
		if (resultLocation != null && !resultLocation.trim().isEmpty()) {
			String resultFile = resultLocation.substring(resultLocation.lastIndexOf("\\") + 1,
					resultLocation.lastIndexOf("."));

			String orgFolder = (String) request.getSession().getAttribute("userOrganization");
			Project project = (Project) request.getSession().getAttribute("userProject");
			if (orgFolder != null && !orgFolder.trim().isEmpty() && project != null) {
				logsFolder = esaipBaseLocation + "\\" + orgFolder + "\\" + util.getProjectDataProperty("projectFolderKey")
						+ "\\" + project.getName() + "\\" + ProjectFolderEnum.LOGS.getFolderName() + "\\" + resultFile
						+ ".log";
			}
		}
		return logsFolder;
	}

	/**
	 * To get result file location for result file in properties file
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public String resultFileLocation(HttpServletRequest request) throws Exception {
		String location = "";
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM_dd_yyyy");
		String resultFile = excelUtil.getProgressProperty("ResultFilename", request);

		String orgFolder = (String) request.getSession().getAttribute("userOrganization");
		Project project = (Project) request.getSession().getAttribute("userProject");
		if (orgFolder != null && !orgFolder.trim().isEmpty() && project != null && resultFile != null
				&& !resultFile.trim().isEmpty()) {
			location = esaipBaseLocation + "\\" + orgFolder + "\\" + util.getProjectDataProperty("projectFolderKey") + "\\"
					+ project.getName() + "\\" + ProjectFolderEnum.RESULTS.getFolderName() + "\\"
					+ dateFormat.format(new Date()) + "\\" + resultFile;
		}
		return location;
	}

	/**
	 * To get result file location for run plan file in properties file
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public String runPLanLocation(HttpServletRequest request) throws Exception {
		String location = "";
		String orgFolder = (String) request.getSession().getAttribute("userOrganization");
		Project project = (Project) request.getSession().getAttribute("userProject");
		if (orgFolder != null && !orgFolder.trim().isEmpty() && project != null) {
			location = esaipBaseLocation + "\\" + orgFolder + "\\" + util.getProjectDataProperty("projectFolderKey") + "\\"
					+ project.getName() + "\\" + ProjectFolderEnum.RUNPLANS.getFolderName() + "\\"
					+ excelUtil.getProgressProperty("RunplanFilename", request);
		}
		return location;
	}

	/**
	 * To get defect file location for defect plan file in properties file
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public String defectFileLocation(HttpServletRequest request) throws Exception {
		String location = "";
		String orgFolder = (String) request.getSession().getAttribute("userOrganization");
		Project project = (Project) request.getSession().getAttribute("userProject");
		if (orgFolder != null && !orgFolder.trim().isEmpty() && project != null) {
			location = esaipBaseLocation + "\\" + orgFolder + "\\" + util.getProjectDataProperty("projectFolderKey") + "\\"
					+ project.getName() + "\\" + ProjectFolderEnum.RESULTS.getFolderName() + "\\"
					+ excelUtil.getProgressProperty("DefectFilename", request);
		}
		return location;
	}
}
