package com.estuate.esaip2_0.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.estuate.esaip2_0.exception.ShowMessage;
import com.estuate.esaip2_0.model.AllObjects;
import com.estuate.esaip2_0.model.MasterPlan;
import com.estuate.esaip2_0.model.TestSteps;





/**
 * <ul>
 * <li>Title: CreateMasterPlan</li>
 * <li>Description: This util class is used to create master plan</li>
 * <li>Created by: Nemmar Rajath Bhat</li>
 * </ul>
 */
@Component
public class CreateMasterPlan {
	/**
	 * The logger
	 */
	private final Logger logger = LoggerFactory.getLogger(CreateMasterPlan.class);
	/**
	 * The excelUtil
	 */
	@Autowired
	private ExcelUtil excelUtil;

	/**
	 * This method is used to update the existing sheet with new values with the new
	 * sheet
	 * 
	 * @param plan
	 * @param fileLocation
	 * @throws NumberFormatException
	 * @throws Exception
	 */
	public void createMasterSheet(List<MasterPlan> plan, String fileLocation) throws NumberFormatException, Exception {
		OutputStream outputStream = null;
		String sheetName = "MasterSheet";
		int numberOfColumns = Integer.parseInt(excelUtil.getValue("numberofColumnsInMasterSheet"));
		Workbook workBook = null;
		try (FileInputStream inputStream = new FileInputStream(new File(fileLocation))) {
			workBook = WorkbookFactory.create(inputStream);
			workBook.removeSheetAt(workBook.getSheetIndex(sheetName));
			Sheet sheet = workBook.createSheet(sheetName);
			Row header = sheet.createRow(0);
			for (int i = 0; i < numberOfColumns; i++) {
				int columnNumber = i + 1;
				header.createCell(i).setCellValue(excelUtil.getValue("MasterSheetColumn" + columnNumber));
			}
			int rowCount = 0;
			for (MasterPlan data : plan) {
				Row row = sheet.createRow(++rowCount);
				writeRunPlan(data, row);
			}
			outputStream = new FileOutputStream(fileLocation);
			workBook.write(outputStream);

		} catch (IOException | InvalidFormatException e) {
			logger.error("Exception : " + ShowMessage.getStackTrace(e));
		} finally {
			if (workBook != null) {
				try {
					workBook.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (outputStream != null) {
				try {
					outputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * This method is used to replace the existing test step sheet with sheet of new
	 * values
	 * 
	 * @param testSteps
	 * @param fileLocation
	 * @throws NumberFormatException
	 * @throws Exception
	 */
	public void createTestSteps(List<TestSteps> testSteps, String fileLocation)
			throws NumberFormatException, Exception {
		OutputStream outputStream = null;
		String sheetName = "TestSteps";
		int numberOfColumns = Integer.parseInt(excelUtil.getValue("numberofColumnsInTestSteps"));
		Workbook workBook = null;
		try (FileInputStream inputStream = new FileInputStream(new File(fileLocation))) {
			workBook = WorkbookFactory.create(inputStream);
			workBook.removeSheetAt(workBook.getSheetIndex(sheetName));
			Sheet sheet = workBook.createSheet(sheetName);
			Row header = sheet.createRow(0);
			for (int i = 0; i < numberOfColumns; i++) {
				int columnNumber = i + 1;
				header.createCell(i).setCellValue(excelUtil.getValue("TestStepsColumn" + columnNumber));
			}
			int rowCount = 0;
			for (TestSteps data : testSteps) {
				Row row = sheet.createRow(++rowCount);
				writeTestSteps(data, row);
			}
			outputStream = new FileOutputStream(fileLocation);
			workBook.write(outputStream);
		} catch (IOException | InvalidFormatException e) {
			logger.error("Exception : " + ShowMessage.getStackTrace(e));
		} finally {
			if (workBook != null) {
				try {
					workBook.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (outputStream != null) {
				try {
					outputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * This method is used to replace the existing allobject sheet with the new one.
	 * 
	 * @param allObj
	 * @param fileLocation
	 * @throws NumberFormatException
	 * @throws Exception
	 */
	public void createAllObjects(List<AllObjects> allObj, String fileLocation) throws NumberFormatException, Exception {
		FileOutputStream outputStream = null;
		String sheetName = "Allobjects";
		int numberOfColumns = Integer.parseInt(excelUtil.getValue("numberofColumnsInAllobjects"));
		Workbook workBook = null;
		try (FileInputStream inputStream = new FileInputStream(fileLocation);) {
			workBook = WorkbookFactory.create(inputStream);
			workBook.removeSheetAt(workBook.getSheetIndex(sheetName));
			Sheet sheet = workBook.createSheet(sheetName);
			Row header = sheet.createRow(0);
			for (int i = 0; i < numberOfColumns; i++) {
				int columnNumber = i + 1;
				header.createCell(i).setCellValue(excelUtil.getValue("AllobjectsColumn" + columnNumber));
			}
			int rowCount = 0;
			for (AllObjects data : allObj) {
				Row row = sheet.createRow(++rowCount);
				writeAllObjects(data, row);
			}
			outputStream = new FileOutputStream(fileLocation);
			workBook.write(outputStream);
		} catch (IOException | InvalidFormatException e) {
			logger.error("Exception : " + ShowMessage.getStackTrace(e));
		} finally {
			if (workBook != null) {
				try {
					workBook.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (outputStream != null) {
				try {
					outputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * This is used to write the data to the respective cells
	 * 
	 * @param plan
	 * @param row
	 */
	private void writeRunPlan(MasterPlan plan, Row row) {
		Cell cell = row.createCell(0);
		cell.setCellValue(plan.getId().trim());

		cell = row.createCell(1);
		cell.setCellValue(plan.getBusinessRequirement());

		cell = row.createCell(2);
		cell.setCellValue(plan.getDescription());

		cell = row.createCell(3);
		cell.setCellValue(plan.getImpactedBRs());

		cell = row.createCell(4);
		cell.setCellValue(plan.getIncludeImpactedBRs());

		cell = row.createCell(5);
		cell.setCellValue(plan.getModules());

		cell = row.createCell(6);
		cell.setCellValue(plan.getModulesInclude());

		cell = row.createCell(7);
		cell.setCellValue(plan.getTestCases());

		cell = row.createCell(8);
		cell.setCellValue(plan.getTestCasesInclude());

		cell = row.createCell(9);
		cell.setCellValue(plan.getTestCaseId());

		cell = row.createCell(10);
		cell.setCellValue(plan.getCriticality());

		cell = row.createCell(11);
		cell.setCellValue(Integer.parseInt(plan.getRepeatablity()));
	}

	/**
	 * This is used to write the values to all objects to the respective cells
	 * 
	 * @param allObject
	 * @param row
	 */
	private void writeAllObjects(AllObjects allObject, Row row) {
		Cell cell = row.createCell(0);
		cell.setCellValue(allObject.getSlNo());

		cell = row.createCell(1);
		cell.setCellValue(allObject.getScreenName().trim());

		cell = row.createCell(2);
		cell.setCellValue(allObject.getObjectName().trim());

		cell = row.createCell(3);
		cell.setCellValue(allObject.getType().trim());

		cell = row.createCell(4);
		cell.setCellValue(allObject.getValues().trim());

		cell = row.createCell(5);
		cell.setCellValue(allObject.getComments().trim());
	}

	/**
	 * This is used to update the new values to the test steps sheet
	 * 
	 * @param steps
	 * @param row
	 */
	private void writeTestSteps(TestSteps steps, Row row) {
		Cell cell = row.createCell(0);
		cell.setCellValue(steps.getTcId());

		cell = row.createCell(1);
		cell.setCellValue(steps.getBusinessRequirement());

		cell = row.createCell(2);
		cell.setCellValue(steps.getModule());

		cell = row.createCell(3);
		cell.setCellValue(steps.getTestcase());

		cell = row.createCell(4);
		cell.setCellValue(steps.getSlNo());

		cell = row.createCell(5);
		cell.setCellValue(steps.getKeyword());

		cell = row.createCell(6);
		cell.setCellValue(steps.getObject());

		cell = row.createCell(7);
		cell.setCellValue(steps.getTestObjectData());

		cell = row.createCell(11);
		cell.setCellValue(steps.getClassfile());

		cell = row.createCell(12);
		cell.setCellValue(steps.getDescription());
	}
}
