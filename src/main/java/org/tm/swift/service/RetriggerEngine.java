/**
 * 
 */
package org.tm.swift.service;

import java.util.Calendar;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.tm.swift.constant.Constant;
import org.tm.swift.dao.RetriggerEngineDao;
import org.tm.swift.domain.Batch;

/**
 * @author DilNawaz
 * 
 */
@Service
@Component("RetriggerEngine_Job")
public class RetriggerEngine {

	/**
	 * logger object.
	 */
	private final Logger log = Logger.getLogger(RetriggerEngine.class);

	@Autowired
	private RetriggerEngineDao retriggerEngineDao;
	
	@Autowired
	private NovaRetriggerService novaRetriggerService;
	
	@Autowired
	private IcpRetriggerService icpRetriggerService;

	/**
	 * Re-trigger Engine. Read SST_RETRIGGER_BATCHES and
	 * SST_RETRIGGER_BATCH_DETAILS DB Tables will run after 3 minutes delay.
	 * (180000 milliseconds)
	 */
	//@Scheduled(fixedDelay = 5000)
	public final void process() {

		log.debug(" Proceccing Re-Trigger Engine @ "
				+ Calendar.getInstance().getTime());
		List<Batch> batchList = null;
		try {

			batchList = retriggerEngineDao.getBatches();
			retriggerEngineDao.updateBatchListStatus(batchList,
					Constant.STATUS_PICKUP, null, "");
			for (Batch batch : batchList) {
				if (Constant.SOURCE_SYSTEM_ICP.equals(batch.getSource())) {
					icpRetriggerService.process(batch);
				} else if (Constant.SOURCE_SYSTEM_NOVA.equals(batch.getSource())) {
					novaRetriggerService.process(batch);
				} else {
					retriggerEngineDao.updateBatchStatus(batch, Constant.STATUS_TERMINATED, null, "Invalid Source System!");
				}
			}

		} catch (Exception e) {
			retriggerEngineDao.updateBatchListStatus(batchList,
					Constant.STATUS_TERMINATED, Constant.STATUS_PICKUP, "General System Error!");
			log.error(" Error [" + e.getMessage() + "]", e);
		}

	}
}
