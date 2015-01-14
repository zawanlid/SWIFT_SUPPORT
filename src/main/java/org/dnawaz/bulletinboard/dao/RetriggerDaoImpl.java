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
import org.dnawaz.bulletinboard.domain.EaiLog;
import org.dnawaz.bulletinboard.domain.SearchCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

@Repository("retriggerDao")
public class RetriggerDaoImpl extends JdbcDaoSupport implements RetriggerDao{

	@Autowired
	private DataSource dataSource;

	@PostConstruct
	private void initialize() {
		setDataSource(dataSource);

	}

	private static Logger log = Logger.getLogger(RetriggerDaoImpl.class.getName());
	
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

	public List<EaiLog> getErrorList(SearchCriteria searchCriteria) {

		String sql = getErrorListQuery(searchCriteria);

		log.debug(">>>>>>>>>>>>>> SQL >>>>>>>>>>> " + sql);
		
		Connection conn = null;

		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			
			List<EaiLog> eaiLogList = new ArrayList<EaiLog>();
			EaiLog eaiLog = null;
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				eaiLog = new EaiLog();
				eaiLog.setEaiId(rs.getInt("EAI_ID"));
				eaiLog.setAuditParam1("\"" + rs.getString("AUDIT_PARAM1") + "\"");
				eaiLog.setAuditParam2("\"" + rs.getString("AUDIT_PARAM2") + "\"");
				eaiLog.setExtMsgId(rs.getString("EXT_MSG_ID"));
				eaiLog.setEventName(rs.getString("EVENT_NAME"));
				eaiLog.setAuditDateTime(rs.getDate("AUDIT_DATETIME"));
				eaiLog.setEaiEndpoint(rs.getString("EAI_ENDPOINT"));
				eaiLog.setTxStatus(rs.getString("TX_STATUS"));
				eaiLogList.add(eaiLog);
			}
			rs.close();
			ps.close();
			return eaiLogList;
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
	
	private String getErrorListQuery(SearchCriteria searchCriteria){
		
		StringBuilder query = new StringBuilder("SELECT * FROM EAI_LOG WHERE 1=1 ");
		
		if (searchCriteria != null) { 
			if ("ICP".equals(searchCriteria.getSource())){
				query.append(" and EVENT_NAME = 'evUpdateTRfrSWF' ");
			} else if ("NOVA".equals(searchCriteria.getSource())){
				query.append(" and EVENT_NAME = 'SwiftNovaUpdateCTT' ");
			}
		}
		
		return query.toString();
	}
}
