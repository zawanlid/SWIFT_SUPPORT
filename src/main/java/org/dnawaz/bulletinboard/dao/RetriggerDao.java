package org.dnawaz.bulletinboard.dao;

import java.util.List;

import org.dnawaz.bulletinboard.domain.EaiLog;
import org.dnawaz.bulletinboard.domain.SearchCriteria;

public interface RetriggerDao {

	EaiLog findById(int eaiId);
	
	List<EaiLog> getErrorList(SearchCriteria searchCriteria);
}
