package generic;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.apache.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import testdriver.DriverScript;


public class IBM_IGC 
{
	static Logger Log = Logger.getLogger(Logger.class.getName());
	private static int maxWaitElement = 20;
	
	ArrayList<String> result= new ArrayList<String>();
//--------------------------------------------------------------------------------		
	public ArrayList<String> enterAssetName (ArrayList<String> parameters)
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
		
		
//--------------------------------------------------------------------------------			
	}
	
}
