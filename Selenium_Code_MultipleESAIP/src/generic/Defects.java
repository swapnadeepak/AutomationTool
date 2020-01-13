package generic;

public class Defects 
	{
		
		private int RowID;
		private final int SlNo;
		private final String TCID;
	    private final String TCName;
	    private final String Log;
	    private final String Summary;
	    private final String Description;
	    private final String Reproducibility;
	    private final String Severity;
	    private final String Priority;
	    private final String AssignTo;
	    private final String Steps;
	    private final String Additional;
	    private final String Upload;
	    private String DefectID;
	    

	    public Defects(int RowID, int SlNo, String TCID, String TCName, String Log, String Summary, String Description,
	    		String Reproducibility, String Severity, String Priority, String AssignTo,
	    		String Steps, String Additional, String Upload, String DefectID)
	    {
	    	this.RowID = RowID;
	    	this.SlNo = SlNo;
	        this.TCID = TCID;
	        this.TCName = TCName;
	        this.Log = Log;
	        this.Summary = Summary;
	        this.Description = Description;
	        this.Reproducibility = Reproducibility;
	        this.Severity = Severity;
	        this.Priority = Priority;
	        this.AssignTo = AssignTo;
	        this.Steps = Steps;
	        this.Additional = Additional;
	        this.Upload = Upload;
	        this.DefectID = DefectID;
	    }
	    public int getSlNo()
	    {
	        return SlNo;
	    }
	    
	    public int getRowID()
	    {
	        return RowID;
	    }
	    public String getTCID()
	    {
	        return TCID;
	    }
	    public String getTCName()
	    {
	        return TCName;
	    }
	    
	    public String getLog()
	    {
	        return Log;
	    }

	    public String getSummary()
	    {
	        return Summary;
	    }

	    public String getDescription()
	    {
	        return Description;
	    }
	    
	    public String getReproducibility()
	    {
	        return Reproducibility;
	    }

	 	public String getSeverity()
	    {
	        return Severity;
	    }
	 	
	 	public String getPriority()
	    {
	        return Priority;
	    }

	    public String getAdditional()
	    {
	        return Additional;
	    }
	    
	    public String getAssignTo()
	    {
	        return AssignTo;
	    }
	 	public String getSteps()
	    {
	        return Steps;
	    }

	    public String getUpload()
	    {
	        return Upload;
	    }
	    
	    public String getDefectID()
	    {
	        return DefectID;
	    }
	    
	    public void setDefectID(String defectid)
	    {
	        this.DefectID=defectid;
	    }
	  
}

