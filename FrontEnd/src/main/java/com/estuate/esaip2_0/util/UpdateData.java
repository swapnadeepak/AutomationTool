package com.estuate.esaip2_0.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.common.usermodel.Hyperlink;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
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

import static com.estuate.esaip2_0.util.Constants.*;


/**
 * <ul>
 * <li>Title: UpdateData</li>
 * <li>Description: This util class is used to update the changes from UI to the
 * excel file</li>
 * <li>Created by: Nemmar Rajath Bhat</li>
 * <li>Modified By: Radhika GopalRaj</li>
 * </ul>
 */
@Component
public class UpdateData{
	/**
	 * The logger
	 */
	private static final Logger logger = LoggerFactory.getLogger(UpdateData.class);
	/**
	 * The updateSheet
	 */
	@Autowired
	UpdateSheet updateSheet;
	
	@Autowired
	Util util;

	/**
	 * To upload master plan file
	 * 
	 * @param tempFileLocation
	 * @param request
	 */
	public void updateMasterFile(String tempFileLocation, HttpServletRequest request) {
		String masterPlanPath = "";
		InputStream inputStream = null;
		OutputStream outputStream = null;
		try {
			String orgFolder = (String) request.getSession().getAttribute("userOrganization");
			Project project = (Project) request.getSession().getAttribute("userProject");
			if (orgFolder != null && !orgFolder.trim().isEmpty() && project != null) {
				masterPlanPath = esaipBaseLocation + "\\" + orgFolder + "\\" + util.getProjectDataProperty("projectFolderKey")
						+ "\\" + project.getName() + "\\" + PropertyFileEnum.MASTERPLAN_XLSX_FILENAME.getProperty()
						+ ".xlsx";

			}
			if (masterPlanPath != null && !masterPlanPath.trim().isEmpty() && tempFileLocation != null
					&& !tempFileLocation.trim().isEmpty()) {
				inputStream = new FileInputStream(tempFileLocation);
				outputStream = new FileOutputStream(masterPlanPath);

				byte[] buf = new byte[1024];
				int len;
				while ((len = inputStream.read(buf)) > 0) {
					outputStream.write(buf, 0, len);
				}
			}
		} catch (Exception e) {
			logger.error("Exception : " + ShowMessage.getStackTrace(e));
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					logger.error("Exception : " + ShowMessage.getStackTrace(e));
				}
			}
			if (outputStream != null) {
				try {
					outputStream.close();
				} catch (IOException e) {
					logger.error("Exception : " + ShowMessage.getStackTrace(e));
				}
			}
		}
	}
	/**
	 * To create a back up of uploaded masterplan
	 * 
	 * @param request
	 */
	public void backupMasterPlan(HttpServletRequest request) {
		InputStream inputStream = null;
		OutputStream outputStream = null;
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy-HH_mm_ss");
		Date date = new Date();
		String masterPlanPath = "";
		String backupLocation = "";
		try {
			String orgFolder = (String) request.getSession().getAttribute("userOrganization");
			Project project = (Project) request.getSession().getAttribute("userProject");
			if (orgFolder != null && !orgFolder.trim().isEmpty() && project != null) {
				masterPlanPath = esaipBaseLocation + "\\" + orgFolder + "\\" + util.getProjectDataProperty("projectFolderKey")
						+ "\\" + project.getName() + "\\" + PropertyFileEnum.MASTERPLAN_XLSX_FILENAME.getProperty()
						+ ".xlsx";

				backupLocation = esaipBaseLocation + "\\" + orgFolder + "\\" + util.getProjectDataProperty("projectFolderKey")
						+ "\\" + project.getName() + "\\" + ProjectFolderEnum.MASTERPLAN_BACKUP.getFolderName()
						+ "\\MasterPlan_" + dateFormat.format(date) + ".xlsx";

			}

			if (masterPlanPath != null && !masterPlanPath.trim().isEmpty() && backupLocation != null
					&& !backupLocation.trim().isEmpty()) {
				inputStream = new FileInputStream(masterPlanPath);
				outputStream = new FileOutputStream(backupLocation);
				byte[] buf = new byte[1024];
				int len;
				while ((len = inputStream.read(buf)) > 0) {
					outputStream.write(buf, 0, len);
				}
			}
		} catch (Exception e) {
			logger.error("Exception : " + ShowMessage.getStackTrace(e));
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					logger.error(
							"Exception : " + ShowMessage.getStackTrace(e));
				}
			}
			if (outputStream != null) {
				try {
					outputStream.close();
				} catch (IOException e) {
					logger.error(
							"Exception : " + ShowMessage.getStackTrace(e));
				}
			}
		}
		File file = new File(masterPlanPath);
		file.delete();
	}

	/**
	 * To create and update a new run plan
	 * 
	 * @param runplan
	 * @param ip
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public String createRunPlan(List<MasterPlan> runplan, String ip, HttpServletRequest request) throws Exception {
		String masterPlanPath = "";
		String runPlanLocation = "";
		FileInputStream inputStream = null;
		Workbook workbook = null;
		try {
			String orgFolder = (String) request.getSession().getAttribute("userOrganization");
			Project project = (Project) request.getSession().getAttribute("userProject");
			if (orgFolder != null && !orgFolder.trim().isEmpty() && project != null && ip != null
					&& !ip.trim().isEmpty()) {
				masterPlanPath = esaipBaseLocation + "\\" + orgFolder + "\\" + util.getProjectDataProperty("projectFolderKey")
						+ "\\" + project.getName() + "\\" + PropertyFileEnum.MASTERPLAN_XLSX_FILENAME.getProperty()
						+ ".xlsx";
				DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy-HH_mm_ss");
				Date date = new Date();

				runPlanLocation = esaipBaseLocation + "\\" + orgFolder + "\\" + util.getProjectDataProperty("projectFolderKey")
						+ "\\" + project.getName() + "\\" + ProjectFolderEnum.RUNPLANS.getFolderName() + "\\Runplan_"
						+ dateFormat.format(date) + "_" + ip + ".xlsx";
			}
			if (runplan != null && !runplan.isEmpty()) {
				inputStream = new FileInputStream(new File(masterPlanPath));
				workbook = WorkbookFactory.create(inputStream);
				Sheet sheet = workbook.getSheet("MasterSheet");
				int rowCount = 0;
				for (MasterPlan plan : runplan) {
					Row row = sheet.createRow(++rowCount);
					writeRunPlan(plan, row);
				}
				FileOutputStream outputStream = new FileOutputStream(runPlanLocation);
				System.out.println("run plan: " + runPlanLocation);
				workbook.write(outputStream);
				workbook.close();
				outputStream.close();
				logger.info("New Run plan file created: " + runPlanLocation);
			} else {
				logger.error("Run plan list is empty or null");
			}
		} catch (IOException | EncryptedDocumentException | InvalidFormatException e) {
			logger.error("Exception : " + ShowMessage.getStackTrace(e));
		} finally {
			if (workbook != null) {
				try {
					workbook.close();
				} catch (IOException e) {
					logger.error("Exception : " + ShowMessage.getStackTrace(e));
				}
			}
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					logger.error("Exception : " + ShowMessage.getStackTrace(e));
				}
			}
		}
		updateSheet.removeUnusedRows(runPlanLocation);
		return runPlanLocation;
	}

	/**
	 * To write the Run PLan to specific row and column in excel sheet
	 * 
	 * @param masterPlan
	 * @param row
	 * @throws Exception
	 */
	private static void writeRunPlan(MasterPlan masterPlan, Row row) throws Exception {
		if (masterPlan != null && row != null) {
			Cell cell = row.createCell(0);
			if(masterPlan.getId().trim().length() != 0 ){
				cell.setCellValue(Integer.parseInt(masterPlan.getId().trim()));
			}

			cell = row.createCell(1);
			if(masterPlan.getBusinessRequirement() != null){
				cell.setCellValue(masterPlan.getBusinessRequirement().trim());
			}

			cell = row.createCell(2);
			if(masterPlan.getDescription()!= null){
				cell.setCellValue(masterPlan.getDescription().trim());
			}

			cell = row.createCell(3);
			if (masterPlan.getImpactedBRs() != null) {
				cell.setCellValue(masterPlan.getImpactedBRs());
			}

			cell = row.createCell(4);
			if (masterPlan.getIncludeImpactedBRs() != null) {
				cell.setCellValue(masterPlan.getIncludeImpactedBRs());
			}

			cell = row.createCell(5);
			if (masterPlan.getModules() != null) {
				cell.setCellValue(masterPlan.getModules());
			}

			cell = row.createCell(6);
			if (masterPlan.getModulesInclude() != null) {
				cell.setCellValue(masterPlan.getModulesInclude());
			}

			cell = row.createCell(7);
			if (masterPlan.getTestCases() != null) {
				cell.setCellValue(masterPlan.getTestCases());
			}

			cell = row.createCell(8);
			if (masterPlan.getTestCasesInclude() != null) {
				cell.setCellValue(masterPlan.getTestCasesInclude());
			}

			cell = row.createCell(9);
			if (masterPlan.getTestCaseId() != null) {
				cell.setCellValue(masterPlan.getTestCaseId());
			}

			cell = row.createCell(10);
			if (masterPlan.getCriticality() != null) {
				cell.setCellValue(masterPlan.getCriticality());
			}

			cell = row.createCell(11);
			if (masterPlan.getRepeatablity() != null) {
				cell.setCellValue(Integer.parseInt(masterPlan.getRepeatablity().trim()));
			}
		}
	}

	/**
	 * To update the Defects sheet in results file
	 * 
	 * @param defectsList
	 * @param fileLocation
	 * @return
	 * @throws Exception
	 */
	public String updateDefects(ArrayList<Defects> defectsList, String fileLocation) {
		FileInputStream inputStream = null;
		FileOutputStream outputStream = null;
		Workbook workbook = null;
		try {
			if (defectsList != null && !defectsList.isEmpty() && fileLocation != null && !fileLocation.isEmpty()) {
				inputStream = new FileInputStream(new File(fileLocation));
				workbook = WorkbookFactory.create(inputStream);
				Sheet sheet = workbook.getSheet("Defects");
				int rowCount = 0;
				for (Defects defect : defectsList) {
					Row row = sheet.createRow(++rowCount);
					writeDefect(defect, row);
				}
				outputStream = new FileOutputStream(fileLocation);
				workbook.write(outputStream);
				logger.info("Defect details in result file: " + fileLocation + " updated");
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
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					logger.error("Exception : " + ShowMessage.getStackTrace(e));
				}
			}
			if (outputStream != null) {
				try {
					outputStream.close();
				} catch (IOException e) {
					logger.error("Exception : " + ShowMessage.getStackTrace(e));
				}
			}
		}
		return fileLocation;
	}

	/**
	 * To update the defects in the excel Sheet
	 * 
	 * @param defect
	 * @param row
	 * @throws Exception
	 */
	private static void writeDefect(Defects defect, Row row) throws Exception {
		if (defect != null && row != null) {
			Cell cell = row.createCell(0);
			cell.setCellValue(Integer.parseInt(defect.getId().trim()));

			cell = row.createCell(1);
			cell.setCellValue(defect.getTestCaseid());

			cell = row.createCell(2);
			cell.setCellValue(defect.getTestCaseName());

			cell = row.createCell(3);
			cell.setCellValue(defect.getLogDefect());

			cell = row.createCell(4);
			cell.setCellValue(defect.getSummary());

			cell = row.createCell(5);
			cell.setCellValue(defect.getDescription());

			cell = row.createCell(6);
			cell.setCellValue(defect.getReproducibility());

			cell = row.createCell(7);
			cell.setCellValue(defect.getSeverity());

			cell = row.createCell(8);
			cell.setCellValue(defect.getPriority());

			cell = row.createCell(9);
			cell.setCellValue(defect.getAssignTo());

			cell = row.createCell(10);
			cell.setCellValue(defect.getStepsToReproduce());

			cell = row.createCell(11);
			cell.setCellValue(defect.getAdditionalInformation());

			cell = row.createCell(12);
			cell.setCellValue(defect.getUploadFilePath());

			cell = row.createCell(13);
			cell.setCellValue(defect.getDefectID());

			cell = row.createCell(14);
			cell.setCellValue(defect.getLoggedDate());

			cell = row.createCell(15);
			cell.setCellValue(defect.getDefectUrl());
		}
	}

	/**
	 * To remove the un-used rows in excel sheet
	 * 
	 * @param request
	 */
	public void removeUnusedRows(HttpServletRequest request) throws Exception {
		String masterPlanPath = "";
		String orgFolder = (String) request.getSession().getAttribute("userOrganization");
		Project project = (Project) request.getSession().getAttribute("userProject");
		if (orgFolder != null && !orgFolder.trim().isEmpty() && project != null) {
			masterPlanPath = esaipBaseLocation + "\\" + orgFolder + "\\" + util.getProjectDataProperty("projectFolderKey") + "\\"
					+ project.getName() + "\\" + PropertyFileEnum.MASTERPLAN_XLSX_FILENAME.getProperty() + ".xlsx";
		}
		if (masterPlanPath != null && !masterPlanPath.trim().isEmpty()) {
			removeHyperlinks(masterPlanPath, "TestSteps");
			removeHyperlinks(masterPlanPath, "Allobjects");
			removeHyperlinks(masterPlanPath, "ListValues");
			removeHyperlinks(masterPlanPath, "Results");
			removeHyperlinks(masterPlanPath, "MasterSheet");
		}
	}

	/**
	 * 
	 * @param fileName
	 * @param sheetName
	 */
	private void removeHyperlinks(String fileName, String sheetName) {
		FileOutputStream outputStream = null;
		FileInputStream inputStream = null;
		Workbook workBook = null;
		try {
			inputStream = new FileInputStream(new File(fileName));
			workBook = new XSSFWorkbook(inputStream);
			Sheet sheet = workBook.getSheet(sheetName);
			Iterator<Row> iterator = sheet.iterator();
			while (iterator.hasNext()) {
				Row nextRow = iterator.next();
				Iterator<Cell> cellIterator = nextRow.cellIterator();
				while (cellIterator.hasNext()) {
					Cell cell = cellIterator.next();
					Hyperlink hyperlink = cell.getHyperlink();
					try {

						Field field = XSSFSheet.class.getDeclaredField("hyperlinks");
						field.setAccessible(true);

						@SuppressWarnings("unchecked")
						List<Hyperlink> hyperlinkList = (List<Hyperlink>) field.get(sheet);

						hyperlinkList.remove(hyperlink);

					} catch (NoSuchFieldException | SecurityException | IllegalArgumentException
							| IllegalAccessException e) {
						logger.error("Exception : " + ShowMessage.getStackTrace(e));
					}
				}
			}
			outputStream = new FileOutputStream(new File(fileName));
			workBook.write(outputStream);
		} catch (Exception e) {
			logger.error("Exception : " + ShowMessage.getStackTrace(e));
		} finally {
			if (workBook != null) {
				try {
					workBook.close();
				} catch (IOException e) {
					logger.error("Exception : " + ShowMessage.getStackTrace(e));
				}
			}
			if (outputStream != null) {
				try {
					outputStream.close();
				} catch (IOException e) {
					logger.error("Exception : " + ShowMessage.getStackTrace(e));
				}
			}
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					logger.error("Exception : " + ShowMessage.getStackTrace(e));
				}
			}
		}
	}

	/**
	 * 
	 * @param fileContent
	 * @return
	 */
	public String createTempfile(InputStream fileContent) {
		String tempLocation = automationBaseLocation + "/src/temp.xlsx";
		OutputStream outputStream = null;
		try {
			if (fileContent != null) {
				outputStream = new FileOutputStream(tempLocation);
				byte[] buf = new byte[1024];
				int len;
				while ((len = fileContent.read(buf)) > 0) {
					outputStream.write(buf, 0, len);
				}
			}
		} catch (Exception e) {
			logger.error("Exception : " + ShowMessage.getStackTrace(e));
		} finally {
			if (fileContent != null) {
				try {
					fileContent.close();
				} catch (IOException e) {
					logger.error(
							"Exception : " + ShowMessage.getStackTrace(e));
				}
			}
			if (outputStream != null) {
				try {
					outputStream.close();
				} catch (IOException e) {
					logger.error(
							"Exception : " + ShowMessage.getStackTrace(e));
				}
			}
		}
		return tempLocation;
	}
}
