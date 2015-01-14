package org.dnawaz.bulletinboard.domain;

import java.io.Serializable;
import java.util.Date;

public class EaiLog implements Serializable{

	private static final long serialVersionUID = 8751282105532159742L;
	
	private int eaiId;
	private String extMsgId;
	private String eventName;
	private Date auditDateTime;
	private String auditParam1;
	private String auditParam2;
	private String eaiEndpoint;
	private String txStatus;
	private String cttNumber;
	
	public int getEaiId() {
		return eaiId;
	}
	public void setEaiId(int eaiId) {
		this.eaiId = eaiId;
	}
	public String getExtMsgId() {
		return extMsgId;
	}
	public void setExtMsgId(String extMsgId) {
		this.extMsgId = extMsgId;
	}
	public String getEventName() {
		return eventName;
	}
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}
	public Date getAuditDateTime() {
		return auditDateTime;
	}
	public void setAuditDateTime(Date auditDateTime) {
		this.auditDateTime = auditDateTime;
	}
	public String getAuditParam1() {
		return auditParam1;
	}
	public void setAuditParam1(String auditParam1) {
		this.auditParam1 = auditParam1;
	}
	public String getAuditParam2() {
		return auditParam2;
	}
	public void setAuditParam2(String auditParam2) {
		this.auditParam2 = auditParam2;
	}
	public String getEaiEndpoint() {
		return eaiEndpoint;
	}
	public void setEaiEndpoint(String eaiEndpoint) {
		this.eaiEndpoint = eaiEndpoint;
	}
	public String getTxStatus() {
		return txStatus;
	}
	public void setTxStatus(String txStatus) {
		this.txStatus = txStatus;
	}
	public String getCttNumber() {
		return cttNumber;
	}
	public void setCttNumber(String cttNumber) {
		this.cttNumber = cttNumber;
	}


}
