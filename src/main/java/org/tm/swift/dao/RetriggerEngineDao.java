package org.tm.swift.dao;

import java.util.List;

import org.tm.swift.domain.Batch;
import org.tm.swift.domain.BatchDetail;
import org.tm.swift.domain.EaiLog;
import org.tm.swift.domain.EaiResponse;

/**
 * 
 * @author DilNawaz
 * 
 */
public interface RetriggerEngineDao {

	EaiLog findById(int eaiId);

	int updateBatchListStatus(List<Batch> batchList, String newStatus, String oldStatus, String remarks);
	
	int updateBatchStatus(Batch batch, String newStatus, String oldStatus, String remarks);

	int updateBatchEAIListStatus(Batch batch, String newStatus);
	
	int updateBatchEaiLogbyId(EaiLog eaiLog, String newStatus);

	List<Batch> getBatches();
	
	List<EaiResponse> getEaiResponseList(Batch batch, String type);
	
	List<EaiLog> getEaiList(Batch batch);
	
	int updateBatchDetailsStatusByBatchId(Batch batch, String newStatus, String oldStatus, String remarks);
	
	int updateBatchDetailsStatusById(String id, String newStatus, String oldStatus, String remarks);
	
	List<EaiLog> getEaiTraceList(EaiLog eaiLog, List<EaiResponse> eaiResponseList, boolean isTraceUp);
	
	int updateTraceBatch(final Batch batch, final List<BatchDetail> batchDetailList);
}
