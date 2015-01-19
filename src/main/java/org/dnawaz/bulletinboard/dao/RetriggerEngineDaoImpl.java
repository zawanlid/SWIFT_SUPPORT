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
import org.dnawaz.constant.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

@Repository("retriggerEngineDao")
public class RetriggerEngineDaoImpl extends JdbcDaoSupport implements
		RetriggerEngineDao {

	@Autowired
	private DataSource dataSource;

	@PostConstruct
	private void initialize() {
		setDataSource(dataSource);

	}

	private static Logger log = Logger.getLogger(RetriggerEngineDaoImpl.class
			.getName());

	public EaiLog findById(int eaiId) {

		String sql = "SELECT * FROM EAI_LOG WHERE eai_id = ?";

		Connection conn = null;

		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, eaiId);
			EaiLog eaiLog = new EaiLog();
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				eaiLog.setEaiId(rs.getInt("EAI_ID"));
				eaiLog.setAuditParam1(rs.getString("AUDIT_PARAM1"));
			}
			rs.close();
			ps.close();
			return eaiLog;
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
				eaiLog.setAuditDateTime(rs.getDate("AUDIT_DATETIME"));
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

	public long updateBatchListStatus(List<Batch> batchList, String status) {

		if (batchList == null || batchList.size() == 0) {
			return 0;
		}

		StringBuilder sql = new StringBuilder(
				"update SST_RETRIGGER_BATCHES set status = '" + status
						+ "' , LAST_UPDATE_DATETIME = SYSDATE where ID in  (");

		int count = 1;
		for (Batch batch : batchList) {

			sql.append("'" + batch.getId() + "'");
			if (count < batchList.size())
				sql.append(",");

			count++;
		}
		sql.append(" ) ");
		log.debug(sql.toString());

		Connection conn = null;
		long rowCount = -1;

		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			rowCount = ps.executeUpdate();
			ps.close();
			return rowCount;
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

	public List<Batch> getBatches() {

		String sql = "SELECT * FROM SST_RETRIGGER_BATCHES WHERE ISACTIVE = '1'  and STATUS in ( 'NEW') and SOURCE_SYSTEM = ?";

		log.debug(sql);

		Connection conn = null;
		List<Batch> batchList = new ArrayList<Batch>();

		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, Constant.SOURCE_SYSTEM_ICP);
			Batch batch = null;
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				batch = new Batch();
				batch.setId(rs.getLong("ID"));
				batch.setName(rs.getString("NAME"));
				batch.setCreateDateTime(rs.getDate("CREATED_DATETIME"));
				batch.setCreatedBy(rs.getString("CREATED_BY"));
				batch.setStatus(rs.getString("STATUS"));
				batch.setLastUpdateDateTime(rs.getDate("LAST_UPDATE_DATETIME"));
				batch.setRemarks(rs.getString("REMARKS"));

				batchList.add(batch);
			}

			log.debug("Retriger Enginer PICKUP batches size: "
					+ batchList.size());
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

	public long updateBatchEAIListStatus(Batch batch, String status) {

		StringBuilder sql = new StringBuilder(
				" update EAI_LOG set TX_STATUS = '"+status+"' where EAI_ID in (select sst.EAI_ID from SST_RETRIGGER_BATCH_DETAILS sst where sst.BATCH_ID = ?) ");

		log.debug(sql.toString());

		Connection conn = null;
		long rowCount = -1;

		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ps.setString(1, batch.getId()+"");
			rowCount = ps.executeUpdate();
			ps.close();
			return rowCount;
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
