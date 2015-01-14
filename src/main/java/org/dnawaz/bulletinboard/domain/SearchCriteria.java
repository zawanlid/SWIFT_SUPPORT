package org.dnawaz.bulletinboard.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class SearchCriteria implements Serializable{

	private static final long serialVersionUID = -2531172149326533396L;
	
	private Date auditDateFrom;
	private Date auditDateTo;
	private List<String> troubleTickets;
	private String source;
	private List<String> additionalParams;
	
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public List<String> getTroubleTickets() {
		return troubleTickets;
	}
	public void setTroubleTickets(List<String> troubleTickets) {
		this.troubleTickets = troubleTickets;
	}
	public List<String> getAdditionalParams() {
		return additionalParams;
	}
	public void setAdditionalParams(List<String> additionalParams) {
		this.additionalParams = additionalParams;
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
	

}