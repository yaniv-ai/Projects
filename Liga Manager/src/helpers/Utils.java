package helpers;

import java.util.Calendar;

public class Utils {
	public static String calanderToTime() {
		Calendar c = Calendar.getInstance();
		String hour, min, sec;
		if (c.get(Calendar.HOUR_OF_DAY) < 10) {
			hour = "0" + c.get(Calendar.HOUR_OF_DAY);
		} else {
			hour = "" + c.get(Calendar.HOUR_OF_DAY);
		}
		if (c.get(Calendar.MINUTE) < 10) {
			min = "0" + c.get(Calendar.MINUTE);
		} else {
			min = "" + c.get(Calendar.MINUTE);
		}
		if (c.get(Calendar.SECOND) < 10) {
			sec = "0" + c.get(Calendar.SECOND);
		} else {
			sec = "" + c.get(Calendar.SECOND);
		}
		return hour + ":" + min + ":" + sec + "      ";
	}

}
