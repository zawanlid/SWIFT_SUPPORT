package org.tm.swift.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tm.swift.constant.Constant;
import org.tm.swift.dao.RetriggerEngineDao;
import org.tm.swift.domain.Batch;
import org.tm.swift.domain.BatchDetail;
import org.tm.swift.domain.EaiLog;
import org.tm.swift.domain.EaiResponse;

/**
 * 
 * @author DilNawaz
 * 
 */
@Service
public class IcpRetriggerService {

	private Logger log = Logger.getLogger(IcpRetriggerService.class.getName());

	@Autowired
	private RetriggerEngineDao retriggerEngineDao;

	public void process(Batch batch) {

		log.debug(" Process ICP Retrigger ");
		List<EaiResponse> eaiResponseList = retriggerEngineDao.getEaiResponseList(batch, Constant.EAI_RESPONSE_SUCCESS);

		if (eaiResponseList != null && eaiResponseList.size() > 0) {
			processStatusUpdate(batch, eaiResponseList);
		} else {
			processNonStatusUpdate(batch);
		}

	}

	private void processNonStatusUpdate(Batch batch) {
		log.debug(" Process Non Status Update Retrigger ");
		retriggerEngineDao.updateBatchEAIListStatus(batch, Constant.STATUS_NEW);
		retriggerEngineDao.updateBatchDetailsStatusByBatchId(batch, Constant.STATUS_SUCCESS, null, Constant.REMARKS_SUCCESS);
		retriggerEngineDao.updateBatchStatus(batch, Constant.STATUS_SUCCESS, null, Constant.REMARKS_SUCCESS);
	}

	private void processStatusUpdate(Batch batch, List<EaiResponse> eaiResponseList) {
		log.debug(" Process Status Update Retrigger ");

		List<EaiLog> eaiList = retriggerEngineDao.getEaiList(batch);
		List<BatchDetail> traceList = new ArrayList<BatchDetail>();
		BatchDetail batchDetail = null;

		for (EaiLog eaiLog : eaiList) {
			Long updateSeq = getUpdateSequence(eaiLog, eaiResponseList);

			if (updateSeq != null) {

				traceList.add(traceEaiLog(eaiLog, eaiResponseList, updateSeq));

			} else {
				// data configuration not found for sequence update
				batchDetail = new BatchDetail();
				batchDetail.setEaiId(eaiLog.getEaiId());
				batchDetail.setExtMsgId(eaiLog.getExtMsgId());
				batchDetail.setStatus(Constant.STATUS_SUCCESS);
				batchDetail.setRemarks(Constant.REMARKS_SUCCESS);
				traceList.add(batchDetail);
			}
		}

		// update batch, batch detail and eai log accordingly.
		retriggerEngineDao.updateTraceBatch(batch, traceList);
	}

	

	private Long getUpdateSequence(EaiLog eaiLog, List<EaiResponse> eaiResponseList) {

		Long updateSeq = null;

		for (EaiResponse eaiResponse : eaiResponseList) {
			if (eaiLog.getAuditParam1().contains(eaiResponse.getAuditParam1()) && eaiLog.getEventName().equals(eaiResponse.getEventName())) {
				updateSeq = eaiResponse.getUpdateSequence();
				return updateSeq;
			}
		}
		return updateSeq;
	}

	private BatchDetail traceEaiLog(EaiLog eaiLog, List<EaiResponse> eaiResponseList, long updateSeq) {
		BatchDetail batchDetail = null;

		List<EaiResponse> traceUpRespList = new ArrayList<EaiResponse>();
		List<EaiResponse> traceDownRespList = new ArrayList<EaiResponse>();
		for (EaiResponse eaiResponse : eaiResponseList) {
			if (eaiResponse.getUpdateSequence() >= updateSeq) {
				traceUpRespList.add(eaiResponse);
			} else if (eaiResponse.getUpdateSequence() < updateSeq) {
				traceDownRespList.add(eaiResponse);
			}
		}

		log.info(" Trace UP Size : " + traceUpRespList.size() + " and Trace Down Size : " + traceDownRespList.size());

		if (traceUpRespList.size() > 0) {
			batchDetail = traceUp(eaiLog, traceUpRespList);
		}

		if (traceDownRespList.size() > 0) {
			batchDetail = traceDown(eaiLog, traceDownRespList);
		}

		if (batchDetail == null) {
			// no traces found.
			batchDetail = new BatchDetail();
			batchDetail.setEaiId(eaiLog.getEaiId());
			batchDetail.setExtMsgId(eaiLog.getExtMsgId());
			batchDetail.setStatus(Constant.STATUS_SUCCESS);
			batchDetail.setRemarks(Constant.REMARKS_SUCCESS);
		}

		return batchDetail;

	}

	/**
	 * trace up eai_log to find successful update to source system and don't
	 * re-trigger current eai id.
	 * 
	 * @param eaiLog
	 * @param traceUpRespList
	 * @return
	 */
	private BatchDetail traceUp(EaiLog eaiLog, List<EaiResponse> traceUpRespList) {

		BatchDetail batchDetail = null;
		List<EaiLog> eaiTraceList = retriggerEngineDao.getEaiTraceList(eaiLog, traceUpRespList, true);

		for (EaiLog eaiTrace : eaiTraceList) {
			for (EaiResponse eaiResponse : traceUpRespList) {
				if (eaiTrace.getAuditParam1().contains(eaiResponse.getAuditParam1()) && eaiTrace.getAuditParam2().contains(eaiResponse.getAuditParam2())
						&& eaiTrace.getEventName().equals(eaiResponse.getEventName())) {

					batchDetail = new BatchDetail();
					batchDetail.setEaiId(eaiLog.getEaiId());
					batchDetail.setExtMsgId(eaiLog.getExtMsgId());
					batchDetail.setRemarks("Trace Up (SUCCESS) - " + eaiTrace.getEaiId());
					batchDetail.setStatus(Constant.STATUS_TERMINATED);
					return batchDetail;
				}
			}

		}

		return batchDetail;
	}

	private BatchDetail traceDown(EaiLog eaiLog, List<EaiResponse> traceDownRespList) {

		BatchDetail batchDetail = null;
		List<EaiLog> eaiTraceList = retriggerEngineDao.getEaiTraceList(eaiLog, traceDownRespList, false);
		Map<Long, EaiLog> errorMap = new HashMap<Long, EaiLog>();

		// get error traces for all update sequences
		for (EaiLog eaiTrace : eaiTraceList) {
			for (EaiResponse eaiResponse : traceDownRespList) {
				if (eaiTrace.getEventName().equals(eaiResponse.getEventName()) && eaiTrace.getAuditParam1().contains(eaiResponse.getAuditParam1())
						&& !eaiTrace.getAuditParam2().contains(eaiResponse.getAuditParam2())) {
					errorMap.put(eaiResponse.getUpdateSequence(), eaiTrace);
				}
			}
		}

		// remove error traces if success trace found for selected update
		// sequence
		for (EaiLog eaiTrace : eaiTraceList) {
			for (EaiResponse eaiResponse : traceDownRespList) {
				if (eaiTrace.getEventName().equals(eaiResponse.getEventName()) && eaiTrace.getAuditParam1().contains(eaiResponse.getAuditParam1())
						&& eaiTrace.getAuditParam2().contains(eaiResponse.getAuditParam2())) {
					errorMap.remove(eaiResponse.getUpdateSequence());
				}
			}
		}

		// check if errors found in down trace.
		if (!errorMap.isEmpty()) {

			batchDetail = new BatchDetail();
			batchDetail.setEaiId(eaiLog.getEaiId());
			batchDetail.setExtMsgId(eaiLog.getExtMsgId());
			batchDetail.setStatus(Constant.STATUS_RETRY);

			StringBuilder remarks = new StringBuilder("Retriggered/Waiting for EAI_LOG.EAI_ID");

			for (Map.Entry<Long, EaiLog> entry : errorMap.entrySet()) {
				remarks.append(" - " + entry.getValue().getEaiId());

				if (!(entry.getValue().getTxStatus().equals(Constant.STATUS_NEW) || entry.getValue().getTxStatus().equals(Constant.STATUS_PICKUP))) {
					retriggerEngineDao.updateBatchEaiLogbyId(entry.getValue(), Constant.STATUS_NEW);
				}
			}
		}
		return batchDetail;
	}

	public static void main(String[] args) {
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();

		System.out.println(map.size() + "-" + map.isEmpty() + "-" + map.get(2));

	}
}
