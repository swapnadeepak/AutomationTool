package testdriver;

import java.lang.reflect.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.WebDriver;
import generic.CommonWebOps;
import generic.Configuration;
import generic.Defects;
import generic.ExcelRelated;
import generic.Fileoperations;
import generic.TestResults;
import generic.Testcases;
import generic.Testobject;
import generic.Teststeps;
import generic.Timefunctions;

public class DriverScript {
	public static Logger Log = Logger.getLogger(Logger.class.getName());
	public static String User_dir = System.getProperty("user.dir");
	public static String UserFolderKey = "";
	public static String UserName = "";
	public static String ProjectRepoPath = "";
	public static String OrgPropertiesPath = "";
	public static String ProjectFolderKey = "";
	public static String ProjectName = "";
	public static String ConvertResultToPdf = "";
	public static String ConvertResultToHTML = "";
	
	public static WebDriver driver = null;
	public static String Browser = "";
	public static String BrowserVersion = "";
	public static String OS = "";
	public static Configuration configuration=null;
	public static ArrayList<Testobject> testobjects=null;
	public static String tcname = "Notset";
	public static String brname = "Notset";
	public static String modulename = "Notset";
	public static String Resultfilename;
		
	public static String GenericPropertiesFilename = "Generic";
	public static String Log4jPropertiesFilename = "Log4j";
	//public static String ProgressFilename = "Progress";
	//public static String MessagesFilename = "Messages";
	//public static String BrowserFilename = "Browser";
	
	public static String Log4jPropertiesFilepath = "";
	public static String GenericPropertiesFile = User_dir+"\\"+GenericPropertiesFilename+".properties";
	//public static String MessagesPropertiesFile = User_dir+"\\Progress\\"+MessagesFilename+".properties";
	//public static String BrowserPropertiesFile = User_dir+"\\Config\\"+BrowserFilename+".properties";
	
	public static String clientip = "ClientIP";
	public static String defectfilename = "DefectFilename";
	public static String defectProgress = "DefectProgressStatus";
	public static String propResultfilename = "ResultFilename";
	public static String nodeServerRunning = "NodeServerRunning";
	public static String nodeServerNoRunMsg = "NodeServerRunningMessage";
	public static String progressStatus = "ProgressStatus";
	public static String propConvertResultToPdf = "convertResultTopdf";
	public static String propConvertResultToHTML = "convertResultTohtml";
		
	public static String proprunplanfilename = "RunplanFilename";
	public static String serverip = "ServerIP";
	public static String serverPort = "ServerPort";
	public static String stopExecution = "StopExecution";
	public static String stopExecutionMessage = "StopExecutionMessage";
	public static String executeOn = "ExecuteOn";
	public static String impWait = "Implicitwait";
	public static String objWait = "Objectwait";
	public static String propUserFolderKey = "userFolderKey";
	public static String propUserName = "UserName";
	public static String propProjectRepoPath = "ProjectRepoPath";
	public static String propProjectFolderKey = "projectFolderKey";
	public static String propProjectName = "projectName";
	
	public static String nodeServerMessage0 = "NodeServerMessage0";
	public static String nodeServerMessage1 = "NodeServerMessage1";
	public static String nodeServerMessage2 = "NodeServerMessage2";
	public static String nodeServerMessage3 = "NodeServerMessage3";
	public static String nodeServerMessage4 = "NodeServerMessage4";
	public static String NodeserverTCstatus = "";
	
	public static final String ALGORITHM = "AES";
	public static final String TRANSFORMATION_TYPE = "AES/CBC/PKCS5Padding";
	public static String reg = "SOFTWARE\\SeleniumTool";
	public static String key = "temp";
	public static String securityKey = "eStuA!@tT0O1pROp";
	public static int implicitWaitTime;
	public static int objectWaitTime;
	
	@SuppressWarnings("unchecked")
	public static void main(String args[]){
				
		String RunPlanfile;
		String RunPlanfilename;
		String Resultfile;
		String Allobjectssheetname="Allobjects";
		String Teststepssheetname="TestSteps";
		String RunplanSheetname = "RunplanSheet";
		String ResultSheetname = "Results";
		String DefectsSheetname = "Defects";
		
		
								
		int NoOfTCs=0;
		int TCexecuted=0;
		//Fileoperations.displayMessage("start");
		/*int count = Fileoperations.updateCount();
		System.out.println(count);
		if (count==5000) 
		{
			
		System.exit(0);	
		}*/
		
		
		System.out.println(Fileoperations.getProperties(GenericPropertiesFile, stopExecution));
		if (!Fileoperations.getProperties(GenericPropertiesFile, stopExecution).equalsIgnoreCase("Yes")) 
		{
			//Fileoperations.displayMessage("before browser check");
			Browser= Fileoperations.getProperties(GenericPropertiesFile, executeOn);
			Log.info("Browser: "+Browser);
			if(!Browser.isEmpty())
			{
				//Fileoperations.displayMessage("after browser check");
				if (!Fileoperations.getProperties(GenericPropertiesFile, stopExecution).equalsIgnoreCase("Yes")) 
				{
					String WaitTime = Fileoperations.getProperties(GenericPropertiesFile, impWait);
					implicitWaitTime = Integer.valueOf(WaitTime);
					Log.info("Implicit wait time:"+implicitWaitTime);
					
					WaitTime = Fileoperations.getProperties(GenericPropertiesFile, objWait);
					objectWaitTime = Integer.valueOf(WaitTime);
					Log.info("Object wait time:"+objectWaitTime);
					
					ConvertResultToPdf = Fileoperations.getProperties(GenericPropertiesFile, propConvertResultToPdf);
					Log.info("Convert Result to PDF Key:"+ConvertResultToPdf);
					//Fileoperations.displayMessage("Convert Result to PDF Key:"+ConvertResultToPdf);
					
					ConvertResultToHTML = Fileoperations.getProperties(GenericPropertiesFile, propConvertResultToHTML);
					Log.info("Convert Result to HTML Key:"+ConvertResultToHTML);
					//Fileoperations.displayMessage("Convert Result to HTML Key:"+ConvertResultToHTML);
					
					ProjectFolderKey = Fileoperations.getProperties(GenericPropertiesFile, propProjectFolderKey);
					Log.info("Project Folder Key:"+ProjectFolderKey);
					//Fileoperations.displayMessage("Project folder key:"+ProjectFolderKey);
					
					ProjectName = Fileoperations.getProperties(GenericPropertiesFile, propProjectName);
					Log.info("Project Folder Key:"+ProjectName);
					//Fileoperations.displayMessage("Project name:"+ProjectName);
					
					ProjectRepoPath = Fileoperations.deriveProjectRepoPath();
					Log.info("Project Path:"+ProjectRepoPath);					
					Fileoperations.displayMessage("Project Path:"+ProjectRepoPath);
					
					OrgPropertiesPath = Fileoperations.deriveOrgPropertiesPath();
					Log.info("Org Path:"+OrgPropertiesPath);					
					Fileoperations.displayMessage("Org Path:"+OrgPropertiesPath);
					
					if (CommonWebOps.checkNodeRunning().equalsIgnoreCase("Yes"))
				 	{
						//Fileoperations.displayMessage("after nodeserver check");
						if (!Fileoperations.getProperties(GenericPropertiesFile, stopExecution).equalsIgnoreCase("Yes"))
						{				
							//System.setProperty("webdriver.chrome.driver", ProjectRepoPath+"\\Resources\\chromedriver.exe");
							//System.setProperty("webdriver.ie.driver", ProjectRepoPath+"\\resources\\IEDriverServer.exe");
							//System.setProperty("webdriver.gecko.driver", ProjectRepoPath+"\\resources\\geckodriver.exe");
							Fileoperations.deleteTemp();
							
							Date StartExecution = Timefunctions.getCurrentDateAndTime();
							SimpleDateFormat dateFormat = new SimpleDateFormat("MM_dd_yyyy_HH_mm_ss");
							String logfilename = dateFormat.format(StartExecution);
							ExcelRelated exrelated = new ExcelRelated();
							//Fileoperations.displayMessage("date time"+logfilename);
							
							RunPlanfilename = Fileoperations.getProperties(GenericPropertiesFile, proprunplanfilename);
							//Fileoperations.displayMessage(RunPlanfilename);
							RunPlanfile = ProjectRepoPath+"\\Runplans\\"+RunPlanfilename;
							//Fileoperations.displayMessage(RunPlanfile);
							
							Resultfile = Fileoperations.createResultFile(RunPlanfile, logfilename);
							Resultfilename = Fileoperations.getProperties(GenericPropertiesFile, propResultfilename);
							Fileoperations.displayMessage("Result filename"+Resultfilename);
							//Log4jPropertiesFilepath = ProjectRepoPath+"\\LogFiles\\"+Log4jPropertiesFilename+".properties";
							Log4jPropertiesFilepath = GenericPropertiesFile;
							
							Fileoperations.displayMessage("Directory is set to"+OrgPropertiesPath);
							System.setProperty("directory", OrgPropertiesPath);
							Fileoperations.displayMessage("directory prop set");
							System.setProperty("resultfilename", Resultfilename.substring(0, Resultfilename.length() - 5));
							Fileoperations.displayMessage("resultfilename prop set");
							PropertyConfigurator.configure(Log4jPropertiesFilepath);
							Log.info("=====================START===========================");
							Fileoperations.displayMessage("after creating log file");
							//commenting for testing start
							
							int percentage = Fileoperations.calcPercentExecution(NoOfTCs,TCexecuted);
							Fileoperations.storeProperties(GenericPropertiesFile, progressStatus, "0");
		
							//exrelated.deleteNoRows(Resultfile, "MasterSheet");
							//exrelated.deletedupBR(Resultfile, "MasterSheet");
							exrelated.removeHyperlinks(Resultfile, Teststepssheetname);
				
							testobjects = new ArrayList<>();
							testobjects=exrelated.readAllobjects(Resultfile, Allobjectssheetname); 
		
							ArrayList<Testcases> AllTestcases = new ArrayList<>();
							AllTestcases=exrelated.readAlltestcases(Resultfile, RunplanSheetname);
				
							ArrayList<Teststeps> AllTeststeps = new ArrayList<>();
							AllTeststeps=exrelated.readAllteststeps(Resultfile, Teststepssheetname, AllTestcases);
							
							//Fileoperations.displayMessage("Before UpdateStartEnd");
							exrelated.updateStartEnd(AllTestcases, AllTeststeps);
							//Fileoperations.displayMessage("After UpdateStartEnd");
							
							exrelated.markteststepsNorun(Resultfile, Teststepssheetname);
							//Fileoperations.displayMessage("After MarkstepsNorun");
		
							TestResults tcresults = new TestResults(RunPlanfilename, StartExecution, StartExecution, "",0, 0, 0, 0);
							tcresults.setexecutionStartTime(StartExecution);
							NoOfTCs = AllTestcases.size();
							tcresults.setnumofTCs(NoOfTCs);
		
							ArrayList<Defects> AllDefects = new ArrayList<>();
							Defects defect;	
							int defectSlNo =1;
		
							//Testcases:
							for (int i = 0; i < NoOfTCs; i++) 
							{
								Log.info("------Testcase:"+(i+1)+"----START------");
								
								String TCstatus = "Norun";
								String statusSet = "No";
						
								int startrow = AllTestcases.get(i).getStartRowNo();
								int endrow = AllTestcases.get(i).getEndRowNo();
								double repeat = AllTestcases.get(i).getRepeatability();
			
								Log.info("Start Row: "+startrow);
								Log.info("End Row: "+endrow);
								Log.info("Repeat: "+repeat);
			
								Date testcaseStartExecution= null;
								String upload= "";
			
								if (!Fileoperations.getProperties(GenericPropertiesFile, stopExecution).equalsIgnoreCase("Yes"))
								{
									if(!NodeserverTCstatus.equalsIgnoreCase("No"))
									{
										
									Log.info("NodeserverTCstatus: "+NodeserverTCstatus);		
									brname = AllTestcases.get(i).getBR();
									modulename = AllTestcases.get(i).getModule();
									tcname = AllTestcases.get(i).getTestcasename();
									
									Log.info("brname"+brname);
									Log.info("modulename"+modulename);
									Log.info("tcname"+tcname);
									
									// check node server for testcase
									if (CommonWebOps.checkNodeRunningforTestcase().equalsIgnoreCase("Yes"))
									{
										
									if (!((startrow==0)&&(endrow==0))) 
									{
										testcaseStartExecution = Timefunctions.getCurrentDateAndTime();
										Log.info("Testcase Start Execution time:"+testcaseStartExecution);
				
									//	for (int repeatiteration = (int) repeat; repeatiteration >= 1; repeatiteration--) 
									 //  { //repeat block
											
										
										//Teststeps:
										for (int k= startrow; k <= endrow; k++) 
										{	
											Log.info("Stop Execution: "+Fileoperations.getProperties(GenericPropertiesFile, stopExecution));
							
											ArrayList<String> resultValue=new ArrayList<String>();
							
											try {
												ArrayList<String> parameters= new ArrayList<String>();
												Object ClassObject = null;
												Method ClassMethod = null;
							
												parameters=exrelated.getTeststepparameters(AllTeststeps,k);
												//parameters.add(AllTestcases.get(k).getBR()+"_"+AllTestcases.get(k).getModule()+"_"+AllTestcases.get(k).getTestcasename());
												Class<?>[] paramTypes = {ArrayList.class};
												Log.info("keyword:  "+AllTeststeps.get(k).getKeyword()+" "+parameters.toString());							
												Class<?> Classname = Class.forName(AllTeststeps.get(k).getClassfile());
												Log.info("classname:"+Classname);
												ClassObject = Classname.newInstance();
												ClassMethod = ClassObject.getClass().getMethod(AllTeststeps.get(k).getKeyword(),paramTypes);
												resultValue = (ArrayList<String>) ClassMethod.invoke(ClassObject, parameters);
												AllTeststeps.get(k).setResult(resultValue.get(0));
												
												// Take screenshot for each step
												
						    					/*AllTeststeps.get(k).setFailureReason(resultValue.get(1));
						    					try {
						    							upload = upload+resultValue.get(2)+";"+"\n";
						    							Log.info("Screenshot path:"+resultValue.get(2));
						    							AllTeststeps.get(k).setFailureImage(resultValue.get(2));
						    						} 
						    					catch (Exception e) 
						    						{
						    							Log.info("Browser does not exist, No screenshot");
						    						}
						    					if (resultValue.get(0).equalsIgnoreCase("Fail")) 
						    					{
						    						TCstatus = "Fail";
						    						statusSet = "Yes";	
						    					}	
						    					else if(statusSet.equalsIgnoreCase("No"))
												{
						    						TCstatus = "Pass";
												}*/
												
												
												// Take screenshot on failure only start
												if (resultValue.get(0).equalsIgnoreCase("Fail")) 
												{
						    						AllTeststeps.get(k).setFailureReason(resultValue.get(1));
						    						try {
						    								upload = upload+resultValue.get(2)+";"+"\n";
						    								AllTeststeps.get(k).setFailureImage(resultValue.get(2));
						    							} 
						    						catch (Exception e) 
						    							{
						    								Log.info("Browser does not exist, No screenshot");
						    							}
						    	
						    						TCstatus = "Fail";
						    						statusSet = "Yes";								
												}
												
												else if (resultValue.get(0).equalsIgnoreCase("Custom")) 
												{
													//resultValue.set(0, "Pass");
													//AllTeststeps.get(k).setResult(resultValue.get(0));
													
						    						AllTeststeps.get(k).setFailureReason("");
						    						try {
						    								//upload = upload+resultValue.get(2)+";"+"\n";
						    								AllTeststeps.get(k).setFailureImage(resultValue.get(2));
						    							} 
						    						catch (Exception e) 
						    							{
						    								Log.info("Browser does not exist, No screenshot");
						    							}
						    	
						    						//TCstatus = "Fail";
						    						//statusSet = "Yes";								
												}
						    
												else if(statusSet.equalsIgnoreCase("No"))
												{
													TCstatus = "Pass";
												}
												// Take screenshot on failure only end
						    
												Log.info((Integer.valueOf(AllTeststeps.get(k).getRowNo())+1)+" "+AllTeststeps.get(k).getKeyword()+" "+parameters.get(0)+" "+resultValue);
												resultValue=null;
												parameters=null;
												//if (resultValue.get(0).equalsIgnoreCase("Fail")) {
												//driver.quit();
												//driver=null;	
												//Log.info(resultValue);
												//Log.error("Exit because of Fail");
												//TCstatus = "Fail";
												//resultValue=null;
												// break Teststeps; //to exit on fail
												//}
											} catch (InvocationTargetException e) {
												e.printStackTrace();
												AllTeststeps.get(k).setResult("Fail");
												AllTeststeps.get(k).setFailureReason(e.toString());
												Log.error("e message: "+e.toString());
												resultValue=null;
												TCstatus = "Fail";
											} 
											catch (Exception e) {
												e.printStackTrace();
												AllTeststeps.get(k).setResult("Fail");
												AllTeststeps.get(k).setFailureReason(e.toString());
												Log.error("e message: "+e.toString());
												resultValue=null;
												TCstatus = "Fail";
											}
										}
					
								//	} //Repeat block
									} //Teststeps block
								}//check node server for testcase
							  }	
									
								}
								
								if (TCstatus.equalsIgnoreCase("Fail")) 
								{
									upload = upload+ProjectRepoPath+"\\Results\\"+Resultfilename+";";
									defect = new Defects(0, defectSlNo, AllTestcases.get(i).getTCid(), 
											AllTestcases.get(i).getTestcasename(), 
											"No", "Enter Summary", "Enter Description", "always", 
											AllTestcases.get(i).getTCcr(), "normal", "nkendole", 
											"Please refer attachment", "Enter Additional comments", upload, " ");
										Log.info("Defect added");
										AllDefects.add(defect);
										defectSlNo++;
								}
												
								Date testcaseEndExecution = Timefunctions.getCurrentDateAndTime();
								Log.info("Testcase End Execution time:"+testcaseEndExecution);
						
								String testcaseexeuctTime = Timefunctions.executionTime(testcaseStartExecution, testcaseEndExecution);
								AllTestcases.get(i).settimeforExecution(testcaseexeuctTime);
			
								TCexecuted=TCexecuted+1;
								percentage = Fileoperations.calcPercentExecution(NoOfTCs,TCexecuted);
			
								if (percentage!=100) 
								{
									Fileoperations.storeProperties(GenericPropertiesFile, progressStatus, String.valueOf(percentage));
								}
							
								if (TCstatus.equalsIgnoreCase("Pass")) 
								{
									tcresults.setnumofPassedTCs((tcresults.getnumofPassedTCs())+1);
									AllTestcases.get(i).setResult("Pass");
								} 
								else if (TCstatus.equalsIgnoreCase("Fail"))
								{
									tcresults.setnumofFailedTCs((tcresults.getnumofFailedTCs())+1);
									AllTestcases.get(i).setResult("Fail");
								}
								else
								{
									tcresults.setnumofMissingTCs((tcresults.getnumofMissingTCs())+1);
									AllTestcases.get(i).setResult("Norun");
								}
			
								Log.info("Passed TC: "+tcresults.getnumofPassedTCs());
								Log.info("Missing TC: "+tcresults.getnumofMissingTCs());
								Log.info("Failed TC: "+tcresults.getnumofFailedTCs());
								Log.info("------Testcase:"+(i+1)+"-----END-------");
							} // Testcases block
		
							if (Fileoperations.getProperties(GenericPropertiesFile, stopExecution).equalsIgnoreCase("Yes")) 
							{
								if (tcname.equalsIgnoreCase("Notset"))
								{
									Fileoperations.storeProperties(GenericPropertiesFile, stopExecutionMessage, "Execution was stopped before executing any testcases. None of the testcases were executed");
								}
								else
								{
								Fileoperations.storeProperties(GenericPropertiesFile, stopExecutionMessage, "Execution was stopped at testcase: "+tcname);
								}
							}
							
							if (NodeserverTCstatus.equalsIgnoreCase("No")) 
							{
								Fileoperations.storeProperties(GenericPropertiesFile, stopExecutionMessage, "Node server was not running at testcase: "+tcname);
							}
				
							Fileoperations.exitBrowserOnStop();	
							exrelated.writeTeststepResults(Resultfile, Teststepssheetname, AllTeststeps);
							
							if (AllDefects.size()==0) {
								exrelated.createDefectsSheet(Resultfile, DefectsSheetname);
							} else {
								exrelated.writedefectsToExcel(Resultfile, DefectsSheetname, AllDefects);
							}
								

							exrelated.removeHyperlinks(Resultfile, Teststepssheetname);
							exrelated.deleteNorun(Resultfile, Teststepssheetname);
					
							Date endExecution = Timefunctions.getCurrentDateAndTime();
							tcresults.setexecutionEndTime(endExecution);
							Log.info("End Execution time:"+endExecution);
							String executTime = Timefunctions.executionTime(StartExecution, endExecution);
							tcresults.settimeTaken(executTime);
							Log.info("Time taken for execution: "+executTime); 
							exrelated.writetestResults(Resultfile, ResultSheetname, tcresults);
							exrelated.writetestcaseResults(Resultfile, RunplanSheetname, AllTestcases);
							exrelated.hideSheetsinResults(Resultfile);
							
							Fileoperations.convertResultToPdf();
							Fileoperations.convertResultToHtml();
							Fileoperations.createResultsZip(Resultfile);
							
							//Fileoperations.copyResultFiletoJenkinsFolder(Resultfile);
							//Fileoperations.displayMessage("Execution Completed");
							Log.info("Execution Completed");
							Log.info("=====================END============================");
							Fileoperations.storeProperties(GenericPropertiesFile, progressStatus, "100");
							
							
							//commenting for testing start
						}
						else 
						{
							Fileoperations.storeProperties(GenericPropertiesFile, stopExecutionMessage, "Execution was stopped after Nodeservercheck. None of the testcases were executed.");
							Fileoperations.storeProperties(GenericPropertiesFile, progressStatus, "100");
						}
				 	} 	// node running
				}		
				else
				{
					//Fileoperations.displayMessage("after browser check loop");
					Fileoperations.storeProperties(GenericPropertiesFile, stopExecutionMessage, "Execution was stopped at Nodeservercheck. None of the testcases were executed.");
					Fileoperations.storeProperties(GenericPropertiesFile, progressStatus, "100");
				}	
			
			}// browser check
			else 
			{
				Fileoperations.storeProperties(GenericPropertiesFile, nodeServerRunning, "No");
				Fileoperations.storeProperties(GenericPropertiesFile, nodeServerNoRunMsg, nodeServerMessage2);
				//Fileoperations.storeProperties(ProgressPropertiesFile, progressStatus, "100");
			}
	
		}	//stop execution before nodeservercheck
		else
		{
			//Fileoperations.displayMessage("last else loop");
			Fileoperations.storeProperties(GenericPropertiesFile, stopExecutionMessage, "Execution was stopped before Nodeservercheck. None of the testcases were executed.");
			Fileoperations.storeProperties(GenericPropertiesFile, progressStatus, "100");		
		}
	}//main
} //class	