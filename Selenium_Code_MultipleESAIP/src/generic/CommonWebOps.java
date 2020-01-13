package generic;

import java.awt.Dimension;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Driver;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.SessionId;
import org.openqa.selenium.support.ui.Select;

import testdriver.DriverScript;

public class CommonWebOps 
{
	static Logger Log = Logger.getLogger(Logger.class.getName());
	
	ArrayList<String> result= new ArrayList<String>();
	private static int maxWaitElement = DriverScript.objectWaitTime;
	//private static int implicitWaitTime=20;
	
	
	
//---------------------------------	
			public ArrayList<String> gotourl (ArrayList<String> parameters)
	{
				Log.info("function: gotourl");
			try {
				this.createRemoteDriver();
				//this.createDriver();
				
				DriverScript.driver.get(parameters.get(1));
				Log.info("Navigated to url: "+parameters.get(1));
				//DriverScript.driver.manage().window().setPosition(new Point(0, 0));
				//DriverScript.driver.manage().timeouts().implicitlyWait(implicitWaitTime, TimeUnit.SECONDS);
				DriverScript.driver.manage().timeouts().implicitlyWait(DriverScript.implicitWaitTime, TimeUnit.SECONDS);
				DriverScript.driver.manage().window().maximize();
				result.add("Pass");
				//result.add("");
				//result.add(Fileoperations.takeScreenShot(DriverScript.driver, parameters.get(1)));
			} catch (Exception e) {
				Log.error("Unable to navigate to url: "+parameters.get(1));
				Log.error(e.toString());
				result.add("Fail");
				result.add("Unable to navigate to url");
				result.add(Fileoperations.takeScreenShot(DriverScript.driver, parameters.get(1)));
				
				
				//System.exit(0);
			}
				

		return result;
	}
	
//---------------------------------	

			public void createDriver()
		{
				Log.info("function: createDriver");
			//if ((DriverScript.driver==null)&&(DriverScript.Browser=="CHROME"))
			//{
			if ((DriverScript.driver==null)&&(DriverScript.Browser.trim().toLowerCase().contains("chrome")))
			{
				try {
					ChromeOptions cOptions = new ChromeOptions();
					cOptions.addArguments("disable-infobars");
					DriverScript.driver = new ChromeDriver(cOptions);				
					DriverScript.driver.manage().window().maximize();
					DriverScript.driver.manage().timeouts().implicitlyWait(DriverScript.implicitWaitTime, TimeUnit.SECONDS);
					DriverScript.Browser="Google Chrome";
					this.getBrowserVersion();
					Log.info("Opened Chrome browser");
				} catch (Exception e) {
					Log.fatal("Unable to open Chrome browser");
				}
				
			}
			else if((DriverScript.driver==null)&&(DriverScript.Browser.trim().toLowerCase().contains("explorer")))
			{
				try {
					DesiredCapabilities caps = DesiredCapabilities.internetExplorer();
					caps.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,true);
					DriverScript.driver = new InternetExplorerDriver(caps);
					DriverScript.driver.manage().window().maximize();
					DriverScript.driver.manage().timeouts().implicitlyWait(DriverScript.implicitWaitTime, TimeUnit.SECONDS);
					DriverScript.Browser="Internet Explorer";
					this.getBrowserVersion();
					Log.info("Opened Internet Explorer Browser");
				} catch (Exception e) {
					Log.fatal("Unable to open Internet Explorer browser");
				}
				
				
			}
			else if((DriverScript.driver==null)&&(DriverScript.Browser.trim().toLowerCase().contains("firefox")))
			{
				try {
					DesiredCapabilities capabilities = DesiredCapabilities.firefox();
			        capabilities.setCapability("marionette", true);
			        DriverScript.driver = new FirefoxDriver(capabilities);
			        DriverScript.driver.manage().window().maximize();
					DriverScript.driver.manage().timeouts().implicitlyWait(DriverScript.implicitWaitTime, TimeUnit.SECONDS);
					DriverScript.Browser="Mozilla Firefox";
					this.getBrowserVersion();
					Log.info("Opened Firefox browser");
				} catch (Exception e) {
					Log.fatal("Unable to open Mozilla Firefox browser");
				}
				
			}				
			
		}
			
//---------------------------------	
			
			public void getBrowserVersion()
			{
				Log.info("function: getBrowserVersion");
				Capabilities cap = ((RemoteWebDriver) DriverScript.driver).getCapabilities();
				DriverScript.BrowserVersion = cap.getVersion().toString();
			    Log.info(DriverScript.BrowserVersion);
				//DriverScript.OS = cap.getPlatform().toString();
			    DriverScript.OS = System.getProperty("os.name");
			    Log.info(DriverScript.OS);		
				
			}

			
			
			
//---------------------------------
			

			public static String checkNodeRunning()
			{
					Log.info("function: checkNodeRunning");
					String nodeStatus = "";
					DesiredCapabilities cap=null;
					String Node = "";
					
					//first if
					if (!Fileoperations.getProperties(DriverScript.GenericPropertiesFile, DriverScript.stopExecution).equalsIgnoreCase("Yes")) 
					{
						//Fileoperations.displayMessage("check node running1");
						if (DriverScript.Browser.trim().toLowerCase().contains("chrome")) 
						{
							//Fileoperations.displayMessage("check node running2");
							// working
							cap = DesiredCapabilities.chrome();
							cap.setBrowserName("chrome");
							//working
							//cap = DesiredCapabilities.firefox();
							//cap.setBrowserName("firefox");
							//cap.setPlatform(Platform.WINDOWS);
							Node = "http://"+Fileoperations.getProperties
								(DriverScript.GenericPropertiesFile,DriverScript.serverip)+":"+
								Fileoperations.getProperties(DriverScript.GenericPropertiesFile, DriverScript.serverPort)+"/wd/hub";
							//Fileoperations.displayMessage("node:"+Node);
						}
						
						//second if
						if (!Fileoperations.getProperties(DriverScript.GenericPropertiesFile, DriverScript.stopExecution).equalsIgnoreCase("Yes")) 
						{
							//String Node = "http://10.10.10.205:4966/wd/hub";
							try {
								//Fileoperations.displayMessage("check node running3");
								DriverScript.driver = new RemoteWebDriver(new URL(Node), cap);
								//DriverScript.driver.manage().window().setSize(rcDriver);
								//DriverScript.driver.manage().window().setPosition(new Point(-2000, 0));
								DriverScript.driver.quit();
								DriverScript.driver =null;
								//Fileoperations.displayMessage("check node running31");
								nodeStatus="Yes";
									//Fileoperations.displayMessage("nodeserver running");
									if (!Fileoperations.getProperties(DriverScript.GenericPropertiesFile, DriverScript.stopExecution).equalsIgnoreCase("Yes")) 
									{
										//Fileoperations.displayMessage("check node running4");
										Fileoperations.storeProperties(DriverScript.GenericPropertiesFile, DriverScript.nodeServerRunning, "Yes");	
									}
									else if (Fileoperations.getProperties(DriverScript.GenericPropertiesFile, DriverScript.stopExecution).equalsIgnoreCase("Yes"))
									{
										//Fileoperations.displayMessage("check node running5");
										Fileoperations.storeProperties(DriverScript.GenericPropertiesFile, DriverScript.nodeServerRunning, "Yes");
										Fileoperations.storeProperties(DriverScript.GenericPropertiesFile, DriverScript.stopExecutionMessage, "Execution was stopped at Nodeservercheck. None of the testcases were executed.");
										Fileoperations.storeProperties(DriverScript.GenericPropertiesFile, DriverScript.progressStatus, "100");
										System.exit(0);
									}
						
								} 
							catch (Exception e) 
								{
									nodeStatus="No";
									//Fileoperations.displayMessage("nodeserver not running");
									if (!Fileoperations.getProperties(DriverScript.GenericPropertiesFile, DriverScript.stopExecution).equalsIgnoreCase("Yes")) 
									{
										//Fileoperations.displayMessage(e.toString());
										if (e.toString().trim().toLowerCase().contains("cannot find chrome binary")) 
										{
											Fileoperations.storeProperties(DriverScript.GenericPropertiesFile, DriverScript.nodeServerNoRunMsg, DriverScript.nodeServerMessage1);
										} 
										else 
										{
											Fileoperations.storeProperties(DriverScript.GenericPropertiesFile, DriverScript.nodeServerNoRunMsg, DriverScript.nodeServerMessage4);
										}
										Fileoperations.storeProperties(DriverScript.GenericPropertiesFile, DriverScript.progressStatus, "0");
										Fileoperations.storeProperties(DriverScript.GenericPropertiesFile, DriverScript.nodeServerRunning, "No");
									}
						
									else if (Fileoperations.getProperties(DriverScript.GenericPropertiesFile, DriverScript.stopExecution).equalsIgnoreCase("Yes"))
									{
										Fileoperations.storeProperties(DriverScript.GenericPropertiesFile, DriverScript.nodeServerRunning, "No");
										Fileoperations.storeProperties(DriverScript.GenericPropertiesFile, DriverScript.stopExecutionMessage, "Execution was stopped after Nodeservercheck. Node server is not running.");
										Fileoperations.storeProperties(DriverScript.GenericPropertiesFile, DriverScript.progressStatus, "100");
										System.exit(0);
									}
								}
						}// second if
						else if(Fileoperations.getProperties(DriverScript.GenericPropertiesFile, DriverScript.stopExecution).equalsIgnoreCase("Yes"))
						{
							Fileoperations.storeProperties(DriverScript.GenericPropertiesFile, DriverScript.nodeServerRunning, "No");
							Fileoperations.storeProperties(DriverScript.GenericPropertiesFile, DriverScript.stopExecutionMessage, "Execution was stopped before Nodeservercheck. None of the testcases executed.");
							Fileoperations.storeProperties(DriverScript.GenericPropertiesFile, DriverScript.progressStatus, "100");
							System.exit(0);
						}
						
					}// first if
					
					else if(Fileoperations.getProperties(DriverScript.GenericPropertiesFile, DriverScript.stopExecution).equalsIgnoreCase("Yes"))
					{
						Fileoperations.storeProperties(DriverScript.GenericPropertiesFile, DriverScript.nodeServerRunning, "No");
						Fileoperations.storeProperties(DriverScript.GenericPropertiesFile, DriverScript.stopExecutionMessage, "Execution was stopped at Nodeservercheck. None of the testcases were executed.");
						Fileoperations.storeProperties(DriverScript.GenericPropertiesFile, DriverScript.progressStatus, "100");
						System.exit(0);
					}
					
					return nodeStatus;		
				
			}
			
//---------------------------------	

			public static String checkNodeRunningforTestcase()
			{
					Log.info("function: checkNodeRunningforTestcase");
					String nodeStatus = "";
					/*DesiredCapabilities cap=null;
					String Node = "";
					
						if (DriverScript.Browser.trim().toLowerCase().contains("chrome")) 
						{
							cap = DesiredCapabilities.chrome();
							cap.setBrowserName("chrome");
							Node = "http://"+Fileoperations.getProperties
								(DriverScript.GenericPropertiesFile,DriverScript.serverip)+":"+
								Fileoperations.getProperties(DriverScript.GenericPropertiesFile, DriverScript.serverPort)+"/wd/hub";
						}
						
						//String Node = "http://10.10.10.205:4966/wd/hub";
							try {
								DriverScript.driver = new RemoteWebDriver(new URL(Node), cap);
								DriverScript.driver.quit();
								DriverScript.driver =null;
								DriverScript.NodeserverTCstatus="Yes";
								Log.info("NodeserverTCstatus = Yes");
								nodeStatus="Yes";
							
								//Fileoperations.storeProperties(DriverScript.ProgressPropertiesFile, DriverScript.nodeServerRunning, "Yes");	
								} 
							catch (Exception e) 
								{
								DriverScript.NodeserverTCstatus="No";
								Log.info("NodeserverTCstatus = No");
								nodeStatus="No";
								//Fileoperations.storeProperties(DriverScript.ProgressPropertiesFile, DriverScript.nodeServerRunning, "No");
								}*/
					nodeStatus="Yes";
					return nodeStatus;		
			}
			
//---------------------------------	

			public void createRemoteDriver()
			{
				Log.info("function: createRemoteDriver");
				SessionId sessionID = null;
				if ((DriverScript.driver==null)&&(DriverScript.Browser.trim().toLowerCase().contains("chrome")))
				{
					DesiredCapabilities cap = DesiredCapabilities.chrome();
					cap.setBrowserName("chrome");
					String Node = "http://"+Fileoperations.getProperties(DriverScript.GenericPropertiesFile, DriverScript.serverip)+":"+
							Fileoperations.getProperties(DriverScript.GenericPropertiesFile, DriverScript.serverPort)+"/wd/hub";
					
					try {
						sessionID = ((RemoteWebDriver)DriverScript.driver).getSessionId();
						Log.info("sessionID"+sessionID.toString());
						Fileoperations.displayMessage("sessionid"+sessionID.toString());
					} catch (Exception e) {
						Log.info("Previous Remote driver session does not exist");
					}
					
					//String Node = "http://10.10.10.205:4966/wd/hub";
					try {

						if (sessionID==null)
						{
							DriverScript.driver = new RemoteWebDriver(new URL(Node), cap);	
						}
						
						DriverScript.driver.manage().window().maximize();
						DriverScript.driver.manage().timeouts().implicitlyWait(DriverScript.implicitWaitTime, TimeUnit.SECONDS);
						
						Log.info("Opened Chrome browser");
						Fileoperations.storeProperties(DriverScript.GenericPropertiesFile, DriverScript.nodeServerRunning, "Yes");
					} catch (Exception e) {

						Fileoperations.storeProperties(DriverScript.GenericPropertiesFile, DriverScript.nodeServerRunning, "No");
						throw new WebDriverException("Node Server not running");
					}
					
					
				}
				else if((DriverScript.driver==null)&&(DriverScript.Browser.trim().toLowerCase().contains("explorer")))
				{
					DesiredCapabilities cap = DesiredCapabilities.internetExplorer();					
					//DesiredCapabilities cap = new  DesiredCapabilities();
					//cap.setBrowserName("internet explorer");
					//cap.setCapability("ignore-certificate", true);
				
					String Node = "http://"+Fileoperations.getProperties(DriverScript.GenericPropertiesFile, DriverScript.serverip)+":"+
							Fileoperations.getProperties(DriverScript.GenericPropertiesFile, DriverScript.serverPort)+"/wd/hub";
					
					//String Node = "http://10.10.10.205:4966/wd/hub";
					try {
						DriverScript.driver = new RemoteWebDriver(new URL(Node), cap);
						//DriverScript.driver = new InternetExplorerDriver(cap);
						DriverScript.driver.manage().window().maximize();
						DriverScript.driver.manage().timeouts().implicitlyWait(DriverScript.implicitWaitTime, TimeUnit.SECONDS);
						Log.info("Opened Chrome browser");
						Fileoperations.storeProperties(DriverScript.GenericPropertiesFile, DriverScript.nodeServerRunning, "Yes");
					} catch (Exception e) {

						Fileoperations.storeProperties(DriverScript.GenericPropertiesFile, DriverScript.nodeServerRunning, "No");
						throw new WebDriverException("Node Server not running");
					}

					
				}
				else
				{
					Log.info("Chrome browser already open");
				}				
				
			}
//---------------------------------	

			public ArrayList<String> refreshBrowser(ArrayList<String> parameters)
		{
				Log.info("function: refreshBrowser");
				try {
					DriverScript.driver.navigate().refresh();
					Log.info("Refreshed Browser");	
					result.add("Pass");
				} catch (Exception e) {
					Log.error("Not able to refresh Browser");	
					Log.error(e.toString());
					result.add("Fail");
				}
			

			return result;
					
		}
//---------------------------------
	
			public ArrayList<String> enter (ArrayList<String> parameters)
	{
		Log.info("function: enter");
		WebElement element=null;
		
		int i=0;
		if (!(DriverScript.driver==null)) 
		{
			for (i = 0; i < DriverScript.testobjects.size(); i++) 
			{
				if(parameters.get(0).equalsIgnoreCase(DriverScript.testobjects.get(i).getObjectName()))
						{
							try {
								
								element = CommonWebOps.returnWebElement(i);
								//JavascriptExecutor js = (JavascriptExecutor) DriverScript.driver;
								//js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "color: red; border: 2px solid red;");	
								
								int waitcount;
								for (waitcount = 0; waitcount < maxWaitElement; waitcount++) 
								{
									try {
										element.sendKeys(parameters.get(1));
										//js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "");
										//js=null;		
										Log.info("Entered value in editbox: "+parameters.get(0));
										result.add("Pass");
										//result.add("");
										//result.add(Fileoperations.takeHighlightScreenShot(DriverScript.driver,element, parameters.get(0)));
										element=null;
										break;
										} 
									catch (Exception e) {
											try {
													Thread.sleep(1000);
													
													if ((e.toString().contains("unknown error:"))||
														(e.toString().contains("is not clickable at point"))||
														(e.toString().contains("Other element would receive the click:"))) 
													{
														JavascriptExecutor jse = (JavascriptExecutor)DriverScript.driver;
														jse.executeScript("scroll(0, -30);");
														jse=null;
													} 
													
													
													
													} catch (InterruptedException e1)
												{
														
												}
											}
								}	
									
								if (waitcount==(maxWaitElement)) {
								Log.error("Unable to Entervalue: "+parameters.get(0));
								result.add("Fail");
								result.add("Element is not editable");
								//js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "");
								//js=null;
								result.add(Fileoperations.takeHighlightScreenShot(DriverScript.driver, element, parameters.get(0)));}			
								break;
								
							} catch (Exception e) {
								//e.printStackTrace();
								Log.error(e.toString());
								Log.error("Unable to enter value in: "+parameters.get(0));
								result.add("Fail");
								result.add(e.toString());
								//js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "");
								result.add(Fileoperations.takeScreenShot(DriverScript.driver, parameters.get(0)));
								
								element=null;
								break;
							}
						}
				else if(i==(DriverScript.testobjects.size()-1))
						{
							Log.error("Unable to find the element in All Objects: "+parameters.get(0));
							result.add("Fail");
							result.add("Element not found in All Objects");
							result.add(Fileoperations.takeScreenShot(DriverScript.driver, parameters.get(0)));
						}
				
			}
				
		} 
		else{
			Log.error("Unable to enter value in: "+parameters.get(0));
			Log.error("Browser not found");
			result.add("Fail");
			result.add("Browser Not Found");
		}
		
		//js = null;
		return result;
	}
//---------------------------------

			
			public ArrayList<String> enterChild (ArrayList<String> parameters)
	{
		Log.info("function: enterChild");
		List<WebElement> elements=null;
		WebElement element=null;
		
		int i=0;
		if (!(DriverScript.driver==null)) 
		{
			for (i = 0; i < DriverScript.testobjects.size(); i++) 
			{
				if(parameters.get(0).equalsIgnoreCase(DriverScript.testobjects.get(i).getObjectName()))
						{
							try {
								
								elements = CommonWebOps.returnWebElements(i);
								String param = parameters.get(1);
								String[] values = param.split("\\:");
								element = elements.get(Integer.valueOf(values[0]));	
								Log.info(Integer.valueOf(values[0]));	
								
								int waitcount;
								for (waitcount = 0; waitcount < maxWaitElement; waitcount++) 
								{
									try {
										element.sendKeys(values[1]);
												
										Log.info("Entered value in editbox: "+values[1]);
										result.add("Pass");
										//result.add("");
										//result.add(Fileoperations.takeScreenShot(DriverScript.driver, parameters.get(1)));
										element=null;
										break;
										} 
									catch (Exception e) {
											try {
													Thread.sleep(1000);
													if ((e.toString().contains("unknown error:"))||
														(e.toString().contains("is not clickable at point"))||
														(e.toString().contains("Other element would receive the click:"))) 
													{
														JavascriptExecutor jse = (JavascriptExecutor)DriverScript.driver;
														jse.executeScript("scroll(0, -30);");
														jse=null;
													} 
													
													
													} catch (InterruptedException e1)
												{
												}
											}
								}	
									
								if (waitcount==(maxWaitElement)) {
								Log.error("Unable to Entervalue: "+parameters.get(0));
								result.add("Fail");
								result.add("Element is not editable");
								
								result.add(Fileoperations.takeHighlightScreenShot(DriverScript.driver, element, parameters.get(0)));}			
								break;
								
							} catch (Exception e) {
								//e.printStackTrace();
								Log.error(e.toString());
								Log.error("Unable to enter value in: "+parameters.get(0));
								result.add("Fail");
								result.add(e.toString());
								//js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "");
								result.add(Fileoperations.takeScreenShot(DriverScript.driver, parameters.get(0)));
								
								element=null;
								break;
							}
						}
				else if(i==(DriverScript.testobjects.size()-1))
						{
							Log.error("Unable to find the element in All Objects: "+parameters.get(0));
							result.add("Fail");
							result.add("Element not found in All Objects");
							result.add(Fileoperations.takeScreenShot(DriverScript.driver, parameters.get(0)));
						}
				
			}
				
		} 
		else{
			Log.error("Unable to enter value in: "+parameters.get(0));
			Log.error("Browser not found");
			result.add("Fail");
			result.add("Browser Not Found");
		}
		
		//js = null;
		return result;
	}
			
//---------------------------------
			
			/*public ArrayList<String> enterAssetName (ArrayList<String> parameters)
	{
		Log.info("function: enter");
		WebElement element=null;
		
		int i=0;
		if (!(DriverScript.driver==null)) 
		{
			for (i = 0; i < DriverScript.testobjects.size(); i++) 
			{
				if(parameters.get(0).equalsIgnoreCase(DriverScript.testobjects.get(i).getObjectName()))
						{
							try {
								
								element = CommonWebOps.returnWebElement(i);
								//JavascriptExecutor js = (JavascriptExecutor) DriverScript.driver;
								//js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "color: red; border: 2px solid red;");	
								
								int waitcount;
								for (waitcount = 0; waitcount < maxWaitElement; waitcount++) 
								{
									try {
										
										Date currentDate = Timefunctions.getCurrentDateAndTime();
										SimpleDateFormat dateFormat = new SimpleDateFormat("MMddyyHHmmss");
										String dateSuffix = dateFormat.format(currentDate);
										Log.info(dateSuffix);
										element.sendKeys(parameters.get(1)+"_"+dateSuffix);
										//js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "");
										//js=null;		
										Log.info("Entered value in editbox: "+parameters.get(0));
										result.add("Pass");
										//result.add("");
										//result.add(Fileoperations.takeHighlightScreenShot(DriverScript.driver,element, parameters.get(0)));
										element=null;
										break;
										} 
									catch (Exception e) {
											try {
													Thread.sleep(1000);
													
													if ((e.toString().contains("unknown error:"))||
														(e.toString().contains("is not clickable at point"))||
														(e.toString().contains("Other element would receive the click:"))) 
													{
														JavascriptExecutor jse = (JavascriptExecutor)DriverScript.driver;
														jse.executeScript("scroll(0, -30);");
														jse=null;
													} 
													
													
													
													} catch (InterruptedException e1)
												{
														
												}
											}
								}	
									
								if (waitcount==(maxWaitElement)) {
								Log.error("Unable to Entervalue: "+parameters.get(0));
								result.add("Fail");
								result.add("Element is not editable");
								//js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "");
								//js=null;
								result.add(Fileoperations.takeHighlightScreenShot(DriverScript.driver, element, parameters.get(0)));}			
								break;
								
							} catch (Exception e) {
								//e.printStackTrace();
								Log.error(e.toString());
								Log.error("Unable to enter value in: "+parameters.get(0));
								result.add("Fail");
								result.add(e.toString());
								//js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "");
								result.add(Fileoperations.takeScreenShot(DriverScript.driver, parameters.get(0)));
								
								element=null;
								break;
							}
						}
				else if(i==(DriverScript.testobjects.size()-1))
						{
							Log.error("Unable to find the element in All Objects: "+parameters.get(0));
							result.add("Fail");
							result.add("Element not found in All Objects");
							result.add(Fileoperations.takeScreenShot(DriverScript.driver, parameters.get(0)));
						}
				
			}
				
		} 
		else{
			Log.error("Unable to enter value in: "+parameters.get(0));
			Log.error("Browser not found");
			result.add("Fail");
			result.add("Browser Not Found");
		}
		
		//js = null;
		return result;
	}*/
//---------------------------------
	
			public ArrayList<String> clearEditbox (ArrayList<String> parameters)
		{
			Log.info("function: clearEditbox");
			WebElement element=null;
			int i=0;
			if (!(DriverScript.driver==null)) 
			{
				for (i = 0; i < DriverScript.testobjects.size(); i++) 
				{
					if(parameters.get(0).equalsIgnoreCase(DriverScript.testobjects.get(i).getObjectName()))
							{
								try {
									element = CommonWebOps.returnWebElement(i);
									JavascriptExecutor js = (JavascriptExecutor) DriverScript.driver;
									js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "color: red; border: 2px solid red;");	
																	
									int waitcount;
									for (waitcount = 0; waitcount < maxWaitElement; waitcount++) 
									{
										try {
											element.clear();
											//js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "");
											//js=null;
											//js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "");
											Log.info("Entered value in editbox: "+parameters.get(0));
											result.add("Pass");
											//result.add("");
											//result.add(Fileoperations.takeHighlightScreenShot(DriverScript.driver,element, parameters.get(0)));
											element=null;
											break;
											} 
										catch (Exception e) {
												try {
													Thread.sleep(1000);
													if ((e.toString().contains("unknown error:"))||
														(e.toString().contains("is not clickable at point"))||
														(e.toString().contains("Other element would receive the click:"))) 
													{
														JavascriptExecutor jse = (JavascriptExecutor)DriverScript.driver;
														jse.executeScript("scroll(0, -30);");
														jse=null;
													} 
													
													
													} catch (Exception e1)
													{
													}
												}
									}	
										
									if (waitcount==(maxWaitElement)) {
									Log.error("Unable to Entervalue: "+parameters.get(0));
									result.add("Fail");
									result.add("Element is not editable");
									//js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "");
									//js=null;
									result.add(Fileoperations.takeHighlightScreenShot(DriverScript.driver, element, parameters.get(0)));}			
									break;
									
								} catch (Exception e) {
									//e.printStackTrace();
									Log.error(e);
									Log.error("Unable to clear editbox: "+parameters.get(0));
									//js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "");
									element=null;
									result.add("Fail");
									result.add(e.toString());
									result.add(Fileoperations.takeScreenShot(DriverScript.driver, parameters.get(0)));
									break;
								}
							}
					else if(i==(DriverScript.testobjects.size()-1))
							{
								Log.error("Unable to find the element in All Objects: "+parameters.get(0));
								result.add("Fail");
								result.add("Element not found in All Objects");
								result.add(Fileoperations.takeScreenShot(DriverScript.driver, parameters.get(0)));
							}
					
				}
					
			} 
			else{
				Log.error("Unable to clear editbox: "+parameters.get(0));
				Log.error("Browser not found");
				result.add("Fail");
				result.add("Browser Not Found");
			}

			return result;
		}


//---------------------------------

			public ArrayList<String> enterPressEnter (ArrayList<String> parameters)
	{
		Log.info("function: enterPressEnter");
		WebElement element=null;
		int i=0;
		if (!(DriverScript.driver==null)) 
		{
			for (i = 0; i < DriverScript.testobjects.size(); i++) 
			{
				if(parameters.get(0).equalsIgnoreCase(DriverScript.testobjects.get(i).getObjectName()))
						{
							try {
								element = CommonWebOps.returnWebElement(i);
								//JavascriptExecutor js = (JavascriptExecutor) DriverScript.driver;
								//js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "color: red; border: 2px solid red;");	
														
								int waitcount;
								for (waitcount = 0; waitcount < maxWaitElement; waitcount++) 
								{
									try {
										element.sendKeys(parameters.get(1)+Keys.ENTER);
										//js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "");
										Log.info("Entered value in editbox: "+parameters.get(0));
										result.add("Pass");
										//result.add("");
										//result.add(Fileoperations.takeScreenShot(DriverScript.driver, parameters.get(1)));
										//js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "");
										//js=null;
										element=null;
										
										break;
										} 
									catch (Exception e) {
											try {
												Thread.sleep(1000);
												} catch (InterruptedException e1)
												{
												}
											}
								}	
									
								if (waitcount==(maxWaitElement)) {
								Log.error("Unable to Entervalue: "+parameters.get(0));
								result.add("Fail");
								result.add("Element is not editable");
								result.add(Fileoperations.takeHighlightScreenShot(DriverScript.driver, element, parameters.get(0)));}			
								break;
								
							} catch (Exception e) {
								//e.printStackTrace();
								Log.error(e);
								Log.error("Unable to enter value in: "+parameters.get(0));
								element=null;
								result.add("Fail");
								result.add(e.toString());
								//js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "");
								result.add(Fileoperations.takeScreenShot(DriverScript.driver, parameters.get(0)));
								break;
							}
						}
				else if(i==(DriverScript.testobjects.size()-1))
						{
							Log.error("Unable to find the element in All Objects: "+parameters.get(0));
							result.add("Fail");
							result.add("Element not found in All Objects");
							result.add(Fileoperations.takeScreenShot(DriverScript.driver, parameters.get(0)));
						}
				
			}
				
		} 
		else{
			Log.error("Unable to enter value in: "+parameters.get(0));
			Log.error("Browser not found");
			result.add("Fail");
			result.add("Browser Not Found");
		}

		return result;
	}
//---------------------------------

	
			public ArrayList<String> pressEnter (ArrayList<String> parameters)
	{
		Log.info("function: pressEnter");
		WebElement element=null;
		int i=0;
		if (!(DriverScript.driver==null)) 
		{
			for (i = 0; i < DriverScript.testobjects.size(); i++) 
			{
				if(parameters.get(0).equalsIgnoreCase(DriverScript.testobjects.get(i).getObjectName()))
						{
							try {
								element = CommonWebOps.returnWebElement(i);
																							
								int waitcount;
								for (waitcount = 0; waitcount < maxWaitElement; waitcount++) 
								{
									try {
										element.sendKeys(Keys.ENTER);
										//js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "");
										Log.info("Pressed Enter button in element: "+parameters.get(0));
										result.add("Pass");
										//result.add("");
										//result.add(Fileoperations.takeHighlightScreenShot(DriverScript.driver,element, parameters.get(0)));
								
										element=null;
										break;
										} 
									catch (Exception e) {
											try {
												Thread.sleep(1000);
												} catch (InterruptedException e1)
												{
												}
											}
								}	
									
								if (waitcount==(maxWaitElement)) {
								Log.error("Unable to press Enter button: "+parameters.get(0));
								result.add("Fail");
								result.add("Element is not editable");
								result.add(Fileoperations.takeHighlightScreenShot(DriverScript.driver, element, parameters.get(0)));}			
								break;
								
							} catch (Exception e) {
								//e.printStackTrace();
								Log.error(e);
								Log.error("Unable to press Enter button: "+parameters.get(0));
								//js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "");
								element=null;
								result.add("Fail");
								result.add(e.toString());
								result.add(Fileoperations.takeScreenShot(DriverScript.driver, parameters.get(0)));
								break;
							}
						}
				else if(i==(DriverScript.testobjects.size()-1))
						{
							Log.error("Unable to find the element in All Objects: "+parameters.get(0));
							result.add("Fail");
							result.add("Element not found in All Objects");
							result.add(Fileoperations.takeScreenShot(DriverScript.driver, parameters.get(0)));
						}
				
			}
				
		} 
		else{
			Log.error("Unable to press Enter button in: "+parameters.get(0));
			Log.error("Browser not found");
			result.add("Fail");
			result.add("Browser Not Found");
		}

		return result;
	}

//---------------------------------
	
	
			public ArrayList<String> click (ArrayList<String> parameters)
		{
			Log.info("function: click");
			WebElement element=null;
			int i=0;
			if (!(DriverScript.driver==null)) 
			{
				for (i = 0; i < DriverScript.testobjects.size(); i++) 
				{
					if(parameters.get(0).equalsIgnoreCase(DriverScript.testobjects.get(i).getObjectName()))
							{
								try {
									element = CommonWebOps.returnWebElement(i);
									//JavascriptExecutor js = (JavascriptExecutor) DriverScript.driver;
									//js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "color: red; border: 2px solid red;");	
									
									int waitcount;
									//ActionLoop:
									for (waitcount = 0; waitcount < maxWaitElement; waitcount++) 
									{
										try {
											element.click();
											//js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "");
											Log.info("Clicked on :"+parameters.get(0));
											result.add("Pass");
											//result.add(" ");
											//result.add(Fileoperations.takeHighlightScreenShot(DriverScript.driver,element, parameters.get(0)));
											//js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "");
											//js=null;
											element=null;
											break;
											} 
										catch (Exception e) {
												try {
													Thread.sleep(1000);
													if ((e.toString().contains("unknown error:"))||
														(e.toString().contains("is not clickable at point"))||
														(e.toString().contains("Other element would receive the click:"))) 
													{
														JavascriptExecutor jse = (JavascriptExecutor)DriverScript.driver;
														jse.executeScript("scroll(0, -30);");
														jse=null;
													} 
																																							
													
													} catch (InterruptedException e1)
													{
													}
												}
									}	
										
									if (waitcount==(maxWaitElement)) {
									Log.error("Unable to click: "+parameters.get(0));
									
									result.add("Fail");
									result.add("Element is not clickable");
									//js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "");
									//js=null;
									result.add(Fileoperations.takeHighlightScreenShot(DriverScript.driver, element, parameters.get(0)));}			
									break;
							
								} catch (Exception e) {
									//e.printStackTrace();
									Log.error(e);
									Log.error("Unable to click : "+parameters.get(0));
									//js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "");
									element=null;
									result.add("Fail");
									result.add(e.toString());
									result.add(Fileoperations.takeScreenShot(DriverScript.driver, parameters.get(0)));
									break;
								}
							}
					else if(i==(DriverScript.testobjects.size()-1))
							{
								Log.error("Unable to find the element in All Objects: "+parameters.get(0));
								result.add("Fail");
								result.add("Element not found in All Objects");
								result.add(Fileoperations.takeScreenShot(DriverScript.driver, parameters.get(0)));
							}
					
				}
					
			} 
			else{
				Log.error("Unable to click on button: "+parameters.get(0));
				Log.error("Browser not found");
				result.add("Fail");
				result.add("Browser Not Found");
			}

			return result;
		}	

			
//---------------------------------
			
			public ArrayList<String> jsClick (ArrayList<String> parameters)
		{
			Log.info("function: click");
			WebElement element=null;
			int i=0;
			if (!(DriverScript.driver==null)) 
			{
				for (i = 0; i < DriverScript.testobjects.size(); i++) 
				{
					if(parameters.get(0).equalsIgnoreCase(DriverScript.testobjects.get(i).getObjectName()))
							{
								try {
									element = CommonWebOps.returnWebElement(i);
									//JavascriptExecutor js = (JavascriptExecutor) DriverScript.driver;
									//js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "color: red; border: 2px solid red;");	
									
									int waitcount;
									for (waitcount = 0; waitcount < maxWaitElement; waitcount++) 
									{
										try {
											//element.click();
											JavascriptExecutor js = (JavascriptExecutor) DriverScript.driver;
											js.executeScript("arguments[0].click();", element);
											js=null;
											//js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "");
											Log.info("Clicked using JS on :"+parameters.get(0));
											result.add("Pass");
											//result.add(" ");
											//result.add(Fileoperations.takeHighlightScreenShot(DriverScript.driver,element, parameters.get(0)));
											//js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "");
											//js=null;
											element=null;
											break;
											} 
										catch (Exception e) {
												try {
													Thread.sleep(1000);
													if ((e.toString().contains("unknown error:"))||
														(e.toString().contains("is not clickable at point"))||
														(e.toString().contains("Other element would receive the click:"))) 
													{
														JavascriptExecutor jse = (JavascriptExecutor)DriverScript.driver;
														jse.executeScript("scroll(0, -30);");
														jse=null;
													} 
													
													
													} catch (InterruptedException e1)
													{
													}
												}
									}	
										
									if (waitcount==(maxWaitElement)) {
									Log.error("Unable to click using JS: "+parameters.get(0));
									
									result.add("Fail");
									result.add("Element is not clickable");
									//js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "");
									//js=null;
									result.add(Fileoperations.takeHighlightScreenShot(DriverScript.driver, element, parameters.get(0)));}			
									break;
							
								} catch (Exception e) {
									//e.printStackTrace();
									Log.error(e);
									Log.error("Unable to click using JS: "+parameters.get(0));
									//js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "");
									element=null;
									result.add("Fail");
									result.add(e.toString());
									result.add(Fileoperations.takeScreenShot(DriverScript.driver, parameters.get(0)));
									break;
								}
							}
					else if(i==(DriverScript.testobjects.size()-1))
							{
								Log.error("Unable to find the element in All Objects: "+parameters.get(0));
								result.add("Fail");
								result.add("Element not found in All Objects");
								result.add(Fileoperations.takeScreenShot(DriverScript.driver, parameters.get(0)));
							}
					
				}
					
			} 
			else{
				Log.error("Unable to click on button using JS: "+parameters.get(0));
				Log.error("Browser not found");
				result.add("Fail");
				result.add("Browser Not Found");
			}

			return result;
		}	
//---------------------------------
			
			
			public ArrayList<String> clickChild (ArrayList<String> parameters)
		{
			Log.info("function: clickChild");
			List<WebElement> elements=null;
			WebElement element=null;
			int i=0;
			if (!(DriverScript.driver==null)) 
			{
				for (i = 0; i < DriverScript.testobjects.size(); i++) 
				{
					if(parameters.get(0).equalsIgnoreCase(DriverScript.testobjects.get(i).getObjectName()))
							{
								try {
									elements = CommonWebOps.returnWebElements(i);
									String param = parameters.get(1);
									String[] values = param.split("\\.");
									element = elements.get(Integer.valueOf(values[0]));	
									Log.info(Integer.valueOf(values[0]));	
									
									int waitcount;
									for (waitcount = 0; waitcount < maxWaitElement; waitcount++) 
									{
										try {
											element.click();
											//js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "");
											Log.info("Clicked on :"+parameters.get(0));
											result.add("Pass");
											//result.add("");
											//result.add(Fileoperations.takeHighlightScreenShot(DriverScript.driver,element, parameters.get(0)));
											
											element=null;
											elements=null;
											param=null;
											values=null;
											break;
											} 
										catch (Exception e) {
												try {
													Thread.sleep(1000);
													if ((e.toString().contains("unknown error:"))||
														(e.toString().contains("is not clickable at point"))||
														(e.toString().contains("Other element would receive the click:"))) 
													{
														JavascriptExecutor jse = (JavascriptExecutor)DriverScript.driver;
														jse.executeScript("scroll(0, -30);");
														jse=null;
													} 
													
													
													} catch (InterruptedException e1)
													{
													}
												}
									}	
										
									if (waitcount==(maxWaitElement)) {
									Log.error("Unable to click: "+parameters.get(0));
									
									result.add("Fail");
									result.add("Element is not clickable");
									element=null;
									elements=null;
									param=null;
									values=null;
									result.add(Fileoperations.takeHighlightScreenShot(DriverScript.driver, element, parameters.get(0)));}			
									break;
							
								} catch (Exception e) {
									//e.printStackTrace();
									Log.error(e);
									Log.error("Unable to click : "+parameters.get(0));
									//js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "");
									element=null;
									elements=null;
									result.add("Fail");
									result.add(e.toString());
									result.add(Fileoperations.takeScreenShot(DriverScript.driver, parameters.get(0)));
									break;
								}
							}
					else if(i==(DriverScript.testobjects.size()-1))
							{
								Log.error("Unable to find the element in All Objects: "+parameters.get(0));
								result.add("Fail");
								result.add("Element not found in All Objects");
								result.add(Fileoperations.takeScreenShot(DriverScript.driver, parameters.get(0)));
							}
					
				}
					
			} 
			else{
				Log.error("Unable to click on button: "+parameters.get(0));
				Log.error("Browser not found");
				result.add("Fail");
				result.add("Browser Not Found");
			}

			return result;
		}	
	
	
//---------------------------------
		
			public ArrayList<String> wait (ArrayList<String> parameters)
			{
				Log.info("function: wait");
				String param = parameters.get(1);
				String[] values = param.split("\\.");
				if (!(DriverScript.driver==null)) 
				{
					try {
						Thread.sleep(Integer.valueOf(values[0]));
						Log.info(Integer.valueOf(values[0]));
					} catch (Exception e) {
						
					}
					Log.info("Waited for:"+parameters.get(1));
					result.add("Pass");
					//result.add(" ");
					//result.add(" ");
										
				} 
				else{
					Log.error("Unable to wait for: "+values[1]);
					Log.error("Browser not found");
					result.add("Fail");
					result.add("Browser Not Found");
				}

				return result;
			}	

//---------------------------------
			
			public static void thiswait (int number)
			{
				try {
						Thread.sleep(number);
					} catch (Exception e) {
						
					}
					Log.info("Waited for: "+number);
			}
			
//---------------------------------
			
			public ArrayList<String> selectByIndex (ArrayList<String> parameters)
			{
				Log.info("function: selectByIndex");
				WebElement element=null;
				int i=0;
				String param = parameters.get(1);
				String[] values = param.split("\\.");
				if (!(DriverScript.driver==null)) 
				{
					for (i = 0; i < DriverScript.testobjects.size(); i++) 
					{
						if(parameters.get(0).equalsIgnoreCase(DriverScript.testobjects.get(i).getObjectName()))
								{
									try {
										element = CommonWebOps.returnWebElement(i);
										//JavascriptExecutor js = (JavascriptExecutor) DriverScript.driver;
										//js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "color: red; border: 2px solid red;");	
										
										int waitcount;
										for (waitcount = 0; waitcount < maxWaitElement; waitcount++) 
										{
											try {
												Select sel = new Select(element);
												sel.selectByIndex(Integer.valueOf(values[0]));
												//js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "");
												Log.info("Selected dropdown value: "+parameters.get(1)+"in :"+parameters.get(0));
												result.add("Pass");
												//result.add("");
												//result.add(Fileoperations.takeScreenShot(DriverScript.driver, parameters.get(1)));
												//js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "");
												//js=null;
												element=null;
												break;
												} 
											catch (Exception e) {
													try {
														Thread.sleep(1000);
														} catch (InterruptedException e1)
														{
														}
													}
										}	
											
										if (waitcount==(maxWaitElement)) {
										Log.error("Unable to select: "+parameters.get(0));
										result.add("Fail");
										result.add("Element is not selectable");
										//js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "");
										//js=null;
										result.add(Fileoperations.takeHighlightScreenShot(DriverScript.driver, element, parameters.get(0)));}			
										break;
										
										
									} catch (Exception e) {
										//e.printStackTrace();
										Log.error(e);
										Log.error("Unable to select dropdown value: "+parameters.get(1)+"in :"+parameters.get(0));
										//js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "");
										element=null;
										result.add("Fail");
										result.add(e.toString());
										result.add(Fileoperations.takeScreenShot(DriverScript.driver, parameters.get(0)));
										break;
									}
								}
						else if(i==(DriverScript.testobjects.size()-1))
								{
									Log.error("Unable to find the element in All Objects: "+parameters.get(0));
									result.add("Fail");
									result.add("Element not found in All Objects");
									result.add(Fileoperations.takeScreenShot(DriverScript.driver, parameters.get(0)));
								}
						
					}
						
				} 
				else{
					Log.error("Unable to select dropdown value in: "+parameters.get(0));
					Log.error("Browser not found");
					result.add("Fail");
					result.add("Browser Not Found");
				}

				return result;
			}
	
//---------------------------------	
			public ArrayList<String> selectByText (ArrayList<String> parameters)
			{
				Log.info("function: selectByText");
				WebElement element=null;
				int i=0;
				if (!(DriverScript.driver==null)) 
				{
					for (i = 0; i < DriverScript.testobjects.size(); i++) 
					{
						if(parameters.get(0).equalsIgnoreCase(DriverScript.testobjects.get(i).getObjectName()))
								{
									try {
										element = CommonWebOps.returnWebElement(i);
										//JavascriptExecutor js = (JavascriptExecutor) DriverScript.driver;
										//js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "color: red; border: 2px solid red;");	
										
										int waitcount;
										for (waitcount = 0; waitcount < maxWaitElement; waitcount++) 
										{
											try {
												Select sel = new Select(element);
												sel.selectByVisibleText(parameters.get(1));
												//js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "");
												Log.info("Selected dropdown value: "+parameters.get(1)+" in: "+parameters.get(0));
												result.add("Pass");
												//result.add("");
												//result.add(Fileoperations.takeHighlightScreenShot(DriverScript.driver,element, parameters.get(0)));
												
												//js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "");
												//js=null;
												element=null;
												break;												
												} 
											catch (Exception e) {
													try {
														Thread.sleep(1000);
														} catch (InterruptedException e1)
														{
														}
													}
										}	
											
										if (waitcount==(maxWaitElement)) {
										Log.error("Unable to select: "+parameters.get(0));
										result.add("Fail");
										result.add("Element is not selectable");
										//js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "");
										//js=null;
										result.add(Fileoperations.takeHighlightScreenShot(DriverScript.driver, element, parameters.get(0)));}			
										break;
										
										
									} catch (Exception e) {
										//e.printStackTrace();
										Log.error(e);
										Log.error("Unable to select dropdown value: "+parameters.get(1)+"in :"+parameters.get(0));
										//js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "");
										element=null;
										result.add("Fail");
										result.add(e.toString());
										result.add(Fileoperations.takeScreenShot(DriverScript.driver, parameters.get(0)));
										break;
									}
								}
						else if(i==(DriverScript.testobjects.size()-1))
								{
									Log.error("Unable to find the element in All Objects: "+parameters.get(0));
									result.add("Fail");
									result.add("Element not found in All Objects");
									result.add(Fileoperations.takeScreenShot(DriverScript.driver, parameters.get(0)));
								}
						
					}
						
				} 
				else{
					Log.error("Unable to select dropdown value in: "+parameters.get(0));
					Log.error("Browser not found");
					result.add("Fail");
					result.add("Browser Not Found");
				}

				return result;
			}
	
//---------------------------------		
		 
			public ArrayList<String> selectByValue (ArrayList<String> parameters)
			{
				Log.info("function: selectByValue");
				WebElement element=null;
				int i=0;
				if (!(DriverScript.driver==null)) 
				{
					for (i = 0; i < DriverScript.testobjects.size(); i++) 
					{
						if(parameters.get(0).equalsIgnoreCase(DriverScript.testobjects.get(i).getObjectName()))
								{
									try {
										element = CommonWebOps.returnWebElement(i);
										//JavascriptExecutor js = (JavascriptExecutor) DriverScript.driver;
										//js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "color: red; border: 2px solid red;");	
										
										int waitcount;
										for (waitcount = 0; waitcount < maxWaitElement; waitcount++) 
										{
											try {
												Select sel = new Select(element);
												sel.selectByValue(parameters.get(1));
												//js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "");
												Log.info("Selected dropdown value: "+parameters.get(1)+" in: "+parameters.get(0));
												result.add("Pass");
												//result.add("");
												//result.add(Fileoperations.takeHighlightScreenShot(DriverScript.driver,element, parameters.get(0)));
												//js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "");
												//js=null;
												element=null;
												break;												
												} 
											catch (Exception e) {
													try {
														Thread.sleep(1000);
														} catch (InterruptedException e1)
														{
														}
													}
										}	
											
										if (waitcount==(maxWaitElement)) {
										Log.error("Unable to select: "+parameters.get(0));
										result.add("Fail");
										result.add("Element is not selectable");
										//js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "");
										//js=null;
										result.add(Fileoperations.takeHighlightScreenShot(DriverScript.driver, element, parameters.get(0)));}			
										break;
										
										
									} catch (Exception e) {
										//e.printStackTrace();
										Log.info(e);
										Log.error("Unable to select dropdown value: "+parameters.get(1)+"in :"+parameters.get(0));
										//js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "");
										element=null;
										result.add("Fail");
										result.add(e.toString());
										result.add(Fileoperations.takeScreenShot(DriverScript.driver, parameters.get(0)));
										break;
									}
								}
						else if(i==(DriverScript.testobjects.size()-1))
								{
									Log.error("Unable to find the element in All Objects: "+parameters.get(0));
									result.add("Fail");
									result.add("Element not found in All Objects");
									result.add(Fileoperations.takeScreenShot(DriverScript.driver, parameters.get(0)));
								}
						
					}
						
				} 
				else{
					Log.error("Unable to select dropdown value in: "+parameters.get(0));
					Log.error("Browser not found");
					result.add("Fail");
					result.add("Browser Not Found");
				}

				return result;
			}

			
//---------------------------------		
			 
			public ArrayList<String> verfiyAllitemsInDropdown (ArrayList<String> parameters)
			{
				Log.info("function: verfiyAllitemsInDropdown");
				WebElement element=null;
				int i=0;
				if (!(DriverScript.driver==null)) 
				{
					for (i = 0; i < DriverScript.testobjects.size(); i++) 
					{
						if(parameters.get(0).equalsIgnoreCase(DriverScript.testobjects.get(i).getObjectName()))
								{
									try {
										element = CommonWebOps.returnWebElement(i);
										//JavascriptExecutor js = (JavascriptExecutor) DriverScript.driver;
										//js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "color: red; border: 2px solid red;");	
										
										List<String> expected = new ArrayList<String>();
										String inputdata = parameters.get(1).trim();
										String[] parts = inputdata.split(";");
										for(String part:parts){  
										     expected.add(part);  
										   }  
										Collections.sort(expected);
										
										int waitcount;
										for (waitcount = 0; waitcount < maxWaitElement; waitcount++) 
										{
											try {
												Select sel = new Select(element);
												List<String> actual = new ArrayList<String>();
												
												for (int j = 0; j < sel.getOptions().size(); j++) 
												{
												
													actual.add(sel.getOptions().get(j).getText().trim());	
												}
												Collections.sort(actual);
												if (expected.equals(actual)) 
												{
													Log.info("Values in dropdown : "+parameters.get(0)+" are matching with exepected");
													//js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "");
													//js=null;
													result.add("Pass");
													//result.add("");
													//result.add(Fileoperations.takeHighlightScreenShot(DriverScript.driver,element, parameters.get(0)));
												} 
												else 
												{
													Log.info("Values in dropdown : "+parameters.get(0)+" are not matching with exepected");
													result.add("Fail");
													//js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "");
													//js=null;
													result.add(Fileoperations.takeHighlightScreenShot(DriverScript.driver, element, parameters.get(0)));
												}
												//js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "");
												element=null;
												break;												
												} 
											catch (Exception e) {
													try {
														Thread.sleep(1000);
														} catch (InterruptedException e1)
														{
														}
													}
										}	
											
										if (waitcount==(maxWaitElement)) {
										Log.error("Unable to select: "+parameters.get(0));
										result.add("Fail");
										result.add("Element is not selectable");
										//js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "");
										//js=null;
										result.add(Fileoperations.takeHighlightScreenShot(DriverScript.driver, element, parameters.get(0)));}			
										break;
										
										
									} catch (Exception e) {
										//e.printStackTrace();
										Log.info(e);
										Log.error("Unable to select dropdown value: "+parameters.get(1)+"in :"+parameters.get(0));
										element=null;
										result.add("Fail");
										result.add(e.toString());
										result.add(Fileoperations.takeScreenShot(DriverScript.driver, parameters.get(0)));
										break;
									}
								}
						else if(i==(DriverScript.testobjects.size()-1))
								{
									Log.error("Unable to find the element in All Objects: "+parameters.get(0));
									result.add("Fail");
									result.add("Element not found in All Objects");
									result.add(Fileoperations.takeScreenShot(DriverScript.driver, parameters.get(0)));
								}
						
					}
						
				} 
				else{
					Log.error("Unable to select dropdown value in: "+parameters.get(0));
					Log.error("Browser not found");
					result.add("Fail");
					result.add("Browser Not Found");
				}

				return result;
			}			
			

//---------------------------------
			
			public ArrayList<String> isChecked (ArrayList<String> parameters)
			{
				Log.info("function: isChecked");
				WebElement element=null;
				int i=0;
				if (!(DriverScript.driver==null)) 
				{
					for (i = 0; i < DriverScript.testobjects.size(); i++) 
					{
						if(parameters.get(0).equalsIgnoreCase(DriverScript.testobjects.get(i).getObjectName()))
								{
									try {
										element = CommonWebOps.returnWebElement(i);
										//JavascriptExecutor js = (JavascriptExecutor) DriverScript.driver;
										//js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "color: red; border: 2px solid red;");	
																		
										int waitcount;
										for (waitcount = 0; waitcount < maxWaitElement; waitcount++) 
										{
											try {
												if (element.getAttribute("checked").equalsIgnoreCase("true")) 
												{
													Log.info("Element "+parameters.get(0)+" is checked: ");
													result.add("Pass");
													//result.add("");
													//result.add(Fileoperations.takeHighlightScreenShot(DriverScript.driver,element, parameters.get(0)));
													
													//js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "");
													//js=null;
													element=null;
													break;
												} else 
												{
													Log.info("Element "+parameters.get(0)+" is not checked: ");
													result.add("Fail");
													result.add("Element is not checked");
													//js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "");
													element=null;
													break;
												}												
												
												} 
											catch (Exception e) {
													try {
														Thread.sleep(1000);
														} catch (InterruptedException e1)
														{
														}
													}
										}	
											
										if (waitcount==(maxWaitElement)) {
										Log.error("Unable to getAttribute from element: "+parameters.get(0));
										result.add("Fail");
										result.add("Element is not accessible");
										//js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "");
										//js=null;
										result.add(Fileoperations.takeHighlightScreenShot(DriverScript.driver, element, parameters.get(0)));}			
										break;
										
									} catch (Exception e) {
										//e.printStackTrace();
										Log.error(e);
										Log.error("Unable to enter value in: "+parameters.get(0));
										element=null;
										result.add("Fail");
										result.add(e.toString());
										result.add(Fileoperations.takeScreenShot(DriverScript.driver, parameters.get(0)));
										break;
									}
								}
						else if(i==(DriverScript.testobjects.size()-1))
								{
									Log.error("Unable to find the element in All Objects: "+parameters.get(0));
									result.add("Fail");
									result.add("Element not found in All Objects");
									result.add(Fileoperations.takeScreenShot(DriverScript.driver, parameters.get(0)));
								}
						
					}
						
				} 
				else{
					Log.error("Unable to check ischecked for: "+parameters.get(0));
					Log.error("Browser not found");
					result.add("Fail");
					result.add("Browser Not Found");
				}

				return result;
			}

//---------------------------------
			
			public ArrayList<String> isSelectedinSelect (ArrayList<String> parameters)
			{
				Log.info("function: isSelectedinSelect");
				WebElement element=null;
				int i=0;
				if (!(DriverScript.driver==null)) 
				{
					for (i = 0; i < DriverScript.testobjects.size(); i++) 
					{
						if(parameters.get(0).equalsIgnoreCase(DriverScript.testobjects.get(i).getObjectName()))
								{
									try {
										element = CommonWebOps.returnWebElement(i);
																		
										int waitcount;
										for (waitcount = 0; waitcount < maxWaitElement; waitcount++) 
										{
											try {
												Select sel = new Select(element);
												String defaultSelected = sel.getFirstSelectedOption().getText();
												String param = parameters.get(1);
												String[] values = param.split("\\.");
												Log.info("defaultSelected:"+defaultSelected);
												Log.info("Value to be checked"+values[0]);
												if (defaultSelected.trim().equals(values[0].trim())) 
												{
													Log.info(parameters.get(1)+" is selected in dropdown: "+parameters.get(0));
													result.add("Pass");
													//result.add("");
													//result.add(Fileoperations.takeHighlightScreenShot(DriverScript.driver,element, parameters.get(0)));
													element=null;
													sel=null;
													defaultSelected=null;
													break;
												} else 
												{
													Log.info(parameters.get(1)+" is not selected in dropdown: "+parameters.get(0));
													result.add("Fail");
													result.add("Element is not selected in dropdown");
													result.add(Fileoperations.takeHighlightScreenShot(DriverScript.driver, element, parameters.get(0)));
													element=null;
													sel=null;
													defaultSelected=null;
													break;
												}
												
												
												} 
											catch (Exception e) {
													try {
														Thread.sleep(1000);
														} catch (InterruptedException e1)
														{
														}
													}
										}	
											
										if (waitcount==(maxWaitElement)) {
										Log.error("Unable to getAttribute from element: "+parameters.get(0));
										result.add("Fail");
										result.add("Element is not accessible");
										result.add(Fileoperations.takeHighlightScreenShot(DriverScript.driver, element, parameters.get(0)));}			
										break;
										
									} catch (Exception e) {
										//e.printStackTrace();
										Log.error(e);
										Log.error("Unable to enter value in: "+parameters.get(0));
										element=null;
										result.add("Fail");
										result.add(e.toString());
										result.add(Fileoperations.takeScreenShot(DriverScript.driver, parameters.get(0)));
										break;
									}
								}
						else if(i==(DriverScript.testobjects.size()-1))
								{
									Log.error("Unable to find the element in All Objects: "+parameters.get(0));
									result.add("Fail");
									result.add("Element not found in All Objects");
									result.add(Fileoperations.takeScreenShot(DriverScript.driver, parameters.get(0)));
								}
						
					}
						
				} 
				else{
					Log.error("Unable to verify selected value in dropdown: "+parameters.get(0));
					Log.error("Browser not found");
					result.add("Fail");
					result.add("Browser Not Found");
				}

				return result;
			}
			
//---------------------------------	
			
			public ArrayList<String> verifyElementPresent (ArrayList<String> parameters)
			{
				Log.info("function: verifyElementPresent");
				WebElement element=null;
				int i=0;
				if (!(DriverScript.driver==null)) 
				{
					for (i = 0; i < DriverScript.testobjects.size(); i++) 
					{
						if(parameters.get(0).equalsIgnoreCase(DriverScript.testobjects.get(i).getObjectName()))
								{
									try {
										element = CommonWebOps.returnWebElement(i);
										if (element!=null) 
										{
											Log.info("Element is displyed on the screen: "+parameters.get(0));
											result.add("Pass");
											//result.add("");
											//result.add(Fileoperations.takeHighlightScreenShot(DriverScript.driver,element, parameters.get(0)));
											element=null;
											break;
										}				
										
										
									} catch (Exception e) {
										//e.printStackTrace();
										Log.error(e);
										Log.error("Element is not displyed on the screen: "+parameters.get(0));
										element=null;
										result.add("Fail");
										result.add(e.toString());
										result.add(Fileoperations.takeHighlightScreenShot(DriverScript.driver, element, parameters.get(0)));
										break;
									}
								}
						else if(i==(DriverScript.testobjects.size()-1))
								{
									Log.error("Unable to find the element in All Objects: "+parameters.get(0));
									result.add("Fail");
									result.add("Element not found in All Objects");
									result.add(Fileoperations.takeScreenShot(DriverScript.driver, parameters.get(0)));
								}
					}
						
				} 
				else{
					Log.error("Unable to verify element: "+parameters.get(0));
					Log.error("Browser not found");
					result.add("Fail");
					result.add("Browser Not Found");
				}

				return result;	
				
			}	
//---------------------------------	
			
			public ArrayList<String> verifyText (ArrayList<String> parameters)
			{
				Log.info("function: verifyText");
				WebElement element=null;
				int i=0;
				if (!(DriverScript.driver==null)) 
				{
					for (i = 0; i < DriverScript.testobjects.size(); i++) 
					{
						if(parameters.get(0).equalsIgnoreCase(DriverScript.testobjects.get(i).getObjectName()))
								{
									try {
										element = CommonWebOps.returnWebElement(i);
										//JavascriptExecutor js = (JavascriptExecutor) DriverScript.driver;
										//js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "color: red; border: 2px solid red;");	
										
										if (element!=null) 
										{
											if (element.getText().trim().equalsIgnoreCase(parameters.get(1).trim())) {
												Log.info("Text value is matching for: "+parameters.get(0));
												result.add("Pass");
												//result.add("");
												//result.add(Fileoperations.takeHighlightScreenShot(DriverScript.driver,element, parameters.get(0)));
												//js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "");
												//js=null;
												//js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "");
												element=null;
												break;
											} else {
												Log.error("Text value is not matching for: "+parameters.get(0));
												result.add("Fail");
												result.add("Text value is not matching");
												//js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "");
												//js=null;
												//js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "");
												result.add(Fileoperations.takeHighlightScreenShot(DriverScript.driver, element, parameters.get(0)));
												element=null;
												break;
											}
											
											
										}				
										
										
									} catch (Exception e) {
										//e.printStackTrace();
										Log.error(e);
										Log.error("Element is not displyed on the screen: "+parameters.get(0));
										
										element=null;
										result.add("Fail");
										result.add(e.toString());
										//js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "");
										result.add(Fileoperations.takeScreenShot(DriverScript.driver, parameters.get(0)));
										break;
									}
								}
						else if(i==(DriverScript.testobjects.size()-1))
								{
									Log.error("Unable to find the element in All Objects: "+parameters.get(0));
									result.add("Fail");
									result.add("Element not found in All Objects");
									result.add(Fileoperations.takeScreenShot(DriverScript.driver, parameters.get(0)));
								}
					}
						
				} 
				else{
					Log.error("Unable to verify text of: "+parameters.get(0));
					Log.error("Browser not found");
					result.add("Fail");
					result.add("Browser Not Found");
				}

				return result;	
				
			}	

			
//---------------------------------	
			
			public ArrayList<String> verifyValue (ArrayList<String> parameters)
			{
				Log.info("function: verifyValue");
				WebElement element=null;
				int i=0;
				if (!(DriverScript.driver==null)) 
				{
					for (i = 0; i < DriverScript.testobjects.size(); i++) 
					{
						if(parameters.get(0).equalsIgnoreCase(DriverScript.testobjects.get(i).getObjectName()))
								{
									try {
										element = CommonWebOps.returnWebElement(i);
										//JavascriptExecutor js = (JavascriptExecutor) DriverScript.driver;
										//js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "color: red; border: 2px solid red;");	
										
										if (element!=null) 
										{
											if (element.getAttribute("value").trim().equalsIgnoreCase(parameters.get(1).trim())) {
												Log.info("Value is matching for: "+parameters.get(0));
												result.add("Pass");
												//result.add("");
												//result.add(Fileoperations.takeHighlightScreenShot(DriverScript.driver,element, parameters.get(0)));
												//js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "");
												//js=null;
												element=null;
												break;
											} else {
												Log.error("Value is not matching for: "+parameters.get(0));
												result.add("Fail");
												result.add("Value is not matching");
												//js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "");
												//js=null;
												//js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "");
												result.add(Fileoperations.takeHighlightScreenShot(DriverScript.driver, element, parameters.get(0)));
												element=null;
												break;
											}
											
											
										}				
										
										
									} catch (Exception e) {
										//e.printStackTrace();
										Log.error(e);
										Log.error("Element is not displyed on the screen: "+parameters.get(0));
										
										element=null;
										result.add("Fail");
										result.add(e.toString());
										//js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "");
										result.add(Fileoperations.takeScreenShot(DriverScript.driver, parameters.get(0)));
										break;
									}
								}
						else if(i==(DriverScript.testobjects.size()-1))
								{
									Log.error("Unable to find the element in All Objects: "+parameters.get(0));
									result.add("Fail");
									result.add("Element not found in All Objects");
									result.add(Fileoperations.takeScreenShot(DriverScript.driver, parameters.get(0)));
								}
					}
						
				} 
				else{
					Log.error("Unable to verify value in: "+parameters.get(0));
					Log.error("Browser not found");
					result.add("Fail");
					result.add("Browser Not Found");
				}

				return result;	
				
			}
			
			
//---------------------------------	
			
			public ArrayList<String> switchToSecondWindow (ArrayList<String> parameters)
			{
				Log.info("function: switchToSecondWindow");
				int waitcount = 0;
				if (!(DriverScript.driver==null)) 
				{
					CommonWebOps.thiswait(3000);
					for (waitcount = 0; waitcount < maxWaitElement; waitcount++) 
					{
						try {
							Set<String> handles = DriverScript.driver.getWindowHandles();
							 Log.info("No of windows: "+handles.size());
							 
							 String firstWinHandle = DriverScript.driver.getWindowHandle(); 
							 handles.remove(firstWinHandle);
							 
							 String winHandle=handles.iterator().next();
							 
							 	if (winHandle!=firstWinHandle)
							 	{
							 		String secondWinHandle=winHandle; 
							 		DriverScript.driver.switchTo().window(secondWinHandle);
							 		//Log.info("Switched to window: "+DriverScript.driver.switchTo().window(secondWinHandle).getTitle());
							 		Log.info("Switched to window: "+DriverScript.driver.getTitle());
							 		result.add("Pass");
							 		//result.add("");
									//result.add(Fileoperations.takeScreenShot(DriverScript.driver, parameters.get(0)));
							 		break;
							 	}
							} catch (Exception e) {
								try {
									Thread.sleep(1000);
									} catch (InterruptedException e1)
									{
									}
								}	
						}
						if (waitcount==(maxWaitElement))
						{
							Log.error("Unable to switch to window: "+parameters.get(0));
							Log.error("Window does not exist");
							result.add("Fail");
							result.add(parameters.get(0)+" :Window does not exist ");
							result.add(Fileoperations.takeScreenShot(DriverScript.driver, parameters.get(0)));
						}
					
					}
				else{
					Log.error("Unable to switch to second window");
					Log.error("Browser not found");
					result.add("Fail");
					result.add("Browser Not Found");
					}

				return result;	
				
		}
	
	
//---------------------------------	
			
			public ArrayList<String> switchToWindowName (ArrayList<String> parameters)
			{
				Log.info("function: switchToWindowName");
				int waitcount = 0;
				if (!(DriverScript.driver==null)) 
				{
					Log.info("switchToWindowName");
					CommonWebOps.thiswait(3000);
					OuterLoop:
					for (waitcount = 0; waitcount < maxWaitElement; waitcount++) 
					{
						try {
							Set<String> handles= DriverScript.driver.getWindowHandles();
							for(String winHandle : handles){
								   if (DriverScript.driver.switchTo().window(winHandle).getTitle().equals(parameters.get(0))) {
									   DriverScript.driver.switchTo().window(winHandle);
									   Log.info("Switched to window: "+parameters.get(0));
									   result.add("Pass");
									   //result.add("");
									   //result.add(Fileoperations.takeScreenShot(DriverScript.driver, parameters.get(0)));
								     break OuterLoop;
								   } 								   
								}
							} catch (Exception e) {
								try {
									Thread.sleep(1000);
									} catch (InterruptedException e1)
									{
									}
								}	
						}
						if (waitcount==(maxWaitElement))
						{
							Log.error("Unable to switch to window: "+parameters.get(0));
							Log.error("Window does not exist");
							result.add("Fail");
							result.add(parameters.get(0)+" :Window does not exist");
							result.add(Fileoperations.takeScreenShot(DriverScript.driver, parameters.get(0)));
						}
					
					}
				else{
					Log.error("Unable to switch to window");
					Log.error("Browser not found");
					result.add("Fail");
					result.add("Browser Not Found");
					}				

				return result;	
				
			}	
	
//---------------------------------	
			
			public ArrayList<String> switchToDefaultContent (ArrayList<String> parameters)
			{
				Log.info("function: switchToDefaultContent");
				if (!(DriverScript.driver==null)) 
				{	
					CommonWebOps.thiswait(2000);
					try {
						DriverScript.driver.switchTo().defaultContent();
						 Log.info("Switched to default content: ");
						 result.add("Pass");
						 //result.add("");
						 //result.add(Fileoperations.takeScreenShot(DriverScript.driver, parameters.get(0)));
					} catch (Exception e) {
						Log.error(e);
						Log.error("Unable to switch to default content");
						Log.error("Frame does not exist");
						result.add("Fail");
						result.add("Unable to switch to default content");
						result.add(Fileoperations.takeScreenShot(DriverScript.driver, parameters.get(0)));
					}
					
				} 
				else
				{
					Log.error("Unable to switch to default content");
					Log.error("Browser not found");
					result.add("Fail");
					result.add("Browser Not Found");
				}

				return result;	
				
			}		

//---------------------------------	
			
			public ArrayList<String> switchToFrame (ArrayList<String> parameters)
			{	Log.info("function: switchToFrame");
				int i = 0;
				WebElement element=null;
				if (!(DriverScript.driver==null)) 
				{
					CommonWebOps.thiswait(2000);
					for (i = 0; i < DriverScript.testobjects.size(); i++) 
					{
						if(parameters.get(0).equalsIgnoreCase(DriverScript.testobjects.get(i).getObjectName()))
								{
									try {
											element = CommonWebOps.returnWebElement(i);
											
											int waitcount;
											for (waitcount = 0; waitcount < maxWaitElement; waitcount++) 
											{
												try {
													DriverScript.driver.switchTo().frame(element);
													Log.info("Switched to frame: ");
													result.add("Pass");
													//result.add("");
													//result.add(Fileoperations.takeHighlightScreenShot(DriverScript.driver,element, parameters.get(0)));
													break;											
													} 
												catch (Exception e) {
														try {
															Thread.sleep(1000);
															} catch (InterruptedException e1)
															{
															}
														}
											}	
												
											if (waitcount==(maxWaitElement)) {
											Log.error("Unable to switch to frame: "+parameters.get(1));
											//Fileoperations.takeScreenShot(DriverScript.driver, parameters.get(1));
											result.add("Fail");
											result.add("Unable to switch to frame");
											result.add(Fileoperations.takeScreenShot(DriverScript.driver, parameters.get(0)));}			
											break;
											
											
										} catch (Exception e) 
									{Log.error("Frame is not displayed on screen");
									result.add("Fail");
									result.add(e.toString());	
									result.add(Fileoperations.takeScreenShot(DriverScript.driver, parameters.get(0)));}		
								}
					
						else if(i==(DriverScript.testobjects.size()-1))
							{
								Log.error("Unable to find the element in All Objects: "+parameters.get(0));
								result.add("Fail");
								result.add("Element not found in All Objects");
								result.add(Fileoperations.takeScreenShot(DriverScript.driver, parameters.get(0)));
							}
						}
					}
				else{
					Log.error("Unable to switch to frame: "+parameters.get(1));
					Log.error("Browser not found");
					result.add("Fail");
					result.add("Browser Not Found");
					}

				return result;
			
	}

//---------------------------------	
			
			public ArrayList<String> acceptAlert (ArrayList<String> parameters)
			{
				Log.info("function: acceptAlert");
				int waitcount = 0;
				if (!(DriverScript.driver==null)) 
				{
					Log.info("acceptAlert");
					
					for (waitcount = 0; waitcount < maxWaitElement; waitcount++) 
					{
						try {
							Alert myAlert = DriverScript.driver.switchTo().alert();
							myAlert.accept();
							Log.info("Accepted alert");
							result.add("Pass");
							//result.add("");
							//result.add(Fileoperations.takeScreenShot(DriverScript.driver, "alert"));
							break;
														
							} catch (Exception e) {
								try {
									Thread.sleep(1000);
									} catch (InterruptedException e1)
									{
									}
								}	
						}
						if (waitcount==(maxWaitElement))
						{
							Log.error("Unable to accept alert: ");
							result.add("Fail");
							result.add("Unable to accept alert");
							result.add(Fileoperations.takeScreenShot(DriverScript.driver, "alert"));
						}
					
					}
				else{
					Log.error("Unable to accept alert");
					Log.error("Browser not found");
					result.add("Fail");
					result.add("Browser Not Found");
					}				

				return result;	
				
			}

//---------------------------------	
			
			public ArrayList<String> rejectAlert(ArrayList<String> parameters)
			{
				Log.info("function: acceptAlert");
				int waitcount = 0;
				if (!(DriverScript.driver==null)) 
				{
					Log.info("acceptAlert");
					
					for (waitcount = 0; waitcount < maxWaitElement; waitcount++) 
					{
						try {
							Alert myAlert = DriverScript.driver.switchTo().alert();
							myAlert.dismiss();
							Log.info("Rejected alert");
							result.add("Pass");
							//result.add("");
							//result.add(Fileoperations.takeScreenShot(DriverScript.driver,"alert"));
							break;
														
							} catch (Exception e) {
								try {
									Thread.sleep(1000);
									} catch (InterruptedException e1)
									{
									}
								}	
						}
						if (waitcount==(maxWaitElement))
						{
							Log.error("Unable to reject alert: ");
							result.add("Fail");
							result.add("Unable to reject alert");
							result.add(Fileoperations.takeScreenShot(DriverScript.driver, "alert"));
						}
					
					}
				else{
					Log.error("Unable to reject alert");
					Log.error("Browser not found");
					result.add("Fail");
					result.add("Browser Not Found");
					}				

				return result;	
				
			}
			
//---------------------------------	
			
			public ArrayList<String> verifyTextinAlert (ArrayList<String> parameters)
			{
				Log.info("function: acceptAlert");
				int waitcount = 0;
				if (!(DriverScript.driver==null)) 
				{
					Log.info("acceptAlert");
					
					for (waitcount = 0; waitcount < maxWaitElement; waitcount++) 
					{
						try {
							Alert myAlert = DriverScript.driver.switchTo().alert();
							if (myAlert.getText().trim().equals(parameters.get(1))) 
							{
								Log.info("Alert Info is matching");
								result.add("Pass");
								//result.add("");
								//result.add(Fileoperations.takeScreenShot(DriverScript.driver, "alert"));
								break;
										
							} 
							else {
								Log.error("Alert Info is Not matching");
								result.add("Fail");
								result.add("Alert Info is Not matching");
								result.add(Fileoperations.takeScreenShot(DriverScript.driver, "alert"));
								break;
							}
							
												
							} catch (Exception e) {
								try {
									Thread.sleep(1000);
									} catch (InterruptedException e1)
									{
									}
								}	
						}
						if (waitcount==(maxWaitElement))
						{
							Log.error("Unable to find alert: ");
							result.add("Fail");
							result.add("Unable to find alert");
							result.add(Fileoperations.takeScreenShot(DriverScript.driver, "alert"));
						}
					
					}
				else{
					Log.error("Unable to find alert");
					Log.error("Browser not found");
					result.add("Fail");
					result.add("Browser Not Found");
					}				

				return result;	
				
			}
			
//-----------------------------------------------------
		
			public ArrayList<String> moveToElement (ArrayList<String> parameters)
	{
		Log.info("function: moveToElement");
		WebElement element=null;
		int i=0;
		if (!(DriverScript.driver==null)) 
		{
			for (i = 0; i < DriverScript.testobjects.size(); i++) 
			{
				if(parameters.get(0).equalsIgnoreCase(DriverScript.testobjects.get(i).getObjectName()))
						{
							try {
								element = CommonWebOps.returnWebElement(i);
																
								int waitcount;
								for (waitcount = 0; waitcount < maxWaitElement; waitcount++) 
								{
									try {
										new Actions(DriverScript.driver).moveToElement(element).build().perform();
										Log.info("Moved cursor to element: "+parameters.get(0));
										result.add("Pass");
										//result.add("");
										//result.add(Fileoperations.takeHighlightScreenShot(DriverScript.driver,element, parameters.get(0)));
										element=null;
										break;
										} 
									catch (Exception e) {
											try {
												Thread.sleep(1000);
												} catch (InterruptedException e1)
												{
												}
											}
								}	
									
								if (waitcount==(maxWaitElement)) {
								Log.error("Unable to move cursor to element: "+parameters.get(0));
								result.add("Fail");
								result.add("Element is not enabled");
								result.add(Fileoperations.takeHighlightScreenShot(DriverScript.driver, element, parameters.get(0)));}			
								break;
								
							} catch (Exception e) {
								//e.printStackTrace();
								Log.error(e);
								Log.error("Unable to move cursor to element: "+parameters.get(0));
								element=null;
								result.add("Fail");
								result.add(e.toString());
								result.add(Fileoperations.takeScreenShot(DriverScript.driver, parameters.get(0)));
								break;
							}
						}
				else if(i==(DriverScript.testobjects.size()-1))
						{
							Log.error("Unable to find the element in All Objects: "+parameters.get(0));
							result.add("Fail");
							result.add("Element not found in All Objects");
							result.add(Fileoperations.takeScreenShot(DriverScript.driver, parameters.get(0)));
						}
				
			}
				
		} 
		else{
			Log.error("Unable to move cursor to element: "+parameters.get(0));
			Log.error("Browser not found");
			result.add("Fail");
			result.add("Browser Not Found");
		}

		return result;
	}

//-----------------------------------------------------
			
			public ArrayList<String> rightClickOnElement (ArrayList<String> parameters)
	{
		Log.info("function: rightClickOnElement");
		WebElement element=null;
		int i=0;
		if (!(DriverScript.driver==null)) 
		{
			for (i = 0; i < DriverScript.testobjects.size(); i++) 
			{
				if(parameters.get(0).equalsIgnoreCase(DriverScript.testobjects.get(i).getObjectName()))
						{
							try {
								element = CommonWebOps.returnWebElement(i);
																
								int waitcount;
								for (waitcount = 0; waitcount < maxWaitElement; waitcount++) 
								{
									try {
										new Actions(DriverScript.driver).contextClick(element).build().perform();
										Log.info("Right clicked on element: "+parameters.get(0));
										result.add("Pass");
										//result.add("");
										//result.add(Fileoperations.takeHighlightScreenShot(DriverScript.driver,element, parameters.get(0)));
										element=null;
										break;
										} 
									catch (Exception e) {
											try {
												Thread.sleep(1000);
												} catch (InterruptedException e1)
												{
												}
											}
								}	
									
								if (waitcount==(maxWaitElement)) {
								Log.error("Unable to right click on element: "+parameters.get(0));
								result.add("Fail");
								result.add("Element is not enabled");
								result.add(Fileoperations.takeHighlightScreenShot(DriverScript.driver, element, parameters.get(0)));}			
								break;
								
							} catch (Exception e) {
								//e.printStackTrace();
								Log.error(e);
								Log.error("Unable to right click on element: "+parameters.get(0));
								element=null;
								result.add("Fail");
								result.add(e.toString());
								result.add(Fileoperations.takeScreenShot(DriverScript.driver, parameters.get(0)));
								break;
							}
						}
				else if(i==(DriverScript.testobjects.size()-1))
						{
							Log.error("Unable to find the element in All Objects: "+parameters.get(0));
							result.add("Fail");
							result.add("Element not found in All Objects");
							result.add(Fileoperations.takeScreenShot(DriverScript.driver, parameters.get(0)));
						}
				
			}
				
		} 
		else{
			Log.error("Unable to move cursor to element: "+parameters.get(0));
			Log.error("Browser not found");
			result.add("Fail");
			result.add("Browser Not Found");
		}

		return result;
	}

			
//-----------------------------------------------------
			
			public ArrayList<String> dragDrop (ArrayList<String> parameters)
	{
		Log.info("function: dragDrop");
		WebElement fromElement =null;
		WebElement toElement =null;
		String fromFound = "No";
		String toFound = "No";
		
		int i=0;
		if (!(DriverScript.driver==null)) 
		{
			for (i = 0; i < DriverScript.testobjects.size(); i++) 
			{
				if(parameters.get(0).equalsIgnoreCase(DriverScript.testobjects.get(i).getObjectName()))
						{
							try {
								fromElement = CommonWebOps.returnWebElement(i);
								fromFound = "Yes";
							} catch (Exception e) {
								//e.printStackTrace();
								Log.error(e);
								Log.error("Unable to find From element: "+parameters.get(0));
								fromElement=null;
								result.add("Fail");
								result.add(e.toString());
								result.add(Fileoperations.takeScreenShot(DriverScript.driver, parameters.get(0)));
								break;
							}
						}
								
				else if(parameters.get(1).equalsIgnoreCase(DriverScript.testobjects.get(i).getObjectName()))
				{
					try {
						toElement = CommonWebOps.returnWebElement(i);
						toFound = "Yes";
						
					} catch (Exception e) {
						//e.printStackTrace();
						Log.error(e);
						Log.error("Unable to find To element: "+parameters.get(1));
						toElement=null;
						result.add("Fail");
						result.add(e.toString());
						result.add(Fileoperations.takeScreenShot(DriverScript.driver, parameters.get(0)));
						break;
					}
				}	
				
				
																
			}		
			
			if (fromFound.equalsIgnoreCase("Yes")&&(toFound.equalsIgnoreCase("Yes"))) 
			{
				int waitcount;
				for (waitcount = 0; waitcount < maxWaitElement; waitcount++) 
				{
					try {
						new Actions(DriverScript.driver).dragAndDrop(fromElement, toElement).build().perform();
						Log.info("Dragged from source: "+parameters.get(0)+" to target: "+parameters.get(1));
						result.add("Pass");
						result.add("");
						result.add(Fileoperations.takeHighlightScreenShot(DriverScript.driver,fromElement, parameters.get(0)));
						fromElement=null;
						toElement=null;
						break;
						} 
					catch (Exception e) {
							try {
								Thread.sleep(1000);
								} catch (InterruptedException e1)
								{
								}
							}
				}	
					
				if (waitcount==(maxWaitElement)) {
				Log.error("Unable drag from source: "+parameters.get(0)+" to target: "+parameters.get(1));
				result.add("Fail");
				result.add("Element is not draggable");
				result.add(Fileoperations.takeHighlightScreenShot(DriverScript.driver, fromElement, parameters.get(0)));			
				}

			} 
			else if(i==(DriverScript.testobjects.size()-1))
				{
					Log.error("Unable to find Source and/or Target element(s) in All Objects: ");
					result.add("Fail");
					result.add("Element(s) not found in All Objects");
					result.add(Fileoperations.takeScreenShot(DriverScript.driver, parameters.get(0)));
				}	
				
		}
				
		
		else{
			Log.error("Unable to find From element: "+parameters.get(0));
			Log.error("Browser not found");
			result.add("Fail");
			result.add("Browser Not Found");
		}

		return result;
	}
//-----------------------------------------------------
			
			public ArrayList<String> dragDropWidget (ArrayList<String> parameters)
	{
		Log.info("function: dragDropWidget");
		WebElement fromElement =null;
		WebElement toElement =null;
		String fromFound = "No";
		String toFound = "No";
		
		int i=0;
		if (!(DriverScript.driver==null)) 
		{
			
			
			for (i = 0; i < DriverScript.testobjects.size(); i++) 
			{
				if(parameters.get(0).equalsIgnoreCase(DriverScript.testobjects.get(i).getObjectName()))
						{
							try {
								//DriverScript.driver.manage().window().fullscreen();
								fromElement = CommonWebOps.returnWebElement(i);
								//fromFound = "Yes";
								((JavascriptExecutor) DriverScript.driver).executeScript("scroll(0, -250);");
								new Actions(DriverScript.driver).clickAndHold(fromElement).moveByOffset(0, 200).release().build().perform();
								Thread.sleep(4000);
								//((JavascriptExecutor) DriverScript.driver).executeScript("scroll(0, -250);");
								//new Actions(DriverScript.driver).clickAndHold(fromElement).moveByOffset(0, 200).release().build().perform();
								result.add("Pass");
								result.add("");
								result.add(Fileoperations.takeHighlightScreenShot(DriverScript.driver,fromElement, parameters.get(0)));
								Log.error("Dragged and dropped widget: "+parameters.get(0));
								fromElement=null;
								break;
									} catch (Exception e) {
								//e.printStackTrace();
								Log.error(e);
								Log.error("Unable to find From element: "+parameters.get(0));
								fromElement=null;
								result.add("Fail");
								result.add(e.toString());
								result.add(Fileoperations.takeScreenShot(DriverScript.driver, parameters.get(0)));
								break;
							}
						}
			}				
				
				
			}
			
			
				
		
		else{
			Log.error("Unable to find From element: "+parameters.get(0));
			Log.error("Browser not found");
			result.add("Fail");
			result.add("Browser Not Found");
		}

		return result;
	}			
//-----------------------------------------------------
			
			public ArrayList<String> dragDropWidgetChild (ArrayList<String> parameters)
	{
		Log.info("function: dragDropWidget");
		List<WebElement> elements=null;
		WebElement element =null;
		
		
		int i=0;
		if (!(DriverScript.driver==null)) 
		{
			
			
			for (i = 0; i < DriverScript.testobjects.size(); i++) 
			{
				if(parameters.get(0).equalsIgnoreCase(DriverScript.testobjects.get(i).getObjectName()))
						{
							try {
								
								elements = CommonWebOps.returnWebElements(i);
								String param = parameters.get(1);
								String[] values = param.split("\\.");
								element = elements.get(Integer.valueOf(values[0]));	
								Log.info(Integer.valueOf(values[0]));	
																
								((JavascriptExecutor) DriverScript.driver).executeScript("scroll(0, -250);");
								new Actions(DriverScript.driver).clickAndHold(element).moveByOffset(0, 200).release().build().perform();
								Thread.sleep(4000);
								
								new Actions(DriverScript.driver).clickAndHold(element).moveByOffset(0, -200).release().build().perform();
								Thread.sleep(4000);
								result.add("Pass");
								result.add("");
								result.add(Fileoperations.takeHighlightScreenShot(DriverScript.driver,element, parameters.get(0)));
								Log.error("Dragged and dropped widget: "+parameters.get(0));
								element=null;
								elements=null;
								break;
									} catch (Exception e) {
								//e.printStackTrace();
								Log.error(e);
								Log.error("Unable to find From element: "+parameters.get(0));
								element=null;
								elements=null;
								result.add("Fail");
								result.add(e.toString());
								result.add(Fileoperations.takeScreenShot(DriverScript.driver, parameters.get(0)));
								break;
							}
						}
			}				
				
				
			}
			
			
				
		
		else{
			Log.error("Unable to find From element: "+parameters.get(0));
			Log.error("Browser not found");
			result.add("Fail");
			result.add("Browser Not Found");
		}

		return result;
	}			

			
//-----------------------------------------------------
			
			public ArrayList<String> doubleClickOnElement (ArrayList<String> parameters)
	{
		Log.info("function: doubleClickOnElement");
		WebElement element=null;
		int i=0;
		if (!(DriverScript.driver==null)) 
		{
			for (i = 0; i < DriverScript.testobjects.size(); i++) 
			{
				if(parameters.get(0).equalsIgnoreCase(DriverScript.testobjects.get(i).getObjectName()))
						{
							try {
								element = CommonWebOps.returnWebElement(i);
																
								int waitcount;
								for (waitcount = 0; waitcount < maxWaitElement; waitcount++) 
								{
									try {
										new Actions(DriverScript.driver).doubleClick(element).build().perform();
										Log.info("Double clicked on element: "+parameters.get(0));
										result.add("Pass");
										//result.add("");
										//result.add(Fileoperations.takeHighlightScreenShot(DriverScript.driver,element, parameters.get(0)));
										element=null;
										break;
										} 
									catch (Exception e) {
											try {
												Thread.sleep(1000);
												} catch (InterruptedException e1)
												{
												}
											}
								}	
									
								if (waitcount==(maxWaitElement)) {
								Log.error("Unable to double click on element: "+parameters.get(0));
								result.add("Fail");
								result.add("Element is not enabled");
								result.add(Fileoperations.takeHighlightScreenShot(DriverScript.driver, element, parameters.get(0)));}			
								break;
								
							} catch (Exception e) {
								//e.printStackTrace();
								Log.error(e);
								Log.error("Unable to double click on element: "+parameters.get(0));
								element=null;
								result.add("Fail");
								result.add(e.toString());
								result.add(Fileoperations.takeScreenShot(DriverScript.driver, parameters.get(0)));
								break;
							}
						}
				else if(i==(DriverScript.testobjects.size()-1))
						{
							Log.error("Unable to find the element in All Objects: "+parameters.get(0));
							result.add("Fail");
							result.add("Element not found in All Objects");
							result.add(Fileoperations.takeScreenShot(DriverScript.driver, parameters.get(0)));
						}
				
			}
				
		} 
		else{
			Log.error("Unable to double click on element: "+parameters.get(0));
			Log.error("Browser not found");
			result.add("Fail");
			result.add("Browser Not Found");
		}

		return result;
	}

	
			
//---------------------------------	
			public static WebElement returnWebElement (int testobjectNumber) throws Exception
	{
		Log.info("function: returnWebElement");
		WebElement returnElement=null;
		int waitcount = 0;
		//Log.info("return element:"+DriverScript.testobjects.get(testobjectNumber).getType());
		switch (DriverScript.testobjects.get(testobjectNumber).getType()) 
		{
		
		case "id":
			for (waitcount = 0; waitcount < maxWaitElement; waitcount++) 
			{
				try {
					returnElement= DriverScript.driver.findElement(By.id(DriverScript.testobjects.get(testobjectNumber).getDescription()));
					
					((JavascriptExecutor) DriverScript.driver).executeScript("arguments[0].scrollIntoView(true);", returnElement);
					
					Thread.sleep(500); 
					Log.info("Element found in application: "+DriverScript.testobjects.get(testobjectNumber).getObjectName());
					break;
					} 
				catch (Exception e) {
						try {
							Thread.sleep(1000);
							} catch (InterruptedException e1)
							{
							}
							
						}
							
			}	
				
			if (waitcount==(maxWaitElement)) {
			Log.error("Object not found: "+DriverScript.testobjects.get(testobjectNumber).getObjectName());
			//Fileoperations.takeScreenShot(DriverScript.driver, DriverScript.testobjects.get(testobjectNumber).getObjectName());
			throw new ElementNotVisibleException("Element is not found");}			
			break;
		
		case "name":
			//int waitcount = 0;
			for (waitcount = 0; waitcount < maxWaitElement; waitcount++) 
			{
				try {
					returnElement= DriverScript.driver.findElement(By.name(DriverScript.testobjects.get(testobjectNumber).getDescription()));
					((JavascriptExecutor) DriverScript.driver).executeScript("arguments[0].scrollIntoView(true);", returnElement);
					break;
					} catch (Exception e) {
						try {
							Thread.sleep(1000);
							} catch (InterruptedException e1)
							{
															
							}
					}	
			}
			if (waitcount==(maxWaitElement)){
			Log.error("Object not found: "+DriverScript.testobjects.get(testobjectNumber).getObjectName());
			//Fileoperations.takeScreenShot(DriverScript.driver, DriverScript.testobjects.get(testobjectNumber).getObjectName());
			throw new ElementNotVisibleException("Element is not found");}
			
			break;
		
		case "classname":
			for (waitcount = 0; waitcount < maxWaitElement; waitcount++) 
			{
				try {
					returnElement= DriverScript.driver.findElement(By.className(DriverScript.testobjects.get(testobjectNumber).getDescription()));
					((JavascriptExecutor) DriverScript.driver).executeScript("arguments[0].scrollIntoView(true);", returnElement);
					break;
					} catch (Exception e) {
						try {
							Thread.sleep(1000);
							} catch (InterruptedException e1)
							{
							}
							
						}	
			}
			if (waitcount==(maxWaitElement)){
				Log.error("Object not found: "+DriverScript.testobjects.get(testobjectNumber).getObjectName());
				//Fileoperations.takeScreenShot(DriverScript.driver, DriverScript.testobjects.get(testobjectNumber).getObjectName());
				throw new ElementNotVisibleException("Element is not found");	
				}
			
			break;
		
		case "linktext":
			for (waitcount = 0; waitcount < maxWaitElement; waitcount++) 
			{
				try {
					returnElement= DriverScript.driver.findElement(By.linkText(DriverScript.testobjects.get(testobjectNumber).getDescription()));
					((JavascriptExecutor) DriverScript.driver).executeScript("arguments[0].scrollIntoView(true);", returnElement);
					break;
					} catch (Exception e) {
						try {
							Thread.sleep(1000);
							} catch (InterruptedException e1)
							{
							}

						}	
			}
			if (waitcount==(maxWaitElement)){
				Log.error("Object not found: "+DriverScript.testobjects.get(testobjectNumber).getObjectName());
				//Fileoperations.takeScreenShot(DriverScript.driver, DriverScript.testobjects.get(testobjectNumber).getObjectName());
				throw new ElementNotVisibleException("Element is not found");	
				}
			break;	
		
		case "partiallinktext":
			for (waitcount = 0; waitcount < maxWaitElement; waitcount++) 
			{
				try {
					returnElement= DriverScript.driver.findElement(By.partialLinkText(DriverScript.testobjects.get(testobjectNumber).getDescription()));
					((JavascriptExecutor) DriverScript.driver).executeScript("arguments[0].scrollIntoView(true);", returnElement);
					break;
					} catch (Exception e) {
						try {
							Thread.sleep(1000);
							} catch (InterruptedException e1)
							{
							}
						
						}	
			}
			if (waitcount==(maxWaitElement)){
				Log.error("Object not found: "+DriverScript.testobjects.get(testobjectNumber).getObjectName());
				//Fileoperations.takeScreenShot(DriverScript.driver, DriverScript.testobjects.get(testobjectNumber).getObjectName());
				throw new ElementNotVisibleException("Element is not found");	
				}
			break;
		case "xpath":
			for (waitcount = 0; waitcount < maxWaitElement; waitcount++) 
			{
				try {
					returnElement= DriverScript.driver.findElement(By.xpath(DriverScript.testobjects.get(testobjectNumber).getDescription()));
					((JavascriptExecutor) DriverScript.driver).executeScript("arguments[0].scrollIntoView(true);", returnElement);
					break;
					} catch (Exception e) {
						try {
							Thread.sleep(1000);
							} catch (InterruptedException e1)
							{
							}
						}	
			}
			if (waitcount==(maxWaitElement)){
				Log.error("Object not found: "+DriverScript.testobjects.get(testobjectNumber).getObjectName());
				//Fileoperations.takeScreenShot(DriverScript.driver, DriverScript.testobjects.get(testobjectNumber).getObjectName());
				throw new ElementNotVisibleException("Element is not found");	
				}
			break;
			
		case "css":
			for (waitcount = 0; waitcount < maxWaitElement; waitcount++) 
			{
				try {
					returnElement= DriverScript.driver.findElement(By.cssSelector(DriverScript.testobjects.get(testobjectNumber).getDescription()));
					((JavascriptExecutor) DriverScript.driver).executeScript("arguments[0].scrollIntoView(true);", returnElement);
					break;
					} catch (Exception e) {
						try {
							Thread.sleep(1000);
							} catch (InterruptedException e1)
							{
							}
						}	
			}
			if (waitcount==(maxWaitElement)){
				Log.error("Object not found: "+DriverScript.testobjects.get(testobjectNumber).getObjectName());
				//Fileoperations.takeScreenShot(DriverScript.driver, DriverScript.testobjects.get(testobjectNumber).getObjectName());
				throw new ElementNotVisibleException("Element is not found");	
				}
			break;
					
	}
		return returnElement;
	}

	
//---------------------------------	
			public static List<WebElement> returnWebElements (int testobjectNumber) throws Exception
		{
			Log.info("function: returnWebElements");
			List<WebElement> returnElements=null;
			int waitcount = 0;
			//Log.info("return element:"+DriverScript.testobjects.get(testobjectNumber).getType());
			switch (DriverScript.testobjects.get(testobjectNumber).getType()) 
			{
			
			case "id":
				for (waitcount = 0; waitcount < maxWaitElement; waitcount++) 
				{
					try {
						returnElements= DriverScript.driver.findElements(By.id(DriverScript.testobjects.get(testobjectNumber).getDescription()));
						Log.info("Element found in application: "+DriverScript.testobjects.get(testobjectNumber).getObjectName());
						break;
						} 
					catch (Exception e) {
							try {
								Thread.sleep(1000);
								} catch (InterruptedException e1)
								{
								}
								
							}
								
				}	
					
				if (waitcount==(maxWaitElement)) {
				Log.error("Object not found: "+DriverScript.testobjects.get(testobjectNumber).getObjectName());
				//Fileoperations.takeScreenShot(DriverScript.driver, DriverScript.testobjects.get(testobjectNumber).getObjectName());
				throw new ElementNotVisibleException("Element is not found");}			
				break;
			
			case "name":
				//int waitcount = 0;
				for (waitcount = 0; waitcount < maxWaitElement; waitcount++) 
				{
					try {
						returnElements= DriverScript.driver.findElements(By.name(DriverScript.testobjects.get(testobjectNumber).getDescription()));
						break;
						} catch (Exception e) {
							try {
								Thread.sleep(1000);
								} catch (InterruptedException e1)
								{
																
								}
						}	
				}
				if (waitcount==(maxWaitElement)){
				Log.error("Object not found: "+DriverScript.testobjects.get(testobjectNumber).getObjectName());
				//Fileoperations.takeScreenShot(DriverScript.driver, DriverScript.testobjects.get(testobjectNumber).getObjectName());
				throw new ElementNotVisibleException("Element is not found");}
				
				break;
			
			case "classname":
				for (waitcount = 0; waitcount < maxWaitElement; waitcount++) 
				{
					try {
						returnElements= DriverScript.driver.findElements(By.className(DriverScript.testobjects.get(testobjectNumber).getDescription()));
						break;
						} catch (Exception e) {
							try {
								Thread.sleep(1000);
								} catch (InterruptedException e1)
								{
								}
								
							}	
				}
				if (waitcount==(maxWaitElement)){
					Log.error("Object not found: "+DriverScript.testobjects.get(testobjectNumber).getObjectName());
					//Fileoperations.takeScreenShot(DriverScript.driver, DriverScript.testobjects.get(testobjectNumber).getObjectName());
					throw new ElementNotVisibleException("Element is not found");	
					}
				
				break;
			
			case "linktext":
				for (waitcount = 0; waitcount < maxWaitElement; waitcount++) 
				{
					try {
						returnElements= DriverScript.driver.findElements(By.linkText(DriverScript.testobjects.get(testobjectNumber).getDescription()));
						break;
						} catch (Exception e) {
							try {
								Thread.sleep(1000);
								} catch (InterruptedException e1)
								{
								}

							}	
				}
				if (waitcount==(maxWaitElement)){
					Log.error("Object not found: "+DriverScript.testobjects.get(testobjectNumber).getObjectName());
					//Fileoperations.takeScreenShot(DriverScript.driver, DriverScript.testobjects.get(testobjectNumber).getObjectName());
					throw new ElementNotVisibleException("Element is not found");	
					}
				break;	
			
			case "partiallinktext":
				for (waitcount = 0; waitcount < maxWaitElement; waitcount++) 
				{
					try {
						returnElements= DriverScript.driver.findElements(By.partialLinkText(DriverScript.testobjects.get(testobjectNumber).getDescription()));
						break;
						} catch (Exception e) {
							try {
								Thread.sleep(1000);
								} catch (InterruptedException e1)
								{
								}
							
							}	
				}
				if (waitcount==(maxWaitElement)){
					Log.error("Object not found: "+DriverScript.testobjects.get(testobjectNumber).getObjectName());
					//Fileoperations.takeScreenShot(DriverScript.driver, DriverScript.testobjects.get(testobjectNumber).getObjectName());
					throw new ElementNotVisibleException("Element is not found");	
					}
				break;
			case "xpath":
				for (waitcount = 0; waitcount < maxWaitElement; waitcount++) 
				{
					try {
						returnElements= DriverScript.driver.findElements(By.xpath(DriverScript.testobjects.get(testobjectNumber).getDescription()));
						break;
						} catch (Exception e) {
							try {
								Thread.sleep(1000);
								} catch (InterruptedException e1)
								{
								}
							}	
				}
				if (waitcount==(maxWaitElement)){
					Log.error("Object not found: "+DriverScript.testobjects.get(testobjectNumber).getObjectName());
					//Fileoperations.takeScreenShot(DriverScript.driver, DriverScript.testobjects.get(testobjectNumber).getObjectName());
					throw new ElementNotVisibleException("Element is not found");	
					}
				break;
				
			case "css":
				for (waitcount = 0; waitcount < maxWaitElement; waitcount++) 
				{
					try {
						returnElements= DriverScript.driver.findElements(By.cssSelector(DriverScript.testobjects.get(testobjectNumber).getDescription()));
						break;
						} catch (Exception e) {
							try {
								Thread.sleep(1000);
								} catch (InterruptedException e1)
								{
								}
							}	
				}
				if (waitcount==(maxWaitElement)){
					Log.error("Object not found: "+DriverScript.testobjects.get(testobjectNumber).getObjectName());
					//Fileoperations.takeScreenShot(DriverScript.driver, DriverScript.testobjects.get(testobjectNumber).getObjectName());
					throw new ElementNotVisibleException("Element is not found");	
					}
				break;
						
		}
			return returnElements;
		}

//---------------------------------	
		
			public ArrayList<String> closeBrowser (ArrayList<String> parameters)
		{
				Log.info("function: closeBrowser");
				if (!(DriverScript.driver==null)) 
				{
					try {
						System.out.println(DriverScript.driver.getTitle());
						DriverScript.driver.close();
						CommonWebOps.thiswait(2000);
						Log.info("Closed the active browser/window");
						result.add("Pass");	
						//result.add("");
						//result.add("");
					} catch (Exception e) {
						Log.error(e);
						Log.error("Unable to close active browser/window");
						result.add("Fail");
						result.add(e.toString());
						result.add(Fileoperations.takeScreenShot(DriverScript.driver, parameters.get(0)));
					}				
						
				} 
				else
				{
					Log.error("Unable to Close browser/window, Bowser does not exist");
					result.add("Fail");
					result.add("Unable to Close browser/window");
				}

			
			return result;
		}
	
	
//---------------------------------	
	
			public ArrayList<String> exitBrowser (ArrayList<String> parameters)
	{
		Log.info("function: exitBrowser");	
		if (!(DriverScript.driver==null)) 
		{
			
			try {
				DriverScript.driver.quit();
				DriverScript.driver=null;
				Log.info("Done Exiting the browser");
				result.add("Pass");		
				//result.add("");
				//result.add("");
			} catch (Exception e) {
				Log.error(e);
				Log.error("Unable to exit active browser");
				result.add("Fail");
				result.add(e.toString());	
				result.add(Fileoperations.takeScreenShot(DriverScript.driver, parameters.get(0)));
			}
		} 
		else
		{
			Log.error("Unable to Exit browser, Bowser does not exist");
			result.add("Fail");
			result.add("Browser Not Found");	
		}
		
		return result;
	}
	
//---------------------------------	
			public ArrayList<String> customScreenshot (ArrayList<String> parameters)
			{
				Log.info("function: takeCustomScreenshot");
				String screenshotFilePath="blank";
				try {
					screenshotFilePath= Fileoperations.takeScreenShot(DriverScript.driver, parameters.get(0));
					if (!screenshotFilePath.equalsIgnoreCase("blank")) 
					{
						result.add("Custom");
						result.add("");	
						result.add(screenshotFilePath);
						Log.info("Took custom screenshot");
					}
					
					}
				catch (Exception e) {
					result.add("Fail");
					result.add("");	
					result.add(screenshotFilePath);
					Log.error(e.toString());
				}
					
				return result;
			}
//---------------------------------	
	
}
