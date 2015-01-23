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

public class Batch implements Serializable,RowMapper<Batch>{

	private static final long serialVersionUID = -7505949950119411556L;
	
	private Long id;
	private String name;
	private String createdBy;
	private Date createDateTime;
	private String status;
	private String remarks;
	private Date lastUpdateDateTime;
	private Boolean isActive;
	private String source;
	private String eventName;

	
	public Batch mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		Batch batch = new Batch();
		batch.setId(rs.getLong("ID"));
		batch.setName(rs.getString("NAME"));
		batch.setCreateDateTime(rs.getTimestamp("CREATED_DATETIME"));
		batch.setCreatedBy(rs.getString("CREATED_BY"));
		batch.setStatus(rs.getString("STATUS"));
		batch.setLastUpdateDateTime(rs.getTimestamp("LAST_UPDATE_DATETIME"));
		batch.setIsActive("1".equals(rs.getString("ISACTIVE"))?true:false);
		batch.setRemarks(rs.getString("REMARKS"));
		batch.setSource(rs.getString("SOURCE_SYSTEM"));
		batch.setEventName(rs.getString("EVENT_NAME"));
		return batch;
	}
	
	public static List<Batch> getList(List<Map<String,Object>> rows, List<Batch> list) {

		Batch batch = null;
		for (Map<String,Object> row : rows) {
			batch = new Batch();
			batch.setId(((BigDecimal)row.get("ID")).longValue());
			batch.setName((String)row.get("NAME"));
			batch.setCreateDateTime((Timestamp)row.get("CREATED_DATETIME"));
			batch.setCreatedBy((String)row.get("CREATED_BY"));
			batch.setStatus((String)row.get("STATUS"));
			batch.setLastUpdateDateTime((Timestamp)row.get("LAST_UPDATE_DATETIME"));
			batch.setIsActive("1".equals((String)row.get("ISACTIVE"))?true:false);
			batch.setRemarks((String)row.get("REMARKS"));
			batch.setSource((String)row.get("SOURCE_SYSTEM"));
			batch.setEventName((String)row.get("EVENT_NAME"));
			list.add(batch);
		}
		return list;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public Date getCreateDateTime() {
		return createDateTime;
	}
	public void setCreateDateTime(Date createDateTime) {
		this.createDateTime = createDateTime;
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
	public Boolean getIsActive() {
		return isActive;
	}
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}
	
	

}
