package org.tm.swift.dao;

import java.util.List;

import org.tm.swift.domain.Batch;
import org.tm.swift.domain.EaiLog;
import org.tm.swift.domain.SearchCriteria;

/**
 * 
 * @author DilNawaz
 *
 */
public interface MonitorDao {

	List<EaiLog> getBatchDetails(SearchCriteria searchCriteria);

	Batch getBatch(SearchCriteria searchCriteria);

	List<Batch> getDistinctBatch();
}
