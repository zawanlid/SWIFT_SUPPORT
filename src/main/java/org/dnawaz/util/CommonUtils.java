package org.dnawaz.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.dnawaz.constant.Constant;

public class CommonUtils {


	/**
	 * 
	 * @param dateFormat
	 * @return
	 */
	public static final String getCurrDateAsString(String dateFormat){
	
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		String dateStr = sdf.format(new Date());

		return dateStr;
	}
	
	
	/**
	 * 
	 * @param date
	 * @param dateFormat
	 * @return
	 */
	public static final String convertDateToString(Date date, String dateFormat) {

	    if(date == null) {
	        return null;
	    }
	    
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		String dateStr = sdf.format(date);

		return dateStr;
	}

	/**
	 * 
	 * @param date
	 * @param dateFormat
	 * @return
	 */
	public static final String convertDateToTimeString(Date date, String dateFormat) {

	    if(date == null) {
	        return null;
	    }
	    
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		String dateStr = sdf.format(date);

		return dateStr;
	}
	
	/**
	 * 
	 * @param dateStr
	 * @param format
	 * @return
	 * @throws Exception
	 */
	public static final Date convertStringToDate(String dateStr, String format)
			throws Exception {

		SimpleDateFormat sd = new SimpleDateFormat(format);
		Date pasrseDate = sd.parse(dateStr);

		return pasrseDate;
	}
	
	
	public static Date incrementDate(Date date, int seconds, int minutes, int hours){
        
		if(date == null){
			return date;
		}
		
        Calendar calender = Calendar.getInstance();
        calender.setTimeInMillis(date.getTime());
        
        if(seconds > 0){
        	calender.add(Calendar.SECOND, seconds);
        }
        if(minutes > 0){
        	calender.add(Calendar.MINUTE, minutes);
        }
        if(hours > 0){
        	calender.add(Calendar.HOUR, hours);
        }
        
        return calender.getTime();
   }
	
	/**
	 * Right justified with 2 decimal places.Procced with zeros? For Revenue material sale price would be populated
	 * format 12.05 to length 12 digits 
	 * value = "       12.05"
	 * 
	 * @param value
	 * @param length
	 * @return
	 */
	public static final String formatDoubleToFixedLength(String value, int length) {

		String valueStr = value+"";
		int diff = length - valueStr.length();
		StringBuilder strBuilder = new StringBuilder();
		
		if(diff > 0){
			
			
			while(diff > 0){
				diff--;
				strBuilder.append(" ");
			}
			
			strBuilder.append(valueStr);
			
			valueStr = strBuilder.toString();
		}

		return valueStr;
	}
	
	/**
	 * The total amount in 2 decimal places without decimal point with a leading zero.ie 12.00 = 1200
	 * value = 000000146560
	 * 
	 * @param value
	 * @param length
	 * @param pad
	 * @return
	 */
	public static final String formatDoubleWithPadding(String value, int length, char pad) {

		String valueStr = value+"";
		int diff = length - valueStr.length();
		StringBuilder strBuilder = new StringBuilder();
		
		if(diff > 0){
			
			
			while(diff > -1){
				diff--;
				strBuilder.append(pad);
			}
			
			strBuilder.append(valueStr);			
			valueStr = strBuilder.toString();
			
			valueStr = valueStr.substring(0, valueStr.indexOf('.')) + valueStr.substring(valueStr.indexOf('.')+1, valueStr.length());
		}

		return valueStr;
	}

	/**
	 * 
	 * @param value
	 * @param length
	 * @param pad
	 * @return
	 */
	public static final String formatIntWithPadding(int value, int length, char pad) {

		String valueStr = value+"";
		int diff = length - valueStr.length();
		StringBuilder strBuilder = new StringBuilder();
		
		if(diff > 0){
			
			
			while(diff > 0){
				diff--;
				strBuilder.append(pad);
			}
			
			strBuilder.append(valueStr);			
			valueStr = strBuilder.toString();
			
		}

		return valueStr;
	}
	

	/**
	 * 
	 * @param value
	 * @param length
	 * @param pad
	 * @return
	 */
	public static final String formatLongWithPadding(long value, int length, char pad) {

		String valueStr = value+"";
		int diff = length - valueStr.length();
		StringBuilder strBuilder = new StringBuilder();
		
		if(diff > 0){
			
			
			while(diff > 0){
				diff--;
				strBuilder.append(pad);
			}
			
			strBuilder.append(valueStr);			
			valueStr = strBuilder.toString();
		}

		return valueStr;
	}

	/**
	 * 
	 * @param value
	 * @param length
	 * @param pad
	 * @return
	 */
	public static final String formatStringWithPadding(String value, int length, char pad) {

		String valueStr = value+"";
		int diff = length - valueStr.length();
		StringBuilder strBuilder = new StringBuilder();
		
		if(diff > 0){
			
			
			while(diff > 0){
				diff--;
				strBuilder.append(pad);
			}
			
			strBuilder.append(valueStr);			
			valueStr = strBuilder.toString();
		}

		return valueStr;
	}
	public static Date parseDateWithMultiParser(String dateStr)
			throws Exception {

		if(StringUtils.isEmpty(dateStr)){
			return null;
		}
		
		List<SimpleDateFormat> dateFormats = new ArrayList<SimpleDateFormat>();
		{
			//dateFormats.add(new SimpleDateFormat(Constant.DATE_FORMAT_DDMMYYYY));			
			dateFormats.add(new SimpleDateFormat(Constant.DATE_FORMAT_YYYYMMDD));
			dateFormats.add(new SimpleDateFormat(Constant.DATE_FORMAT_DDMMYYYY2));
			dateFormats.add(new SimpleDateFormat(Constant.DATE_FORMAT_TIMESTAMP));
		}
		;

		for (SimpleDateFormat sdf : dateFormats) {
			try {
				return sdf.parse(dateStr);
			} catch (Exception e) {
				// Ignore and try next date parser
			}
		}
		// All parsers failed
		return null;
	}
	
	public static String formatDate(Date date, String dateFormat){
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		return sdf.format(date);
	}


    public static String dateToOracleDateString(Date date) {
        System.out.println("To Oracle Date; [" + formatDate(date, "dd-MMM-yy") + "]");
        return formatDate(date, "dd-MMM-yy");
    }

}
