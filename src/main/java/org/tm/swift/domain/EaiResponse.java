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
public class EaiResponse implements Serializable,RowMapper<EaiResponse>{

	private static final long serialVersionUID = -7505949950119411556L;
	
	private Long id;
	private String type;
	private String eventName;
	private Date createDateTime;
	private String auditParam1;
	private String auditParam2;
	private Boolean isActive;
	private Boolean isStatusUpdate;
	private String source;
	private String ttStatus;
	private Long updateSequence;
	
	public EaiResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		EaiResponse eaiResponse = new EaiResponse();
		eaiResponse.setId(rs.getLong("ID"));
		eaiResponse.setType(rs.getString("TYPE"));
		eaiResponse.setEventName(rs.getString("EVENT_NAME"));
		eaiResponse.setCreateDateTime(rs.getTimestamp("CREATED_DATETIME"));
		eaiResponse.setIsActive("1".equals(rs.getString("ISACTIVE"))?true:false);
		eaiResponse.setIsStatusUpdate("1".equals(rs.getString("IS_STATUS_UPDATE"))?true:false);
		eaiResponse.setAuditParam1(rs.getString("AUDIT_PARAM1"));
		eaiResponse.setAuditParam2(rs.getString("AUDIT_PARAM2"));
		eaiResponse.setSource(rs.getString("SOURCE_SYSTEM"));
		eaiResponse.setTtStatus(rs.getString("TT_STATUS"));
		eaiResponse.setUpdateSequence(rs.getLong("UPDATE_SEQUENCE"));
		return eaiResponse;
	}
	
	public static List<EaiResponse> getList(List<Map<String,Object>> rows, List<EaiResponse> list) {

		EaiResponse eaiResponse = null;
		for (Map<String,Object> row : rows) {
			
			eaiResponse = new EaiResponse();
			eaiResponse.setId(((BigDecimal)row.get("ID")).longValue());
			eaiResponse.setType((String)row.get("TYPE"));
			eaiResponse.setEventName((String)row.get("EVENT_NAME"));
			eaiResponse.setCreateDateTime((Timestamp)row.get("CREATED_DATETIME"));
			eaiResponse.setIsActive("1".equals((String)row.get("ISACTIVE"))?true:false);
			eaiResponse.setIsStatusUpdate("1".equals((String)row.get("IS_STATUS_UPDATE"))?true:false);
			eaiResponse.setAuditParam1((String)row.get("AUDIT_PARAM1"));
			eaiResponse.setAuditParam2((String)row.get("AUDIT_PARAM2"));
			eaiResponse.setSource((String)row.get("SOURCE_SYSTEM"));
			eaiResponse.setTtStatus((String)row.get("TT_STATUS"));
			eaiResponse.setUpdateSequence(((BigDecimal)row.get("UPDATE_SEQUENCE")).longValue());			
			
			list.add(eaiResponse);
		}
		return list;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public Date getCreateDateTime() {
		return createDateTime;
	}

	public void setCreateDateTime(Date createDateTime) {
		this.createDateTime = createDateTime;
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

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public Boolean getIsStatusUpdate() {
		return isStatusUpdate;
	}

	public void setIsStatusUpdate(Boolean isStatusUpdate) {
		this.isStatusUpdate = isStatusUpdate;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getTtStatus() {
		return ttStatus;
	}

	public void setTtStatus(String ttStatus) {
		this.ttStatus = ttStatus;
	}

	public Long getUpdateSequence() {
		return updateSequence;
	}

	public void setUpdateSequence(Long updateSequence) {
		this.updateSequence = updateSequence;
	}
}
