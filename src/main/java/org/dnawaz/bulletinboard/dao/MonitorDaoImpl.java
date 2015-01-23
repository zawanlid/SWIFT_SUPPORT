package org.dnawaz.bulletinboard.dao;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.dnawaz.bulletinboard.domain.Batch;
import org.dnawaz.bulletinboard.domain.EaiLog;
import org.dnawaz.bulletinboard.domain.SearchCriteria;
import org.dnawaz.util.StringUtils;
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

	private static Logger log = Logger
			.getLogger(MonitorDaoImpl.class.getName());

	public List<EaiLog> getBatchDetails(SearchCriteria searchCriteria) {

		StringBuilder query = new StringBuilder(
				" SELECT e.* FROM EAI_LOG e, SST_RETRIGGER_BATCHES b, SST_RETRIGGER_BATCH_DETAILS bd"
						+ "  where b.ID = bd.BATCH_ID and bd.EAI_ID = e.EAI_ID and b.NAME = '"
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

		List<EaiLog> eaiList = new ArrayList<EaiLog>();

		log.debug(">>>>>>>>>>>>>> SQL >>>>>>>>>>> " + query.toString());

		try {
			return EaiLog.getList(
					getJdbcTemplate().queryForList(query.toString()), eaiList);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public Batch getBatch(SearchCriteria searchCriteria) {

		String sql = "SELECT * FROM SST_RETRIGGER_BATCHES WHERE NAME = ?";

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

	public List<String> getDistinctBatch() {

		String sql = " SELECT DISTINCT(NAME) as NAME FROM SST_RETRIGGER_BATCHES WHERE ISACTIVE = '1'  ";

		log.debug(sql);

		List<String> batchList = new ArrayList<String>();

		try {
			batchList = getJdbcTemplate().queryForList(sql, String.class);
			return batchList;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
