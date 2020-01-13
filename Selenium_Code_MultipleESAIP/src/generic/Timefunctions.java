package generic;

import java.util.Date;

import org.apache.log4j.Logger;

public class Timefunctions 
{
	static Logger Log = Logger.getLogger(Logger.class.getName());
	public static Date getCurrentDateAndTime() 
	{
		Date date=null;
		try {
			date =new Date();
		} catch (Exception e) {
			Log.fatal(e);
		}
		return date;
	}
	
	public static String executionTime(Date startDate, Date endDate)

	{
		String timeDifferenceWithTimeUnit = null;

		try {

			// Get msec from each, and subtract.
			long timediffInMilliSeconds = endDate.getTime() - startDate.getTime();
			timeDifferenceWithTimeUnit = getFormatedExecutionTime(timediffInMilliSeconds);

			/*
			 //Use the below code as an alternative (optional)
			 * long secondsValue = timediffInMilliSeconds / 1000 % 60; long
			 * minutesValue = timediffInMilliSeconds / (60 * 1000) % 60; long
			 * hoursValue = timediffInMilliSeconds / (60 * 60 * 1000);
			 * 
			 * difference = hoursValue + " hrs: " + minutesValue + " mins: " +
			 * secondsValue + " secs"; System.out.println("difference - > " +
			 * difference);
			 */

		} catch (Exception e) {
			//e.printStackTrace();
			Log.fatal(e.toString());
		}
		return timeDifferenceWithTimeUnit;

	}
	
	public static String getFormatedExecutionTime(long milliSeconds) {
		String timeformattedValue = null;

		int secValue = (int) milliSeconds / 1000;
		//Alternatively use the below  
		//int secValue = (int) TimeUnit.MILLISECONDS.toSeconds(milliSeconds);
		
		int minValue = 0;
		int hourValue = 0;

		if (secValue >= 60) {
			minValue = minValue + Integer.valueOf(secValue / 60);
			secValue = secValue % 60;
		}

		if (minValue >= 60) {
			hourValue = hourValue + Integer.valueOf(minValue / 60);
			minValue = minValue % 60;
		}

		if (hourValue == 0) {
			if (minValue == 0) {
				timeformattedValue = timePluralLogic(secValue, "s");
			} else {
				timeformattedValue = timePluralLogic(minValue, "m") + ", "
						+ timePluralLogic(secValue, "s");
			}
		} else {
			timeformattedValue = timePluralLogic(hourValue, "h") + ", "
					+ timePluralLogic(minValue, "m") + ", "
					+ timePluralLogic(secValue, "s");
		}
		return timeformattedValue;
	}
	
	public static String timePluralLogic(long timeValue, String timeType) {
		String timePluralValue = null;
		switch (String.valueOf(timeType).charAt(0)) {
		case 'h':
			if (timeValue <= 1) {
				timePluralValue = timeValue + " hour";
			} else {
				timePluralValue = timeValue + " hours";
			}
			break;
		case 'm':
			if (timeValue <= 1) {
				timePluralValue = timeValue + " min";
			} else {
				timePluralValue = timeValue + " mins";
			}
			break;
		case 's':
			if (timeValue <= 1) {
				timePluralValue = timeValue + " sec";
			} else {
				timePluralValue = timeValue + " secs";
			}
			break;
		}
		return timePluralValue;
	}
	
}
