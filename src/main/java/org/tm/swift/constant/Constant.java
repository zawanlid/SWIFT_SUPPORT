package org.tm.swift.constant;

/**
 * 
 * @author DilNawaz
 *
 */
public interface Constant {

	String SOURCE_SYSTEM_NOVA = "NOVA";
	String EVENT_NAME_NOVA = "SwiftNovaUpdateCTT";
	
	String SOURCE_SYSTEM_ICP = "ICP";
	String EVENT_NAME_ICP = "evUpdateTRfrSWF";
	
	
	/**
	 * 
	 */
	String DATE_FORMAT_DDMMYYYY = "ddMMyyyy";
	
	/**
	 * 
	 */
	String DATE_FORMAT_DDMMYYYY2 = "dd/MM/yyyy";
	
	/**
	 * 
	 */
	String DATE_FORMAT_YYYYMMDD = "yyyyMMdd";
	/**
	 * 
	 */
	String DATE_FORMAT_YYYY_MM_DD = "yyyy-MM-dd";
	/**
	 * 
	 */
	String DATE_FORMAT_TIMESTAMP = "yyyyMMddHHmmss";
	
	String STATUS_PICKUP = "PICKUP";
	String STATUS_RETRY = "RETRY";
	String STATUS_NEW = "NEW";
	String STATUS_TERMINATED = "TERMINATED";
	String STATUS_SUCCESS = "SUCCESS";
	String STATUS_PARTIALLY_SUCCESS = "PARTIALLY SUCCESS";
	String STATUS_CLOSED = "Closed";
	
	String EAI_RESPONSE_SUCCESS = "SUCCESS";
	String EAI_RESPONSE_ERROR = "ERROR";
	
	String REMARKS_SUCCESS = "Successfully Processed";

	String LOGIN = "admin";
	String PASSWORD = "SST";
	
}
