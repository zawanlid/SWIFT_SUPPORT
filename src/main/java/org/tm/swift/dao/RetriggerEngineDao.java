package org.tm.swift.dao;

import java.util.List;

import org.tm.swift.domain.Batch;
import org.tm.swift.domain.EaiLog;

/**
 * 
 * @author DilNawaz
 * 
 */
public interface RetriggerEngineDao {

	EaiLog findById(int eaiId);

	long updateBatchListStatus(List<Batch> batchList, String status);

	long updateBatchEAIListStatus(Batch batchList, String status);

	List<Batch> getBatches();
}
