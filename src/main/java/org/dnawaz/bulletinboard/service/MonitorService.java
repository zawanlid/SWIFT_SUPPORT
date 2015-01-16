package org.dnawaz.bulletinboard.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.dnawaz.bulletinboard.dao.MonitorDao;
import org.dnawaz.bulletinboard.domain.Batch;
import org.dnawaz.bulletinboard.domain.EaiLog;
import org.dnawaz.bulletinboard.domain.SearchCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author DilNawaz
 * 
 */
@Service
public class MonitorService {
	
	@SuppressWarnings("unused")
	private Logger log = Logger.getLogger(MonitorService.class.getName());

	@Autowired
	private MonitorDao monitorDao;

	public Batch getBatch(SearchCriteria searchCriteria) {
		return monitorDao.getBatch(searchCriteria);
	}

	public List<String> getDistinctBatch(SearchCriteria searchCriteria) {
		return monitorDao.getDistinctBatch(searchCriteria);
	}
	
	public List<EaiLog> getBatchDetails(SearchCriteria searchCriteria) {
		return monitorDao.getBatchDetails(searchCriteria);
	}

}