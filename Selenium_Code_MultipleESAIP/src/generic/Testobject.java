package generic;

public class Testobject 
{
	private final String SlNo;
	private final String ScreenName;
	private final String ObjectName;
    private final String Type;
    private final String Description;

    public Testobject(String SlNo, String ScreenName, String ObjectName, String Type, String Description)
    {
    	this.SlNo = SlNo;
    	this.ScreenName = ScreenName;
        this.ObjectName = ObjectName;
        this.Type = Type;
        this.Description = Description;
    }
    
    
    public String getSlNo()
    {
        return SlNo;
    }
    
    public String getScreenName()
    {
        return ScreenName;
    }

 	public String getObjectName()
    {
        return ObjectName;
    }

    public String getType()
    {
        return Type;
    }

    public String getDescription()
    {
        return Description;
    }
	
}
