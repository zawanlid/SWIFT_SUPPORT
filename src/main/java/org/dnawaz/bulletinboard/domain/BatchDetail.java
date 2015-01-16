package org.dnawaz.bulletinboard.domain;

import java.io.Serializable;
import java.util.Date;

public class BatchDetail implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long id;
	private Long batchId;
	private String eaiId;
	private String extMsgId;
	private String activityId;
	private String updateType;
	private String status;
	private String remarks;
	private Date lastUpdateDateTime;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getBatchId() {
		return batchId;
	}
	public void setBatchId(Long batchId) {
		this.batchId = batchId;
	}
	public String getEaiId() {
		return eaiId;
	}
	public void setEaiId(String eaiId) {
		this.eaiId = eaiId;
	}
	public String getExtMsgId() {
		return extMsgId;
	}
	public void setExtMsgId(String extMsgId) {
		this.extMsgId = extMsgId;
	}
	public String getActivityId() {
		return activityId;
	}
	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}
	public String getUpdateType() {
		return updateType;
	}
	public void setUpdateType(String updateType) {
		this.updateType = updateType;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getLastUpdateDateTime() {
		return lastUpdateDateTime;
	}
	public void setLastUpdateDateTime(Date lastUpdateDateTime) {
		this.lastUpdateDateTime = lastUpdateDateTime;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	

}
