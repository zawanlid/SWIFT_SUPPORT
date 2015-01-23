package org.tm.swift.dao;

import java.util.List;

import org.tm.swift.domain.EaiLog;
import org.tm.swift.domain.SearchCriteria;

/**
 * 
 * @author DilNawaz
 *
 */
public interface RetriggerDao {

	EaiLog findById(long eaiId);

	List<EaiLog> getErrorList(SearchCriteria searchCriteria) throws Exception;

	void retriggerErrorList(SearchCriteria searchCriteria, List<EaiLog> eaiList);

	List<String> getEAIResponseParamList(String type);

	List<String> getEventNameList(String type);
}
