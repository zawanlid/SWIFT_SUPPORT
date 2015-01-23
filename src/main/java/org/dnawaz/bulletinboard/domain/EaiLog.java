package org.dnawaz.bulletinboard.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;

public class EaiLog implements Serializable, RowMapper<EaiLog> {

	private static final long serialVersionUID = 8751282105532159742L;

	private long eaiId;
	private String extMsgId;
	private String eventName;
	private Date auditDateTime;
	private String auditParam1;
	private String auditParam2;
	private String eaiEndpoint;
	private String txStatus;
	private String cttNumber;

	public EaiLog mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		EaiLog eaiLog = new EaiLog();
		eaiLog.setEaiId(rs.getBigDecimal(("EAI_ID")).longValue());
		eaiLog.setAuditParam1(rs.getString("AUDIT_PARAM1"));
		eaiLog.setAuditParam2(rs.getString("AUDIT_PARAM2"));
		eaiLog.setExtMsgId(rs.getString("EXT_MSG_ID"));
		eaiLog.setEventName(rs.getString("EVENT_NAME"));
		eaiLog.setAuditDateTime(rs.getTimestamp("AUDIT_DATETIME"));
		eaiLog.setEaiEndpoint(rs.getString("EAI_ENDPOINT"));
		eaiLog.setTxStatus(rs.getString("TX_STATUS"));
		eaiLog.setCttNumber(rs.getString("CTT_NUMBER"));
		return eaiLog;
	}
	
	public static List<EaiLog> getList(List<Map<String,Object>> rows, List<EaiLog> list) {

		EaiLog eaiLog = null;
		for (Map<String,Object> row : rows) {
			eaiLog = new EaiLog();
			eaiLog.setEaiId(((BigDecimal)row.get("EAI_ID")).longValue());
			eaiLog.setAuditParam1((String)row.get("AUDIT_PARAM1"));
			eaiLog.setAuditParam2((String)row.get("AUDIT_PARAM2"));
			eaiLog.setExtMsgId((String)row.get("EXT_MSG_ID"));
			eaiLog.setEventName((String)row.get("EVENT_NAME"));
			eaiLog.setAuditDateTime((Timestamp)row.get("AUDIT_DATETIME"));
			eaiLog.setEaiEndpoint((String)row.get("EAI_ENDPOINT"));
			eaiLog.setTxStatus((String)row.get("TX_STATUS"));
			eaiLog.setCttNumber((String)row.get("CTT_NUMBER"));
			list.add(eaiLog);
		}
		return list;
	}

	public long getEaiId() {
		return eaiId;
	}

	public void setEaiId(long eaiId) {
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
