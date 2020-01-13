package generic;

import java.awt.Dimension;
import java.awt.Font;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.security.Key;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.google.common.io.Files;
//import com.google.common.io.Files;
import com.sun.jna.platform.win32.Advapi32Util;
import com.sun.jna.platform.win32.WinReg;

import testdriver.DriverScript;

public class Fileoperations 
{
	static Logger Log = Logger.getLogger(Logger.class.getName());
	private static List <String> fileList = new ArrayList < String > ();
	private static String sourceFolder = "";
	private static String folderName="";
	

//--------------------------------------------------------------------------------	
	public static String takeScreenShot(WebDriver driver, String fileNamePrefix) 
		{
			Log.info("function: takeScreenShot");
			String screenshotFileName ="", screenshotFilePath="";
			try {
				
				
				fileNamePrefix = fileNamePrefix.replaceAll("[\\:,/]", "");			
				// Capture screenshot.
				File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

				// Create folder under project with name "screenshots" provided to screenshotFolderPath.
				//new File(screenshotFolderPath).mkdirs();

				// Set folder name to store screenshots.
				String screenshotFolderPath = DriverScript.ProjectRepoPath+"\\Screenshots\\"+createScreenshotFolder();

				// Set file name using current date time.
				//screenshotFileName = fileNamePrefix + "_" + getFormatedDateForScreenshots(new Date()) + ".png";
				//screenshotFileName = DriverScript.tcname + "_" + getFormatedDateForScreenshots(new Date()) + ".png";
				//screenshotFileName = DriverScript.tcname + "_"+fileNamePrefix+ "_"+getFormatedDateForScreenshots(new Date()) + ".png";
				screenshotFileName = DriverScript.brname + "_"+DriverScript.modulename + "_"+DriverScript.tcname + "_"+
				fileNamePrefix+ "_"+getFormatedDateForScreenshots(new Date()) + ".png";
						
				try {
					// Copy paste file at destination folder location
					screenshotFilePath = screenshotFolderPath + "\\" + screenshotFileName;
					File destFile = new File(screenshotFilePath);					
					FileUtils.copyFile(scrFile, destFile);
					Log.info("Screenshot path: "+screenshotFilePath);
				} catch (IOException e) {
					e.printStackTrace();
					Log.error(e.toString());
				}
			} catch (Exception e) {
				//e.printStackTrace();
				Log.error(e.toString());
			}
				
			return screenshotFilePath;
		}	
	

//--------------------------------------------------------------------------------	
	public static String takeHighlightScreenShot(WebDriver driver, WebElement element, String fileNamePrefix) 
	{
		Log.info("function: takeHighlightScreenShot");
		String screenshotFileName ="", screenshotFilePath="";
		try {
			// Set folder name to store screenshots.
			//String screenshotFolderPath = DriverScript.User_dir+"\\Repository\\screenshots";
			fileNamePrefix = fileNamePrefix.replaceAll("[\\:,/||]", "");
			JavascriptExecutor js = (JavascriptExecutor) DriverScript.driver;
			try {
				
				js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "color: red; border: 2px solid red;");	
			} catch (Exception e) {
				Log.error(e.toString());
			}
			
			
			// Capture screenshot.
			File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			try {
				js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "");
				js=null;
			} catch (Exception e) {
				Log.error(e.toString());
			}
			
			// Create folder under project with name "screenshots" provided to screenshotFolderPath.
			//new File(screenshotFolderPath).mkdirs();
			//createScreenshotFolder();

			// Set folder name to store screenshots.
			String screenshotFolderPath = DriverScript.ProjectRepoPath+"\\Screenshots\\"+createScreenshotFolder();
			// Set file name using current date time.
			//screenshotFileName = fileNamePrefix + "_" + getFormatedDateForScreenshots(new Date()) + ".png";
			screenshotFileName = DriverScript.brname + "_"+DriverScript.modulename + "_"+DriverScript.tcname + "_"+
					fileNamePrefix+ "_"+getFormatedDateForScreenshots(new Date()) + ".png";
			//screenshotFileName = DriverScript.tcname + "_"+fileNamePrefix+ "_"+ getFormatedDateForScreenshots(new Date()) + ".png";
					
			try {
				// Copy paste file at destination folder location
				screenshotFilePath = screenshotFolderPath + "\\" + screenshotFileName;
				File destFile = new File(screenshotFilePath);
				FileUtils.copyFile(scrFile, destFile);
				Log.info("Screenshot path:"+screenshotFilePath);
			} catch (IOException e) {
				//e.printStackTrace();
				Log.error(e.toString());
			}
		} catch (Exception e) {
			//e.printStackTrace();
			Log.error(e.toString());
		}
			
		return screenshotFilePath;
	}
	
	
//--------------------------------------------------------------------------------	
	public static String getFormatedDateForScreenshots(Date date) {
		SimpleDateFormat dateAndTimeformat=null;
		try {
			dateAndTimeformat =  new SimpleDateFormat("dd_MM_yyyy_kk_mm_ss");
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return dateAndTimeformat.format(date);
	}

//--------------------------------------------------------------------------------
public static void storeProperties(String filename, String key, String value)
{
	Log.info("function: storeProperties");
	try {
		File file = new File(filename);
		if (!file.exists()) {
			file.createNewFile();
		}
		
		//DriverScript.progressFileWriter = new FileWriter(DriverScript.User_dir+"\\Progress.properties");
		//DriverScript.progressFile.store(new FileWriter(DriverScript.User_dir+"\\Progress\\"+DriverScript.ProgressFilename+".properties"),null);
		FileOutputStream fileOut = null;
	    FileInputStream fileIn = null;
	    Properties progressFile = new Properties();
		//File file = new File(DriverScript.User_dir+"\\Progress\\"+DriverScript.ProgressFilename+".properties");
        fileIn = new FileInputStream(file);
        //DriverScript.progressFile.load(fileIn);
        //DriverScript.progressFile.setProperty(key, value);
        progressFile.load(fileIn);
        progressFile.setProperty(key, value);
        fileOut = new FileOutputStream(file);
        //DriverScript.progressFile.store(fileOut, null);
        progressFile.store(fileOut, null);
        fileOut.close();
        Log.info("Entered Key: "+key+" value:"+value+" in file:"+filename);
		Log.info("Saved properties file: "+filename);
	} catch (Exception e) {
		Log.error("Unable to enter Key: "+key+" value:"+value+" in file:"+filename);
		Log.error("Unable to save Progress properties file");  
	}
}
//--------------------------------------------------------------------------------
public static String getProperties(String filename, String key)
{
	Log.info("function: getProperties");
	String value="";
	try {
		Properties p=new Properties();  
	    p.load(new FileReader(filename));  
	    value = p.getProperty(key);  
		p=null;	
		Log.info("Fetched property value: "+value+" for Key: "+key+" from file:"+filename);
	} catch (Exception e) {
		Log.error("Unable to fetch property value: "+value+" for Key: "+key+" from file:"+filename);  
	}
	
	return value;
}	

//--------------------------------------------------------------------------------
public static String deriveProjectRepoPath()
{
	Log.info("function: getProperties");
	String value="";
	try {
		int lastbutonepos = DriverScript.User_dir.lastIndexOf("\\",DriverScript.User_dir.lastIndexOf("\\")-1);
		Log.info(lastbutonepos);
		
		String tempPath = DriverScript.User_dir.substring(0, lastbutonepos);
		Log.info(tempPath);
		
		value = tempPath+"\\"+DriverScript.ProjectFolderKey+"\\"+DriverScript.ProjectName;
		Log.info("Project Repo Path:"+value);
		
	} catch (Exception e) {
		Log.error("Unable to derive Project Repo Path: "+e.toString());  
	}
	
	return value;
}		
//--------------------------------------------------------------------------------



public static String deriveOrgPropertiesPath()
{
	Log.info("function: deriveOrgPropertiesPath");
	String value="";
	try {
		int lastbutonepos = DriverScript.User_dir.lastIndexOf("\\",DriverScript.User_dir.lastIndexOf("\\")-1);
		Log.info(lastbutonepos);
		Fileoperations.displayMessage(String.valueOf(lastbutonepos));
				
		String tempValue = DriverScript.User_dir.substring(0, lastbutonepos);
		Log.info(tempValue);
		
		int lastbuttwopos = tempValue.lastIndexOf("\\",tempValue.lastIndexOf("\\"));
		Log.info(lastbuttwopos);
		Fileoperations.displayMessage(String.valueOf(lastbuttwopos));
				
		value = tempValue.substring(0, lastbuttwopos);
		Log.info(value);
		
		Fileoperations.displayMessage("inside deriveOrgPropertiesPath"+value);
		
		
	} catch (Exception e) {
		Log.error("Unable to derive Project Repo Path: "+e.toString());  
	}
	
	return value;
}

//--------------------------------------------------------------------------------
public static void copyResultFile(String Resultfile)
	{
		Log.info("copyResultFile");
		try {
			Log.info(Resultfile);
			String dest =DriverScript.User_dir+"\\Repository\\LatestResultForJenkins\\Results.xlsx";
			File file2 = new File(dest);
			
			if (file2.exists()) {
				file2.delete();
			} else {

			}
			
			
			File file1 = new File(Resultfile);
			file2 = new File(dest);
			Files.copy(file1, file2);
			
			Log.info("Copied result file");
		} catch (Exception e) {
			Log.error("Unable to copy results file to results folder");
			Log.error(e);
		}
		
}


//--------------------------------------------------------------------------------
	public static String createScreenshotFolder()
	{
		Log.info("function: createScreenshotFolder");
		File screenshotResultFolder=null;
		String foldername = DriverScript.Resultfilename.substring(0, DriverScript.Resultfilename.length() - 5);
		try {
			
			screenshotResultFolder = new File(DriverScript.ProjectRepoPath+"\\Screenshots\\"+foldername);	
			Log.info("Screenshot Results folder is: "+screenshotResultFolder.getAbsolutePath());
		} catch (Exception e) {
			Log.error(e.toString());
			Log.error("Screenshot Results folder is: "+screenshotResultFolder.getAbsolutePath());
		}
		
		
        if (!screenshotResultFolder.exists()) {
            if (screenshotResultFolder.mkdir()) {
            	Log.info("Screenshot Results folder is created");            	
            } else {
            	Log.error("Failed to Screenshot Results folder");
            }
        }
        else
        {
        	Log.info("Screenshot Results folder already exist");
        	
        }
        
        return foldername;
	}

//--------------------------------------------------------------------------------		
	public static String createResultFile(String RunPlanfile, String filename)
	{
		Log.info("function: createResultFile");
		String resultFile = "";
		String resultFilename = "";
		try {
			//String src = DriverScript.User_dir+"\\Repository\\currentrun\\Results.xlsx";
			
			/*Date strtime = Starttime;  
			DateFormat df = new SimpleDateFormat("MM_dd_yyyy_HH_mm_ss");
			String reportDate = df.format(strtime);*/
			//String replaceString=reportDate.replace(':','_');
			
			resultFilename = "Results_"+filename+"_"
			+Fileoperations.getProperties(DriverScript.GenericPropertiesFile, DriverScript.clientip)+".xlsx";
			//Fileoperations.displayMessage("inside createresultfile"+resultFilename);
			
			//resultFile =DriverScript.User_dir+"\\Repository\\results\\Results_"+reportDate+".xlsx";
			resultFile =DriverScript.ProjectRepoPath+"\\Results"+"\\"+createTodayFolder()+"\\"+resultFilename;
			//Fileoperations.displayMessage("ResultFile: "+resultFile);
			Log.info("resultFile: "+resultFile);
			Log.info(resultFile);
			@SuppressWarnings("resource")
			XSSFWorkbook workbook = new XSSFWorkbook();
			FileOutputStream out = new FileOutputStream( 
				      new File(resultFile));
				      workbook.write(out);
				      out.close();
			
			
			File file1 = new File(RunPlanfile);
			File file2 = new File(resultFile);
			Files.copy(file1, file2);
			//Fileoperations.displayMessage("inside createresultfile:Created result file");
			Log.info("Created result file");
		} catch (Exception e) {
			Log.error("Unable to create results file");
			Log.error(e.toString());
		}
		//enterResultFile();
		//enterResultFileinProperties();
		storeProperties(DriverScript.GenericPropertiesFile, DriverScript.propResultfilename, resultFilename);
		storeProperties(DriverScript.GenericPropertiesFile, DriverScript.defectfilename, resultFilename);
		return resultFile;
		
	}

	
	//--------------------------------------------------------------------------------
			public static String createTodayFolder()
			{
				Log.info("function: createScreenshotFolder");
				File todayFolder=null;
				
				Date todayDate = Timefunctions.getCurrentDateAndTime();
				SimpleDateFormat dateFormat = new SimpleDateFormat("MM_dd_yyyy");
				folderName = dateFormat.format(todayDate);

				try {
					todayFolder = new File(DriverScript.ProjectRepoPath+"\\Results"+"\\"+folderName);	
					//Fileoperations.displayMessage("todayFolder: "+todayFolder);
					Log.info("Today folder in Results folder is: "+todayFolder.getAbsolutePath());
				} catch (Exception e) {
					Log.error(e.toString());
					Log.error("oday folder in Results folder is falied: "+todayFolder.getAbsolutePath());
				}
				
				
		        if (!todayFolder.exists()) {
		            if (todayFolder.mkdir()) {
		            	Log.info("Today folder is created in Results folder");            	
		            } else {
		            	Log.error("Today folder is not created in Results folder");
		            }
		        }
		        else
		        {
		        	Log.info("Today folder in Results folder already exist");
		        	
		        }
		        
		        return folderName;
			}	


		
//--------------------------------------------------------------------------------		
	/*
	public static void enterResultFile()
				{
					Log.info("enterResultFile");
					String filename = DriverScript.User_dir+"\\Progress\\ResultFile.txt";
					
					try {
						//Runtime.getRuntime().exec("taskkill /f /im notepad.exe");
						//Log.info("Killed notepad exe");
						new FileOutputStream(filename, true).close();
						Log.info("ResultFile txt file is created");
					} catch (Exception e) {
						Log.error("Unable to create ResultFile txt file");
					}
					
					BufferedWriter buffWriter = null;
					FileWriter fileWriter = null;
					
					try {		

						fileWriter = new FileWriter(filename);
						buffWriter = new BufferedWriter(fileWriter);
						buffWriter.write(DriverScript.resultFilename);

						Log.info("Updated ResultFile as: "+DriverScript.resultFilename);

					} catch (IOException e) {
						Log.info(e.toString());
						e.printStackTrace();

					} finally {

						try {

							if (buffWriter != null)
								buffWriter.close();

							if (fileWriter != null)
								fileWriter.close();

						} catch (IOException ex) {
							Log.info(ex.toString());
							ex.printStackTrace();

						}

					}
					
				}
*/
	
//--------------------------------------------------------------------------------		
	/*	public static void enterResultFileinProperties()
					{
						Log.info("enterResultFileinProperties");
											
						try {
							
							DriverScript.progressFile.setProperty(DriverScript.propresultfilename, DriverScript.resultFilename);
							Fileoperations.storeProperties();
							Log.info("ResultFilename is updated in Properties file");
						} catch (Exception e) {
							Log.error("Unable to update ResultFilename in Properties file");
						}
						
						
						
					}
*/
		
//--------------------------------------------------------------------------------	
	public static int calcPercentExecution(int total, int executed)
	{
		Log.info("function: calcPercentExecution");
		int percentage=0;
		
		try {
			percentage = (executed*100)/total;
		} catch (Exception e) {
			//Log.error(e.toString());
			percentage=0;
		}
		Log.info("Percentage is : "+percentage);
		
		return percentage;
	}
	

//--------------------------------------------------------------------------------
/*
	public static void updateProgressStatus(int percentage)
	{
		String filename = DriverScript.User_dir+"\\Progress\\ProgressStatus.txt";
				
		try {
			//Runtime.getRuntime().exec("taskkill /f /im notepad.exe");
			//Log.info("Killed notepad exe");
			new FileOutputStream(filename, true).close();
			Log.info("ProgressStatus txt file is created");
		} catch (Exception e) {
			Log.error("Unable to create ProgressStatus txt file");
		}
		
		BufferedWriter buffWriter = null;
		FileWriter fileWriter = null;
		
		try {		

			fileWriter = new FileWriter(filename);
			buffWriter = new BufferedWriter(fileWriter);
			buffWriter.write(String.valueOf(percentage));

			Log.info("Updated progress status as: "+percentage);

		} catch (IOException e) {
			Log.info(e.toString());
			e.printStackTrace();

		} finally {

			try {

				if (buffWriter != null)
					buffWriter.close();

				if (fileWriter != null)
					fileWriter.close();

			} catch (IOException ex) {
				Log.info(ex.toString());
				ex.printStackTrace();

			}

		}

		
		
	}
	
	
*/	

//--------------------------------------------------------------------------------
/*
	public static void updateNodeServerStatus(String status)
		{
			String filename = DriverScript.User_dir+"\\Progress\\NodeServerRunning.txt";
					
			try {
				//Runtime.getRuntime().exec("taskkill /f /im notepad.exe");
				//Log.info("Killed notepad exe");
				new FileOutputStream(filename, true).close();
				Log.info("NodeServerRunning txt file is created");
			} catch (Exception e) {
				Log.error("Unable to create NodeServerRunning txt file");
			}
			
			BufferedWriter buffWriter = null;
			FileWriter fileWriter = null;
			
			try {		

				fileWriter = new FileWriter(filename);
				buffWriter = new BufferedWriter(fileWriter);
				buffWriter.write(status);

				Log.info("Updated NodeServerRunning status as: "+status);

			} catch (IOException e) {
				Log.info(e.toString());
				e.printStackTrace();

			} finally {

				try {

					if (buffWriter != null)
						buffWriter.close();

					if (fileWriter != null)
						fileWriter.close();

				} catch (IOException ex) {
					Log.info(ex.toString());
					ex.printStackTrace();

				}

			}

			
			
		}
	*/	
//--------------------------------------------------------------------------------
/*
	public static void deleteNodeServerStatus()
				{
					String filename = DriverScript.User_dir+"\\Progress\\NodeServerRunning.txt";
							
					try {
						new File(filename).delete();
						Log.info("Deleted NodeServerRunning txt file");
					} catch (Exception e) {
						Log.error("Unable to delete NodeServerRunning txt file");
					}
					
				}		
		
	*/
//--------------------------------------------------------------------------------	
	public static void deleteNorunvbs()
	{
		Log.info("function: deleteNorunvbs");
		String filePath = DriverScript.User_dir;
		filePath.replace("\\", "\\\\");
		//String vbsfile = "wscript "+filePath+"\\deleteNorun.vbs";
		String vbsfile = filePath+"\\vbs\\deleteNorun.vbs";
		
		Log.info("deleteNorunvbs file path: "+vbsfile);
		try {
			
			String[] cmdArray = new String[2];
			cmdArray[0] = "wscript";
			cmdArray[1] = vbsfile;
			Process process = Runtime.getRuntime().exec(cmdArray);
			process.waitFor();
			process=null;
			//Process p = Runtime.getRuntime().exec(vbsfile);
			//p.waitFor();//Wait for .vbs file to finish executing
			//p=null;
			Log.info("Deleted Norun steps");
			} catch (Exception e) {
			Log.error("Unable to excute Deletenorunvbs and unable to delete Norun steps");
			Log.error(e.toString());
			}
	}
	

//--------------------------------------------------------------------------------	
	public static void convertResultToPdf()
	{
		Log.info("function: convertResultToPdf");
		String filePath = DriverScript.User_dir;
		filePath.replace("\\", "\\\\");
		//String vbsfile = "wscript "+filePath+"\\deleteNorun.vbs";
		String vbsfile = filePath+"\\convertResultToPdf.vbs";
		
		Log.info("convertResultToPdf file path: "+vbsfile);
		try {
			
			if (DriverScript.ConvertResultToPdf.equalsIgnoreCase("Yes")) 
			{
				String[] cmdArray = new String[2];
				cmdArray[0] = "wscript";
				cmdArray[1] = vbsfile;
				Process process = Runtime.getRuntime().exec(cmdArray);
				process.waitFor();
				process=null;
				//Process p = Runtime.getRuntime().exec(vbsfile);
				//p.waitFor();//Wait for .vbs file to finish executing
				//p=null;
				Log.info("Converted Result To Pdf");
			}
			
			
			
			} catch (Exception e) {
			Log.error("Unable to convert Result To Pdf");
			Log.error(e.toString());
			}
	}


//--------------------------------------------------------------------------------	
	public static void convertResultToHtml()
	{
		Log.info("function: convertResultToPdf");
		String filePath = DriverScript.User_dir;
		filePath.replace("\\", "\\\\");
		//String vbsfile = "wscript "+filePath+"\\deleteNorun.vbs";
		String vbsfile = filePath+"\\convertResultToHtml.vbs";
		
		Log.info("convertResultToHtml file path: "+vbsfile);
		try {
			
			if (DriverScript.ConvertResultToHTML.equalsIgnoreCase("Yes")) 
			{
				String[] cmdArray = new String[2];
				cmdArray[0] = "wscript";
				cmdArray[1] = vbsfile;
				Process process = Runtime.getRuntime().exec(cmdArray);
				process.waitFor();
				process=null;
				//Process p = Runtime.getRuntime().exec(vbsfile);
				//p.waitFor();//Wait for .vbs file to finish executing
				//p=null;
				Log.info("Converted Result To Html");
			}
			
			
			} catch (Exception e) {
			Log.error("Unable to convert Result To Html");
			Log.error(e.toString());
			}
	}
		
//--------------------------------------------------------------------------------	
				public static void createResultsZip(String resultFile)
				{
					Log.info("function: createResultsZip");
					
					sourceFolder = resultFile.substring(0, resultFile.length() - 5);
				    String htmlFile = sourceFolder+".html";
				    String htmlFiles = sourceFolder+"_files";
				    String outputZipFile = sourceFolder+".zip";	
				    
				    Log.info("htmlfile: "+htmlFile);
				    Log.info("html files"+htmlFiles);
				    Log.info("zip file"+outputZipFile);
				    
				    File file1 = new File(htmlFile);
			    	File file2 = new File(htmlFiles);
			    	File file3 = new File(sourceFolder);
			    	
			    	try {
			        	if (!file3.exists()) {
							file3.mkdirs();
						}
			        	file1.renameTo(new File(file3+ "\\" + file1.getName()));
			        	file2.renameTo(new File(file3+ "\\" + file2.getName()));
			        	generateFileList(file3);
			        	zipIt(outputZipFile);
			        	FileUtils.deleteDirectory(file3);
			        
					} catch (Exception e) {
						e.printStackTrace();
					}					
				}
				
				private static void generateFileList(File node) 
				{
			        if (node.isFile()) 
			        {
			            fileList.add(generateZipEntry(node.toString()));
			        }

			        if (node.isDirectory()) 
			        {
			            String[] subNote = node.list();
			            for (String filename: subNote) 
			            {
			                generateFileList(new File(node, filename));
			            }
			        }
			    }

			    private static String generateZipEntry(String file) 
			    {
			        return file.substring(sourceFolder.length() + 1, file.length());
			    }
			    
			    private static void zipIt(String zipFile) {
			        byte[] buffer = new byte[1024];
			        String source1 = new File(sourceFolder).getName();
			        FileOutputStream fos = null;
			        ZipOutputStream zos = null;
			        try {
			            fos = new FileOutputStream(zipFile);
			            zos = new ZipOutputStream(fos);

			            Log.info("Output to Zip : " + zipFile);
			            FileInputStream in = null;

			            for (String file: fileList) {
			            	Log.info("File Added : " + file);
			                ZipEntry ze = new ZipEntry(source1 + File.separator + file);
			                zos.putNextEntry(ze);
			                
			                try {
			                    in = new FileInputStream(sourceFolder + File.separator + file);
			                    int len;
			                    	while ((len = in .read(buffer)) > 0) 
			                    	{
			                      	 zos.write(buffer, 0, len);
			                    	}                  
			                	} 
			                finally 
			                	{
			                    	in.close();
			                	}
			            		}            
			            zos.closeEntry();
			            Log.info("Folder successfully compressed");
			              
			        } catch (IOException ex) {
			        	Log.error(ex.toString());
			           
			        } finally {
			            try {
			                zos.close();
			            } catch (IOException e) {
			            	Log.error(e.toString());
			            }
			        }
			    }
		
//--------------------------------------------------------------------------------	
				
				
				
	/*
	public static void deleteTemp()
		{
			String filePath = DriverScript.User_dir;
			filePath.replace("\\", "\\\\");
			//String vbsfile = "wscript "+filePath+"\\deleteNorun.vbs";
			String batfile = filePath+"\\DeleteTemp.bat";
			
			Log.info("DeleteTemp file path: "+batfile);
			try {
				
				String[] cmdArray = new String[1];
				cmdArray[0] = batfile;
				Process process = Runtime.getRuntime().exec(cmdArray);
				process.waitFor();
				process=null;
				
				Log.info("Deleted files in Temp folder");
				} catch (Exception e) {
				Log.info("Unable to delete files in Temp folder");
				Log.error(e);
				}
		}	*/
	
	public static void deleteTemp()
	{
		Log.info("function: deleteTemp");
		String userhome = System.getProperty("user.home");
		String tempFolder = userhome+"\\Appdata\\Local\\Temp";
		
		Log.info("Deleting temp folder: "+tempFolder);
		try {
			
			File tempfiles = new File(tempFolder);
			FileUtils.cleanDirectory(tempfiles);	
					
			Log.info("Deleted files in Temp folder");
			} catch (Exception e) {
			Log.error("Unable to delete files in Temp folder");
			Log.error(e.toString());
			}
	}

//--------------------------------------------------------------------------------
	/*
	public static String readBrowser()
	{
		
		String browser = "";
		String filePath = DriverScript.User_dir;
		filePath.replace("\\", "\\\\");
		String browserfile = filePath+"\\Config\\Browser.txt";
		
		Log.info("Browser file path: "+browserfile);
		try 
		{
			BufferedReader reader = new BufferedReader(new FileReader(browserfile));
			String line1 =reader.readLine();
			
			String[] values = line1.split(":");
			
			
			if (values[1].trim().toUpperCase().contains("CHROME")) 
			{
				Log.info("Browser selected: "+values[1]);
				browser = "CHROME";				
			}
			else if(values[1].trim().toUpperCase().contains("EXPLORER"))
			{
				Log.info("Browser selected: "+values[1]);
				browser = "INTERNET EXPLORER";
			}
			else if(values[1].isEmpty())
			{
				Log.info("Please mention browser in Browser txt file");
			}
			
			
			reader.close();
			reader=null;
			
		}
		catch(Exception e)
		{
			Log.info("Unable to read browser from Browser.txt"+e.toString());
			//browser = e.toString();
		}
		
		return browser;
	}
	*/

//--------------------------------------------------------------------------------
	/*
	public static ArrayList<String> readip()
		{
			
			ArrayList<String> serverip=new ArrayList<String>();
			String filePath = DriverScript.User_dir;
			filePath.replace("\\", "\\\\");
			String ipfile = filePath+"\\Progress\\Serverip.txt";
			
			Log.info("Readip file path: "+ipfile);
			try 
			{
				BufferedReader reader = new BufferedReader(new FileReader(ipfile));
				serverip.add(reader.readLine());
				serverip.add(reader.readLine());						
				reader.close();
				reader=null;
				
			}
			catch(Exception e)
			{
				Log.info("Unable to read browser from Browser.txt"+e.toString());
				//browser = e.toString();
			}
			
			return serverip;
		}
	
	*/
//--------------------------------------------------------------------------------
	/*
	public static String readRunplanFilename()
		{
			
			String filename = "";
			String filePath = DriverScript.User_dir;
			filePath.replace("\\", "\\\\");
			String browserfile = filePath+"\\Progress\\RunplanFile.txt";
			
			Log.info("Browser file path: "+browserfile);
			try 
			{
				BufferedReader reader = new BufferedReader(new FileReader(browserfile));
				filename =reader.readLine();
				Log.info("RunPlan filename: "+filename);		
				
				reader.close();
				reader=null;
				
			}
			catch(Exception e)
			{
				Log.info("Unable to read RunPlan filename from RunplanFile.txt"+e.toString());
				//browser = e.toString();
			}
			
			return filename;
		}
	*/
	
//--------------------------------------------------------------------------------
	
	public static void exitBrowserOnStop ()
	{
		Log.info("function: exitBrowserOnStop");
		if (!(DriverScript.driver==null)) 
		{
	
			try {
				DriverScript.driver.quit();
				DriverScript.driver=null;
				Log.info("Done Exiting the browser due to Stop button click or Because end of execution");
										
			} catch (Exception e) {
				Log.error(e.toString());
				Log.error("Unable to exit active browser");
				
			}
		}
	}
//--------------------------------------------------------------------------------	
	public static void killChromeExe()
	{
		Log.info("function: killChromeExe");
		try {
			Runtime.getRuntime().exec("taskkill /f /im chromedriver.exe");
			Log.info("Killed chromedriver exe");
		} catch (Exception e) {
			Log.error("Unable to kill chromedriver exe");
		}
	}

//--------------------------------------------------------------------------------	
	public static void deleteLog()
	{
		Log.info("function: deleteLog");
		try {
			String filePath = DriverScript.User_dir;
			filePath.replace("\\", "\\\\");
			String logfile = filePath+"\\Repository\\Logs\\logfile.log";
			File source = new File(logfile);
			FileUtils.forceDelete(source);				
			
			Log.info("Deleted previous log file");
			
		} catch (Exception e) {
			Log.error("Unable to delete previous log file: "+e.toString());
		}
	}
//--------------------------------------------------------------------------------	
public static void renameLog(String resultFilename)
{
	try {
		String filePath = DriverScript.User_dir;
		filePath.replace("\\", "\\\\");
		String logfile = filePath+"\\Repository\\Logs\\logfile.log";
		File source = new File(logfile);
						
		String resultlogfile = filePath+"\\Repository\\Logs\\"+(resultFilename.substring(0, resultFilename.length() - 5))+".log";
		
		File dest = new File(resultlogfile);
		
		Log.info("source:"+source);
		Log.info("dest:"+dest);
		FileUtils.copyFile(source, dest);
		Log.info("Copied log file to result filename");
		
	} catch (Exception e) {
		Log.error("Unable to rename log file to result filename: "+e.toString());
	}
}

//--------------------------------------------------------------------------------	
	public static void displayMessage(String message)
	{
		/*UIManager.put("OptionPane.minimumSize",new Dimension(300,150)); 
		
		JLabel label = new JLabel(message);
		label.setFont(new Font("Arial", Font.BOLD, 18));
		JOptionPane.showMessageDialog(null,label,"Selenium Open Source Tool",JOptionPane.INFORMATION_MESSAGE);
		Log.info("Execution completed");*/
	}

//--------------------------------------------------------------------------------	
	public static void executionComplete()
	{
		UIManager.put("OptionPane.minimumSize",new Dimension(300,150)); 
		
		JLabel label = new JLabel("Execution Completed");
		label.setFont(new Font("Arial", Font.BOLD, 18));
		JOptionPane.showMessageDialog(null,label,"Selenium Open Source Tool",JOptionPane.INFORMATION_MESSAGE);
		Log.info("Execution completed");
	}

//--------------------------------------------------------------------------------
	/*
	public static void browserNotMentioned()
	{
		UIManager.put("OptionPane.minimumSize",new Dimension(300,150)); 
		
		JLabel label = new JLabel("Execution Not Started: Please mention browser in Browser.txt");
		label.setFont(new Font("Arial", Font.BOLD, 18));
		JOptionPane.showMessageDialog(null,label,"Selenium Open Source Tool",JOptionPane.INFORMATION_MESSAGE);
		Log.info("Execution completed");
	}
	*/
//--------------------------------------------------------------------------------
	public static void killExcelExe()
	{
		Log.info("function: killExcelExe");
		try {
			Runtime.getRuntime().exec("taskkill /f /im Excel.exe");
			Log.info("Killed Excel Process");
		} catch (Exception e) {
			Log.error("Unable to kill Excel Process");
		}
	}

//--------------------------------------------------------------------------------
	public static String encryptString(String value, String securityKey)
	{
		Log.info("function: encryptString");
		String encryptedString="";
		//System.out.println("encrypt");
		try 
        {
            Log.info("String encrypted:"+value);
			/*Key aesKey = new SecretKeySpec(securityKey.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, aesKey);
            byte[] encrypted = cipher.doFinal(value.getBytes());
            encryptedString = new String(encrypted);
            Log.info(encryptedString);
            System.out.println(encryptedString);*/
            //    String secretKey = "eStuA!@112345678";
   		 Key key = new SecretKeySpec(securityKey.getBytes(), DriverScript.ALGORITHM);
   		 Cipher cipher = Cipher.getInstance(DriverScript.TRANSFORMATION_TYPE);
   		 byte[] ivByte = new byte[cipher.getBlockSize()];
   		 IvParameterSpec ivParamsSpec = new IvParameterSpec(ivByte);
   		 cipher.init(Cipher.ENCRYPT_MODE, key, ivParamsSpec);
   		 byte[] encryptedByteArray = cipher.doFinal(value.getBytes());
   		encryptedString = Base64.getEncoder().encodeToString(encryptedByteArray);
        }
        catch(Exception e) 
        {
            Log.error(e.toString());        	
        }
		return encryptedString;
	}

//--------------------------------------------------------------------------------
	public static String decryptString(String value, String securityKey)
	{
		Log.info("function: decryptString");
		String decryptedString="";
		try 
        {
            /*Key aesKey = new SecretKeySpec(securityKey.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, aesKey);
            byte[] encrypted = cipher.doFinal(value.getBytes());
            String decrypted = new String(cipher.doFinal(encrypted));
            decryptedString = decrypted;
            Log.info(decryptedString);
            System.err.println("decrypted:"+decrypted);*/
			//String secretKey = "eStuA!@112345678";
			//String secretKey = "eStuA!@112345678";
			 Key key = new SecretKeySpec(securityKey.getBytes(), DriverScript.ALGORITHM);
			 Cipher cipher = Cipher.getInstance(DriverScript.TRANSFORMATION_TYPE);
			 byte[] ivByte = new byte[cipher.getBlockSize()];
			 IvParameterSpec ivParamsSpec = new IvParameterSpec(ivByte);
			 cipher.init(Cipher.DECRYPT_MODE, key, ivParamsSpec);
			 byte[] original = cipher.doFinal(Base64.getDecoder().decode(value));
			 decryptedString = new String(original);
			// System.out.println("in decryptString function:"+decryptedString);
			
			
        }
        catch(Exception e) 
        {
        	Log.error(e.toString());
        	System.out.println(e.toString());
        }
		return decryptedString;
	}

//--------------------------------------------------------------------------------	
	
		public static void updateRegistryValue(String userORmachine, String reg, String key, String value)
		{
			Log.info("function: updateRegistryValue");
			Log.info(userORmachine);
			String SeleniumTooltemp = "";
			
			if (userORmachine.equalsIgnoreCase("user")) 
			{
				try 
		        {
					//SeleniumTooltemp = Advapi32Util.registryGetStringValue(WinReg.HKEY_CURRENT_USER, reg, key);
					Advapi32Util.registrySetStringValue(WinReg.HKEY_CURRENT_USER, reg, key, value);
					Log.info(SeleniumTooltemp);
					//System.out.println("key present");
		        }
		        catch(Exception e) 
		        {
		        	Log.error(e.toString());
		        	
		        	try 
		        	{
		        		//System.out.println("key not present");
		        		Advapi32Util.registryCreateKey(WinReg.HKEY_CURRENT_USER, reg);
		        		Advapi32Util.registrySetStringValue(WinReg.HKEY_CURRENT_USER, reg, key, value);
					} catch (Exception e2) 
		        	{
						Log.error(e2.toString());
					}
		        } 
			}
				else if (userORmachine.equalsIgnoreCase("user"))
				{
					try 
			        {
						SeleniumTooltemp = Advapi32Util.registryGetStringValue(WinReg.HKEY_LOCAL_MACHINE, reg, key);
						Log.info(SeleniumTooltemp);
			        }
			        catch(Exception e) 
			        {
			        	Log.error(e.toString());
			        	
			        	try 
			        	{
			        		Advapi32Util.registryCreateKey(WinReg.HKEY_LOCAL_MACHINE, reg);
			        		Advapi32Util.registrySetStringValue(WinReg.HKEY_LOCAL_MACHINE, reg, key, value);
						} catch (Exception e2) 
			        	{
							Log.error(e2.toString());
						}
			        }
				}
			
			
				        	
	        			
		}
//--------------------------------------------------------------------------------
		public static String readCurrentUserRegistryValue(String userORmachine, String reg, String key)
		{
			Log.info("function: readCurrentUserRegistryValue");
			String SeleniumTooltemp = "blank";
			
			if (userORmachine.equalsIgnoreCase("user")) 
			{
				try 
		        {
					SeleniumTooltemp = Advapi32Util.registryGetStringValue(WinReg.HKEY_CURRENT_USER, reg, key);
					Log.info(SeleniumTooltemp);
					//System.out.println(SeleniumTooltemp);
		        }
		        catch(Exception e) 
		        {
		        	Log.error(e.toString()); 
		        	//System.out.println(e.toString());
		        }
			} 
			else if (userORmachine.equalsIgnoreCase("machine"))
			{
				try 
		        {
					SeleniumTooltemp = Advapi32Util.registryGetStringValue(WinReg.HKEY_LOCAL_MACHINE, reg, key);
					Log.info(SeleniumTooltemp);
		        }
		        catch(Exception e) 
		        {
		        	Log.error(e.toString());    
		        }
			}
				
			
			return SeleniumTooltemp;
		}
//--------------------------------------------------------------------------------
		public static int updateCount()
		{
			Log.info("function: updateCount");
			//System.out.println("udpatecount");
			//String SeleniumTooltemp = "blank";
			String valueBefore = "0";
			String valueAfter = "1";
			int intvalueafter =1;
			
			String regvaluBefore = readCurrentUserRegistryValue("user", DriverScript.reg, DriverScript.key);
			//System.out.println("valuebefore: "+regvaluBefore);
			if (!regvaluBefore.equalsIgnoreCase("blank")) 
			{
				//System.out.println("value exists");
				valueBefore= decryptString(regvaluBefore, DriverScript.securityKey);
				//System.out.println("value after decryption:"+valueBefore);
				try {
					intvalueafter = Integer.valueOf(valueBefore)+1;
				} catch (Exception e) {
					//Advapi32Util.registryDeleteKey(WinReg.HKEY_CURRENT_USER, DriverScript.reg, DriverScript.key);
					Advapi32Util.registryCreateKey(WinReg.HKEY_CURRENT_USER, DriverScript.reg);
				}
				
				//System.out.println("value after addition"+intvalueafter);
				valueAfter = encryptString(String.valueOf(intvalueafter), DriverScript.securityKey);
				//System.out.println("value after addition"+valueAfter);
				updateRegistryValue("user", DriverScript.reg, DriverScript.key, valueAfter);
			}			
			else  
			{
				//System.out.println(String.valueOf(valueafter));
				//System.out.println("value does not exist");
				valueAfter = encryptString(String.valueOf(intvalueafter), DriverScript.securityKey);
				//System.out.println(valueAfter);
				updateRegistryValue("user", DriverScript.reg, DriverScript.key, valueAfter);
			}		
			//System.out.println(valueAfter);
			return intvalueafter;
		}
//--------------------------------------------------------------------------------
}
