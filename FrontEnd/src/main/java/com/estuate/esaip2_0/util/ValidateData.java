package com.estuate.esaip2_0.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.estuate.esaip2_0.exception.ShowMessage;



/**
 * <ul>
 * <li>Title: ValidateData</li>
 * <li>Description:</li>
 * <li>Created by: Nemmar Rajath Bhat</li>
 * </ul>
 */
@Component
public class ValidateData {
	/**
	 * The logger
	 */
	private static final Logger logger = LoggerFactory.getLogger(ValidateData.class);
	/**
	 * The excelUtil
	 */
	@Autowired
	ExcelUtil excelUtil;

	/**
	 * To validate the uploaded master plan
	 * 
	 * @param tempFileLocation
	 * @return
	 * @throws Exception
	 */
	public String validateUploadedData(String tempFileLocation) throws Exception {
		String result = "";
		String value = "";
		if (tempFileLocation != null && !tempFileLocation.trim().isEmpty()) {
			value = validateTabs(tempFileLocation, "TestSteps");
			value = value.trim() + " " + validateTabs(tempFileLocation, "MasterSheet") + " ";
			value = value.trim() + " " + validateTabs(tempFileLocation, "ListValues") + " ";
			value = value.trim() + " " + validateTabs(tempFileLocation, "Allobjects") + " ";
			value = value.trim() + " " + validateTabs(tempFileLocation, "Results");
			if (value.trim().length() != 0) {
				result = value.trim().replace(" ", ", ")
						+ " tab(s) are not found in the Master Plan. Please upload a valid file.";
				return result;
			} else {
				value = validateColumns(tempFileLocation, "MasterSheet");
				value = value.trim() + " " + validateColumns(tempFileLocation, "ListValues");
				value = value.trim() + " " + validateColumns(tempFileLocation, "Allobjects");
				value = value.trim() + " " + validateColumns(tempFileLocation, "TestSteps");
				value = value.trim() + " " + validateResultSheet(tempFileLocation);
				if (value.trim().length() != 0) {
					result = "Number of column(s) mismatch in " + value.trim().replace(" ", ", ")
							+ " tab(s). Please upload a valid file.";
					return result;
				} else {
					value += validateColumnNames(tempFileLocation, "MasterSheet");
					value += validateColumnNames(tempFileLocation, "Allobjects");
					value += validateColumnNames(tempFileLocation, "TestSteps");
					value += validateColumnNames(tempFileLocation, "ListValues");
					if (value.trim().length() != 0) {
						result = value + "Please upload a valid file";
						return result;
					} else {
						value = checkforEmptyRows(tempFileLocation, "MasterSheet");
						value = value.trim() + " " + checkforEmptyRows(tempFileLocation, "Allobjects");
						value = value.trim() + " " + checkforEmptyRows(tempFileLocation, "TestSteps");
						value = value.trim() + " " + checkforEmptyRows(tempFileLocation, "ListValues");
						if (value.trim().length() != 0) {
							result = "Found empty rows in " + value.trim().replace(" ", ", ")
									+ ". Please correct and upload a valid file.";
							return result;
						}
					}
				}
			}
		}
		return "success";
	}

	/**
	 * This is to validate whether all the tabs are available in the uploaded file
	 * or not
	 * 
	 * @param tempFileLocation
	 * @param sheetName
	 * @return
	 */
	private String validateTabs(String tempFileLocation, String sheetName) {
		String response = "";
		FileInputStream inputStream = null;
		XSSFWorkbook workBook = null;
		try {
			if (tempFileLocation != null && !tempFileLocation.trim().isEmpty() && sheetName != null
					&& !sheetName.trim().isEmpty()) {
				inputStream = new FileInputStream(tempFileLocation);
				workBook = new XSSFWorkbook(inputStream);
				XSSFSheet sheet = workBook.getSheet(sheetName);
				sheet.getFirstHeader();
			}
		} catch (Exception e) {
			response = sheetName;
			logger.error("Exception : " + ShowMessage.getStackTrace(e));
		} finally {
			try {
				if (workBook != null) {
					workBook.close();
				}
				if (inputStream != null) {
					inputStream.close();
				}
			} catch (IOException e) {
				logger.error("Exception : " + ShowMessage.getStackTrace(e));
			}
		}
		return response;
	}

	/**
	 * This is to validate the sheets of updated file with number of columns
	 * 
	 * @param tempFileLocation
	 * @param sheetName
	 * @return
	 */
	private String validateColumns(String tempFileLocation, String sheetName) {
		int colCount = 0;
		String response = "";
		FileInputStream inputStream = null;
		XSSFWorkbook workBook = null;
		try {
			if (tempFileLocation != null && !tempFileLocation.trim().isEmpty() && sheetName != null
					&& !sheetName.trim().isEmpty()) {
				int noOfColumns = Integer.parseInt(excelUtil.getValue("numberofColumnsIn" + sheetName));
				inputStream = new FileInputStream(tempFileLocation);
				workBook = new XSSFWorkbook(inputStream);
				XSSFSheet sheet = workBook.getSheet(sheetName);
				colCount = sheet.getRow(0).getLastCellNum();

				if (colCount != (noOfColumns)) {
					response = sheetName;
				}
			}
		} catch (Exception e) {
			response = sheetName;
			logger.error("Exception : " + ShowMessage.getStackTrace(e));
		} finally {
			try {
				if (workBook != null) {
					workBook.close();
				}
				if (inputStream != null) {
					inputStream.close();
				}
			} catch (IOException e) {
				logger.error("Exception : " + ShowMessage.getStackTrace(e));
			}
		}
		return response;
	}

	/**
	 * This is to validate the Result sheet of updated file with number of rows and
	 * columns
	 * 
	 * @param tempFileLocation
	 * @return
	 */
	private String validateResultSheet(String tempFileLocation) {
		String response = "";
		FileInputStream inputStream = null;
		XSSFWorkbook workBook = null;
		try {
			if (tempFileLocation != null && !tempFileLocation.trim().isEmpty()) {
				inputStream = new FileInputStream(tempFileLocation);
				workBook = new XSSFWorkbook(inputStream);
				XSSFSheet sheet = workBook.getSheet("Results");
				String rowData = "";
				for (int i = 3; i < 10; i++) {
					for (int j = 1; j < 3; j++) {
						DataFormatter formatter = new DataFormatter();
						XSSFCell cell = sheet.getRow(i).getCell(j);
						rowData = rowData + formatter.formatCellValue(cell);
					}
					if (rowData.trim().length() == 0) {
						response = "Results";
					}
				}
			}
		} catch (Exception e) {
			logger.error("Exception" + ShowMessage.getStackTrace(e));
		} finally {
			try {
				if (workBook != null) {
					workBook.close();
				}
				if (inputStream != null) {
					inputStream.close();
				}
			} catch (IOException e) {
				logger.error("Exception : " + ShowMessage.getStackTrace(e));
			}
		}
		return response;
	}

	/**
	 * This method is used to validate the column headers with the predefined values
	 * 
	 * @param tempFileLocation
	 * @param sheetName
	 * @return
	 */
	private String validateColumnNames(String tempFileLocation, String sheetName) {
		String response = "";
		String result = "";
		List<String> columnNames = new ArrayList<String>();
		FileInputStream inputStream = null;
		XSSFWorkbook workBook = null;
		try {
			if (tempFileLocation != null && !tempFileLocation.trim().isEmpty() && sheetName != null
					&& !sheetName.trim().isEmpty()) {
				inputStream = new FileInputStream(tempFileLocation);
				workBook = new XSSFWorkbook(inputStream);
				XSSFSheet sheet = workBook.getSheet(sheetName);
				DataFormatter formatter = new DataFormatter();
				@SuppressWarnings("unused")
				XSSFCell cell;

				int numberOfColumn = Integer.parseInt(excelUtil.getValue("numberofColumnsIn" + sheetName));
				for (int i = 1; i <= numberOfColumn; i++) {
					if (!(formatter.formatCellValue(cell = sheet.getRow(0).getCell(i - 1))
							.equals(excelUtil.getValue(sheetName + "Column" + i)))) {
						columnNames.add(excelUtil.getValue(sheetName + "Column" + i));
					}
				}
				if (columnNames.size() != 0) {
					Iterator<String> itr = columnNames.iterator();
					while (itr.hasNext()) {
						result = result + itr.next() + ", ";
					}
					result = result.substring(0, result.lastIndexOf(","));
					response = result + " column(s) are not found in " + sheetName + ". ";
				}
			}
		} catch (Exception e) {
			logger.error("Exception : " + ShowMessage.getStackTrace(e));
		} finally {
			try {
				if (workBook != null) {
					workBook.close();
				}
				if (inputStream != null) {
					inputStream.close();
				}
			} catch (IOException e) {
				logger.error("Exception : " + ShowMessage.getStackTrace(e));
			}
		}
		return response;
	}

	/**
	 * This method is used to check if any other empty rows are given in between the
	 * existing ones
	 * 
	 * @param tempFileLocation
	 * @param sheetName
	 * @return
	 */
	private String checkforEmptyRows(String tempFileLocation, String sheetName) {
		boolean flag = false;
		FileInputStream fis = null;
		XSSFWorkbook workBook = null;
		try {
			if (tempFileLocation != null && !tempFileLocation.trim().isEmpty() && sheetName != null
					&& !sheetName.trim().isEmpty()) {
				int noOfColumns = Integer.parseInt(excelUtil.getValue("numberofColumnsIn" + sheetName));
				fis = new FileInputStream(tempFileLocation);
				workBook = new XSSFWorkbook(fis);
				XSSFSheet sheet = workBook.getSheet(sheetName);
				for (int i = 1; i < sheet.getLastRowNum() + 1; i++) {
					String rowData = "";
					for (int j = 0; j < noOfColumns; j++) {
						DataFormatter formatter = new DataFormatter();
						XSSFCell cell = sheet.getRow(i).getCell(j);
						rowData += formatter.formatCellValue(cell);
					}
					if (rowData.trim().length() == 0) {
						flag = true;
					}
				}
			}
		} catch (Exception e) {
			logger.error("Exception" + ShowMessage.getStackTrace(e));
		} finally {
			try {
				if (workBook != null) {
					workBook.close();
				}
				if (fis != null) {
					fis.close();
				}
			} catch (IOException e) {
				logger.error("Exception : " + ShowMessage.getStackTrace(e));
			}
		}
		if (flag) {
			return sheetName;
		} else {
			return "";
		}
	}
}
