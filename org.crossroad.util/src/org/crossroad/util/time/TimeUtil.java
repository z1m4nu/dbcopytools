/**
 * 
 */
package org.crossroad.util.time;

import java.util.concurrent.TimeUnit;

/**
 * @author e.soden
 *
 */
public final class TimeUtil {

	/**
	 * 
	 */
	private TimeUtil() {
	}
	
	 public static String convertMillis(long millis) {
	        if(millis < 0) {
	            throw new IllegalArgumentException("Duration must be greater than zero!");
	        }

	        long days = TimeUnit.MILLISECONDS.toDays(millis);
	        millis -= TimeUnit.DAYS.toMillis(days);
	        long hours = TimeUnit.MILLISECONDS.toHours(millis);
	        millis -= TimeUnit.HOURS.toMillis(hours);
	        long minutes = TimeUnit.MILLISECONDS.toMinutes(millis);
	        millis -= TimeUnit.MINUTES.toMillis(minutes);
	        long seconds = TimeUnit.MILLISECONDS.toSeconds(millis);
	        millis -= TimeUnit.MILLISECONDS.toSeconds(millis);
	        
	        
	        return(String.format("%02d d %02d:%02d:%02d.%03d",days,hours,minutes,seconds,millis));
	    }
	

}
