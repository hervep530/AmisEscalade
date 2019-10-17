package com.ocherve.jcm.utils;

public class JcmException {
	
	public static String formatStackTrace(Exception e) {
		String trace = "";
		for (int t = 0; t < e.getStackTrace().length; t++) {
			trace += "%n" + e.getStackTrace()[t].toString();
		}
		return String.format(trace);
	}


}
