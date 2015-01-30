package org.tm.swift.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tm.swift.dao.RetriggerDao;
import org.tm.swift.domain.EaiLog;
import org.tm.swift.domain.SearchCriteria;
import org.tm.swift.util.SSTTransactional;

/**
 * 
 * @author DilNawaz
 * 
 */
@Service
public class RetriggerService {

	private Logger log = Logger.getLogger(RetriggerService.class.getName());

	@Autowired
	private RetriggerDao retriggerDao;

	public List<EaiLog> getErrorList(SearchCriteria searchCriteria)
			throws Exception {
		List<EaiLog> list;

		list = retriggerDao.getErrorList(searchCriteria);
		log.debug(" >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> " + list.size());

		return list;
	}

	public void getbyId(SearchCriteria searchCriteria) {
		EaiLog eailog = retriggerDao.findById(23266857);
		log.debug(" >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> " + eailog.getEaiId());
	}

	@SSTTransactional
	public void retriggerErrorList(SearchCriteria searchCriteria,
			List<EaiLog> eaiList) {

		retriggerDao.retriggerErrorList(searchCriteria, eaiList);

	}

	public List<String> getEAIResponseParamList(String type) {
		return retriggerDao.getEAIResponseParamList(type);
	}

	public List<String> getEventNameList(String type) {
		return retriggerDao.getEventNameList(type);
	}
}