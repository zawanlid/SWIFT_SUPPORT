package org.tm.swift.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 
 * @author DilNawaz
 *
 */
public class SearchCriteria implements Serializable{

	private static final long serialVersionUID = -2531172149326533396L;
	
	private Date auditDateFrom;
	private Date auditDateTo;
	private String troubleTickets;
	private String source;
	private List<String> additionalParams;
	private Boolean saveParam;
	private String batchName;
	private String createdBy;
	private String eventName;
	
	public String getEventName() {
		return eventName;
	}
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}
	public Date getAuditDateFrom() {
		return auditDateFrom;
	}
	public void setAuditDateFrom(Date auditDateFrom) {
		this.auditDateFrom = auditDateFrom;
	}
	public Date getAuditDateTo() {
		return auditDateTo;
	}
	public void setAuditDateTo(Date auditDateTo) {
		this.auditDateTo = auditDateTo;
	}
	public String getTroubleTickets() {
		return troubleTickets;
	}
	public void setTroubleTickets(String troubleTickets) {
		this.troubleTickets = troubleTickets;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public List<String> getAdditionalParams() {
		return additionalParams;
	}
	public void setAdditionalParams(List<String> additionalParams) {
		this.additionalParams = additionalParams;
	}
	public Boolean getSaveParam() {
		return saveParam;
	}
	public void setSaveParam(Boolean saveParam) {
		this.saveParam = saveParam;
	}
	public String getBatchName() {
		return batchName;
	}
	public void setBatchName(String batchName) {
		this.batchName = batchName;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	
}
