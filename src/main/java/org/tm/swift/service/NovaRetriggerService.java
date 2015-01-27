package org.tm.swift.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tm.swift.constant.Constant;
import org.tm.swift.dao.RetriggerEngineDao;
import org.tm.swift.domain.Batch;
import org.tm.swift.domain.EaiResponse;

/**
 * 
 * @author DilNawaz
 * 
 */
@Service
public class NovaRetriggerService {

	private Logger log = Logger.getLogger(NovaRetriggerService.class.getName());

	@Autowired
	private RetriggerEngineDao retriggerEngineDao;

	public void process(Batch batch) {
		log.debug(" Process Non Status Update Retrigger ");
		List<EaiResponse> eaiResponseList = retriggerEngineDao.getEaiResponseList(batch, Constant.EAI_RESPONSE_SUCCESS);
		boolean isStatusUpdate = false;
		for (EaiResponse eaiResponse : eaiResponseList) {
			
			if (eaiResponse.getIsStatusUpdate()) {
				isStatusUpdate = true;
				break;
			}
		}
		
		if (isStatusUpdate) {
			processStatusUpdate(batch);
		} else {
			processNonStatusUpdate(batch);
		}

	}

	private void processNonStatusUpdate(Batch batch) {
		log.debug(" Process Non Status Update Retrigger ");
		retriggerEngineDao.updateBatchEAIListStatus(batch, Constant.STATUS_NEW);
		retriggerEngineDao.updateBatchDetailsStatusByBatchId(batch, Constant.STATUS_SUCCESS, null , "Successfully Processed");
		retriggerEngineDao.updateBatchStatus(batch, Constant.STATUS_SUCCESS, null , "Successfully Processed");
	}

	private void processStatusUpdate(Batch batch) {
		log.debug(" Process Status Update Retrigger ");

	}
}