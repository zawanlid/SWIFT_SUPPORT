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
import org.tm.swift.domain.SearchCriteria;

/**
 * 
 * @author DilNawaz
 * 
 */
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

		log.debug(sql);

		try {
			return (EaiLog) getJdbcTemplate().queryForObject(sql,
					new Object[] { eaiId }, new EaiLog());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public List<EaiLog> getBatchDetails(SearchCriteria searchCriteria) {

		String sql = " SELECT e.* FROM EAI_LOG e, SST_RETRIGGER_BATCHES b, SST_RETRIGGER_BATCH_DETAILS bd"
				+ "  where b.ID = bd.BATCH_ID and bd.EAI_ID = e.EAI_ID and b.NAME = ? ";

		log.debug(">>>>>>>>>>>>>> SQL >>>>>>>>>>> " + sql);

		List<EaiLog> eaiLogList = new ArrayList<EaiLog>();
		try {
			return EaiLog.getList(
					getJdbcTemplate().queryForList(sql,
							new Object[] { searchCriteria.getBatchName() }),
					eaiLogList);
		} catch (Exception e) {
			throw new RuntimeException(e);
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

		try {
			return getJdbcTemplate().update(sql.toString());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public List<Batch> getBatches() {

		String sql = "SELECT * FROM SST_RETRIGGER_BATCHES WHERE ISACTIVE = '1'  and STATUS in ( 'NEW')";

		log.debug(sql);
		List<Batch> batchList = new ArrayList<Batch>();
		try {
			return Batch.getList(
					getJdbcTemplate().queryForList(sql),
					batchList);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public long updateBatchEAIListStatus(Batch batch, String status) {

		StringBuilder sql = new StringBuilder(
				" update EAI_LOG set TX_STATUS = '"
						+ status
						+ "' where EAI_ID in (select sst.EAI_ID from SST_RETRIGGER_BATCH_DETAILS sst where sst.BATCH_ID = ?) ");

		log.debug(sql.toString());

		try {
			return getJdbcTemplate().update(sql.toString(),new Object[]{batch.getId()});
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
