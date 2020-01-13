package generic;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.DataValidationConstraint;
import org.apache.poi.ss.usermodel.DataValidationHelper;
import org.apache.poi.ss.usermodel.Hyperlink;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFDataValidationHelper;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import testdriver.DriverScript;


public class ExcelRelated 

{
	Logger Log = Logger.getLogger(Logger.class.getName());
	ArrayList<Testobject> testobjects = new ArrayList<>();
	ArrayList<Teststeps> allTeststeps = new ArrayList<>();
	ArrayList<Testcases> allTestcases = new ArrayList<>();
	
	
	
	/*int br =0;
	int module =1;
	int testcase =2;
	int slno =3;
	int keyword =4;
	int object =5;
	int input =6;
	int result =7;
	int failureReason =8;
	int failureImage = 9;
	int classfile =10;*/
	
	int br =1;
	int module =2;
	int testcase =3;
	int slno =4;
	int keyword =5;
	int object =6;
	int input =7;
	int result =8;
	int failureReason =9;
	int failureImage = 10;
	int classfile =11;
	
		String[] LogDefect = new String[2];
		String[] Reproducibility = new String[8];
		String[] Severity = new String[8];
		String[] Priority = new String[8];
		String[] AssignTo = new String[8];
	
//---------------------------------	
	public ArrayList<Testobject> readAllobjects(String Filename, String Sheetname)
	{
		Log.info("function: readAllobjects");
		
		int objslno =0;
		int screenname =1;
		int objectname =2;
		int type =3;
		int description =4;
		
		String serialNo="blank";
		String screenName="blank";
		String objectName="blank";
		String typeOfObject="blank";
		String descriptionofObject="blank";
		
		Testobject obj=null;
		File file = new File(Filename);
		
		Workbook workbook = null;
		Sheet sheet = null;
		try {
			FileInputStream inputStream = new FileInputStream(file);
			String fileExtensionName = Filename.substring(Filename.length()-4);
			//Log.info(""+Filename+"-extenstion-"+fileExtensionName);
				if (fileExtensionName.equals("xlsx")) 
				{
					workbook = new XSSFWorkbook(inputStream);
					
					sheet = workbook.getSheet(Sheetname);
					Log.info("Reading: "+Filename+"-"+Sheetname);
					int rowCount = sheet.getLastRowNum()-sheet.getFirstRowNum();
				    Log.info("Row count: "+rowCount);
				    for (int i = 1; i <= rowCount; i++) 
					{
				    	if (!(sheet.getRow(i).getCell(objslno).toString().length()==0)) {
				    		serialNo = sheet.getRow(i).getCell(objslno).toString().trim();}
				    	if (!(sheet.getRow(i).getCell(screenname).toString().length()==0)) {
				    		screenName = sheet.getRow(i).getCell(screenname).toString().trim();}
				    	if (!(sheet.getRow(i).getCell(objectname).toString().length()==0)) {
				    		objectName = sheet.getRow(i).getCell(objectname).toString().trim();}
				    	if (!(sheet.getRow(i).getCell(type).toString().length()==0)) {
				    		typeOfObject = sheet.getRow(i).getCell(type).toString().trim();}
				    	if (!(sheet.getRow(i).getCell(description).toString().length()==0)) {
				    		descriptionofObject = sheet.getRow(i).getCell(description).toString().trim();}
				    	
						obj = new Testobject(serialNo,
								screenName,
								objectName,
								typeOfObject,
								descriptionofObject);
						testobjects.add(obj);
					}
				    
				   	
				} else {
					Log.fatal(""+Filename+" "+"extension is invalid");
				}
					inputStream.close();
					//workbook.close();
				 	sheet=null;
					workbook=null;
					
		
		} catch (Exception e) {
			Log.fatal(e);
		}
		
		return testobjects; 
	}
	
//---------------------------------
	/*
 	public Configuration readConfiguration(String Filename, String Sheetname)
		{
 			Log.info("readConfiguration");
 			Configuration configuration=new Configuration("","","","");
			File file = new File(Filename);
			int i=0,j=0;
			Cell datarow;
			String url="URL";
			String browser="Browser";
			String username="Username";
			String password="Password";
			Workbook workbook = null;
			Sheet sheet = null;
			
			
			try {
				FileInputStream inputStream = new FileInputStream(file);
				String fileExtensionName = Filename.substring(Filename.length()-4);
				//Log.info(""+Filename+"-extenstion-"+fileExtensionName);
					if (fileExtensionName.equals("xlsx")) 
					{
						workbook = new XSSFWorkbook(inputStream);
						sheet = workbook.getSheet(Sheetname);
						Log.info("Reading: "+Filename+"-"+Sheetname);
						int rowCount = sheet.getLastRowNum()-sheet.getFirstRowNum();
					    Log.info("Row count: "+rowCount);
					    Iterator<Row> iterator = sheet.iterator();
					    
					    while (iterator.hasNext()) {
				            Row nextRow = iterator.next();
				            Iterator<Cell> cellIterator = nextRow.cellIterator();
				             
				            while (cellIterator.hasNext()) {
				                Cell cell = cellIterator.next();
				                
				                if (cell.getStringCellValue().equalsIgnoreCase(url)) {
				                	i=cell.getRowIndex();
				                	j=cell.getColumnIndex();
				                	datarow = sheet.getRow(i).getCell(j+1);
				                	Log.info(cell.getStringCellValue()+":"+datarow.getStringCellValue());
				                	configuration.seturl(datarow.getStringCellValue().trim());
				                	break;
								} else if(cell.getStringCellValue().equalsIgnoreCase(browser)) {
									i=cell.getRowIndex();
				                	j=cell.getColumnIndex();
				                	datarow = sheet.getRow(i).getCell(j+1);
				                	Log.info(cell.getStringCellValue()+":"+datarow.getStringCellValue());
				                	configuration.setbrowser(datarow.getStringCellValue().trim());
				                	break;
								}else if(cell.getStringCellValue().equalsIgnoreCase(username)) {
									i=cell.getRowIndex();
				                	j=cell.getColumnIndex();
				                	datarow = sheet.getRow(i).getCell(j+1);
				                	Log.info(cell.getStringCellValue()+":"+datarow.getStringCellValue());
				                	configuration.setappusername(datarow.getStringCellValue().trim());
				                	break;
								}else if(cell.getStringCellValue().equalsIgnoreCase(password)) {
									i=cell.getRowIndex();
				                	j=cell.getColumnIndex();
				                	datarow = sheet.getRow(i).getCell(j+1);
				                	Log.info(cell.getStringCellValue()+":"+datarow.getStringCellValue());
				                	configuration.setapppassword(datarow.getStringCellValue().trim());
				                	break;
								}
				                
				                				                   
				            }
				        }
     				} else {
						Log.fatal(""+Filename+" "+"extension is invalid");
					}
					inputStream.close();
					workbook.close();
				    sheet=null;
					workbook=null;
					
					
			} catch (Exception e) {
				Log.fatal(e);
				e.printStackTrace();
			}
			
			return configuration; 
		}
		*/
//---------------------------------
 	

		public void markteststepsNorun(String Filename, String Sheetname)
 		{
			Log.info("function: markteststepsNorun");
 			String result ="Result";
 			int resultColNo=0;
 			File file = new File(Filename);
 			
 			try {
				
 				FileInputStream inputStream = new FileInputStream(file);
 				String fileExtensionName = Filename.substring(Filename.length()-4);
 				//Log.info(""+Filename+"-extenstion-"+fileExtensionName);
 					if (fileExtensionName.equals("xlsx")) 
 					{
 						Workbook workbook = new XSSFWorkbook(inputStream);
 						Sheet sheet = workbook.getSheet(Sheetname);
 						Row headerrow = sheet.getRow(0);
 						for (int j = 0; j < headerrow.getLastCellNum(); j++) {
							if (headerrow.getCell(j).getStringCellValue().equalsIgnoreCase(result)) 
							{
								resultColNo=j;
								break;
							}
						}
 						
 						Log.info("Reading: "+Filename+"-"+Sheetname);
 						int rowCount = sheet.getLastRowNum()-sheet.getFirstRowNum();
 					    Log.info("Row count: "+rowCount);
       
 					    
 					    for (int i = 1; i <= rowCount; i++) 
 	 						{
 					    	
 					    		Row row = sheet.getRow(i);
 					    		//Cell writeCell = row.createCell(10);
			                	Cell writeCell = row.getCell(resultColNo);
			                	
			                	writeCell.setCellValue("Norun");
			                	//sheet.getRow(i).getCell(result).setCellValue("Norun");
 					    	
			                	//Log.info("Row: "+(i+1)+" Col: "+result+" is set to Norun");
 	 						} 
 					    	inputStream.close();
 					    	
 					    	FileOutputStream outputStream = new FileOutputStream(file); 
 					    	workbook.write(outputStream);
 					    	outputStream.close();
 					    	//workbook.close();
 					    	sheet=null;
 					    	workbook=null;
 						   } 
 					  else {
 	 						Log.fatal(""+Filename+" "+"extension is invalid");}
 				
 					 
			} catch (Exception e) {
				Log.fatal(e);
			} 			
 			
 		}
 //---------------------------------	
 		
		public ArrayList<Teststeps> readAllteststeps(String Filename, String Sheetname, ArrayList<Testcases> AllTestcases)

 		{
			Log.info("function: readAllteststeps");
			
			//int expectedoutput =9;
			ArrayList<Testcases> AllTC = AllTestcases;
 					
 			Teststeps obj=null;
 			File file = new File(Filename);
 			Workbook workbook = null;
 			Sheet sheet = null;
 			
 			try {
				
 				FileInputStream inputStream = new FileInputStream(file);
 				String fileExtensionName = Filename.substring(Filename.length()-4);
 				//Log.info(""+Filename+"-extenstion-"+fileExtensionName);
 					if (fileExtensionName.equals("xlsx")) 
 					{
 						workbook = new XSSFWorkbook(inputStream);
 						sheet = workbook.getSheet(Sheetname);
 						Log.info("Reading: "+Filename+"-"+Sheetname);
 						int rowCount = sheet.getLastRowNum()-sheet.getFirstRowNum();
 					    Log.info("Row count: "+rowCount);
 					    
 					    String tempbusreq="",tempmodule="",temptcname="";
 					    
 				            for (int i = 1; i <= rowCount; i++) 
 	 						{
 				            	String serialNo="blank", classFile="blank", keyWord="blank", objectname="blank", inputValue="blank", resultVal="blank";
 				            	String tcid = " ", tccrit = " ";
 				            	String busreq = sheet.getRow(i).getCell(br).getStringCellValue().trim();

 				            	if (busreq.length()==0)
 				            	 {busreq=tempbusreq;}
								
 				            	String mod = sheet.getRow(i).getCell(module).getStringCellValue().trim();

 				            	if (mod.length()==0)
 				            	 {mod=tempmodule;}
 				            	
 				            	String tcname = sheet.getRow(i).getCell(testcase).getStringCellValue().trim();

 				            	if (tcname.length()==0)
 				            	 {tcname=temptcname;}
 				            	
 				            	if (!(sheet.getRow(i).getCell(slno).toString().length()==0)) 
 				            	{
 				            		try {
 				            			serialNo=sheet.getRow(i).getCell(slno).getStringCellValue().trim();
									} catch (Exception e) {
										double tempvalue= sheet.getRow(i).getCell(slno).getNumericCellValue();
										serialNo = String.valueOf(tempvalue);
										//Log.info("Serial No:"+serialNo);
									}
 				            		
 				            		
								} 
 				            	
 				            	if (!(sheet.getRow(i).getCell(classfile).toString().length()==0)) 
 				            	{
 				            		classFile=sheet.getRow(i).getCell(classfile).toString().trim();
								} 
 				            	
 				            	if (!(sheet.getRow(i).getCell(keyword).toString().length()==0)) 
 				            	{
 				            		keyWord=sheet.getRow(i).getCell(keyword).toString().trim();
								} 
 				            	
 				            	if (!(sheet.getRow(i).getCell(object).toString().length()==0)) 
 				            	{
 				            		objectname=sheet.getRow(i).getCell(object).toString().trim();
								} 
 				            	
 				            	if (!(sheet.getRow(i).getCell(input).toString().length()==0)) 
 				            	{
 				            		try {
 				            			inputValue=sheet.getRow(i).getCell(input).toString().trim();
 				            		} catch (Exception e) {
										double tempvalue= sheet.getRow(i).getCell(input).getNumericCellValue();
										inputValue = String.valueOf(tempvalue);
										//Log.info("input value:"+inputValue);
									}
 				            	}
 				            	
 				            		resultVal="Norun";
 				            		for (int j = 0; j < AllTestcases.size(); j++) 
 				            		{
										if (busreq.equalsIgnoreCase(AllTC.get(j).getBR())) 
										{
											if (mod.equalsIgnoreCase(AllTC.get(j).getModule())) 
											{
												if (tcname.equalsIgnoreCase(AllTC.get(j).getTestcasename()))
												{
													tcid = AllTC.get(j).getTCid();
													tccrit = AllTC.get(j).getTCcr();
												}
											}
										}
									}
 				            	

 	 							obj = new Teststeps(busreq,
 	 									mod,
 	 									tcname,
 	 									serialNo,
 	 									classFile,
 	 									keyWord,
 	 									objectname,
 	 									inputValue,
 	 									//outputVal,
 	 									resultVal,
 	 									String.valueOf(i), 	 																	
 	 									"","",
 	 									tcid,
 	 									tccrit);
 	 							allTeststeps.add(obj);	
 	 							tempbusreq=busreq;
 	 							tempmodule=mod;
 	 							temptcname=tcname;
 	 							//Log.info(""+busreq+" "+mod+" "+tcname+" "+sheet.getRow(i).getCell(slno).toString());
 	 						} 
 				            
 	 					   } 
 					  else {
 	 						Log.fatal(""+Filename+" "+"extension is invalid");}
 				
 				//	workbook.close();			
 					sheet=null;
					workbook=null;
					inputStream.close();
					} catch (Exception e) {
				Log.fatal(e);
			} 			
 			return allTeststeps; 
 		}
 		
//---------------------------------		
 		public ArrayList<String> getTeststepparameters(ArrayList<Teststeps> testSteps, int teststepnum)

 		{
 			Log.info("function: getTeststepparameters");
 			ArrayList<String> params= new ArrayList<String>();
  			
 			try {
 				//Log.info("getTeststepparameters");
 				Log.info("Step#:  "+teststepnum);
 				Log.info("Object: "+testSteps.get(teststepnum).getObject());
				Log.info("Input:  "+testSteps.get(teststepnum).getInput());
				//Log.info("Ouput:  "+testSteps.get(teststepnum).getOutput());
 				
				//params = new ArrayList<String>();
				params.add(testSteps.get(teststepnum).getObject());
				params.add(testSteps.get(teststepnum).getInput());
				//params.add(testSteps.get(teststepnum).getOutput());
 				 
 					
			} catch (Exception e) {
				Log.fatal(e);
				
			} 			
 			return params; 
 		}	
 		
 //----------------------------------------------------			
 		
 		
 		public void printdata(ArrayList<String> data){
 			System.setProperty("webdriver.chrome.driver", "C:\\Users\\Kendolen\\Downloads\\Selenium\\chromedriver\\chromedriver.exe");
 			//driver.get("http://www.google.com");
 			System.out.println("printed data"+data);
 		}
 		
 		public void openbrowser(ArrayList<String> data){
 			
 			
 			System.out.println("printed data"+data);
 		}
 		
 		/*
 		public void printdata(String a, String b, String c){
 			System.out.println("printed data"+a+" "+b+" "+c);
 		}*/
 		
//----------------------------------------------------
 		public void writeTeststepResults(String Filename, String Sheetname, ArrayList<Teststeps> results)
 		{
 	 	
 			Log.info("function: writeTeststepResults");
 			File file = new File(Filename);
 			Workbook workbook = null;
 			Sheet sheet = null;
 			
 			
				
 			try {
 				FileInputStream inputStream = new FileInputStream(file);
 				String fileExtensionName = Filename.substring(Filename.length()-4);
 				//Log.info(""+Filename+"-extenstion-"+fileExtensionName);
 					if (fileExtensionName.equals("xlsx")) 
 					{
 						workbook = new XSSFWorkbook(inputStream);
 						sheet = workbook.getSheet(Sheetname);
 						XSSFCellStyle style=(XSSFCellStyle) workbook.createCellStyle();
 						XSSFFont font= (XSSFFont) workbook.createFont();
 						font.setBold(true);
 						font.setColor(new XSSFColor(new java.awt.Color(255, 255, 255)));
 						style.setFont(font);
 						style.setFillForegroundColor(new XSSFColor(new java.awt.Color(255,0,0)));
 					//	style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
 						
 						
 						Log.info("Reading: "+Filename+"-"+Sheetname);
 						//int rowCount = sheet.getLastRowNum()-sheet.getFirstRowNum();
 					    Log.info("No of result Teststeps: "+results.size());
 					    Log.info("No of Teststeps: "+sheet.getLastRowNum());
 					    for (int i = 0; i < results.size(); i++) 
 						{
 					    	Row row = sheet.getRow(i+1);
 					    	Cell cell = row.getCell(result);
 					    	cell.setCellValue(results.get(i).getResult());
 					    	//Log.info("Wrote result for"+(i+1));
 					    	if (results.get(i).getResult().equalsIgnoreCase("Fail")) {
 					    		row.getCell(br).setCellStyle(style);
 					    		row.getCell(module).setCellStyle(style);
 					    		row.getCell(testcase).setCellStyle(style);
 					    		row.getCell(slno).setCellStyle(style);
 					    		row.getCell(keyword).setCellStyle(style);
 					    		row.getCell(object).setCellStyle(style);
 					    		row.getCell(input).setCellStyle(style);
 					    		row.getCell(result).setCellStyle(style);
 					    		row.getCell(failureReason).setCellStyle(style);
 					    		row.getCell(failureImage).setCellStyle(style);
 					    		row.getCell(9).setCellStyle(style);
 					    		row.getCell(10).setCellStyle(style);
 					    		row.getCell(11).setCellStyle(style);
 					    		
 					    		Cell cell2 = row.getCell(failureReason);
 					    		cell2.setCellValue(results.get(i).getFailureReason());
 					    		Cell cell3 = row.getCell(failureImage);
 					    		cell3.setCellFormula("HYPERLINK("+"\""+results.get(i).getFailureImage()+"\""+","+"\""+"Sreenshot"+"\""+")");
 					    		  	      	    
 					    		
							}
 					    	else if (results.get(i).getResult().equalsIgnoreCase("Custom")) {
 					    		/*row.getCell(br).setCellStyle(style);
 					    		row.getCell(module).setCellStyle(style);
 					    		row.getCell(testcase).setCellStyle(style);
 					    		row.getCell(slno).setCellStyle(style);
 					    		row.getCell(keyword).setCellStyle(style);
 					    		row.getCell(object).setCellStyle(style);
 					    		row.getCell(input).setCellStyle(style);
 					    		row.getCell(result).setCellStyle(style);
 					    		row.getCell(failureReason).setCellStyle(style);
 					    		row.getCell(failureImage).setCellStyle(style);
 					    		row.getCell(9).setCellStyle(style);
 					    		row.getCell(10).setCellStyle(style);
 					    		row.getCell(11).setCellStyle(style);*/
 					    		
 					    		Cell cell1 = row.getCell(result);
 					    		cell1.setCellValue("Pass");
 					    		Cell cell2 = row.getCell(failureReason);
 					    		cell2.setCellValue(results.get(i).getFailureReason());
 					    		Cell cell3 = row.getCell(failureImage);
 					    		cell3.setCellFormula("HYPERLINK("+"\""+results.get(i).getFailureImage()+"\""+","+"\""+"Sreenshot"+"\""+")");
 					    		  	      	    
 					    		
							}
 					    	
 					    		/*Cell cell2 = row.getCell(failureReason);
					    		cell2.setCellValue(results.get(i).getFailureReason());
					    		Cell cell3 = row.getCell(failureImage);
					    		cell3.setCellFormula("HYPERLINK("+"\""+results.get(i).getFailureImage()+"\""+","+"\""+"Sreenshot"+"\""+")");*/
 						}
 					   sheet.createFreezePane(0, 1);
 					   inputStream.close();			    	
 					   FileOutputStream outputStream = new FileOutputStream(file);		
 					   workbook.write(outputStream);
 					   outputStream.close(); 
 				//	   workbook.close();
 					   style=null;
 					   font=null;
 	 				   sheet=null;
 					   workbook=null;
 					   Log.info("Wrote results to Teststeps sheet");
					} else {
 						Log.fatal(""+Filename+" "+"extension is invalid");
 					}
 			} catch (Exception e) {
 				Log.fatal(e);
 			}
 			
 			
 		}
 		
//----------------------------------------------------	
 		
 		public ArrayList<Testcases> readAlltestcases(String Filename, String Sheetname)

 		 		{
 					Log.info("function: readAlltestcases");
 		 			int tcbr =1;
 		 			int tcmodule =2;
 		 			int tctestcase =3;
 		 			int tcID =4;
 		 			int tccritical =5;
 		 			int tcrepeatable =6;
 		 			
 		 			String resultVal="Norun";
 		 			//String busreq="blank", classFile="blank", keyWord="blank", objectname="blank", inputValue="blank", outputVal="blank", resultVal="blank";
 		 			
 		 			Testcases testobj=null;
 		 			File file = new File(Filename);
 		 			Workbook workbook = null;
 		 			Sheet sheet = null;
 		 			
 		 			try {
 						
 		 				FileInputStream inputStream = new FileInputStream(file);
 		 				String fileExtensionName = Filename.substring(Filename.length()-4);
 		 				//Log.info(""+Filename+"-extenstion-"+fileExtensionName);
 		 					if (fileExtensionName.equals("xlsx")) 
 		 					{
 		 						workbook = new XSSFWorkbook(inputStream);
 		 						sheet = workbook.getSheet(Sheetname);
 		 						Log.info("Reading: "+Filename+"-"+Sheetname);
 		 						int rowCount = sheet.getLastRowNum()-sheet.getFirstRowNum();
 		 					    Log.info("Row count: "+rowCount);
 		 					    
 		 					    String tempbusreq="",tempmodule="",temptcname="";
 		 				            for (int i = 1; i <= rowCount; i++) 
 		 	 						{

 		 				            	String busreq = sheet.getRow(i).getCell(tcbr).getStringCellValue().trim();

 		 				            	if (busreq.length()==0)
 		 				            	 {busreq=tempbusreq;}
 										
 		 				            	String mod = sheet.getRow(i).getCell(tcmodule).getStringCellValue().trim();

 		 				            	if (mod.length()==0)
 		 				            	 {mod=tempmodule;}
 		 				            	
 		 				            	String tcname = sheet.getRow(i).getCell(tctestcase).getStringCellValue().trim();

 		 				            	if (tcname.length()==0)
 		 				            	 {tcname=temptcname;}
 		 				            	
 		 				            	String tcid = sheet.getRow(i).getCell(tcID).getStringCellValue().trim();
 		 				            	if (tcid.length()==0)
		 				            	 {tcid="";}
 		 				            	
 		 				            	String tccritic = sheet.getRow(i).getCell(tccritical).getStringCellValue().trim();
 		 				            	if (tccritic.length()==0)
		 				            	 {tccritic="";}
 		 				            	double tcrep=0;
 		 				            	try {
 		 				            		String tcrepeat = sheet.getRow(i).getCell(tcrepeatable).getStringCellValue().trim();
 		 				            		tcrep = Double.parseDouble(tcrepeat);
 	 		 				            	if (tcrep==0)
 			 				            	 {tcrep=0;}
										} catch (Exception e) {
											tcrep = sheet.getRow(i).getCell(tcrepeatable).getNumericCellValue();
										}
 		 				            	
 		 				            	/*if (tccritic.length()==0)
		 				            	 {tccritic="";}*/
 		 				            	
 		 				            	
 		 				            	
 		 				            	/*double tcrepeat= sheet.getRow(i).getCell(tcrepeatable).getNumericCellValue();
 		 				            	if (tcrepeat==0)
		 				            	 {tcrepeat=0;}*/
 		 				            	
 		 				            	testobj = new Testcases(busreq,
 		 	 									mod,
 		 	 									tcname,
 		 	 									0,
 		 	 									0,
 		 	 									tcid,
 		 	 									tccritic,
 		 	 									resultVal,
 		 	 									"",
 		 	 									tcrep);
 		 	 							allTestcases.add(testobj);	
 		 	 							tempbusreq=busreq;
 		 	 							tempmodule=mod;
 		 	 							temptcname=tcname;
 		 	 							Log.info(""+busreq+" "+mod+" "+tcname+" "+tcid+" "+tccritic+" ");
 		 	 						} 
 		 				          inputStream.close();
 		 				        //  workbook.close();
 	 	 	 				      sheet=null;
 	 	 	 				      workbook=null;
 		 	 					   } 
 		 					
 		 					  else {
 		 	 						Log.fatal(""+Filename+" "+"extension is invalid");}
 		 				
 		 				
 		 				
 					} catch (Exception e) {
 						Log.fatal(e);
 					} 	
 		 			
 		 			Log.info("No of testcases: "+allTestcases.size());
 		 			return allTestcases; 
 		 		}
 		
//----------------------------------------------------
 			/*	
 			 	public TestResults readTestResults(String Filename, String Sheetname)
 					{
 			 			TestResults testResults=new TestResults("","","",0,0,0,0);
 						File file = new File(Filename);
 						Workbook workbook = null;
 						Sheet sheet = null;
 						int i=0,j=0;
 						Cell datarow;
 						String executionTime="Execution DateTime";
 						String timeTaken="Time Take for Execution";
 						String numofBRs = "No of BRs executed";
 						String numofModules = "No of modules executed";
 						String numofTCs = "No of testcases executed";
 						String numofPassedTCs = "Total Number of Passed testcases";
 						String numofFailedTCs = "Total Number of Failed testcases";

 						
 						try {
 							FileInputStream inputStream = new FileInputStream(file);
 							String fileExtensionName = Filename.substring(Filename.length()-4);
 							 Log.info(""+Filename+"-extenstion-"+fileExtensionName);
 								if (fileExtensionName.equals("xlsx")) 
 								{
 									workbook = new XSSFWorkbook(inputStream);
 									sheet = workbook.getSheet(Sheetname);
 									Log.info("Reading: "+Filename+"-"+Sheetname);
 									int rowCount = sheet.getLastRowNum()-sheet.getFirstRowNum();
 								    Log.info("Row count: "+rowCount);
 								    Iterator<Row> iterator = sheet.iterator();
 								    
 								    while (iterator.hasNext()) {
 							            Row nextRow = iterator.next();
 							            Iterator<Cell> cellIterator = nextRow.cellIterator();
 							             
 							            while (cellIterator.hasNext()) {
 							                Cell cell = cellIterator.next();
 							                
 							                try {
 							                	if (cell.getStringCellValue().equalsIgnoreCase(executionTime)) {
 	 							                	i=cell.getRowIndex();
 	 							                	j=cell.getColumnIndex();
 	 							                	datarow = sheet.getRow(i).getCell(j+1);
 	 							                	Log.info(cell.getStringCellValue()+":"+datarow.getStringCellValue());
 	 							                	testResults.setexecutionTime(datarow.getStringCellValue().trim());
 	 							                	break;
 	 											} else if(cell.getStringCellValue().equalsIgnoreCase(timeTaken)) {
 	 												i=cell.getRowIndex();
 	 							                	j=cell.getColumnIndex();
 	 							                	datarow = sheet.getRow(i).getCell(j+1);
 	 							                	Log.info(cell.getStringCellValue()+":"+datarow.getStringCellValue());
 	 							                	testResults.settimeTaken(datarow.getStringCellValue().trim());
 	 							                	break;
 	 											}else if(cell.getStringCellValue().equalsIgnoreCase(numofBRs)) {
 	 												i=cell.getRowIndex();
 	 							                	j=cell.getColumnIndex();
 	 							                	datarow = sheet.getRow(i).getCell(j+1);
 	 							                	Log.info(cell.getStringCellValue()+":"+datarow.getNumericCellValue());
 	 							                	testResults.setnumofBRs(datarow.getNumericCellValue());
 	 							                	break;
 	 											}else if(cell.getStringCellValue().equalsIgnoreCase(numofModules)) {
 	 												i=cell.getRowIndex();
 	 							                	j=cell.getColumnIndex();
 	 							                	datarow = sheet.getRow(i).getCell(j+1);
 	 							                	Log.info(cell.getStringCellValue()+":"+datarow.getNumericCellValue());
 	 							                	testResults.setnumofModules(datarow.getNumericCellValue());
 	 							                	break;
 	 											}else if(cell.getStringCellValue().equalsIgnoreCase(numofTCs)) {
 	 												i=cell.getRowIndex();
 	 							                	j=cell.getColumnIndex();
 	 							                	datarow = sheet.getRow(i).getCell(j+1);
 	 							                	Log.info(cell.getStringCellValue()+":"+datarow.getNumericCellValue());
 	 							                	testResults.setnumofTCs(datarow.getNumericCellValue());
 	 							                	break;
 	 											}else if(cell.getStringCellValue().equalsIgnoreCase(numofPassedTCs)) {
 	 												i=cell.getRowIndex();
 	 							                	j=cell.getColumnIndex();
 	 							                	datarow = sheet.getRow(i).getCell(j+1);
 	 							                	Log.info(cell.getStringCellValue()+":"+datarow.getNumericCellValue());
 	 							                	testResults.setnumofPassedTCs(datarow.getNumericCellValue());
 	 							                	break;
 	 											}else if(cell.getStringCellValue().equalsIgnoreCase(numofFailedTCs)) {
 	 												i=cell.getRowIndex();
 	 							                	j=cell.getColumnIndex();
 	 							                	datarow = sheet.getRow(i).getCell(j+1);
 	 							                	Log.info(cell.getStringCellValue()+":"+datarow.getNumericCellValue());
 	 							                	testResults.setnumofFailedTCs(datarow.getNumericCellValue());
 	 							                	break;
 	 											}	
											} catch (Exception e) {
												Log.fatal(e);
												continue;
											}			                   
 							            }
 							        }
 								   
 								
 								} else {
 									Log.fatal(""+Filename+" "+"extension is invalid");
 								}
 							    sheet=null;
 								workbook=null;
 								inputStream.close();
 								
 						} catch (Exception e) {
 							Log.fatal(e);
 							//e.printStackTrace();
 						}
 						
 						return testResults; 
 					} 		
	
 			 	*/
 			 	
 //----------------------------------------------------	
 		 		public void writetestResults(String Filename, String Sheetname, TestResults results)
 		 		{
 		 			Log.info("function: writetestResults");
 		 			File file = new File(Filename);
 		 			Workbook workbook = null;
 		 			Sheet sheet = null;
 		 			try {
 		 				FileInputStream inputStream = new FileInputStream(file);
 		 				 int i=0, j=0;
 		 				
 		 				String runplanUsed = "Run plan used"; 
 		 				String executionstartTime="Execution Start Time";
 						String executionendTime = "Execution End Time";
 						String timeTaken="Total Execution time";
 						String numofTCs = "Total testcases executed";
 						String numofPassedTCs = "Passed testcases";
 						String numofFailedTCs = "Failed testcases";
 						String numofmissingTCs = "No run testcases";
 						String browser = "Browser";
 						String browserVersion = "Browser Version";
 						String os = "Operating System";
 						
 						String fileExtensionName = Filename.substring(Filename.length()-4);
 		 				//Log.info(""+Filename+"-extenstion-"+fileExtensionName);
 		 					if (fileExtensionName.equals("xlsx")) 
 		 					{

 		 						    workbook = new XSSFWorkbook(inputStream);
									sheet = workbook.getSheet(Sheetname);
									Log.info("Reading: "+Filename+"-"+Sheetname);
									int rowCount = sheet.getLastRowNum()-sheet.getFirstRowNum();
								    Log.info("Row count: "+rowCount);
								    Iterator<Row> iterator = sheet.iterator();
								    
								    while (iterator.hasNext()) {
							            Row nextRow = iterator.next();
							            Iterator<Cell> cellIterator = nextRow.cellIterator();
							             
							            while (cellIterator.hasNext()) {
							                Cell cell = cellIterator.next();
							                i=cell.getRowIndex();
							                j=cell.getColumnIndex();
							                	
							                	//Log.info("Row: "+i);
							                	//Log.info("Col: "+j);
							                
							                
							                try {
							                	if (cell.getStringCellValue().trim().equalsIgnoreCase(runplanUsed)) {
	 							                	i=cell.getRowIndex();
	 							                	j=cell.getColumnIndex();
	 							                	Row row = sheet.getRow(i);
	 							                	Cell writeCell = row.getCell(j+1);
	 							                   	writeCell.setCellValue(results.getrunPlanUsed());
	 							                	Log.info("Wrote Runplanused "+results.getrunPlanUsed());	 							                	
	 							                	break;
	 											} 
							                else if (cell.getStringCellValue().equalsIgnoreCase(executionstartTime)) {
	 							                	i=cell.getRowIndex();
	 							                	j=cell.getColumnIndex();
	 							                	Row row = sheet.getRow(i);
	 							                	Cell writeCell = row.getCell(j+1);
	 							                   	writeCell.setCellValue(results.getexecutionStartTime());
	 							                	Log.info("Wrote Start execution time "+results.getexecutionStartTime());	 							                	
	 							                	break;
	 											} else if(cell.getStringCellValue().equalsIgnoreCase(executionendTime)) {
	 												i=cell.getRowIndex();
	 							                	j=cell.getColumnIndex();
	 							                	Row row = sheet.getRow(i);
	 							                	Cell writeCell = row.getCell(j+1);
	 							                	writeCell.setCellValue(results.getexecutionEndTime());
	 							                	Log.info("Wrote End execution time "+results.getexecutionEndTime());
	 							                	break;
	 											}else if(cell.getStringCellValue().equalsIgnoreCase(timeTaken)) {
	 												i=cell.getRowIndex();
	 							                	j=cell.getColumnIndex();
	 							                	Row row = sheet.getRow(i);
	 							                	Cell writeCell = row.getCell(j+1);
	 							                	writeCell.setCellValue(results.gettimeTaken());
	 							                	Log.info("Wrote time taken "+results.gettimeTaken());
	 							                	break;
	 											}else if(cell.getStringCellValue().equalsIgnoreCase(numofTCs)) {
	 												i=cell.getRowIndex();
	 							                	j=cell.getColumnIndex();
	 							                	Row row = sheet.getRow(i);
	 							                	Cell writeCell = row.getCell(j+1);
	 							                	writeCell.setCellValue(results.getnumofTCs());
	 							                	Log.info("Wrote No of TCs "+results.getnumofTCs());
	 							                	break;
	 											}else if(cell.getStringCellValue().equalsIgnoreCase(numofPassedTCs)) {
	 												i=cell.getRowIndex();
	 							                	j=cell.getColumnIndex();
	 							                	Row row = sheet.getRow(i);
	 							                	Cell writeCell = row.getCell(j+1);
	 							                	writeCell.setCellValue(results.getnumofPassedTCs());
	 							                	Log.info("Wrote No of Passed TCs "+results.getnumofPassedTCs());
	 							                	break;
	 											}else if(cell.getStringCellValue().equalsIgnoreCase(numofFailedTCs)) {
	 												i=cell.getRowIndex();
	 							                	j=cell.getColumnIndex();
	 							                	Row row = sheet.getRow(i);
	 							                	Cell writeCell = row.getCell(j+1);
	 							                	writeCell.setCellValue(results.getnumofFailedTCs());
	 							                	Log.info("Wrote No of Failed TCs "+results.getnumofFailedTCs());
	 							                	break;
	 											}else if(cell.getStringCellValue().equalsIgnoreCase(numofmissingTCs)) {
	 												i=cell.getRowIndex();
	 							                	j=cell.getColumnIndex();
	 							                	Row row = sheet.getRow(i);
	 							                	Cell writeCell = row.getCell(j+1);
	 							                	writeCell.setCellValue(results.getnumofMissingTCs());
	 							                	Log.info("Wrote No of No run TCs "+results.getnumofMissingTCs());
	 											}	
	 											else if(cell.getStringCellValue().equalsIgnoreCase(browser)) {
	 												i=cell.getRowIndex();
	 							                	j=cell.getColumnIndex();
	 							                	Row row = sheet.getRow(i);
	 							                	Cell writeCell = row.getCell(j+1);
	 							                	writeCell.setCellValue(DriverScript.Browser);
	 							                	Log.info("Wrote Browser "+DriverScript.Browser);
	 											}
	 											else if(cell.getStringCellValue().equalsIgnoreCase(browserVersion)) {
	 												i=cell.getRowIndex();
	 							                	j=cell.getColumnIndex();
	 							                	Row row = sheet.getRow(i);
	 							                	Cell writeCell = row.getCell(j+1);
	 							                	writeCell.setCellValue(DriverScript.BrowserVersion);
	 							                	Log.info("Wrote Browser Version"+DriverScript.BrowserVersion);
	 											}
	 											else if(cell.getStringCellValue().equalsIgnoreCase(os)) {
	 												i=cell.getRowIndex();
	 							                	j=cell.getColumnIndex();
	 							                	Row row = sheet.getRow(i);
	 							                	Cell writeCell = row.getCell(j+1);
	 							                	writeCell.setCellValue(DriverScript.OS);
	 							                	Log.info("Wrote OS "+DriverScript.OS);
	 											}
										} catch (Exception e) {
											Log.fatal(e);
											continue;
										}
							    
							            }
							        }
								    inputStream.close();			    	
								    FileOutputStream outputStream = new FileOutputStream(file);		
	 			 					workbook.write(outputStream);
	 			 					outputStream.close(); 
	 			 				//	workbook.close();
	 			 	 				sheet=null;
	 			 					workbook=null;
		 					   
								    Log.info("Wrote results to Results sheet");
 		 						
 		 					} else {
 		 						Log.fatal(""+Filename+" "+"extension is invalid");
 		 					}
 		 			} catch (Exception e) {
 		 				Log.fatal(e);
 		 			}
 		 		}
 		 		
//----------------------------------------------------	
 		 		public void updateStartEnd(ArrayList<Testcases> AllTestcases, ArrayList<Teststeps> AllTeststeps)
 		 		{	
 		 			Log.info("function: updateStartEnd");
 		 			for (int i = 0; i < AllTestcases.size(); i++) 
 		 			{
 		 				String masterbr = AllTestcases.get(i).getBR();
 		 				String mastermodule = AllTestcases.get(i).getModule();
 		 				String mastertc = AllTestcases.get(i).getTestcasename();
 		 				
 		 				String startrow="Notupdated";
 		 				
 		 				//Log.info(""+masterbr+": "+mastermodule+": "+mastertc);
 		 				for (int j = 0; j < AllTeststeps.size(); j++) 
 		 				{
 		 					String br = AllTeststeps.get(j).getBR();
 		 					String module = AllTeststeps.get(j).getModule();
 		 					String tc = AllTeststeps.get(j).getTestcase();
 		 					//Log.info(""+br+": "+module+": "+tc);
 		 					//String print ="Notprinted";
 		 					if ((masterbr.equalsIgnoreCase(br))&&(mastermodule.equalsIgnoreCase(module))&&
 		 							(mastertc.equalsIgnoreCase(tc)))
 		 					{
 		 						
 		 						if (startrow.equalsIgnoreCase("Notupdated")) 
 		 						{
 		 							//Log.info("Testcase found");
 		 							Log.info("Testcase found: "+masterbr+": "+mastermodule+": "+mastertc);
 		 							//Log.info(""+br+": "+module+": "+tc);
 		 							AllTestcases.get(i).setStartRowNo(j);	
 		 							startrow="updated";
 		 							//print="printed";
 		 						}	
 		 						AllTestcases.get(i).setEndRowNo(j);
 		 					} 
 		 					/*
 		 					else if (print.equalsIgnoreCase("Notprinted"))
 		 					{
 		 						if(j==(AllTeststeps.size()-1)){
 		 						Log.info("Testcase NOT found "+masterbr+": "+mastermodule+": "+mastertc);}
 		 					}*/
 		 				}
 		 				if (startrow.equalsIgnoreCase("Notupdated"))
		 					{
 		 					AllTestcases.get(i).setStartRowNo(0);	
 		 					AllTestcases.get(i).setEndRowNo(0);
 		 					Log.error("Testcase NOT found "+masterbr+": "+mastermodule+": "+mastertc);
		 					}
 		 				
 		 			}
 		 }
		
//----------------------------------------------------			 		


@SuppressWarnings("deprecation")
				public void deleteNoRows(String Filename, String Sheetname)
 {
	 Log.info("deleteNoRows");
	 try 
		{
		 	
		 	int br =1;
		 	int brdesc=2;
		 	int impactbr=3;
		 	int brinclude=4;
		 	int module =5;
		 	int modinclude=6;
			int testcase =7;
			int tcinclude=8;
			int tcid=9;
			int criticality=10;
			int repeatability=11;
			
		 
		 		//Filename = "C:\\Users\\Kendolen\\Desktop\\Testcase.xlsx";
				//Sheetname = "Master";
				String BR="", BRinclude ="", BRdesc="", ImpactBR="", Mod="", Modinclude="", TC="", TCinclude="", TcId="", Criticality="";
				double Repeatability=0;
				String tempBR="", tempBRinclude ="",   tempMod="", tempModinclude="";
				
				
				int rowCount =0, colCount=0;
				FileInputStream inputStream = null;
				FileOutputStream outputStream=null;
				Workbook workbook = null;
				Sheet sheet = null;
				Sheet tempDeletesheet=null;
				File file = new File(Filename);
				
				inputStream = new FileInputStream(file);
				
				workbook = new XSSFWorkbook(inputStream);
					sheet = workbook.getSheet(Sheetname);
					Row addrow = null;
							
					Log.info("Reading: "+Filename+"-"+Sheetname);
					rowCount = sheet.getLastRowNum()-sheet.getFirstRowNum();
				    Log.info("Row count: "+rowCount);
				    colCount = sheet.getRow(0).getLastCellNum();
				    Log.info("Col count: "+colCount);
				    if (workbook.getSheet("TempDelete") == null) {
						workbook.createSheet("TempDelete");
						Log.info("Created TempDelete sheet");
						
						
					}
				    XSSFCellStyle style=(XSSFCellStyle) workbook.createCellStyle();
				/*
				 *   style.setBorderBottom(XSSFCellStyle.BORDER_MEDIUM);
				
					style.setBorderTop(XSSFCellStyle.BORDER_MEDIUM);
					style.setBorderRight(XSSFCellStyle.BORDER_MEDIUM);
					style.setBorderLeft(XSSFCellStyle.BORDER_MEDIUM);
					XSSFColor blackcolor = new XSSFColor(new java.awt.Color(0, 0, 0));
					//XSSFColor greencolor = new XSSFColor(new java.awt.Color(150, 250, 150));
				style.setBottomBorderColor(blackcolor);
					//style.setRightBorderColor(blackcolor);
					style.setTopBorderColor(blackcolor);
					style.setLeftBorderColor(blackcolor);
					
					//XSSFColor lightGrey = new XSSFColor(new java.awt.Color(192, 192, 192));
					XSSFColor lightGrey = new XSSFColor(new java.awt.Color(220, 220, 220));
					style.setFillForegroundColor(lightGrey);
					//style.setFillForegroundColor(HSSFColor.LIGHT_GREEN.index);
					style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
					 */	XSSFFont font= (XSSFFont) workbook.createFont();
					font.setBold(true);
					style.setFont(font);
					
					
				    
					XSSFCellStyle style2=(XSSFCellStyle) workbook.createCellStyle();
				/**	style2.setBorderBottom(XSSFCellStyle.BORDER_THIN);
					style2.setBorderTop(XSSFCellStyle.BORDER_THIN);
					style2.setBorderRight(XSSFCellStyle.BORDER_THIN);
					style2.setBorderLeft(XSSFCellStyle.BORDER_THIN);
					**/XSSFColor blackcolor = new XSSFColor(new java.awt.Color(0, 0, 0));
					XSSFColor greencolor = new XSSFColor(new java.awt.Color(150, 250, 150));
					style2.setBottomBorderColor(blackcolor);
					style2.setRightBorderColor(blackcolor);
					style2.setTopBorderColor(blackcolor);
					style2.setLeftBorderColor(blackcolor);
					XSSFColor lightGreen = new XSSFColor(new java.awt.Color(180, 240, 170));
					style2.setFillForegroundColor(lightGreen);
					//style2.setFillForegroundColor(HSSFColor.LEMON_CHIFFON.index);
					//style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
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
				    //firstRow.getCell(6).setCellValue("Desc");
				    firstRow.getCell(0).setCellStyle(style);
				    firstRow.getCell(1).setCellStyle(style);
				    firstRow.getCell(2).setCellStyle(style);
				    firstRow.getCell(3).setCellStyle(style);
				    firstRow.getCell(4).setCellStyle(style);
				    firstRow.getCell(5).setCellStyle(style);
				    firstRow.getCell(6).setCellStyle(style);
				    firstRow.getCell(7).setCellStyle(style);
				    firstRow.getCell(8).setCellStyle(style);
				    
				    
				    int k=1;
				    
				    String tempBRdesc="", tempImpactBR="", tempTC="", tempTCinclude="";
				    for (int i = 1; i <= rowCount; i++) 
						{
				    	
				    		try 
				    		{  
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
						    	
						    	//Log.info(tempBR+tempBRinclude+tempMod+tempModinclude+tempTC+tempTCinclude);
						    	
						    	if ((row.getCell(br).getCellType()==CellType.BLANK)||
						    		(BR.equalsIgnoreCase(""))) 
						    	{
						    		BR=tempBR;
								}
						    							    	
						    	if ((!BRinclude.equalsIgnoreCase("Yes"))&&(!BRinclude.equalsIgnoreCase("No"))) 
						    	{
						    		BRinclude=tempBRinclude;
								}
						    	
						    	if ((row.getCell(module).getCellType()==CellType.BLANK)||
						    		(Mod.equalsIgnoreCase(""))) 
						    	{
						    		Mod=tempMod;
								}
						    	
						    	if ((!Modinclude.equalsIgnoreCase("Yes"))&&(!Modinclude.equalsIgnoreCase("No"))) 
						    	{
						    		Modinclude=tempModinclude;
								}
						    	
						    	//Log.info("After if");
						    	//Log.info(BR+BRinclude+Mod+Modinclude+TC+TCinclude);
						    	//Log.info(tempBR+tempBRinclude+tempMod+tempModinclude+tempTC+tempTCinclude);
						    	
						    	if (BRinclude.equalsIgnoreCase("Yes")) 
								{
									if (Modinclude.equalsIgnoreCase("Yes")) 
									{
										if (TCinclude.equalsIgnoreCase("Yes")) {
											tempDeletesheet.createRow(k);
									    	addrow = tempDeletesheet.getRow(k);
									    	Log.info("Adding values in row outside loop:"+k);
									    	
									    	addrow.createCell(0).setCellValue("#"+k);
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
									    	Log.info("intRepeat: "+intRepeat);
									    	
									    	if (intRepeat>1) 
									    	{
									    		int iterationUpdate=2;
												for (int j = intRepeat; j > 1; j--) 
												{													
													Log.info("Entered row in Tempdelete:"+k);
											    	k++;
													tempDeletesheet.createRow(k);
													Log.info("Adding values in row inside loop:"+k);
													addrow = tempDeletesheet.getRow(k);
													
													addrow.createCell(0).setCellValue("#"+k);
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
									    										    	
									    	
									    	Log.info("Entered row in Tempdelete:"+k);
									    	k++;
									    	
										}
									}
									
								} 
						    	
						    	else 
						    	{

								}
						    	tempBR=BR;
						    	tempMod=Mod;
						    	tempTC=TC;
						    	tempBRdesc=BRdesc;
						    	tempImpactBR=ImpactBR;
						    	tempBRinclude=BRinclude;
						    	tempModinclude=Modinclude;
						    	
				    		} catch (Exception e) 
				    		{
				    			Log.info(e);
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
				    outputStream = new FileOutputStream(file); 
				    workbook.write(outputStream);
				    	outputStream.close();
				    	//workbook.close();
				    	sheet=null;
				    	workbook=null;
				    
				    
				
		} catch (Exception e) 
		{
			Log.info(e);
		}
		
		
	}
 
 
 
//----------------------------------------------------			 		


 				public void deletedupBR(String Filename, String Sheetname)
	{	
	 Log.info("deletedupBR");
	 try 
		{
		 	int br =1;
		 	int module =2;
			int testcase =3;
			//int brdesc=4;
		 	//int impactbr=5;
			
			
				//String Filename = "C:\\Users\\Kendolen\\Desktop\\Testcase.xlsx";
				String OldSheetname = "TempDelete";
				String BR="", BRdesc ="", Mod="", ImpactBR="", TC="";
				String tempBR="", tempMod="";
				//String tempBRdesc ="", TCinclude="", tempImpactBR="", tempBRinclude ="", tempModinclude="", tempTC="", tempTCinclude="";
				//String BRadded="",Modadded="";
				
				int rowCount =0, colCount=0;
				FileInputStream inputStream = null;
				FileOutputStream outputStream=null;
				Workbook workbook = null;
				Sheet sheet = null;
				//Sheet tempDeletesheet=null;
				File file = new File(Filename);
				
				inputStream = new FileInputStream(file);
				
				workbook = new XSSFWorkbook(inputStream);
					sheet = workbook.getSheet(OldSheetname);
					//Row addrow = null, prerow=null;
							
					Log.info("Reading: "+Filename+"-"+OldSheetname);
					rowCount = sheet.getLastRowNum()-sheet.getFirstRowNum();
				    Log.info("Row count: "+rowCount);
				    colCount = sheet.getRow(0).getLastCellNum();
				    Log.info("Col count: "+colCount);
				    			    
				    
				    for (int i = 1; i <= rowCount; i++) 
						{
				    	
				    		try 
				    		{  
				    			Row row = sheet.getRow(i);
						    	BR = row.getCell(br).getStringCellValue();
						    	
						    	Mod = row.getCell(module).getStringCellValue();
						    	TC = row.getCell(testcase).getStringCellValue();
						    	//BRdesc = row.getCell(brdesc).getStringCellValue();
						    	//ImpactBR = row.getCell(impactbr).getStringCellValue();
						    	Log.info(i);
						    	Log.info(BR+Mod+TC+BRdesc+ImpactBR);
	
						    	if (BR.equalsIgnoreCase(tempBR)) 
						    	{
									row.getCell(br).setCellValue("");
									Log.info("set BR =null");
								}
						    	if (Mod.equalsIgnoreCase(tempMod)) 
						    	{
									row.getCell(module).setCellValue("");
								}
	
						    	tempBR=BR;
						    	tempMod=Mod;

				    		} catch (Exception e) 
				    		{
				    			Log.info(e);
				    		}
				    	} 
				    
				    try {
				    	workbook.removeSheetAt(workbook.getSheetIndex(Sheetname));
				    	Log.info("Deleted sheet:"+Sheetname);
					} catch (Exception e) {
						Log.info("Unable to delete sheet:"+Sheetname);
						Log.error(e);
					}
				    try {
				    	workbook.setSheetName(workbook.getSheetIndex("TempDelete"), "RunplanSheet");
				    	Log.info("Renamed TempDelete sheet to:"+"RunplanSheet");
				    	inputStream.close();
					    outputStream = new FileOutputStream(file); 
					    workbook.write(outputStream);
					    outputStream.close();
					} catch (Exception e) {
						Log.info("Unable to rename TempDelete sheet to:"+"RunplanSheet");
						Log.error(e);
					}
				    //	workbook.close();
				    	sheet=null;
				    	workbook=null;
		} catch (Exception e) 
		{
			Log.info(e);
		}
	} 		
 	
 
 //----------------------------------------------------
 				public void removeHyperlinks(String Filename, String Sheetname)
 {
 	Log.info("function: removeHyperlinks");
	 try {
		  	
			int rowCount =0;
			//int input =6;
			//int result =7;
			FileOutputStream outputStream=null;
			String fileExtensionName="";
			Workbook workbook = null;
			Sheet sheet = null;
		
			FileInputStream readfile = new FileInputStream(Filename);
			
			fileExtensionName = Filename.substring(Filename.length()-4);
			Log.info(""+Filename+"-extenstion-"+fileExtensionName);
			if (fileExtensionName.equals("xlsx")) 
			{
				workbook = new XSSFWorkbook(readfile);
				sheet =workbook.getSheet(Sheetname);
				Log.info("Reading: "+Filename+"-"+Sheetname);
				rowCount = sheet.getLastRowNum()-sheet.getFirstRowNum();
			    Log.info("Row count: "+rowCount);
			    Iterator<Row> iterator = sheet.iterator();
			    while (iterator.hasNext()) {
		            Row nextRow = iterator.next();
		            Iterator<Cell> cellIterator = nextRow.cellIterator();
		             
		            while (cellIterator.hasNext()) {
		                Cell cell = cellIterator.next();
		                
		               // if (cell.getColumnIndex()==input) 
		                //{
		                	Hyperlink hyperlink = cell.getHyperlink();
			    			 try { 

		                           java.lang.reflect.Field field = XSSFSheet.class 
		                                           .getDeclaredField("hyperlinks"); 
		                           field.setAccessible(true); 

		                           @SuppressWarnings("unchecked")
									List<Hyperlink> hyperlinkList = (List<Hyperlink>) field 
		                                           .get(sheet); 

		                           hyperlinkList.remove(hyperlink); 
		                           //Log.info("Removed hyperlink from ");

		                   } catch (NoSuchFieldException | SecurityException 
		                                   | IllegalArgumentException | IllegalAccessException e) { 
		                           e.printStackTrace(); 
		                   } 
						//} 
		                
		                
		            }
			    }
			    
			    
			    Log.info("Removed hyperlink from all cells");
			    outputStream = new FileOutputStream(Filename); 
		    	workbook.write(outputStream);
		    	outputStream.close(); 
		    	readfile.close();
		    	//workbook.close();
		    	readfile=null;
		    	sheet=null;
		    	workbook=null;
		    	outputStream=null;
		    	
		    	Thread.sleep(3000);
			}	
	 }catch (Exception e) {
				Log.fatal(e);
				e.printStackTrace();
			} 
			    
	 
 }
 
 
 
 //----------------------------------------------------
 				public void deleteNorun(String Filename, String Sheetname)
	{	Log.info("function: deleteNorun");
	  try {
		  	
			int rowCount =0;
			//int input =7;
			//int result =7;
			FileOutputStream outputStream=null;
			String fileExtensionName="";
			Workbook workbook = null;
			Sheet sheet = null;
		
			FileInputStream readfile = new FileInputStream(Filename);
			
			fileExtensionName = Filename.substring(Filename.length()-4);
			//Log.info(""+Filename+"-extenstion-"+fileExtensionName);
			if (fileExtensionName.equals("xlsx")) 
			{
				workbook = new XSSFWorkbook(readfile);
				sheet =workbook.getSheet(Sheetname);
				Log.info("Reading: "+Filename+"-"+Sheetname);
				rowCount = sheet.getLastRowNum()-sheet.getFirstRowNum();
			    Log.info("Row count: "+rowCount);
			       	int i=0;
			    	Row row=null;
			    	for (i = 1; i <= rowCount; i++) 
						{
			    		
			    		try {
					    	//Log.info("Rowcount: "+rowCount);
					    		row = sheet.getRow(i);
					    						    		
					    		String data =row.getCell(result).getStringCellValue();
					    		//Log.info(data);
					    		if (data.equalsIgnoreCase("Norun")) 
					    		{
					    			 int lastRowNum=sheet.getLastRowNum();
					    			 int j=i+1;
					    			 //Log.info("lastrownum:"+lastRowNum);
					    			 if(i>0&&i<lastRowNum)
						    	        {
						    	            sheet.shiftRows(j,lastRowNum, -1);
						    	            i=i-1;
						    	        }
						    	        else if(i==lastRowNum)
						    	        {
						    	        	row = sheet.getRow(i);
							    			for (int k = 0; k < row.getLastCellNum(); k++) 
							    			{
												Cell delcell =row.getCell(k);
												row.removeCell(delcell);
											}
							    			i=i+1;
						    	        }
								    /*	outputStream = new FileOutputStream(Filename); 
								    	workbook.write(outputStream);
								    	outputStream.close();*/
								    	
										rowCount = sheet.getLastRowNum()-sheet.getFirstRowNum();
									   //Log.info("Row count after delete: "+rowCount);
					    			//Log.info("Row: "+(i)+" is deleted");
								}					    	
	 						
						} catch (Exception e) {
							Log.error(e);
							//e.printStackTrace();
							Runtime.getRuntime().exec("taskkill /F /IM excel.exe");
							Thread.sleep(1000);
							continue;
						}
			    	//Log.info("out of deleteNorun loop");

						}
			    	//sheet.showInPane(1,1);
			    	
			    	
			    	//sheet.setActiveCell(new CellAddress(0, 0));
			    	
			    	outputStream = new FileOutputStream(Filename); 
			    	workbook.write(outputStream);
			    	outputStream.close(); 
			    	//workbook.close();
		    	sheet=null;
		    	workbook=null;
			}
				  else {
					 Log.error(""+Filename+" "+"extension is invalid");}
			
				 
	} catch (Exception e) {
		Log.fatal(e);
		e.printStackTrace();
	} 		
	}

//----------------------------------------------------	
 				public void writetestcaseResults(String Filename, String Sheetname, ArrayList<Testcases> AllTestcases)
	{	
 		Log.info("function: writetestcaseResults");
		File file = new File(Filename);
		Workbook workbook = null;
		Sheet sheet = null;
		int tcRepeat = 6;
		int tcResult = 7;
		int tcTimetaken =8;
		try {
			FileInputStream inputStream = new FileInputStream(file);
			String fileExtensionName = Filename.substring(Filename.length()-4);
			//Log.info(""+Filename+"-extenstion-"+fileExtensionName);
				if (fileExtensionName.equals("xlsx")) 
				{
						//inputStream = new FileInputStream(file);
					    workbook = new XSSFWorkbook(inputStream);
						sheet = workbook.getSheet(Sheetname);
						Log.info("Reading: "+Filename+"-"+Sheetname);
						//int rowCount = sheet.getLastRowNum()-sheet.getFirstRowNum();
					    //Log.info("Row count: "+rowCount);
						Log.info("All testcase size:"+AllTestcases.size());
						
						XSSFCellStyle style2=(XSSFCellStyle) workbook.createCellStyle();
					/*
					 * 	style2.setBorderBottom(XSSFCellStyle.BORDER_THIN);
					
						style2.setBorderTop(XSSFCellStyle.BORDER_THIN);
						style2.setBorderRight(XSSFCellStyle.BORDER_THIN);
						style2.setBorderLeft(XSSFCellStyle.BORDER_THIN);
						 */XSSFColor blackcolor = new XSSFColor(new java.awt.Color(0, 0, 0));
						//XSSFColor greencolor = new XSSFColor(new java.awt.Color(150, 250, 150));
						style2.setBottomBorderColor(blackcolor);
						style2.setRightBorderColor(blackcolor);
						style2.setTopBorderColor(blackcolor);
						style2.setLeftBorderColor(blackcolor);
						XSSFColor white = new XSSFColor(new java.awt.Color(255, 255, 150));
						style2.setFillForegroundColor(white);
						//style2.setFillForegroundColor(HSSFColor.LEMON_CHIFFON.index);
						//style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
						style2.setWrapText(true);
						XSSFFont font= (XSSFFont) workbook.createFont();
 						font.setBold(true);
 						font.setColor(new XSSFColor(new java.awt.Color(255, 0, 0)));
 						style2.setFont(font);
 						
						
						
						XSSFCellStyle style=(XSSFCellStyle) workbook.createCellStyle();
					/*
					 * 	style.setBorderBottom(XSSFCellStyle.BORDER_THIN);
					
						style.setBorderTop(XSSFCellStyle.BORDER_THIN);
						style.setBorderRight(XSSFCellStyle.BORDER_THIN);
						style.setBorderLeft(XSSFCellStyle.BORDER_THIN);
						 XSSFColor blackcolor = new XSSFColor(new java.awt.Color(0, 0, 0));
						XSSFColor yellow = Color.yellow;//new XSSFColor(new java.awt.Color(150, 250, 150));
						style.setBottomBorderColor(blackcolor);
						style.setRightBorderColor(blackcolor);
						style.setTopBorderColor(blackcolor);
						style.setLeftBorderColor(blackcolor);
						
						style.setFillForegroundColor(HSSFColor.toHSSFColor(color));
						//style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
						style.setWrapText(true);
 						/*style.setFillForegroundColor(new XSSFColor(new java.awt.Color(255,0,0)));
 						style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);*/
 						
					    for (int k = 0; k < AllTestcases.size(); k++) 
					    
					    {   
					    	Row row = sheet.getRow(k+1);
					    
					    try {
				    	
							String result = AllTestcases.get(k).getResult();
							double repeat = AllTestcases.get(k).getRepeatability();
							if (result.equalsIgnoreCase("Fail")) {
								for (int i = 0; i <= 8; i++) {
									row.getCell(i).setCellStyle(style2);
								}
								
							} else {

							}
							row.getCell(tcRepeat).setCellValue(repeat);
							Log.info("Wrote TC result: "+(k+1)+" as: "+AllTestcases.get(k).getRepeatability());
							row.getCell(tcResult).setCellValue(result);
							Log.info("Wrote TC result: "+(k+1)+" as: "+AllTestcases.get(k).getResult());
							row.getCell(tcTimetaken).setCellValue(AllTestcases.get(k).gettimeforExecution());
							Log.info("Wrote TC Time taken: "+(k+1)+"as: "+AllTestcases.get(k).gettimeforExecution());
							} 
					    catch (Exception e) 
					    {
							Log.fatal(e);
							Log.fatal("Unable to write TC result and Time taken:"+(k+1));
								
						}
				    
					    }    
				        //sheet.setActiveCell(new CellAddress(0, 0));
					    sheet.autoSizeColumn(1);
					    sheet.autoSizeColumn(2);
					    sheet.autoSizeColumn(3);
					    sheet.autoSizeColumn(4);
					    sheet.autoSizeColumn(5);
					    sheet.autoSizeColumn(6);
					    sheet.autoSizeColumn(7);
					    sheet.autoSizeColumn(8);
					    inputStream.close();			    	
					    FileOutputStream outputStream = new FileOutputStream(file);		
	 					workbook.write(outputStream);
	 					outputStream.close(); 
	 					//workbook.close();
	 	 				sheet=null;
	 					workbook=null;
				   
					    Log.info("Wrote results to Runplan sheet");
					
				} else {
					Log.fatal(""+Filename+" "+"extension is invalid");
				}
		} catch (Exception e) {
			Log.fatal(e);
			e.printStackTrace();
			
		}
		 		
	}	
 				

 //----------------------------------------------------
 				@SuppressWarnings("deprecation")
				public void writedefectsToExcel(String Filename, String Sheetname, ArrayList<Defects> defects)
 				{	
 					Log.info("function: writedefectsToExcel");
 					
 					
 					try {
 						DataValidation dataValidation = null;
 						DataValidationConstraint constraint = null;
 						DataValidationHelper validationHelper = null;
 						CellRangeAddressList addressList = null;
 						
 					//	this.readMantisProperties(Filename, "ListValues");
 						/*
 						String[] LogDefect = {"Yes", "No"};
 						String[] Reproducibility = {"always","sometimes","random","have not tried","unable to replicate","N/A"};
 						String[] Severity = {"feature", "trivial","text","tweak","minor","major","crash","block"};
 						String[] Priority = {"none", "low","normal","high","urgent","immediate"};
 						String[] AssignTo = {"vnagesh"};
 						*/
 						
 						FileInputStream inputStream = null;
 						FileOutputStream outputStream=null;
 						String fileExtensionName="";
 						Workbook workbook = null;
 						Sheet sheet = null;
 						File file = new File(Filename);
 					
 						inputStream = new FileInputStream(file);
 						fileExtensionName = Filename.substring(Filename.length()-4);
 						//Log.info(""+Filename+"-extenstion-"+fileExtensionName);
 							if (fileExtensionName.equals("xlsx")) 
 							{
 								try {
 									
 									workbook = new XSSFWorkbook(inputStream);
 									sheet = workbook.createSheet(Sheetname);
 								
 									Log.info("Writing: "+Filename+"-"+Sheetname);
 									
 									XSSFCellStyle style=(XSSFCellStyle) workbook.createCellStyle();
 								  /*
 								   *   style.setBorderBottom(XSSFCellStyle.BORDER_MEDIUM);
 								  
 									style.setBorderTop(XSSFCellStyle.BORDER_MEDIUM);
 									style.setBorderRight(XSSFCellStyle.BORDER_MEDIUM);
 									style.setBorderLeft(XSSFCellStyle.BORDER_MEDIUM);
 									 */XSSFColor blackcolor = new XSSFColor(new java.awt.Color(0, 0, 0));
 									XSSFColor greencolor = new XSSFColor(new java.awt.Color(150, 250, 150));
 									style.setBottomBorderColor(blackcolor);
 									style.setRightBorderColor(blackcolor);
 									style.setTopBorderColor(blackcolor);
 									style.setLeftBorderColor(blackcolor);
 									XSSFColor darkGrey = new XSSFColor(new java.awt.Color(169, 169, 169));
 									style.setFillForegroundColor(darkGrey);
 									//style.setFillForegroundColor(HSSFColor.PINK.index);
 									//style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
 									XSSFFont font= (XSSFFont) workbook.createFont();
 									font.setBold(true);
 									//font.setColor(new XSSFColor(new java.awt.Color(255, 255, 255)));
 									style.setFont(font);
 									
 									
 									
 								    
 									XSSFCellStyle style2=(XSSFCellStyle) workbook.createCellStyle();
 									//style2.setBorderBottom(XSSFCellStyle.BORDER_THIN);
 									//style2.setBorderTop(XSSFCellStyle.BORDER_THIN);
 									//style2.setBorderRight(XSSFCellStyle.BORDER_THIN);
 									//style2.setBorderLeft(XSSFCellStyle.BORDER_THIN);
 									//XSSFColor blackcolor = new XSSFColor(new java.awt.Color(0, 0, 0));
 									//XSSFColor greencolor = new XSSFColor(new java.awt.Color(150, 250, 150));
 									style2.setBottomBorderColor(blackcolor);
 									style2.setRightBorderColor(blackcolor);
 									style2.setTopBorderColor(blackcolor);
 									style2.setLeftBorderColor(blackcolor);
 									XSSFColor lightGrey = new XSSFColor(new java.awt.Color(247, 247, 247));
 									style2.setFillForegroundColor(lightGrey);
 									//style2.setFillForegroundColor(HSSFColor.PALE_BLUE.index);
 								//	style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
 									style2.setWrapText(true);
 									XSSFFont font2 = (XSSFFont) workbook.createFont();
 			 						font2.setBold(true);
 			 						font2.setColor(new XSSFColor(new java.awt.Color(255, 0, 0)));
 			 						style2.setFont(font2);
 								  
 								    Row firstRow = sheet.createRow(0);
 								    
 								    for (int i = 0; i <= 15 ; i++) {
 										firstRow.createCell(i);
 									}
 								    
 								    firstRow.getCell(0).setCellValue("SlNo");
 								    firstRow.getCell(1).setCellValue("TestCaseID");
 								    firstRow.getCell(2).setCellValue("TestCaseName");
 								    firstRow.getCell(3).setCellValue("Log Defect");
 								    firstRow.getCell(4).setCellValue("Summary");
 								    firstRow.getCell(5).setCellValue("Description");
 								    firstRow.getCell(6).setCellValue("Reproducibility");
 								    firstRow.getCell(7).setCellValue("Severity");
 								    firstRow.getCell(8).setCellValue("Priority");
 								    firstRow.getCell(9).setCellValue("Assign To");
 								    firstRow.getCell(10).setCellValue("Steps to Reproduce");
 								    firstRow.getCell(11).setCellValue("Additional Information");
 								    firstRow.getCell(12).setCellValue("Upload File Path");
 								    firstRow.getCell(13).setCellValue("Defect ID");
 								    firstRow.getCell(14).setCellValue("Logged Date");
 								    firstRow.getCell(15).setCellValue("Defect Link");
 								    Log.info("Created header row");
 								   
 								    for (int i = 0; i <= 15; i++) {
 								    	firstRow.getCell(i).setCellStyle(style);	
 									}
 								    
 								    
 								    for (int i = 0; i < defects.size(); i++) 
 				 						{
 								    		
 								    		Row row = sheet.createRow(i+1);
 								    		for (int j = 0; j <= 15; j++) {
 								    			row.createCell(j);
 											}
 								    			
 								    		validationHelper=new XSSFDataValidationHelper((XSSFSheet) sheet);
 								    	    addressList = new  CellRangeAddressList((i+1),(i+1),3,3);
 								    	    constraint =validationHelper.createExplicitListConstraint(LogDefect);
 								    	    dataValidation = validationHelper.createValidation(constraint, addressList);
 								    	    dataValidation.setSuppressDropDownArrow(true);      
 								    	    sheet.addValidationData(dataValidation);
 								    	    addressList =null; constraint=null; dataValidation =null;
 								    	    
 								    	    addressList = new  CellRangeAddressList((i+1),(i+1),6,6);
 								    	    constraint =validationHelper.createExplicitListConstraint(Reproducibility);
 								    	    dataValidation = validationHelper.createValidation(constraint, addressList);
 								    	    dataValidation.setSuppressDropDownArrow(true);      
 								    	    sheet.addValidationData(dataValidation);
 								    	    addressList =null; constraint=null; dataValidation =null;
 								    	    
 								    	    addressList = new  CellRangeAddressList((i+1),(i+1),7,7);
 								    	    constraint =validationHelper.createExplicitListConstraint(Severity);
 								    	    dataValidation = validationHelper.createValidation(constraint, addressList);
 								    	    dataValidation.setSuppressDropDownArrow(true);      
 								    	    sheet.addValidationData(dataValidation);
 								    	    addressList =null; constraint=null; dataValidation =null;
 								    	    
 								    	    addressList = new  CellRangeAddressList((i+1),(i+1),8,8);
 								    	    constraint =validationHelper.createExplicitListConstraint(Priority);
 								    	    dataValidation = validationHelper.createValidation(constraint, addressList);
 								    	    dataValidation.setSuppressDropDownArrow(true);      
 								    	    sheet.addValidationData(dataValidation);
 								    	    addressList =null; constraint=null; dataValidation =null;
 								    	    
 								    	    addressList = new  CellRangeAddressList((i+1),(i+1),9,9);
 								    	    constraint =validationHelper.createExplicitListConstraint(AssignTo);
 								    	    dataValidation = validationHelper.createValidation(constraint, addressList);
 								    	    dataValidation.setSuppressDropDownArrow(true);      
 								    	    sheet.addValidationData(dataValidation);
 								    	    addressList =null; constraint=null; dataValidation =null;
 								    	
 								    	    row.getCell(0).setCellValue((i+1));
 								    	    row.getCell(1).setCellValue(defects.get(i).getTCID());
 								    	    row.getCell(2).setCellValue(defects.get(i).getTCName());
 								    	    row.getCell(3).setCellValue(defects.get(i).getLog());
 								    	    row.getCell(4).setCellValue(defects.get(i).getSummary());
 								    	    row.getCell(5).setCellValue(defects.get(i).getDescription());
 								    	    row.getCell(6).setCellValue(defects.get(i).getReproducibility());
 								    	    row.getCell(7).setCellValue(defects.get(i).getSeverity());
 								    	    row.getCell(8).setCellValue(defects.get(i).getPriority());
 								    	    row.getCell(9).setCellValue(defects.get(i).getAssignTo());
 								    	    row.getCell(10).setCellValue(defects.get(i).getSteps());
 								    	    row.getCell(11).setCellValue(defects.get(i).getAdditional());
 								    	    row.getCell(12).setCellValue(defects.get(i).getUpload());
 								    	    row.getCell(13).setCellValue(defects.get(i).getDefectID());
 								    	    Log.info("Created row: "+(i+1));
 								    	    
 								    	    
 								    	    for (int l = 0; l <= 15; l++) {
 								    	    	row.getCell(l).setCellStyle(style2);	
 												}
 				 						}
 								    
 								    
 									
 									for (int j = 0; j <=15 ; j++) {
 										sheet.autoSizeColumn(j);
 									}
 								} catch (Exception e) {
 									Log.fatal(e.toString());
 								}
 								
 								
 							    	
 			 						//System.out.println("Row: "+(i)+"Col: "+result+"is set to Norun");
 			 						 
 							    	inputStream.close();
 							    	outputStream = new FileOutputStream(file); 
 							    	workbook.write(outputStream);
 							    	outputStream.close();
 							    	sheet=null;
 							    	workbook=null;
 								   } 
 							  else {
 								 Log.fatal(""+Filename+" "+"extension is invalid");}
 						
 							 
 				} catch (Exception e) {
 					Log.fatal(e.toString());
 				} 	

 					
 				}	

 //----------------------------------------------------
 				@SuppressWarnings("deprecation")
				public void createDefectsSheet(String Filename, String Sheetname)
 				{	
 					Log.info("function: createDefectsSheet");
 					
 					
 					try {
 						DataValidation dataValidation = null;
 						DataValidationConstraint constraint = null;
 						DataValidationHelper validationHelper = null;
 						CellRangeAddressList addressList = null;
 						
 					//	this.readMantisProperties(Filename, "ListValues");
 						/*
 						String[] LogDefect = {"Yes", "No"};
 						String[] Reproducibility = {"always","sometimes","random","have not tried","unable to replicate","N/A"};
 						String[] Severity = {"feature", "trivial","text","tweak","minor","major","crash","block"};
 						String[] Priority = {"none", "low","normal","high","urgent","immediate"};
 						String[] AssignTo = {"vnagesh"};
 						*/
 						
 						FileInputStream inputStream = null;
 						FileOutputStream outputStream=null;
 						String fileExtensionName="";
 						Workbook workbook = null;
 						Sheet sheet = null;
 						File file = new File(Filename);
 					
 						inputStream = new FileInputStream(file);
 						fileExtensionName = Filename.substring(Filename.length()-4);
 						//Log.info(""+Filename+"-extenstion-"+fileExtensionName);
 							if (fileExtensionName.equals("xlsx")) 
 							{
 								try {
 									
 									workbook = new XSSFWorkbook(inputStream);
 									sheet = workbook.createSheet(Sheetname);
 								
 									Log.info("Writing: "+Filename+"-"+Sheetname);
 									
 									XSSFCellStyle style=(XSSFCellStyle) workbook.createCellStyle();
 								 /*
 								  *    style.setBorderBottom(XSSFCellStyle.BORDER_MEDIUM);
 								
 									style.setBorderTop(XSSFCellStyle.BORDER_MEDIUM);
 									style.setBorderRight(XSSFCellStyle.BORDER_MEDIUM);
 									  *///style.setBorderLeft(XSSFCellStyle.BORDER_MEDIUM);
 									XSSFColor blackcolor = new XSSFColor(new java.awt.Color(0, 0, 0));
 									//XSSFColor greencolor = new XSSFColor(new java.awt.Color(150, 250, 150));
 									style.setBottomBorderColor(blackcolor);
 									style.setRightBorderColor(blackcolor);
 									style.setTopBorderColor(blackcolor);
 									style.setLeftBorderColor(blackcolor);
 									XSSFColor darkGrey = new XSSFColor(new java.awt.Color(169, 169, 169));
 									style.setFillForegroundColor(darkGrey);
 									//style.setFillForegroundColor(HSSFColor.PINK.index);
 								//	style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
 									XSSFFont font= (XSSFFont) workbook.createFont();
 									font.setBold(true);
 									//font.setColor(new XSSFColor(new java.awt.Color(255, 255, 255)));
 									style.setFont(font);
 									
 									
 								    
 								//	XSSFCellStyle style2=(XSSFCellStyle) workbook.createCellStyle();
 								/*	style2.setBorderBottom(XSSFCellStyle.BORDER_THIN);
 									style2.setBorderTop(XSSFCellStyle.BORDER_THIN);
 									style2.setBorderRight(XSSFCellStyle.BORDER_THIN);
 									style2.setBorderLeft(XSSFCellStyle.BORDER_THIN);
 									XSSFColor blackcolor = new XSSFColor(new java.awt.Color(0, 0, 0));
 									XSSFColor greencolor = new XSSFColor(new java.awt.Color(150, 250, 150));
 									style2.setBottomBorderColor(blackcolor);
 									style2.setRightBorderColor(blackcolor);
 									style2.setTopBorderColor(blackcolor);
 									style2.setLeftBorderColor(blackcolor);*
 									
 									XSSFColor lightGrey = new XSSFColor(new java.awt.Color(247, 247, 247));
 									style2.setFillForegroundColor(lightGrey);
 									//style2.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
 									style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
 									style2.setWrapText(true);
 									XSSFFont font2 = (XSSFFont) workbook.createFont();
 			 						font2.setBold(true);
 			 						font2.setColor(new XSSFColor(new java.awt.Color(255, 0, 0)));
 			 						style2.setFont(font2);
 								  */
 								    Row firstRow = sheet.createRow(0);
 								    
 								    for (int i = 0; i <= 15 ; i++) {
 										firstRow.createCell(i);
 									}
 								    
 								    firstRow.getCell(0).setCellValue("SlNo");
 								    firstRow.getCell(1).setCellValue("TestCaseID");
 								    firstRow.getCell(2).setCellValue("TestCaseName");
 								    firstRow.getCell(3).setCellValue("Log Defect");
 								    firstRow.getCell(4).setCellValue("Summary");
 								    firstRow.getCell(5).setCellValue("Description");
 								    firstRow.getCell(6).setCellValue("Reproducibility");
 								    firstRow.getCell(7).setCellValue("Severity");
 								    firstRow.getCell(8).setCellValue("Priority");
 								    firstRow.getCell(9).setCellValue("Assign To");
 								    firstRow.getCell(10).setCellValue("Steps to Reproduce");
 								    firstRow.getCell(11).setCellValue("Additional Information");
 								    firstRow.getCell(12).setCellValue("Upload File Path");
 								    firstRow.getCell(13).setCellValue("Defect ID");
 								    firstRow.getCell(14).setCellValue("Logged Date");
 								    firstRow.getCell(15).setCellValue("Defect Link");
 								    Log.info("Created header row");
 								   
 								    for (int i = 0; i <= 15; i++) {
 								    	firstRow.getCell(i).setCellStyle(style);	
 									}
 								 	
 									for (int j = 0; j <=15 ; j++) {
 										sheet.autoSizeColumn(j);
 									}
 								} catch (Exception e) {
 									Log.fatal(e.toString());
 								}
 								
 								
 							    	
 			 						//System.out.println("Row: "+(i)+"Col: "+result+"is set to Norun");
 			 						 
 							    	inputStream.close();
 							    	outputStream = new FileOutputStream(file); 
 							    	workbook.write(outputStream);
 							    	outputStream.close();
 							    	sheet=null;
 							    	workbook=null;
 								   } 
 							  else {
 								 Log.fatal(""+Filename+" "+"extension is invalid");}
 						
 							 
 				} catch (Exception e) {
 					Log.fatal(e.toString());
 				} 	

 					
 				}	

 				
 				
//----------------------------------------------------				
 				public void hideSheetsinResults(String Filename)
 				{	
 					Log.info("function: hideSheetsinResults");
 					
 					
 					try {
 												
 						FileInputStream inputStream = null;
 						FileOutputStream outputStream=null;
 						String fileExtensionName="";
 						Workbook workbook = null;
 						Sheet sheet = null;
 						File file = new File(Filename);
 					
 						inputStream = new FileInputStream(file);
 						fileExtensionName = Filename.substring(Filename.length()-4);
 						
 							if (fileExtensionName.equals("xlsx")) 
 							{
 								try {
 									
 									workbook = new XSSFWorkbook(inputStream);
 								//	workbook.setSheetHidden(workbook.getSheetIndex("Allobjects"), XSSFWorkbook.SHEET_STATE_HIDDEN);
 									Log.info("Allobjects sheet was hidden in: "+Filename);
 								//	workbook.setSheetHidden(workbook.getSheetIndex("ListValues"), XSSFWorkbook.SHEET_STATE_HIDDEN);
 									Log.info("ListValues sheet was hidden in: "+Filename);
 									workbook.setActiveSheet(workbook.getSheetIndex("Results"));
 									Log.info("Results sheet was set as Activesheet in: "+Filename);
 						
 									sheet = workbook.getSheet("RunPlanForDisplay");
 									if(sheet != null)   {
 									    int index = workbook.getSheetIndex(sheet);
 									    workbook.removeSheetAt(index);
 									   Log.info("RunPlanForDisplay sheet was deleted in: "+Filename);
 									}
 									
 									
 								} catch (Exception e) {
 									Log.fatal(e.toString());
 								}
 								
 								
 							    	
 			 						//System.out.println("Row: "+(i)+"Col: "+result+"is set to Norun");
 			 						 
 							    	inputStream.close();
 							    	outputStream = new FileOutputStream(file); 
 							    	workbook.write(outputStream);
 							    	outputStream.close();
 							    	sheet=null;
 							    	workbook=null;
 								   } 
 							  else {
 								 Log.fatal(""+Filename+" "+"extension is invalid");}
 						
 							 
 				} catch (Exception e) {
 					Log.fatal(e.toString());
 				} 	

 					
 				}
//----------------------------------------------------
 				public void readMantisProperties(String Filename, String Sheetname)
 				{
 					Log.info("function: readMantisProperties");
 					
 					int logdefect=0;
 					int reproducibility=0;
 					int severity=0;
 					int priority=0;
 					int assignTo=0;
 					
 					String strLogDefect="Logdefect";
 					String strReProducibility="Reproducibility";
 					String strSeverity="Severity";
 					String strPriority="Priority";
 					String strAssignTo="AssignTo";

 					File file = new File(Filename);
					Workbook workbook = null;
 					Sheet sheet = null;
 					try {
 						FileInputStream inputStream = new FileInputStream(file);
 						String fileExtensionName = Filename.substring(Filename.length()-4);
 						 Log.info(""+Filename+"-extenstion-"+fileExtensionName);
 							if (fileExtensionName.equals("xlsx")) 
 							{
 								workbook = new XSSFWorkbook(inputStream);
 								sheet = workbook.getSheet(Sheetname);
 								Log.info("Reading: "+Filename+"-"+Sheetname);
 															
 								for (int i = 0; i < sheet.getRow(0).getLastCellNum(); i++) 
 								{
 									try {
 										if (sheet.getRow(0).getCell(i).getStringCellValue().equalsIgnoreCase(strLogDefect)) 
 										{
 										logdefect = i;	
 										Log.info("logdefect: "+i);
 										} 
 										else if (sheet.getRow(0).getCell(i).getStringCellValue().equalsIgnoreCase(strReProducibility)) {
 										reproducibility = i;
 										Log.info("reproducibility: "+i);
 										} 
 										else if (sheet.getRow(0).getCell(i).getStringCellValue().equalsIgnoreCase(strSeverity))
 										{
 											severity = i;
 										Log.info("severity: "+i);	
 										}
 										else if (sheet.getRow(0).getCell(i).getStringCellValue().equalsIgnoreCase(strPriority))
 										{
 											priority = i;
 											Log.info("priority: "+i);
 										}
 										else if (sheet.getRow(0).getCell(i).getStringCellValue().equalsIgnoreCase(strAssignTo))
 										{
 											assignTo = i;
 											Log.info("Assign To: "+i);
 										}
 										
									} catch (Exception e) {
										//Log.error(e.toString());
										//Log.info("Some Mantis parameters are missing");
									}
									
								}
 								
 								
 								for (int i = 1; i <= 8; i++) 
 								{
 									try {

 	 									Row row = sheet.getRow(i);
 	 									
 	 									if (i<=2) 
 	 									{
 	 										try {
 	 											LogDefect[(i-1)] = row.getCell(logdefect).getStringCellValue();
											} catch (Exception e) {
												LogDefect[(i-1)] = " ";
											}
 	 										
 	 										try {
 	 											Reproducibility[(i-1)] = row.getCell(reproducibility).getStringCellValue();
											} catch (Exception e) {
												Reproducibility[(i-1)] = " ";
											}
 	 										
 	 										try {
 	 											Severity[(i-1)] = row.getCell(severity).getStringCellValue();
											} catch (Exception e) {
												Severity[(i-1)] = " ";
											}
 	 										
 	 										try {
 	 											Priority[(i-1)] = row.getCell(priority).getStringCellValue();
											} catch (Exception e) {
												Priority[(i-1)] = " ";
											}
 	 	 									 	 	 									
 	 	 									try {
 	 	 										AssignTo[(i-1)] = row.getCell(assignTo).getStringCellValue();
											} catch (Exception e) {
												AssignTo[(i-1)] = " ";
											}
 	 	 									
 	 	 									
 	 	 										
										} 
 	 									else if(((i>2)&&(i<7))) 
 	 									{
 	 										try {
 	 											Reproducibility[(i-1)] = row.getCell(reproducibility).getStringCellValue();
											} catch (Exception e) {
												Reproducibility[(i-1)] = " ";
											}
 	 										
 	 										try {
 	 											Severity[(i-1)] = row.getCell(severity).getStringCellValue();
											} catch (Exception e) {
												Severity[(i-1)] = " ";
											}
 	 										
 	 										try {
 	 											Priority[(i-1)] = row.getCell(priority).getStringCellValue();
											} catch (Exception e) {
												Priority[(i-1)] = " ";
											}
 	 	 									
 	 	 									try {
 	 	 										AssignTo[(i-1)] = row.getCell(assignTo).getStringCellValue();
											} catch (Exception e) {
												AssignTo[(i-1)] = " ";
											}
 	 	 									
 	 	 									
										}
 	 									//else if(((i>7)&&(i<=8)))
 	 									else if(i>=7)
 	 									{
 	 										try {
 	 											Reproducibility[(i-1)] = row.getCell(reproducibility).getStringCellValue();
											} catch (Exception e) {
												Reproducibility[(i-1)] = " ";
											}
 	 										
 	 										try {
 	 											Priority[(i-1)] = row.getCell(priority).getStringCellValue();
											} catch (Exception e) {
												Priority[(i-1)] = " ";
											}
 	 										
 	 										try {
 	 											Severity[(i-1)] = row.getCell(severity).getStringCellValue();
											} catch (Exception e) {
												Severity[(i-1)] = " ";
											}
 	 	 									
 	 	 									try {
 	 	 										AssignTo[(i-1)] = row.getCell(assignTo).getStringCellValue();
											} catch (Exception e) {
												AssignTo[(i-1)] = " ";
											}
 	 	 									
 	 	 									
										}
 	 									
 	 									
 	 									
 	 									
									} catch (Exception e) {
										Log.fatal(e.toString());
										//e.printStackTrace();
									}
 									/*
 									Log.info("i: "+i+" Log defect: "+LogDefect.length+" Values:"+
 	 										Arrays.toString(LogDefect));
 	 								Log.info("i: "+i+" Reprodu: "+Reproducibility.length+" Values:"+
 	 										Arrays.toString(Reproducibility));
 	 								Log.info("i: "+i+" Severity: "+Severity.length+" Values:"+
 	 										Arrays.toString(Severity));
 	 								Log.info("i: "+i+" Priority: "+Priority.length+" Values:"+
 	 										Arrays.toString(Priority));
 	 								Log.info("i: "+i+" Assigned To: "+AssignTo.length+" Values:"+
 	 										Arrays.toString(AssignTo));	*/
 									
							//	}
 								/*
 								Log.info("Log defect: "+LogDefect.length+" Values:"+
 										Arrays.toString(LogDefect));
 								Log.info("Reprodu: "+Reproducibility.length+" Values:"+
 										Arrays.toString(Reproducibility));
 								Log.info("Severity: "+Severity.length+" Values:"+
 										Arrays.toString(Severity));
 								Log.info("Priority: "+Priority.length+" Values:"+
 										Arrays.toString(Priority));
 								Log.info("Assigned To: "+AssignTo.length+" Values:"+
 										Arrays.toString(AssignTo));
 								
 								Log.info("Read all Mantis properties");*/
 								
 							}	
 								
 							inputStream.close();
 							
 					    	workbook=null;
 					    	sheet = null;
 							}	} 
 					
 					catch (Exception e) {
 						Log.fatal(e.toString());
 					}
 					
 					
 			}
}
 				
 				
//}
