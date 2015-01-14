package org.dnawaz.bulletinboard.persistence;

import java.util.List;

import org.dnawaz.bulletinboard.domain.EaiLog;
import org.dnawaz.bulletinboard.domain.SearchCriteria;

/**
 * 
 * @author DilNawaz
 * 
 */
public interface RetriggerMapper {

	List<EaiLog> getList();
	List<EaiLog> searchList(SearchCriteria searchCriteria);
}
