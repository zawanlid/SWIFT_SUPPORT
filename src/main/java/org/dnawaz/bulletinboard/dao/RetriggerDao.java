package org.dnawaz.bulletinboard.dao;

import java.util.List;

import org.dnawaz.bulletinboard.domain.EaiLog;
import org.dnawaz.bulletinboard.domain.SearchCriteria;

public interface RetriggerDao {

	EaiLog findById(long eaiId);
	
	List<EaiLog> getErrorList(SearchCriteria searchCriteria) throws Exception ;
	
	void retriggerErrorList(SearchCriteria searchCriteria, List<EaiLog> eaiList);
	
	List<String> getEAIResponseParamList(String type);
	
	List<String> getEventNameList(String type);
}
