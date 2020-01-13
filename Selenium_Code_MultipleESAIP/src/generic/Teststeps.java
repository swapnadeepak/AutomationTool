package generic;

public class Teststeps 
{
	
	private final String BR;
    private final String Module;
    private final String Testcase;
    private final String SlNo;
	private final String Classfile;
    private final String Keyword;
    private final String ApplicationObject;
    private final String Input;
    //private final String Output;
    private String Result;
    private final String RowNo;
    private String failureReason;
    private String failureImage;
    private String TCID;
    private String Critical;
    

    public Teststeps(String BR, String Module, String Testcase, String SlNo,
    		String Classfile, String Keyword, String ApplicationObject, String Input, //String Output, 
    		String Result,
    		String RowNo, String failureReason, String failureImage, String TCID, String Critical)
    {
    	this.BR = BR;
        this.Module = Module;
        this.Testcase = Testcase;
        this.SlNo = SlNo;
        this.Classfile = Classfile;
        this.Keyword = Keyword;
        this.ApplicationObject = ApplicationObject;
        this.Input = Input;
        //this.Output = Output;
        this.Result = Result;
        this.RowNo = RowNo;
        this.failureReason = failureReason; 
        this.failureImage = failureImage;
        this.TCID = TCID;
        this.Critical = Critical;
    }
    
    public String getBR()
    {
        return BR;
    }

    public String getModule()
    {
        return Module;
    }

    public String getTestcase()
    {
        return Testcase;
    }
    public String getSlNo()
    {
        return SlNo;
    }

 	public String getClassfile()
    {
        return Classfile;
    }

    public String getKeyword()
    {
        return Keyword;
    }

    public String getObject()
    {
        return ApplicationObject;
    }
    public String getInput()
    {
        return Input;
    }
    /*
    public String getOutput()
    {
        return Output;
    }*/

    public String getResult()
    {
        return Result;
    }
    
    public String getTCID()
    {
        return TCID;
    }
    
    public String getCritical()
    {
        return Critical;
    }
    
    public String getRowNo()
    {
        return RowNo;
    }

    public void setResult(String result)
    {
    	Result = result;
    }
    
    public String getFailureReason()
    {
    	return failureReason;
    }
    
    public void setFailureReason(String reason)
    {
    	failureReason = reason;
    }
    
    public String getFailureImage()
    {
    	return failureImage;
    }
    
    public void setFailureImage(String imagePath)
    {
    	failureImage = imagePath;
    }
    

}
