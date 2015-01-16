package org.dnawaz.bulletinboard.dao;

import java.util.List;

import org.dnawaz.bulletinboard.domain.Batch;
import org.dnawaz.bulletinboard.domain.EaiLog;
import org.dnawaz.bulletinboard.domain.SearchCriteria;

public interface MonitorDao {
	
	List<EaiLog> getBatchDetails(SearchCriteria searchCriteria);
	
	Batch getBatch(SearchCriteria searchCriteria);
	
	List<String> getDistinctBatch();
}
