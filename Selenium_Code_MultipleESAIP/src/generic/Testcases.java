package generic;

public class Testcases 
{
	
	private final String BR;
    private final String Module;
    private final String Testcasename;
    private int StartRowNo;
	private int EndRowNo;
	private String TCID;
    private String TCCritical;	
    private String Result;
    private String timeforExecution;
    private double Repeatability;
    

    public Testcases(String BR, String Module, String Testcasename, int StartRowNo,
    		int EndRowNo, String TCID, String TCCritical, String Result, String timeforExecution, double Repeatability)
    {
    	this.BR = BR;
        this.Module = Module;
        this.Testcasename = Testcasename;
        this.StartRowNo = StartRowNo;
        this.EndRowNo = EndRowNo;
        this.TCID = TCID;
        this.TCCritical = TCCritical;
        this.Result = Result;
        this.timeforExecution = timeforExecution;
        this.Repeatability = Repeatability;
    }
    
    public String getBR()
    {
        return BR;
    }

    public String getModule()
    {
        return Module;
    }

    public String getTestcasename()
    {
        return Testcasename;
    }
    
    public int getStartRowNo()
    {
        return StartRowNo;
    }

 	public int getEndRowNo()
    {
        return EndRowNo;
    }

    public String getResult()
    {
        return Result;
    }
    
    public String gettimeforExecution()
    {
        return timeforExecution;
    }
    
    public double getRepeatability()
    {
        return Repeatability;
    }
    
    public String getTCid()
    {
        return TCID;
    }
    
    public String getTCcr()
    {
        return TCCritical;
    }
    
    public void setStartRowNo(int rownum)
    {
        this.StartRowNo=rownum;
    }

 	public void setEndRowNo(int rownum)
    {
        this.EndRowNo=rownum;
    }
 	
 	public void setResult(String result)
    {
 		this.Result = result;
    } 

    
    public void settimeforExecution(String timeTaken)
    {
    	this.timeforExecution = timeTaken;
    }
    
    /*
    public int getNoofteststeps()
    {
        return Noofteststeps;
    }
    
    public void setNoofteststeps(int num)
    {
        this.Noofteststeps=num;
    }
 
    public String getOutput()
    {
        return Output;
    }

    public String getResult()
    {
        return Result;
    }
    
    public String getRowNo()
    {
        return RowNo;
    }

    public void setResult(String result)
    {
    	Result = result;
    } */

}
