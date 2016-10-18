package org.durian.route;

import java.text.DecimalFormat;

public class Util {

	public static String timeString( int mins ) {
		DecimalFormat format = new DecimalFormat("00");
		String ts = format.format(mins / 60) + ":" + format.format(mins % 60);
		return ts;
	}
	
	public static String SEKString( int sek ) {
		String sekstr = "" + sek;
		String ts = "      " + sek;
		
		return ts.substring( sekstr.length() );
	}
}
