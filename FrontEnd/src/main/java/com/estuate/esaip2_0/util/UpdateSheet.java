package com.estuate.esaip2_0.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.estuate.esaip2_0.exception.ShowMessage;



/**
 * <ul>
 * <li>Title: UpdateSheet</li>
 * <li>Description: This util class is used to make the changes in the data
 * while updating to the excel sheet.</li>
 * <li>Created by: Nemmar Rajath Bhat</li>
 * <li>Modified By: Radhika GopalRaj</li>
 * </ul>
 */
@Component
public class UpdateSheet {
	/**
	 * The logger
	 */
	private static final Logger logger = LoggerFactory.getLogger(UpdateSheet.class);

	/**
	 * To remove un-used rows in the master sheet
	 * 
	 * @param fileName
	 * @throws Exception
	 */
	public void removeUnusedRows(String fileName) throws Exception {
		if (fileName != null) {
			createIterations(fileName, "MasterSheet");
			deleteDuplicateBR(fileName, "MasterSheet");
			deleteNoRows(fileName, "MasterSheet");
			setRunFile(fileName, "Results");
		} else {
			logger.error("File name is empty in removeUnusedRows method");
		}
	}

	/**
	 * To remove the rows with drop down select Number
	 * 
	 * @param fileName
	 * @param sheetName
	 */
	@SuppressWarnings("unused")
	private void createIterations(String fileName, String sheetName) {
		int br = 1;
		int brdesc = 2;
		int impactbr = 3;
		int brinclude = 4;
		int module = 5;
		int modinclude = 6;
		int testcase = 7;
		int tcinclude = 8;
		int tcid = 9;
		int criticality = 10;
		int repeatability = 11;

		String BR = "", BRinclude = "", BRdesc = "", ImpactBR = "", Mod = "", Modinclude = "", TC = "", TCinclude = "",
				TcId = "", Criticality = "", tempBR = "", tempBRinclude = "", tempMod = "", tempModinclude = "";
		double Repeatability = 0;

		int rowCount = 0;
		FileInputStream inputStream = null;
		FileOutputStream outputStream = null;
		Workbook workbook = null;
		try {
			inputStream = new FileInputStream(new File(fileName));
			workbook = new XSSFWorkbook(inputStream);
			Sheet sheet = workbook.getSheet(sheetName);
			Row addrow = null;
			rowCount = sheet.getLastRowNum() - sheet.getFirstRowNum();
			if (workbook.getSheet("TempDelete") == null) {
				workbook.createSheet("TempDelete");
			}
			XSSFCellStyle style = (XSSFCellStyle) workbook.createCellStyle();
			style.setBorderBottom(XSSFCellStyle.BORDER_MEDIUM);
			style.setBorderTop(XSSFCellStyle.BORDER_MEDIUM);
			style.setBorderRight(XSSFCellStyle.BORDER_MEDIUM);
			style.setBorderLeft(XSSFCellStyle.BORDER_MEDIUM);
			XSSFColor blackcolor = new XSSFColor(new java.awt.Color(0, 0, 0));
			style.setBottomBorderColor(blackcolor);
			style.setRightBorderColor(blackcolor);
			style.setTopBorderColor(blackcolor);
			style.setLeftBorderColor(blackcolor);
			style.setFillForegroundColor(HSSFColor.LIGHT_GREEN.index);
			style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			XSSFFont font = (XSSFFont) workbook.createFont();
			font.setBold(true);
			style.setFont(font);

			XSSFCellStyle style2 = (XSSFCellStyle) workbook.createCellStyle();
			style2.setBorderBottom(XSSFCellStyle.BORDER_THIN);
			style2.setBorderTop(XSSFCellStyle.BORDER_THIN);
			style2.setBorderRight(XSSFCellStyle.BORDER_THIN);
			style2.setBorderLeft(XSSFCellStyle.BORDER_THIN);
			style2.setBottomBorderColor(blackcolor);
			style2.setRightBorderColor(blackcolor);
			style2.setTopBorderColor(blackcolor);
			style2.setLeftBorderColor(blackcolor);
			style2.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
			style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			style2.setWrapText(true);

			Sheet tempDeletesheet = workbook.getSheet("TempDelete");

			Row firstRow = tempDeletesheet.createRow(0);

			for (int i = 0; i <= 8; i++) {
				firstRow.createCell(i);
			}

			firstRow.getCell(0).setCellValue("SlNo");
			firstRow.getCell(1).setCellValue("BR");
			firstRow.getCell(2).setCellValue("Module");
			firstRow.getCell(3).setCellValue("Testcase");
			firstRow.getCell(4).setCellValue("TestcaseID");
			firstRow.getCell(5).setCellValue("Criticality");
			firstRow.getCell(6).setCellValue("Iterations");
			firstRow.getCell(7).setCellValue("Results");
			firstRow.getCell(8).setCellValue("Time Taken");
			firstRow.getCell(0).setCellStyle(style);
			firstRow.getCell(1).setCellStyle(style);
			firstRow.getCell(2).setCellStyle(style);
			firstRow.getCell(3).setCellStyle(style);
			firstRow.getCell(4).setCellStyle(style);
			firstRow.getCell(5).setCellStyle(style);
			firstRow.getCell(6).setCellStyle(style);
			firstRow.getCell(7).setCellStyle(style);
			firstRow.getCell(8).setCellStyle(style);

			int k = 1;

			String tempBRdesc = "", tempImpactBR = "", tempTC = "", tempTCinclude = "";
			for (int i = 1; i <= rowCount; i++) {

				try {
					Row row = sheet.getRow(i);
					BR = row.getCell(br).getStringCellValue();
					BRdesc = row.getCell(brdesc).getStringCellValue();
					ImpactBR = row.getCell(impactbr).getStringCellValue();
					BRinclude = row.getCell(brinclude).getStringCellValue();
					Mod = row.getCell(module).getStringCellValue();
					Modinclude = row.getCell(modinclude).getStringCellValue();
					TC = row.getCell(testcase).getStringCellValue();
					TCinclude = row.getCell(tcinclude).getStringCellValue();
					TcId = row.getCell(tcid).getStringCellValue();
					Criticality = row.getCell(criticality).getStringCellValue();
					Repeatability = row.getCell(repeatability).getNumericCellValue();

					if ((row.getCell(br).getCellType() == Cell.CELL_TYPE_BLANK) || (BR.equalsIgnoreCase(""))) {
						BR = tempBR;
					}

					if ((!BRinclude.equalsIgnoreCase("Yes")) && (!BRinclude.equalsIgnoreCase("No"))) {
						BRinclude = tempBRinclude;
					}

					if ((row.getCell(module).getCellType() == Cell.CELL_TYPE_BLANK) || (Mod.equalsIgnoreCase(""))) {
						Mod = tempMod;
					}

					if ((!Modinclude.equalsIgnoreCase("Yes")) && (!Modinclude.equalsIgnoreCase("No"))) {
						Modinclude = tempModinclude;
					}
					if (BRinclude.equalsIgnoreCase("Yes")) {
						if (Modinclude.equalsIgnoreCase("Yes")) {
							if (TCinclude.equalsIgnoreCase("Yes")) {
								tempDeletesheet.createRow(k);
								addrow = tempDeletesheet.getRow(k);
								addrow.createCell(0).setCellValue("#" + k);
								addrow.createCell(1).setCellValue(BR);
								addrow.createCell(2).setCellValue(Mod);
								addrow.createCell(3).setCellValue(TC);
								addrow.createCell(4).setCellValue(TcId);
								addrow.createCell(5).setCellValue(Criticality);
								addrow.createCell(6).setCellValue("1");
								addrow.createCell(7).setCellValue("Norun");
								addrow.createCell(8).setCellValue("N/A");

								addrow.getCell(0).setCellStyle(style2);
								addrow.getCell(1).setCellStyle(style2);
								addrow.getCell(2).setCellStyle(style2);
								addrow.getCell(3).setCellStyle(style2);
								addrow.getCell(4).setCellStyle(style2);
								addrow.getCell(5).setCellStyle(style2);
								addrow.getCell(6).setCellStyle(style2);
								addrow.getCell(7).setCellStyle(style2);
								addrow.getCell(8).setCellStyle(style2);

								int intRepeat = (int) Repeatability;
								if (intRepeat > 1) {
									int iterationUpdate = 2;
									for (int j = intRepeat; j > 1; j--) {
										k++;
										tempDeletesheet.createRow(k);
										addrow = tempDeletesheet.getRow(k);
										addrow.createCell(0).setCellValue("#" + k);
										addrow.createCell(1).setCellValue(BR);
										addrow.createCell(2).setCellValue(Mod);
										addrow.createCell(3).setCellValue(TC);
										addrow.createCell(4).setCellValue(TcId);
										addrow.createCell(5).setCellValue(Criticality);
										addrow.createCell(6).setCellValue(String.valueOf(iterationUpdate));
										addrow.createCell(7).setCellValue("Norun");
										addrow.createCell(8).setCellValue("N/A");

										addrow.getCell(0).setCellStyle(style2);
										addrow.getCell(1).setCellStyle(style2);
										addrow.getCell(2).setCellStyle(style2);
										addrow.getCell(3).setCellStyle(style2);
										addrow.getCell(4).setCellStyle(style2);
										addrow.getCell(5).setCellStyle(style2);
										addrow.getCell(6).setCellStyle(style2);
										addrow.getCell(7).setCellStyle(style2);
										addrow.getCell(8).setCellStyle(style2);
										iterationUpdate++;
									}
								}
								k++;
							}
						}

					}

					else {

					}
					tempBR = BR;
					tempMod = Mod;
					tempTC = TC;
					tempBRdesc = BRdesc;
					tempImpactBR = ImpactBR;
					tempBRinclude = BRinclude;
					tempModinclude = Modinclude;

				} catch (Exception e) {
					logger.error("Exception : " + ShowMessage.getStackTrace(e));
				}
				tempDeletesheet.autoSizeColumn(0);
				tempDeletesheet.autoSizeColumn(1);
				tempDeletesheet.autoSizeColumn(2);
				tempDeletesheet.autoSizeColumn(3);
				tempDeletesheet.autoSizeColumn(5);
				tempDeletesheet.autoSizeColumn(6);
				tempDeletesheet.autoSizeColumn(7);
				tempDeletesheet.autoSizeColumn(8);
			}

			inputStream.close();
			outputStream = new FileOutputStream(new File(fileName));
			workbook.write(outputStream);
			outputStream.close();
			workbook.close();
			sheet = null;
			workbook = null;

		} catch (Exception e) {
			logger.error("Exception : " + ShowMessage.getStackTrace(e));
		}
	}

	/**
	 * To remove the rows with drop down select No
	 * 
	 * @param fileName
	 * @param sheetName
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	private void deleteNoRows(String Filename, String Sheetname) {
		int br = 1;
		int brdesc = 2;
		int impactbr = 3;
		int brinclude = 4;
		int module = 5;
		int modinclude = 6;
		int testcase = 7;
		int tcinclude = 8;
		int tcid = 9;
		int criticality = 10;
		int repeatability = 11;

		String BR = "", BRinclude = "", BRdesc = "", ImpactBR = "", Mod = "", Modinclude = "", TC = "", TCinclude = "",
				TcId = "", Criticality = "";
		double Repeatability = 0;
		String tempBR = "", tempBRinclude = "", tempMod = "", tempModinclude = "";

		int rowCount = 0;
		FileInputStream inputStream = null;
		FileOutputStream outputStream = null;
		Workbook workbook = null;
		Sheet sheet = null;
		Sheet tempDeletesheet = null;

		try {
			File file = new File(Filename);

			inputStream = new FileInputStream(file);

			workbook = new XSSFWorkbook(inputStream);
			sheet = workbook.getSheet(Sheetname);
			Row addrow = null;

			logger.info("Reading: " + Filename + "-" + Sheetname);
			rowCount = sheet.getLastRowNum() - sheet.getFirstRowNum();
			if (workbook.getSheet("TempDelete") == null) {
				workbook.createSheet("TempDelete");
				logger.info("Created TempDelete sheet");

			}
			XSSFCellStyle style = (XSSFCellStyle) workbook.createCellStyle();
			style.setBorderBottom(XSSFCellStyle.BORDER_MEDIUM);
			style.setBorderTop(XSSFCellStyle.BORDER_MEDIUM);
			style.setBorderRight(XSSFCellStyle.BORDER_MEDIUM);
			style.setBorderLeft(XSSFCellStyle.BORDER_MEDIUM);
			XSSFColor blackcolor = new XSSFColor(new java.awt.Color(0, 0, 0));
			style.setBottomBorderColor(blackcolor);
			style.setRightBorderColor(blackcolor);
			style.setTopBorderColor(blackcolor);
			style.setLeftBorderColor(blackcolor);
			style.setFillForegroundColor(HSSFColor.LIGHT_GREEN.index);
			style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			XSSFFont font = (XSSFFont) workbook.createFont();
			font.setBold(true);
			style.setFont(font);

			XSSFCellStyle style2 = (XSSFCellStyle) workbook.createCellStyle();
			style2.setBorderBottom(XSSFCellStyle.BORDER_THIN);
			style2.setBorderTop(XSSFCellStyle.BORDER_THIN);
			style2.setBorderRight(XSSFCellStyle.BORDER_THIN);
			style2.setBorderLeft(XSSFCellStyle.BORDER_THIN);
			style2.setBottomBorderColor(blackcolor);
			style2.setRightBorderColor(blackcolor);
			style2.setTopBorderColor(blackcolor);
			style2.setLeftBorderColor(blackcolor);
			style2.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
			style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			style2.setWrapText(true);

			tempDeletesheet = workbook.getSheet("TempDelete");

			Row firstRow = tempDeletesheet.createRow(0);

			for (int i = 0; i <= 8; i++) {
				firstRow.createCell(i);
			}

			firstRow.getCell(0).setCellValue("SlNo");
			firstRow.getCell(1).setCellValue("BR");
			firstRow.getCell(2).setCellValue("Module");
			firstRow.getCell(3).setCellValue("Testcase");
			firstRow.getCell(4).setCellValue("TestcaseID");
			firstRow.getCell(5).setCellValue("Criticality");
			firstRow.getCell(6).setCellValue("Iterations");
			firstRow.getCell(7).setCellValue("Results");
			firstRow.getCell(8).setCellValue("Time Taken");
			firstRow.getCell(0).setCellStyle(style);
			firstRow.getCell(1).setCellStyle(style);
			firstRow.getCell(2).setCellStyle(style);
			firstRow.getCell(3).setCellStyle(style);
			firstRow.getCell(4).setCellStyle(style);
			firstRow.getCell(5).setCellStyle(style);
			firstRow.getCell(6).setCellStyle(style);
			firstRow.getCell(7).setCellStyle(style);
			firstRow.getCell(8).setCellStyle(style);

			int k = 1;

			String tempBRdesc = "", tempImpactBR = "", tempTC = "", tempTCinclude = "";
			for (int i = 1; i <= rowCount; i++) {

				try {
					Row row = sheet.getRow(i);
					BR = row.getCell(br).getStringCellValue();
					BRdesc = row.getCell(brdesc).getStringCellValue();
					ImpactBR = row.getCell(impactbr).getStringCellValue();
					BRinclude = row.getCell(brinclude).getStringCellValue();
					Mod = row.getCell(module).getStringCellValue();
					Modinclude = row.getCell(modinclude).getStringCellValue();
					TC = row.getCell(testcase).getStringCellValue();
					TCinclude = row.getCell(tcinclude).getStringCellValue();
					TcId = row.getCell(tcid).getStringCellValue();
					Criticality = row.getCell(criticality).getStringCellValue();
					Repeatability = row.getCell(repeatability).getNumericCellValue();

					if ((row.getCell(br).getCellType() == Cell.CELL_TYPE_BLANK) || (BR.equalsIgnoreCase(""))) {
						BR = tempBR;
					}

					if ((!BRinclude.equalsIgnoreCase("Yes")) && (!BRinclude.equalsIgnoreCase("No"))) {
						BRinclude = tempBRinclude;
					}

					if ((row.getCell(module).getCellType() == Cell.CELL_TYPE_BLANK) || (Mod.equalsIgnoreCase(""))) {
						Mod = tempMod;
					}

					if ((!Modinclude.equalsIgnoreCase("Yes")) && (!Modinclude.equalsIgnoreCase("No"))) {
						Modinclude = tempModinclude;
					}

					if (BRinclude.equalsIgnoreCase("Yes")) {
						if (Modinclude.equalsIgnoreCase("Yes")) {
							if (TCinclude.equalsIgnoreCase("Yes")) {
								tempDeletesheet.createRow(k);
								addrow = tempDeletesheet.getRow(k);

								addrow.createCell(0).setCellValue("#" + k);
								addrow.createCell(1).setCellValue(BR);
								addrow.createCell(2).setCellValue(Mod);
								addrow.createCell(3).setCellValue(TC);
								addrow.createCell(4).setCellValue(TcId);
								addrow.createCell(5).setCellValue(Criticality);
								addrow.createCell(6).setCellValue(Repeatability);
								addrow.createCell(7).setCellValue("Norun");
								addrow.createCell(8).setCellValue("N/A");

								addrow.getCell(0).setCellStyle(style2);
								addrow.getCell(1).setCellStyle(style2);
								addrow.getCell(2).setCellStyle(style2);
								addrow.getCell(3).setCellStyle(style2);
								addrow.getCell(4).setCellStyle(style2);
								addrow.getCell(5).setCellStyle(style2);
								addrow.getCell(6).setCellStyle(style2);
								addrow.getCell(7).setCellStyle(style2);
								addrow.getCell(8).setCellStyle(style2);
								k++;

							}
						}

					}
					else {
					}
					tempBR = BR;
					tempMod = Mod;
					tempTC = TC;
					tempBRdesc = BRdesc;
					tempImpactBR = ImpactBR;
					tempBRinclude = BRinclude;
					tempModinclude = Modinclude;
				} catch (Exception e) {
					logger.error("error deleting no rows " + e);
				}
				tempDeletesheet.autoSizeColumn(0);
				tempDeletesheet.autoSizeColumn(1);
				tempDeletesheet.autoSizeColumn(2);
				tempDeletesheet.autoSizeColumn(3);
				tempDeletesheet.autoSizeColumn(5);
				tempDeletesheet.autoSizeColumn(6);
				tempDeletesheet.autoSizeColumn(7);
				tempDeletesheet.autoSizeColumn(8);
			}
			br = 1;
			module = 2;
			testcase = 3;
			BR = "";
			BRdesc = "";
			Mod = "";
			ImpactBR = "";
			TC = "";
			tempBR = "";
			tempMod = "";
			rowCount = tempDeletesheet.getLastRowNum() - tempDeletesheet.getFirstRowNum();
			for (int i = 1; i <= rowCount; i++) {
				try {
					Row row = tempDeletesheet.getRow(i);
					BR = row.getCell(br).getStringCellValue();
					Mod = row.getCell(module).getStringCellValue();
					TC = row.getCell(testcase).getStringCellValue();

					if (BR.equalsIgnoreCase(tempBR)) {
						row.getCell(br).setCellValue("");
					}
					if (Mod.equalsIgnoreCase(tempMod)) {
						row.getCell(module).setCellValue("");
					}

					tempBR = BR;
					tempMod = Mod;

				} catch (Exception e) {
					logger.error("Exception : " + ShowMessage.getStackTrace(e));
				}
			}
			try {
				workbook.removeSheetAt(workbook.getSheetIndex(Sheetname));
			} catch (Exception e) {
				logger.error("Unable to delete sheet:" + Sheetname + " " + ShowMessage.getStackTrace(e));
			}
			workbook.setSheetName(workbook.getSheetIndex("TempDelete"), "RunPlanForDisplay");
			inputStream.close();
			outputStream = new FileOutputStream(file);
			workbook.write(outputStream);
			outputStream.close();
			workbook.close();
		} catch (Exception e) {
			logger.error("Exception : " +ShowMessage.getStackTrace(e));
		}

	}

	/**
	 * To delete the multiple rows with same BR name
	 * 
	 * @param fileName
	 * @param sheetName
	 */
	@SuppressWarnings("unused")
	private void deleteDuplicateBR(String fileName, String sheetName) {
		int br = 1;
		int module = 2;
		int testcase = 3;
		String OldSheetname = "TempDelete";
		String BR = "", BRdesc = "", Mod = "", ImpactBR = "", TC = "";
		String tempBR = "", tempMod = "";
		int rowCount = 0;
		FileInputStream inputStream = null;
		FileOutputStream outputStream = null;
		Workbook workBook = null;
		Sheet sheet = null;
		try {
			if (fileName == null || fileName.trim().isEmpty() || sheetName == null || sheetName.trim().isEmpty()) {
				return;
			}
			File file = new File(fileName);
			inputStream = new FileInputStream(file);
			workBook = new XSSFWorkbook(inputStream);
			sheet = workBook.getSheet(OldSheetname);
			rowCount = sheet.getLastRowNum() - sheet.getFirstRowNum();
			for (int i = 1; i <= rowCount; i++) {
				try {
					Row row = sheet.getRow(i);
					BR = row.getCell(br).getStringCellValue();
					Mod = row.getCell(module).getStringCellValue();
					TC = row.getCell(testcase).getStringCellValue();

					if (BR.equalsIgnoreCase(tempBR)) {
						row.getCell(br).setCellValue("");
					}
					if (Mod.equalsIgnoreCase(tempMod)) {
						row.getCell(module).setCellValue("");
					}

					tempBR = BR;
					tempMod = Mod;

				} catch (Exception e) {
					logger.error("Exception : " +ShowMessage.getStackTrace(e));
				}
			}
			try {
				workBook.setSheetName(workBook.getSheetIndex("TempDelete"), "RunplanSheet");
				inputStream.close();
				outputStream = new FileOutputStream(file);
				workBook.write(outputStream);
				outputStream.close();
			} catch (Exception e) {
				logger.error("Exception : " +ShowMessage.getStackTrace(e));
			}
			workBook.close();
			sheet = null;
			workBook = null;
		} catch (Exception e) {
			logger.error("Exception : " +ShowMessage.getStackTrace(e));
		}
	}

	/**
	 * To set the run file name in the results sheet of excel
	 * 
	 * @param fileName
	 * @param sheetName
	 */
	private void setRunFile(String fileName, String sheetName) {
		FileInputStream inputStream = null;
		FileOutputStream outputStream = null;
		Workbook workBook = null;
		Sheet sheet = null;
		try {
			if (fileName == null || fileName.trim().isEmpty() || sheetName == null || sheetName.trim().isEmpty()) {
				return;
			}
			File file = new File(fileName);
			inputStream = new FileInputStream(file);
			workBook = new XSSFWorkbook(inputStream);
			sheet = workBook.getSheet(sheetName);
			Row r = sheet.getRow(3);
			Cell c = r.getCell(2);
			if (c == null) {
				c = r.createCell(3, Cell.CELL_TYPE_STRING);
			}
			c.setCellValue(fileName.substring(fileName.lastIndexOf("\\") + 1));
			sheet.autoSizeColumn(2);
			outputStream = new FileOutputStream(file);
			workBook.write(outputStream);
		} catch (Exception e) {
			logger.error("Exception : " +ShowMessage.getStackTrace(e));
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
			if (workBook != null) {
				try {
					workBook.close();
				} catch (IOException e) {
					logger.error("Exception : " + ShowMessage.getStackTrace(e));
				}
				workBook = null;
			}
		}
	}
}
