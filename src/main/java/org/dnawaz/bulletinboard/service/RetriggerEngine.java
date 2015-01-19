/**
 * 
 */
package org.dnawaz.bulletinboard.service;

import java.util.Calendar;
import java.util.List;

import org.apache.log4j.Logger;
import org.dnawaz.bulletinboard.dao.RetriggerEngineDao;
import org.dnawaz.bulletinboard.domain.Batch;
import org.dnawaz.constant.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

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

	/**
	 * Re-trigger Engine.
	 * Read SST_RETRIGGER_BATCHES and SST_RETRIGGER_BATCH_DETAILS DB Tables
	 * will run after 3 minutes delay. (180000 milliseconds)
	 */
    @Scheduled(fixedDelay = 30000)
	public final void process() {

    	log.debug(" Proceccing Re-Trigger Engine @ " + Calendar.getInstance().getTime());
    	List<Batch> batchList = null;
		try {
			
			batchList = retriggerEngineDao.getBatches();
			retriggerEngineDao.updateBatchListStatus(batchList,Constant.STATUS_PICKUP);
			for (Batch batch: batchList) {
				processRetrigger(batch);
			}			

			retriggerEngineDao.updateBatchListStatus(batchList,Constant.STATUS_SUCCESS);
		} catch (Exception e) {
			retriggerEngineDao.updateBatchListStatus(batchList,Constant.STATUS_TERMINATED);
			log.error(" Error [" + e.getMessage() + "]", e);
		}

	}

    private void processRetrigger(Batch batch) throws Exception{
    	retriggerEngineDao.updateBatchEAIListStatus(batch, Constant.STATUS_NEW);
    }
}
