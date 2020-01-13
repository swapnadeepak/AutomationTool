package generic;

import java.util.Date;

public class TestResults 
{
	private String runPlanUsed;
	private Date executionStartTime;
	private Date executionEndTime;
    private String timeTaken;
	private double numofTCs;
	private double numofPassedTCs;
	private double numofFailedTCs;
	private double numofMissingTCs;
    

    public TestResults(String runPlanUsed, Date executionStartTime, Date executionEndTime, 
    		String timeTaken,
    		double numofTCs, double numofPassedTCs,
    		double numofFailedTCs, double numofMissingTCs)
    {
    	this.runPlanUsed = runPlanUsed;
    	this.executionStartTime = executionStartTime;
        this.executionEndTime = executionEndTime;
        this.timeTaken = timeTaken;
        this.numofTCs = numofTCs;
        this.numofPassedTCs = numofPassedTCs;
    	this.numofFailedTCs=numofFailedTCs;
    	this.numofMissingTCs=numofMissingTCs;
    }
    
    public Date getexecutionStartTime()
    {
        return executionStartTime;
    }

    public Date getexecutionEndTime()
    {
        return executionEndTime;
    }
    
    public String gettimeTaken()
    {
        return timeTaken;
    }
    
    public double getnumofTCs()
    {
        return numofTCs;
    }

    public double getnumofPassedTCs()
    {
        return numofPassedTCs;
    }
    
    public double getnumofFailedTCs()
    {
        return numofFailedTCs;
    }
    
    public double getnumofMissingTCs()
    {
        return numofMissingTCs;
    }
    
    public String getrunPlanUsed()
    {
        return runPlanUsed;
    }
    
    public void setrunPlanUsed(String value)
    {
        this.runPlanUsed=value;
    }
    
    public void setexecutionStartTime(Date value)
    {
        this.executionStartTime=value;
    }

    public void setexecutionEndTime(Date value)
    {
        this.executionEndTime= value;
    }
    
    public void settimeTaken(String value)
    {
        this.timeTaken= value;
    }

 	public void setnumofTCs(double value)
    {
        this.numofTCs=value;
    }

    public void setnumofPassedTCs(double value)
    {
        this.numofPassedTCs=value;
    }
    
    public void setnumofFailedTCs(double value)
    {
        this.numofFailedTCs=value;
    }
    
    public void setnumofMissingTCs(double value)
    {
    	this.numofMissingTCs=value;
    }
    
}
