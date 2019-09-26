package com.ocherve.jcm.utils;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.concurrent.TimeUnit;

/**
 * Utils to simplify Date computing with static methods
 * 
 * @author herve_dev
 *
 */
public class JcmDate {

	/**
	 * @param tsValue
	 * @return String to display elapse time
	 */
	public static String getElapseTime(Timestamp tsValue) {
		long minutesPassed = getTimestampDiff(tsValue, Timestamp.from(Instant.now()),TimeUnit.MINUTES);
		String textTimePassed = "" + Math.round(minutesPassed) + " minute(s)";
		if ( minutesPassed > 60*24*365 )
			textTimePassed = "" + Math.round(minutesPassed / (60*24*365)) + " an(s)";
		else {
			if ( minutesPassed > 60*24*30 )
				textTimePassed = "" + Math.round(minutesPassed / (60*24*30)) + " mois";
			else {
				if ( minutesPassed > 60*24 )
					textTimePassed = "" + Math.round(minutesPassed / (60*24)) + " jour(s)";
				else {
					if ( minutesPassed > 60 )
						textTimePassed = "" + Math.round(minutesPassed / 60) + " heure(s)";
				}
			}
		}
		return textTimePassed;
	}
	
	
	/**
	 * Get a diff between two timestamps.
	 *
	 * @param oldTs The older timestamp
	 * @param newTs The newer timestamp
	 * @param timeUnit The unit in which you want the diff
	 * @return The diff value, in the provided time unit.
	 */
	public static long getTimestampDiff(Timestamp oldTs, Timestamp newTs, TimeUnit timeUnit) {
	    long diffInMS = newTs.getTime() - oldTs.getTime();
	    return timeUnit.convert(diffInMS, TimeUnit.MILLISECONDS);
	}
}
