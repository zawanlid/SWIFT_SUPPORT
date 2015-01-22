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
import org.dnawaz.constant.Constant;
import org.dnawaz.util.CommonUtils;
import org.dnawaz.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

@Repository("retriggerDao")
public class RetriggerDaoImpl extends JdbcDaoSupport implements RetriggerDao {

	@Autowired
	private DataSource dataSource;

	@PostConstruct
	private void initialize() {
		setDataSource(dataSource);

	}

	private static Logger log = Logger.getLogger(RetriggerDaoImpl.class
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

	public List<EaiLog> getErrorList(SearchCriteria searchCriteria) {

		List<EaiLog> eaiLogList = new ArrayList<EaiLog>();
		
		getErrorListWOTerminated(getErrorListQuery(searchCriteria), eaiLogList);
		getErrorListTerminated(getTerminatedListQuery(searchCriteria), eaiLogList);
		
		return eaiLogList;
	}
	
	public List<EaiLog> getErrorListWOTerminated(String sql, List<EaiLog> eaiLogList) {
		log.debug(">>>>>>>>>>>>>> SQL >>>>>>>>>>> " + sql);

		Connection conn = null;

		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			EaiLog eaiLog = null;
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

	public List<EaiLog> getErrorListTerminated(String sql, List<EaiLog> eaiLogList) {
		log.debug(">>>>>>>>>>>>>> SQL >>>>>>>>>>> " + sql);

		Connection conn = null;

		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			EaiLog eaiLog = null;
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

	
	private String getErrorListQuery(SearchCriteria searchCriteria) {

		String dateFrom = CommonUtils.convertDateToString(
				searchCriteria.getAuditDateFrom(), "yyyy/MM/dd");
		String dateTo = CommonUtils.convertDateToString(
				searchCriteria.getAuditDateTo(), "yyyy/MM/dd");

		StringBuilder query = new StringBuilder(
				"SELECT * FROM EAI_LOG WHERE AUDIT_DATETIME between TO_DATE ('"
						+ dateFrom + "', 'yyyy/mm/dd') AND TO_DATE ('" + dateTo
						+ "', 'yyyy/mm/dd')  and TX_STATUS not in ( 'NEW','PICKUP' ) ");

		if (searchCriteria != null) {
			if (Constant.SOURCE_SYSTEM_ICP.equals(searchCriteria.getSource())) {
				query.append(" and EVENT_NAME = '" + Constant.EVENT_NAME_ICP
						+ "' ");
			} else if (Constant.SOURCE_SYSTEM_NOVA.equals(searchCriteria
					.getSource())) {
				query.append(" and EVENT_NAME = '" + Constant.EVENT_NAME_NOVA
						+ "' ");
			}
		}

		if (StringUtils.isNotEmpty(searchCriteria.getTroubleTickets())) {

			String troubleTicketList[] = searchCriteria.getTroubleTickets()
					.split(",");
			StringBuilder troubleTicketCriteria = null;

			if (troubleTicketList.length > 0) {
				troubleTicketCriteria = new StringBuilder(
						"and EXT_MSG_ID in ( ");
				for (String troubleticket : troubleTicketList) {
					troubleTicketCriteria.append("'" + troubleticket.trim()
							+ "',");
				}
				troubleTicketCriteria
						.append("'" + troubleTicketList[0] + "') ");
				query.append(troubleTicketCriteria.toString());
			}
		}

		List<String> errorParams = getErrorParams(searchCriteria.getSource());
		if (errorParams.size() > 0) {
			int counter = 1;
			query.append(" and (");
			for (String param : errorParams) {
				query.append(" AUDIT_PARAM2 like '%" + param + "%' ");
				if (counter < errorParams.size())
					query.append(" or ");
				counter++;
			}

			List<String> additionalParams = searchCriteria
					.getAdditionalParams();
			counter = 1;
			if (additionalParams != null) {
				for (String param : additionalParams) {
					if (counter <= additionalParams.size())
						query.append(" or ");
					query.append(" AUDIT_PARAM2 like '%" + param + "%' ");

					counter++;

					if (searchCriteria.getSaveParam() != null
							&& searchCriteria.getSaveParam()) {
						if (StringUtils.isNotEmpty(param)) {
							insertAdditionalParam(searchCriteria.getSource(),
									param);
						}
					}
				}

			}

			query.append(" ) ");
		} else {

			List<String> additionalParams = searchCriteria
					.getAdditionalParams();
			int counter = 1;
			if (additionalParams.size() > 0) {
				query.append(" and ( ");
				for (String param : additionalParams) {
					query.append(" AUDIT_PARAM2 like '%" + param + "%' ");

					if (counter < additionalParams.size())
						query.append(" or ");

					counter++;

					if (searchCriteria.getSaveParam()) {
						if (StringUtils.isNotEmpty(param)) {
							insertAdditionalParam(searchCriteria.getSource(),
									param);
						}
					}
				}
				query.append(" ) ");
			}

		}

		return query.toString();
	}
	
	private String getTerminatedListQuery(SearchCriteria searchCriteria) {

		String dateFrom = CommonUtils.convertDateToString(
				searchCriteria.getAuditDateFrom(), "yyyy/MM/dd");
		String dateTo = CommonUtils.convertDateToString(
				searchCriteria.getAuditDateTo(), "yyyy/MM/dd");

		StringBuilder query = new StringBuilder(
				" select *  from EAI_LOG where TX_STATUS = 'TERMINATED' and AUDIT_DATETIME between TO_DATE ('"
						+ dateFrom + "', 'yyyy/mm/dd') AND TO_DATE ('" + dateTo
						+ "', 'yyyy/mm/dd')  and TX_STATUS not in ( 'NEW','PICKUP' ) ");

		if (searchCriteria != null) {
			if (Constant.SOURCE_SYSTEM_ICP.equals(searchCriteria.getSource())) {
				query.append(" and EVENT_NAME = '" + Constant.EVENT_NAME_ICP
						+ "' ");
			} else if (Constant.SOURCE_SYSTEM_NOVA.equals(searchCriteria
					.getSource())) {
				query.append(" and EVENT_NAME = '" + Constant.EVENT_NAME_NOVA
						+ "' ");
			}
		}

		if (StringUtils.isNotEmpty(searchCriteria.getTroubleTickets())) {

			String troubleTicketList[] = searchCriteria.getTroubleTickets()
					.split(",");
			StringBuilder troubleTicketCriteria = null;

			if (troubleTicketList.length > 0) {
				troubleTicketCriteria = new StringBuilder(
						"and EXT_MSG_ID in ( ");
				for (String troubleticket : troubleTicketList) {
					troubleTicketCriteria.append("'" + troubleticket.trim()
							+ "',");
				}
				troubleTicketCriteria
						.append("'" + troubleTicketList[0] + "') ");
				query.append(troubleTicketCriteria.toString());
			}
		}

		
		return query.toString();
	}

	private List<String> getErrorParams(String sourceSystem) {

		String sql = " select AUDIT_PARAM from SST_EAI_RESPONSES where TYPE = 'ERROR' and ISACTIVE = 1 and EVENT_NAME = ? ";

		log.debug(">>>>>>>>>>>>>> SQL >>>>>>>>>>> " + sql);

		Connection conn = null;

		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);

			if (Constant.SOURCE_SYSTEM_ICP.equals(sourceSystem)) {
				ps.setString(1, Constant.EVENT_NAME_ICP);
			} else if (Constant.SOURCE_SYSTEM_NOVA.equals(sourceSystem)) {
				ps.setString(1, Constant.EVENT_NAME_NOVA);
			} else {
				ps.setString(1, "NA");
			}

			List<String> errorParams = new ArrayList<String>();
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				if (StringUtils.isNotEmpty(rs.getString("AUDIT_PARAM")))
					errorParams.add(rs.getString("AUDIT_PARAM"));
			}
			rs.close();
			ps.close();
			return errorParams;
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

	private void insertAdditionalParam(String sourceSystem, String param) {

		String sql = "insert into SST_EAI_RESPONSES (TYPE,EVENT_NAME,CREATED_DATETIME,AUDIT_PARAM) "
				+ "  values ('ERROR',?,SYSDATE,?)";

		log.debug(">>>>>>>>>>>>>> SQL >>>>>>>>>>> " + sql);

		Connection conn = null;

		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			if (Constant.SOURCE_SYSTEM_ICP.equals(sourceSystem)) {
				ps.setString(1, Constant.EVENT_NAME_ICP);
			} else if (Constant.SOURCE_SYSTEM_NOVA.equals(sourceSystem)) {
				ps.setString(1, Constant.EVENT_NAME_NOVA);
			} else {
				ps.setString(1, "NA");
			}

			ps.setString(2, param);

			ps.executeUpdate();
			ps.close();
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

	public static void main(String[] args) {

		String searchCriteria = "1-23232323,1-565656";
		if (StringUtils.isNotEmpty(searchCriteria)) {

			String arr[] = searchCriteria.split(",");
			StringBuilder troubleTicketCriteria = null;

			if (arr.length > 0) {
				troubleTicketCriteria = new StringBuilder(
						"and EXT_MSG_ID in ( ");
				for (String troubleticket : arr) {
					troubleTicketCriteria.append("'" + troubleticket + "',");
				}
				troubleTicketCriteria.append("'" + arr[0] + "') ");
			}
			log.debug(troubleTicketCriteria.toString());
		}

	}

	public void retriggerErrorList(SearchCriteria searchCriteria,
			List<EaiLog> eaiList) {

		final String sql = "insert into SST_RETRIGGER_BATCHES (name,created_by,CREATED_DATETIME,status,LAST_UPDATE_DATETIME,isactive,SOURCE_SYSTEM)  "
				+ " values (?,?,SYSDATE,'NEW',SYSDATE,1,?)";
		Connection conn = null;
		long id = -1;
		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql,
					new String[] { "ID" });
			ps.setString(1, searchCriteria.getBatchName());
			ps.setString(2, searchCriteria.getCreatedBy());
			if (Constant.EVENT_NAME_ICP.equals(eaiList.get(0).getEventName())) {
				ps.setString(3, Constant.SOURCE_SYSTEM_ICP);
			} else if (Constant.EVENT_NAME_NOVA.equals(eaiList.get(0).getEventName())) {
				ps.setString(3, Constant.SOURCE_SYSTEM_NOVA);
			} else {
				ps.setString(3, "NA");
			}
			
			int executeUpdate = ps.executeUpdate();
			ResultSet rs = ps.getGeneratedKeys();
			if (rs.next()) {
				// The generated id
				id = rs.getLong(1);
				log.debug("executeUpdate: " + executeUpdate + ", id: " + id);
			}

			int counter = 1;

			ps = conn
					.prepareStatement("insert into SST_RETRIGGER_BATCH_DETAILS (BATCH_ID,EAI_ID,EXT_MSG_ID,STATUS) values (?,?,?,?)");

			
			for (EaiLog eaiLog : eaiList) {
				log.debug("insert new batch detail record");
				counter = 1;
				ps.clearParameters();
				ps.setString(counter++, id + "");
				ps.setString(counter++, eaiLog.getEaiId()+"");
				ps.setString(counter++, eaiLog.getExtMsgId());
				ps.setString(counter++, "NEW");
				ps.executeUpdate();
			}

			ps.close();
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
