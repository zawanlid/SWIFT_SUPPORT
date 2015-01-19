package org.dnawaz.bulletinboard.dao;

import java.util.List;

import org.dnawaz.bulletinboard.domain.Batch;
import org.dnawaz.bulletinboard.domain.EaiLog;

public interface RetriggerEngineDao {

	EaiLog findById(int eaiId);
	
	long updateBatchListStatus(List<Batch> batchList);
	
	List<Batch> getBatches();
}