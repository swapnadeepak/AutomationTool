package generic;

public class Configuration 
{
	private String url;
	private String browser;
	private String appusername;
    private String apppassword;
    //private final String executionstarttime;
    //private final String executionendtime;
   // private final String runplanname;
   // private final String noofmodulesexecuted;

    public Configuration(String url, String browser, String appusername, String apppassword 
    		//String executionstarttime,String executionendtime,String runplanname,
    		//String noofmodulesexecuted
    		)
    {
    	this.url = url;
    	this.browser = browser;
        this.appusername = appusername;
        this.apppassword = apppassword;
        //this.executionstarttime = executionstarttime;
        //this.executionendtime = executionendtime;
        //this.runplanname = runplanname;
        //this.noofmodulesexecuted = noofmodulesexecuted;
        
    }
    
    public String geturl()
    {
        return url;
    }
    
    public String getbrowser()
    {
        return browser;
    }

 	public String getappusername()
    {
        return appusername;
    }

    public String getapppassword()
    {
        return apppassword;
    }
    
    public void seturl(String url)
    {
    	this.url=url;
    }
    
    public void setbrowser(String browser)
    {
        this.browser=browser;
    }

 	public void setappusername(String setappusername)
    {
 		this.appusername=setappusername;
    }

    public void setapppassword(String apppassword)
    {
    	this.apppassword=apppassword;
    }

    /*
    public String getexecutionstarttime()
    {
        return executionstarttime;
    }
    public String getrunplanname()
    {
        return runplanname;
    }

    public String getnoofmodulesexecuted()
    {
        return noofmodulesexecuted;
    }
	
	*/
}
