package org.tm.swift.util;

/**
 * 
 * @author DilNawaz
 *
 */
public final class StringUtils {

    /**
     * Constructor for utility class.
     */
    private StringUtils() {
    }

    /**
     * @param arg
     *            .
     * @return boolean
     */
    public static boolean isEmpty(final String arg) {
        return (arg == null || arg.trim().length() == 0)? true:false;
    }

    /**
     * @param arg
     *            .
     * @return boolean
     */
    public static boolean isNotEmpty(final String arg) {
        return (arg != null && arg.trim().length() > 0)? true:false;
    }

    /**
     * convert null to empty string
     * @param arg
     * @return
     */
    public static String fixNull(final String arg) {
        return arg == null? "":arg;
    }
    
    /**
     * Trim string to long type.
     * @param val
     * @return
     */
	public static String trimStringToLong (String val) {
		
		if (StringUtils.isNotEmpty(val)) {
			return Long.parseLong(val)+"";
		} else {
			return val;
		}
		
	}
}
