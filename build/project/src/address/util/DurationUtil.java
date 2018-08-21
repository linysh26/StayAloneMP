package address.util;

import javafx.util.Duration;

public class DurationUtil {
	
	
	public static String parse(Duration duration) {
		int duration_second = (int)duration.toSeconds();
		int duration_minute = duration_second/60;
		
		return String.format("%02d:%02d", duration_minute, duration_second - duration_minute*60);
	}
	
}
