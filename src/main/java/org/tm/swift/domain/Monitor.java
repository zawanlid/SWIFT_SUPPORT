package org.tm.swift.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author DilNawaz
 *
 */
public class Monitor implements Serializable {

	private static final long serialVersionUID = -7505949950119411556L;
	
	private Batch batch;
	private BatchDetail batchDetail;
	private EaiLog eaiLog;
	
	public Batch getBatch() {
		return batch;
	}
	public void setBatch(Batch batch) {
		this.batch = batch;
	}
	public BatchDetail getBatchDetail() {
		return batchDetail;
	}
	public void setBatchDetail(BatchDetail batchDetail) {
		this.batchDetail = batchDetail;
	}
	public EaiLog getEaiLog() {
		return eaiLog;
	}
	public void setEaiLog(EaiLog eaiLog) {
		this.eaiLog = eaiLog;
	}
	
	public static List<Monitor> getList(List<Map<String,Object>> rows, List<Monitor> list) {

		EaiLog eaiLogL = null;
		BatchDetail batchDetailL = null;
		Monitor monitor = null;
		for (Map<String,Object> row : rows) {
			monitor = new Monitor();
			eaiLogL = new EaiLog();
			batchDetailL = new BatchDetail();
			
			eaiLogL.setEaiId(((BigDecimal)row.get("EAI_ID")).longValue());
			eaiLogL.setAuditParam1((String)row.get("AUDIT_PARAM1"));
			eaiLogL.setAuditParam2((String)row.get("AUDIT_PARAM2"));
			eaiLogL.setExtMsgId((String)row.get("EXT_MSG_ID"));
			eaiLogL.setEventName((String)row.get("EVENT_NAME"));
			eaiLogL.setAuditDateTime((Timestamp)row.get("AUDIT_DATETIME"));
			eaiLogL.setEaiEndpoint((String)row.get("EAI_ENDPOINT"));
			eaiLogL.setTxStatus((String)row.get("TX_STATUS"));
			eaiLogL.setCttNumber((String)row.get("CTT_NUMBER"));
			
			batchDetailL.setRemarks((String)row.get("REMARKS"));
			batchDetailL.setStatus((String)row.get("STATUS"));
			batchDetailL.setLastUpdateDateTime((Timestamp)row.get("LAST_UPDATE_DATETIME"));
			
			monitor.setBatch(null);
			monitor.setBatchDetail(batchDetailL);
			monitor.setEaiLog(eaiLogL);
			list.add(monitor);
		}
		
		return list;
	}
}
