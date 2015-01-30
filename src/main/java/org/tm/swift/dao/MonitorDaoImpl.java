package org.tm.swift.dao;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.tm.swift.domain.Batch;
import org.tm.swift.domain.EaiLog;
import org.tm.swift.domain.Monitor;
import org.tm.swift.domain.SearchCriteria;
import org.tm.swift.util.StringUtils;

/**
 * 
 * @author DilNawaz
 *
 */
@Repository("monitorDao")
public class MonitorDaoImpl extends JdbcDaoSupport implements MonitorDao {

	@Autowired
	private DataSource dataSource;

	@PostConstruct
	private void initialize() {
		setDataSource(dataSource);

	}

	private static Logger log = Logger
			.getLogger(MonitorDaoImpl.class.getName());

	public List<Monitor> getBatchDetails(SearchCriteria searchCriteria) {

		StringBuilder query = new StringBuilder(
				" SELECT e.*,bd.LAST_UPDATE_DATETIME,bd.STATUS,bd.REMARKS FROM EAI_LOG e, SST_RETRIGGER_BATCHES b, SST_RETRIGGER_BATCH_DETAILS bd"
						+ "  where b.ID = bd.BATCH_ID and bd.EAI_ID = e.EAI_ID and b.id = '"
						+ searchCriteria.getBatchName() + "' ");

		if (StringUtils.isNotEmpty(searchCriteria.getTroubleTickets())) {

			String troubleTicketList[] = searchCriteria.getTroubleTickets()
					.split(",");
			StringBuilder troubleTicketCriteria = null;

			if (troubleTicketList.length > 0) {
				troubleTicketCriteria = new StringBuilder(
						"and e.EXT_MSG_ID in ( ");
				for (String troubleticket : troubleTicketList) {
					troubleTicketCriteria.append("'" + troubleticket.trim()
							+ "',");
				}
				troubleTicketCriteria
						.append("'" + troubleTicketList[0] + "') ");
				query.append(troubleTicketCriteria.toString());
			}
		}

		log.debug(query.toString());

		List<Monitor> monitorList = new ArrayList<Monitor>();

		log.debug(">>>>>>>>>>>>>> SQL >>>>>>>>>>> " + query.toString());

		try {
			return Monitor.getList(
					getJdbcTemplate().queryForList(query.toString()), monitorList);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public Batch getBatch(SearchCriteria searchCriteria) {

		String sql = "SELECT * FROM SST_RETRIGGER_BATCHES WHERE id = ?";

		log.debug(sql);

		try {
			return (Batch) getJdbcTemplate()
					.queryForObject(sql,
							new Object[] { searchCriteria.getBatchName() },
							new Batch());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public List<Batch> getDistinctBatch() {

		String sql = " SELECT * FROM SST_RETRIGGER_BATCHES WHERE ISACTIVE = '1'  order by CREATED_DATETIME desc ";

		log.debug(sql);

		List<Batch> batchList = new ArrayList<Batch>();

		try {
			return Batch.getList(
					getJdbcTemplate().queryForList(sql), batchList);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
