package org.tm.swift.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tm.swift.dao.MonitorDao;
import org.tm.swift.domain.Batch;
import org.tm.swift.domain.EaiLog;
import org.tm.swift.domain.SearchCriteria;

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

	public List<Batch> getDistinctBatch() {
		return monitorDao.getDistinctBatch();
	}

	public List<EaiLog> getBatchDetails(SearchCriteria searchCriteria) {
		return monitorDao.getBatchDetails(searchCriteria);
	}

}