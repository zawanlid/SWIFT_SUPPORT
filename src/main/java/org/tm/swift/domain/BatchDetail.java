package org.tm.swift.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;

/**
 * 
 * @author DilNawaz
 *
 */
public class BatchDetail implements Serializable,RowMapper<BatchDetail>{

	private static final long serialVersionUID = 1L;
	
	private Long id;
	private Long batchId;
	private Long eaiId;
	private String extMsgId;
	private String activityId;
	private String updateType;
	private String status;
	private String remarks;
	private Date lastUpdateDateTime;
	
	public BatchDetail mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		BatchDetail batchdetail = new BatchDetail();
		batchdetail.setId(rs.getLong("ID"));
		batchdetail.setBatchId(rs.getLong("BATCH_ID"));
		batchdetail.setEaiId(rs.getLong("EAI_ID"));
		batchdetail.setExtMsgId(rs.getString("EXT_MSG_ID"));
		batchdetail.setUpdateType(rs.getString("UPDATE_TYPE"));
		batchdetail.setActivityId(rs.getString("ACTIVITY_ID"));
		batchdetail.setStatus(rs.getString("STATUS"));
		batchdetail.setLastUpdateDateTime(rs.getTimestamp("LAST_UPDATE_DATETIME"));
		batchdetail.setRemarks(rs.getString("REMARKS"));
		return batchdetail;
	}
	
	public static List<BatchDetail> getList(List<Map<String,Object>> rows, List<BatchDetail> list) {

		BatchDetail batchdetail = null;
		for (Map<String,Object> row : rows) {
			batchdetail = new BatchDetail();
			batchdetail.setId(((BigDecimal)row.get("ID")).longValue());
			batchdetail.setBatchId(((BigDecimal)row.get("BATCH_ID")).longValue());
			batchdetail.setEaiId(((BigDecimal)row.get("EAI_ID")).longValue());
			batchdetail.setExtMsgId((String)row.get("EXT_MSG_ID"));
			batchdetail.setUpdateType((String)row.get("UPDATE_TYPE"));
			batchdetail.setActivityId((String)row.get("ACTIVITY_ID"));
			batchdetail.setStatus((String)row.get("STATUS"));
			batchdetail.setLastUpdateDateTime((Timestamp)row.get("LAST_UPDATE_DATETIME"));
			batchdetail.setRemarks((String)row.get("REMARKS"));
			list.add(batchdetail);
		}
		return list;
	}
	
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

	public Long getEaiId() {
		return eaiId;
	}

	public void setEaiId(Long eaiId) {
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
