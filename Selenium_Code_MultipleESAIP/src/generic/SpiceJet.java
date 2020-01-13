package generic;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import testdriver.DriverScript;

public class SpiceJet 
{

	Logger Log = Logger.getLogger(Logger.class.getName());
	//ArrayList<Teststeps> Teststeps = new ArrayList<>();
	ArrayList<String> result= new ArrayList<String>();
	
	//---------------------------------
	
			public ArrayList<String> selectLocation (ArrayList<String> parameters)
			{
				WebElement element=null;
				int i=0;
				if (!(DriverScript.driver==null)) 
				{
					for (i = 0; i < DriverScript.testobjects.size(); i++) 
					{
						if(parameters.get(0).equalsIgnoreCase(DriverScript.testobjects.get(i).getObjectName()))
								{
									try {
										element =CommonWebOps.returnWebElement(i);
										element.sendKeys(parameters.get(1));
										Thread.sleep(2000);
										DriverScript.driver.findElement(By.xpath("//li[@class='livecl city_selected']/a")).click();
										Log.info("Selected"+parameters.get(1)+" in dropdown:"+parameters.get(0));
										result.add("Pass");
									
										break;
									} catch (Exception e) {
										//e.printStackTrace();
										Log.error(e);
										Log.error("Unable to select"+parameters.get(1)+" in dropdown:"+parameters.get(0));
										
										result.add("Fail");
										result.add(e.toString());
										break;
									}
								}
						else if(i==(DriverScript.testobjects.size()-1))
								{
									Log.error("Unable to find the element in All Objects: "+parameters.get(0));
									result.add("Fail");
									result.add("Element not found in All Objects");
								}
						
					}
						
				} 
				else{
					Log.error("Unable to select "+parameters.get(1)+" in dropdown:"+parameters.get(0));
					Log.error("Browser not found");
					result.add("Fail");
					result.add("Browser Not Found");
				}

				return result;
			}
	
	
	
	
	//---------------------------------
	
		public ArrayList<String> selectDate (ArrayList<String> parameters)
		{
			//WebElement element=null;
			String clicked="No";
			int i=0;
			if (!(DriverScript.driver==null)) 
			{
				for (i = 0; i < DriverScript.testobjects.size(); i++) 
				{
					if(parameters.get(0).equalsIgnoreCase(DriverScript.testobjects.get(i).getObjectName()))
							{
								try {
									List<WebElement> allDates=CommonWebOps.returnWebElements(i);
									String param = parameters.get(1);
									String[] values = param.split("\\.");
									Log.info(values[0]);
																		
									for(WebElement element:allDates)
									{
										
										String date=element.getText();
										Log.info(date);
										
										//System.out.println(date);
										if(date.equalsIgnoreCase(values[0]))
										{
											element.click();
											element=null;
											clicked="Yes";
											break;
										}
										
									}
									allDates=null;
									if (clicked.equals("Yes")) 
									{
										Log.info("Clicked "+parameters.get(1)+" in calender:"+parameters.get(0));
										result.add("Pass");
									}
									
									else
									{
										Log.error("Unable to click"+parameters.get(1)+" in calender:"+parameters.get(0));
										result.add("Fail");
										result.add("Unable to click in calendar");	
									}
									
									break;
								} catch (Exception e) {
									//e.printStackTrace();
									Log.error(e);
									Log.error("Unable to click"+parameters.get(1)+" in calender:"+parameters.get(0));
									
									result.add("Fail");
									result.add(e.toString());
									break;
								}
							}
					else if(i==(DriverScript.testobjects.size()-1))
							{
								Log.error("Unable to find the element in All Objects: "+parameters.get(0));
								result.add("Fail");
								result.add("Element not found in All Objects");
							}
					
				}
					
			} 
			else{
				Log.error("Unable to click "+parameters.get(1)+" in calender:"+parameters.get(0));
				Log.error("Browser not found");
				result.add("Fail");
				result.add("Browser Not Found");
			}

			return result;
		}
	
	
	
}
