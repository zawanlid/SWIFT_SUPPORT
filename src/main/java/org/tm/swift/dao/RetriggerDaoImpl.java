package org.tm.swift.dao;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.tm.swift.constant.Constant;
import org.tm.swift.domain.EaiLog;
import org.tm.swift.domain.SearchCriteria;
import org.tm.swift.util.CommonUtils;
import org.tm.swift.util.StringUtils;

/**
 * 
 * @author DilNawaz
 * 
 */
@Repository("retriggerDao")
public class RetriggerDaoImpl extends JdbcDaoSupport implements RetriggerDao {

	@Autowired
	private DataSource dataSource;

	@PostConstruct
	private void initialize() {
		setDataSource(dataSource);
	}

	private static Logger log = Logger.getLogger(RetriggerDaoImpl.class.getName());

	public EaiLog findById(long eaiId) {

		String sql = "SELECT * FROM EAI_LOG WHERE eai_id = ?";

		log.debug(sql);

		try {
			return (EaiLog) getJdbcTemplate().queryForObject(sql, new Object[] {
				eaiId
			}, new EaiLog());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public List<EaiLog> getErrorList(SearchCriteria searchCriteria) throws Exception {

		List<EaiLog> eaiLogList = new ArrayList<EaiLog>();

		getErrorListWOTerminated(getErrorListQuery(searchCriteria), eaiLogList);
		getErrorListTerminated(getTerminatedListQuery(searchCriteria), eaiLogList);

		return eaiLogList;
	}

	public List<EaiLog> getErrorListWOTerminated(String sql, List<EaiLog> eaiLogList) {
		log.debug(">>>>>>>>>>>>>> SQL >>>>>>>>>>> " + sql);

		try {
			return EaiLog.getList(getJdbcTemplate().queryForList(sql), eaiLogList);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public List<EaiLog> getErrorListTerminated(String sql, List<EaiLog> eaiLogList) {
		log.debug(">>>>>>>>>>>>>> SQL >>>>>>>>>>> " + sql);

		try {
			return EaiLog.getList(getJdbcTemplate().queryForList(sql), eaiLogList);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	private String getErrorListQuery(SearchCriteria searchCriteria) throws Exception {

		String dateFrom = CommonUtils.convertDateToString(searchCriteria.getAuditDateFrom(), "yyyy/MM/dd");
		String dateTo = CommonUtils.convertDateToString(searchCriteria.getAuditDateTo(), "yyyy/MM/dd");

		StringBuilder query = new StringBuilder("SELECT * FROM EAI_LOG WHERE ");
		query.append(" ( select count(DOCKET.DOC_NUMBER) from docket where DOCKET.DOC_NUMBER = EAI_LOG.EXT_MSG_ID and DOCKET.STATUS != 'Closed' ) != 0 ");
		query.append(" and AUDIT_DATETIME between TO_DATE ('" + dateFrom + "', 'yyyy/mm/dd') AND TO_DATE ('" + dateTo + "', 'yyyy/mm/dd')  ");
		query.append(" and TX_STATUS not in ( '" + Constant.STATUS_NEW + "','" + Constant.STATUS_PICKUP + "' , '" + Constant.STATUS_TERMINATED + "' ) ");

		if (searchCriteria != null) {
			if (Constant.SOURCE_SYSTEM_ICP.equals(searchCriteria.getSource())) {
				query.append(" and EVENT_NAME = '" + Constant.EVENT_NAME_ICP + "' ");
			} else if (Constant.SOURCE_SYSTEM_NOVA.equals(searchCriteria.getSource())) {
				query.append(" and EVENT_NAME = '" + Constant.EVENT_NAME_NOVA + "' ");
			}
		}

		if (StringUtils.isNotEmpty(searchCriteria.getTroubleTickets())) {

			String troubleTicketList[] = searchCriteria.getTroubleTickets().split(",");
			StringBuilder troubleTicketCriteria = null;

			if (troubleTicketList.length > 0) {
				troubleTicketCriteria = new StringBuilder("and EXT_MSG_ID in ( ");
				for (String troubleticket : troubleTicketList) {
					troubleTicketCriteria.append("'" + troubleticket.trim() + "',");
				}
				troubleTicketCriteria.append("'" + troubleTicketList[0] + "') ");
				query.append(troubleTicketCriteria.toString());
			}
		}

		List<String> additionalParams = searchCriteria.getAdditionalParams();
		int counter = 1;
		if (additionalParams.size() > 0) {
			query.append(" and ( ");
			for (String param : additionalParams) {
				param = param.trim();
				query.append(" AUDIT_PARAM2 like '%" + param + "%' ");

				if (counter < additionalParams.size())
					query.append(" or ");

				counter++;

				if (StringUtils.isNotEmpty(param) && !isDuplicateAdditionalParam(searchCriteria, param, Constant.EAI_RESPONSE_ERROR)) {
					insertAdditionalParam(searchCriteria, param);
				}
			}
			query.append(" ) ");
		}

		return query.toString();
	}

	private String getTerminatedListQuery(SearchCriteria searchCriteria) {

		String dateFrom = CommonUtils.convertDateToString(searchCriteria.getAuditDateFrom(), "yyyy/MM/dd");
		String dateTo = CommonUtils.convertDateToString(searchCriteria.getAuditDateTo(), "yyyy/MM/dd");

		StringBuilder query = new StringBuilder("SELECT * FROM EAI_LOG WHERE ");
		query.append(" ( select count(DOCKET.DOC_NUMBER) from docket where DOCKET.DOC_NUMBER = EAI_LOG.EXT_MSG_ID and DOCKET.STATUS != 'Closed' ) != 0 ");
		query.append(" and AUDIT_DATETIME between TO_DATE ('" + dateFrom + "', 'yyyy/mm/dd') AND TO_DATE ('" + dateTo + "', 'yyyy/mm/dd')  ");
		query.append(" and TX_STATUS = '"+ Constant.STATUS_TERMINATED + "' and TX_STATUS not in ( '" + Constant.STATUS_NEW + "','" + Constant.STATUS_PICKUP + "'  ) ");
		
		if (searchCriteria != null) {
			if (Constant.SOURCE_SYSTEM_ICP.equals(searchCriteria.getSource())) {
				query.append(" and EVENT_NAME = '" + Constant.EVENT_NAME_ICP + "' ");
			} else if (Constant.SOURCE_SYSTEM_NOVA.equals(searchCriteria.getSource())) {
				query.append(" and EVENT_NAME = '" + Constant.EVENT_NAME_NOVA + "' ");
			}
		}

		if (StringUtils.isNotEmpty(searchCriteria.getTroubleTickets())) {

			String troubleTicketList[] = searchCriteria.getTroubleTickets().split(",");
			StringBuilder troubleTicketCriteria = null;

			if (troubleTicketList.length > 0) {
				troubleTicketCriteria = new StringBuilder("and EXT_MSG_ID in ( ");
				for (String troubleticket : troubleTicketList) {
					troubleTicketCriteria.append("'" + troubleticket.trim() + "',");
				}
				troubleTicketCriteria.append("'" + troubleTicketList[0] + "') ");
				query.append(troubleTicketCriteria.toString());
			}
		}

		return query.toString();
	}

	public List<String> getEAIResponseParamList(String type) {

		String sql = " select Distinct(AUDIT_PARAM2) as AUDIT_PARAM2 from SST_EAI_RESPONSES where TYPE = '" + type + "' and ISACTIVE = 1 ";

		log.debug(">>>>>>>>>>>>>> SQL >>>>>>>>>>> " + sql);

		log.debug(sql);

		List<String> list = new ArrayList<String>();

		try {
			list = getJdbcTemplate().queryForList(sql, String.class);
			return list;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	public List<String> getEventNameList(String type) {

		String sql = " select Distinct(EVENT_NAME) as EVENT_NAME from SST_EAI_RESPONSES where TYPE = '" + type + "' and ISACTIVE = 1 ";

		log.debug(">>>>>>>>>>>>>> SQL >>>>>>>>>>> " + sql);

		List<String> list = new ArrayList<String>();

		try {
			list = getJdbcTemplate().queryForList(sql, String.class);
			return list;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private void insertAdditionalParam(SearchCriteria searchCriteria, String param) {

		String sql = "insert into SST_EAI_RESPONSES (SOURCE_SYSTEM,TYPE,EVENT_NAME,CREATED_DATETIME,AUDIT_PARAM2,ISACTIVE) " + "  values (?,'ERROR',?,SYSDATE,?,1)";

		log.debug(">>>>>>>>>>>>>> SQL >>>>>>>>>>> " + sql);

		try {
			getJdbcTemplate().update(sql, new Object[] {
					searchCriteria.getSource(), searchCriteria.getEventName(), param
			});
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private boolean isDuplicateAdditionalParam(SearchCriteria searchCriteria, String param, String type) {

		String sql = " select AUDIT_PARAM2 from SST_EAI_RESPONSES where SOURCE_SYSTEM = ? and EVENT_NAME = ? and AUDIT_PARAM2 = ? and TYPE = ? ";

		log.debug(">>>>>>>>>>>>>> SQL >>>>>>>>>>> " + sql);
		List<String> list = new ArrayList<String>();
		try {
			list = getJdbcTemplate().queryForList(sql, new Object[] {
					searchCriteria.getSource(), searchCriteria.getEventName(), param, type
			}, String.class);
			return (list != null && list.size() > 0) ? true : false;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	@SuppressWarnings("deprecation")
	public void retriggerErrorList(SearchCriteria searchCriteria, List<EaiLog> eaiList) {

		final String sql = "insert into SST_RETRIGGER_BATCHES (name,created_by,CREATED_DATETIME,status,LAST_UPDATE_DATETIME,isactive,SOURCE_SYSTEM,EVENT_NAME)  " + " values (?,?,SYSDATE,'"
				+ Constant.STATUS_NEW + "',null,1,?,?)";
		long id = -1;
		try {

			getJdbcTemplate().update(sql, new Object[] {
					searchCriteria.getBatchName(), searchCriteria.getCreatedBy(), searchCriteria.getSource(), searchCriteria.getEventName()
			});

			id = getJdbcTemplate().queryForLong(" SELECT AUTO_SST_RETRIGGER_BATCHES.CURRVAL from DUAL");
			log.debug("insert new batch detail records [Batch ID]: " + id);

			for (EaiLog eaiLog : eaiList) {

				getJdbcTemplate().update("insert into SST_RETRIGGER_BATCH_DETAILS (BATCH_ID,EAI_ID,EXT_MSG_ID,STATUS,LAST_UPDATE_DATETIME) values (?,?,?,?,null)", new Object[] {
						id, eaiLog.getEaiId(), eaiLog.getExtMsgId(), Constant.STATUS_NEW,
				});

			}

		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}
}
