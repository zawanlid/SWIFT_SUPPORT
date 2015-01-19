package org.dnawaz.bulletinboard.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.dnawaz.bulletinboard.domain.Batch;
import org.dnawaz.bulletinboard.domain.EaiLog;
import org.dnawaz.bulletinboard.domain.SearchCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

@Repository("monitorDao")
public class MonitorDaoImpl extends JdbcDaoSupport implements MonitorDao {

	@Autowired
	private DataSource dataSource;

	@PostConstruct
	private void initialize() {
		setDataSource(dataSource);

	}

	private static Logger log = Logger.getLogger(MonitorDaoImpl.class
			.getName());

	public List<EaiLog> getBatchDetails(SearchCriteria searchCriteria) {

		String sql = " SELECT e.* FROM EAI_LOG e, SST_RETRIGGER_BATCHES b, SST_RETRIGGER_BATCH_DETAILS bd"
				+ "  where b.ID = bd.BATCH_ID and bd.EAI_ID = e.EAI_ID and b.NAME = ? ";

		log.debug(sql);
		
		Connection conn = null;
		List<EaiLog> eaiList = new ArrayList<EaiLog>();
		EaiLog eaiLog = null;

		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, searchCriteria.getBatchName());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				
				eaiLog = new EaiLog();
				eaiLog.setEaiId(rs.getInt("EAI_ID"));
				eaiLog.setAuditParam1(rs.getString("AUDIT_PARAM1"));
				eaiLog.setAuditParam2(rs.getString("AUDIT_PARAM2"));
				eaiLog.setExtMsgId(rs.getString("EXT_MSG_ID"));
				eaiLog.setEventName(rs.getString("EVENT_NAME"));
				eaiLog.setAuditDateTime(rs.getTimestamp("AUDIT_DATETIME"));
				eaiLog.setEaiEndpoint(rs.getString("EAI_ENDPOINT"));
				eaiLog.setTxStatus(rs.getString("TX_STATUS"));
				eaiLog.setCttNumber(rs.getString("CTT_NUMBER"));
				
				eaiList.add(eaiLog);
			}
			rs.close();
			ps.close();
			return eaiList;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
				}
			}
		}
	}

	public Batch getBatch(SearchCriteria searchCriteria) {

		String sql = "SELECT * FROM SST_RETRIGGER_BATCHES WHERE NAME = ?";

		log.debug(sql);
		
		Connection conn = null;

		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, searchCriteria.getBatchName());
			Batch batch = new Batch();
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				batch.setName(rs.getString("NAME"));
				batch.setCreateDateTime(rs.getTimestamp("CREATED_DATETIME"));
				batch.setCreatedBy(rs.getString("CREATED_BY"));
				batch.setStatus(rs.getString("STATUS"));
				batch.setLastUpdateDateTime(rs.getTimestamp("LAST_UPDATE_DATETIME"));
				batch.setIsActive("1".equals(rs.getString("ISACTIVE"))?true:false);
				batch.setRemarks(rs.getString("REMARKS"));
				batch.setSource(rs.getString("SOURCE_SYSTEM"));
			}
			rs.close();
			ps.close();
			return batch;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
				}
			}
		}
	}

	public List<String> getDistinctBatch() {

		String sql = " SELECT DISTINCT(NAME) as NAME, CREATED_DATETIME FROM SST_RETRIGGER_BATCHES WHERE 1 = 1  order by CREATED_DATETIME desc  ";

		log.debug(sql);
		
		Connection conn = null;
		List<String> batchList = new ArrayList<String>();

		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				batchList.add(rs.getString("NAME"));
			}
			rs.close();
			ps.close();
			return batchList;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
				}
			}
		}
	}
}
