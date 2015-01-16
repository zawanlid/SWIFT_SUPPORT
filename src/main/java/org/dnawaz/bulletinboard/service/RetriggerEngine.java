/**
 * 
 */
package org.dnawaz.bulletinboard.service;

import java.util.Calendar;

import org.apache.log4j.Logger;
import org.dnawaz.bulletinboard.dao.RetriggerDao;
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
	private RetriggerDao retriggerDao;

	/**
	 * Re-trigger Engine.
	 * Read SST_RETRIGGER_BATCHES and SST_RETRIGGER_BATCH_DETAILS DB Tables
	 * will run after 3 minutes delay. (180000 milliseconds)
	 */
    @Scheduled(fixedDelay = 30000)
	public final void process() {

    	log.debug(" Proceccing Re-Trigger Engine @ " + Calendar.getInstance().getTime());
		try {
			
			

		} catch (Exception e) {
			log.error(" Error [" + e.getMessage() + "]", e);
		}

	}

}
