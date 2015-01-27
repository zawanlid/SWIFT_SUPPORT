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
import org.tm.swift.domain.Batch;
import org.tm.swift.domain.BatchDetail;
import org.tm.swift.domain.EaiLog;
import org.tm.swift.domain.EaiResponse;
import org.tm.swift.domain.SearchCriteria;
import org.tm.swift.util.StringUtils;

/**
 * 
 * @author DilNawaz
 * 
 */
@Repository("retriggerEngineDao")
public class RetriggerEngineDaoImpl extends JdbcDaoSupport implements RetriggerEngineDao {

	@Autowired
	private DataSource dataSource;

	@PostConstruct
	private void initialize() {
		setDataSource(dataSource);

	}

	private static Logger log = Logger.getLogger(RetriggerEngineDaoImpl.class.getName());

	public EaiLog findById(int eaiId) {

		String sql = "SELECT * FROM EAI_LOG WHERE eai_id = ?";

		log.debug(sql);

		try {
			return (EaiLog) getJdbcTemplate().queryForObject(sql, new Object[] { eaiId }, new EaiLog());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public List<EaiLog> getBatchDetails(SearchCriteria searchCriteria) {

		String sql = " SELECT e.* FROM EAI_LOG e, SST_RETRIGGER_BATCHES b, SST_RETRIGGER_BATCH_DETAILS bd" + "  where b.ID = bd.BATCH_ID and bd.EAI_ID = e.EAI_ID and b.NAME = ? ";

		log.debug(">>>>>>>>>>>>>> SQL >>>>>>>>>>> " + sql);

		List<EaiLog> eaiLogList = new ArrayList<EaiLog>();
		try {
			return EaiLog.getList(getJdbcTemplate().queryForList(sql, new Object[] { searchCriteria.getBatchName() }), eaiLogList);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public List<Batch> getBatches() {

		String sql = "SELECT * FROM SST_RETRIGGER_BATCHES WHERE ISACTIVE = '1'  and STATUS in ( 'NEW')";

		log.debug(sql);
		List<Batch> batchList = new ArrayList<Batch>();
		try {
			return Batch.getList(getJdbcTemplate().queryForList(sql), batchList);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public int updateBatchEAIListStatus(Batch batch, String newStatus) {

		StringBuilder sql = new StringBuilder(" update EAI_LOG set TX_STATUS = '" + newStatus
				+ "' where EAI_ID in (select sst.EAI_ID from SST_RETRIGGER_BATCH_DETAILS sst,DOCKET d where sst.BATCH_ID = ? and sst.EXT_MSG_ID = d.DOC_NUMBER and d.STATUS != 'Closed') and TX_STATUS NOT IN ('" + Constant.STATUS_NEW + "','"
				+ Constant.STATUS_PICKUP + "')  ");

		log.debug(sql.toString());

		try {
			return getJdbcTemplate().update(sql.toString(), new Object[] { batch.getId() });
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public int updateBatchDetailsStatusByBatchId(Batch batch, String newStatus, String oldStatus, String remarks) {

		StringBuilder sql = new StringBuilder("update SST_RETRIGGER_BATCH_DETAILS set status = '" + newStatus + "' , LAST_UPDATE_DATETIME = SYSDATE , REMARKS = '" + remarks + "' where BATCH_ID  = '" + batch.getId() + "'");

		if (StringUtils.isNotEmpty(oldStatus)) {
			sql.append(" and STATUS = '" + oldStatus + "' ");
		}
		log.debug(sql.toString());

		try {
			return getJdbcTemplate().update(sql.toString());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public int updateTraceBatch(final Batch batch, final List<BatchDetail> batchDetailList) {

		String batchDetailSql = " update SST_RETRIGGER_BATCH_DETAILS set status = ? , LAST_UPDATE_DATETIME = SYSDATE , REMARKS = ? where BATCH_ID  = ? and EAI_ID = ? ";
		String eaiSql = " update EAI_LOG set TX_STATUS = ? where EAI_ID in (select sst.EAI_ID from SST_RETRIGGER_BATCH_DETAILS sst,DOCKET d where sst.BATCH_ID = ? and sst.EAI_ID = ? and sst.EXT_MSG_ID = d.DOC_NUMBER and d.STATUS != 'Closed') and TX_STATUS NOT IN ('"
				+ Constant.STATUS_NEW + "','" + Constant.STATUS_PICKUP + "')  ";
		boolean isPartialSuccess = false;

		List<Object[]> batchDetailParams = new ArrayList<Object[]>();
		List<Object[]> eaiLogParams = new ArrayList<Object[]>();

		for (BatchDetail batchDetail : batchDetailList) {
			Object[] batchDetailParam = { batchDetail.getStatus(), batchDetail.getRemarks(), batch.getId(), batchDetail.getEaiId() };
			batchDetailParams.add(batchDetailParam);

			if (Constant.STATUS_SUCCESS.equals(batchDetail.getStatus())) {
				Object[] eaiLogParam = { Constant.STATUS_NEW, batch.getId(), batchDetail.getEaiId() };
				eaiLogParams.add(eaiLogParam);
			} else {
				isPartialSuccess = true;
			}
		}

		getJdbcTemplate().batchUpdate(eaiSql, eaiLogParams);
		getJdbcTemplate().batchUpdate(batchDetailSql, batchDetailParams);

		if (isPartialSuccess) {
			updateBatchStatus(batch, Constant.STATUS_PARTIALLY_SUCCESS, null, Constant.REMARKS_SUCCESS);
		} else {
			updateBatchStatus(batch, Constant.STATUS_SUCCESS, null, Constant.REMARKS_SUCCESS);
		}

		return 0;

	}

	public int updateBatchDetailsStatusById(String id, String newStatus, String oldStatus, String remarks) {

		StringBuilder sql = new StringBuilder("update SST_RETRIGGER_BATCH_DETAILS set status = '" + newStatus + "' , LAST_UPDATE_DATETIME = SYSDATE , REMARKS = '" + remarks + "' where ID  = '" + id + "'");

		if (StringUtils.isNotEmpty(oldStatus)) {
			sql.append(" and STATUS = '" + oldStatus + "' ");
		}
		log.debug(sql.toString());

		try {
			return getJdbcTemplate().update(sql.toString());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public List<EaiResponse> getEaiResponseList(Batch batch, String type) {

		String sql = " select * from SST_EAI_RESPONSES where TYPE = '" + type + "' and SOURCE_SYSTEM  = '" + batch.getSource()
				+ "' and IS_STATUS_UPDATE = 1 and IS_ACTIVE = 1 and UPDATE_SEQUENCE > 0 and AUDIT_PARAM1 is not null and AUDIT_PARAM2 is not null ";

		log.debug(">>>>>>>>>>>>>> SQL >>>>>>>>>>> " + sql);

		List<EaiResponse> list = new ArrayList<EaiResponse>();

		try {
			EaiResponse.getList(getJdbcTemplate().queryForList(sql), list);
			return list;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public int updateBatchStatus(Batch batch, String newStatus, String oldStatus, String remarks) {

		StringBuilder sql = new StringBuilder("update SST_RETRIGGER_BATCHES set status = '" + newStatus + "' , LAST_UPDATE_DATETIME = SYSDATE , REMARKS = '" + remarks + "' where ID  = '" + batch.getId() + "'");
		if (StringUtils.isNotEmpty(oldStatus)) {
			sql.append(" and STATUS = '" + oldStatus + "' ");
		}
		log.debug(sql.toString());

		try {
			return getJdbcTemplate().update(sql.toString());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public int updateBatchListStatus(List<Batch> batchList, String newStatus, String oldStatus, String remarks) {

		if (batchList == null || batchList.size() == 0) {
			return 0;
		}

		StringBuilder sql = new StringBuilder("update SST_RETRIGGER_BATCHES set status = '" + newStatus + "' , LAST_UPDATE_DATETIME = SYSDATE , REMARKS = '" + remarks + "' where ID in  (");

		int count = 1;
		for (Batch batch : batchList) {

			sql.append("'" + batch.getId() + "'");
			if (count < batchList.size())
				sql.append(",");

			count++;
		}
		sql.append(" ) ");

		if (StringUtils.isNotEmpty(oldStatus)) {
			sql.append(" and STATUS = '" + oldStatus + "' ");
		}
		log.debug(sql.toString());

		try {
			return getJdbcTemplate().update(sql.toString());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public List<EaiLog> getEaiList(Batch batch) {

		StringBuilder sql = new StringBuilder(" SELECT e.* FROM EAI_LOG e, SST_RETRIGGER_BATCHES b, SST_RETRIGGER_BATCH_DETAILS bd" + "  where b.ID = bd.BATCH_ID and bd.EAI_ID = e.EAI_ID and b.id = '" + batch.getId() + "' ");

		log.debug(">>>>>>>>>>>>>> SQL >>>>>>>>>>> " + sql);

		List<EaiLog> eaiLogList = new ArrayList<EaiLog>();
		try {
			return EaiLog.getList(getJdbcTemplate().queryForList(sql.toString()), eaiLogList);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public List<EaiLog> getEaiTraceList(EaiLog eaiLog, List<EaiResponse> eaiResponseList, boolean isTraceUp) {

		StringBuilder sql = new StringBuilder(" SELECT e.* FROM EAI_LOG e where e.EXT_MSG_ID = ? ");

		if (isTraceUp) {
			sql.append(" and e.AUDIT_DATETIME  > ? ");
		} else {
			sql.append(" and e.AUDIT_DATETIME  < ? ");
		}

		sql.append(" and e.EVENT_NAME in ( ");

		int count = 1;
		for (EaiResponse eaiResponse : eaiResponseList) {

			sql.append("'" + eaiResponse.getEventName() + "'");
			if (count < eaiResponseList.size())
				sql.append(",");

			count++;
		}
		sql.append(" ) ");

		log.debug(">>>>>>>>>>>>>> SQL >>>>>>>>>>> " + sql);

		List<EaiLog> eaiLogList = new ArrayList<EaiLog>();
		try {
			return EaiLog.getList(getJdbcTemplate().queryForList(sql.toString(), new Object[] { eaiLog.getExtMsgId(), eaiLog.getAuditDateTime() }), eaiLogList);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public int updateBatchEaiLogbyId(EaiLog eaiLog, String newStatus) {

		StringBuilder sql = new StringBuilder(" update EAI_LOG set TX_STATUS = '" + newStatus + "' where EAI_ID = ?  ");

		log.debug(sql.toString());

		try {
			return getJdbcTemplate().update(sql.toString(), new Object[] { eaiLog.getEaiId() });
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
