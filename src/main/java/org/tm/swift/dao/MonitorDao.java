package org.tm.swift.dao;

import java.util.List;

import org.tm.swift.domain.Batch;
import org.tm.swift.domain.Monitor;
import org.tm.swift.domain.SearchCriteria;

/**
 * 
 * @author DilNawaz
 *
 */
public interface MonitorDao {

	List<Monitor> getBatchDetails(SearchCriteria searchCriteria);

	Batch getBatch(SearchCriteria searchCriteria);

	List<Batch> getDistinctBatch();
}
