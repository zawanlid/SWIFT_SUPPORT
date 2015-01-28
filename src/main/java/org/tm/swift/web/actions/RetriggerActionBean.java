package org.tm.swift.web.actions;

import java.util.ArrayList;
import java.util.List;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.SessionScope;
import net.sourceforge.stripes.integration.spring.SpringBean;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.tm.swift.constant.Constant;
import org.tm.swift.domain.EaiLog;
import org.tm.swift.domain.SearchCriteria;
import org.tm.swift.service.RetriggerEngine;
import org.tm.swift.service.RetriggerService;
import org.tm.swift.util.StringUtils;

/**
 * 
 * @author DilNawaz
 * 
 */
@SessionScope
public class RetriggerActionBean extends AbstractActionBean {

	@SpringBean
	private transient RetriggerService retriggerService;
	
	@SpringBean
	private transient RetriggerEngine retriggerEngine;
	
	private static final Log log = LogFactory.getLog("RetriggerActionBean");
	private static final long serialVersionUID = 1761705363265894883L;
	private static final String MAIN = "/WEB-INF/jsp/common/Retrigger.jsp";

	private List<EaiLog> eaiList;
	private SearchCriteria searchCriteria;
	private String paramListTA;
	private int totalRecord;
	private List<String> eventNameList;
	private List<String> paramList;

	@DefaultHandler
	public ForwardResolution viewMain() {
		List<String> events = new ArrayList<String>();
		events.add("evManualTrigger");
		events.add("evCreateEvent");
		setEventNameList(retriggerService
				.getEventNameList(Constant.EAI_RESPONSE_ERROR));
		setParamList(retriggerService
				.getEAIResponseParamList(Constant.EAI_RESPONSE_ERROR));

		return new ForwardResolution(MAIN);
	}

	public ForwardResolution getList() {

		if (StringUtils.isNotEmpty(getParamListTA())) {
			String additionParamList[] = getParamListTA().split("\\|");
			List<String> list = new ArrayList<String>();
			for (String param : additionParamList) {
				if (StringUtils.isNotEmpty(param))
					list.add(param);
			}
			searchCriteria.setAdditionalParams(list);
		}
		try {
			setEaiList(retriggerService.getErrorList(searchCriteria));

			setTotalRecord(eaiList.size());

		} catch (Exception e) {
			e.printStackTrace();
		}
		log.debug(">>>>>>>>>>>>>Eai List:" + eaiList.size());

		log.debug("Date From:" + searchCriteria.getAuditDateFrom());
		log.debug("Date To:" + searchCriteria.getAuditDateTo());
		log.debug("System:" + searchCriteria.getSource());
		log.debug("TT Lists:" + searchCriteria.getTroubleTickets());
		log.debug("Additional Param:" + searchCriteria.getAdditionalParams());

		log.debug("Save Param:" + searchCriteria.getSaveParam());
		searchCriteria.setSaveParam(null);
		return new ForwardResolution(MAIN);
	}

	public ForwardResolution retriggerErrorList() {
		log.debug("Batch Name:" + searchCriteria.getBatchName());
		log.debug("Created By:" + searchCriteria.getCreatedBy());
		retriggerService.retriggerErrorList(searchCriteria, getEaiList());
		setEaiList(null);
		setTotalRecord(0);
		setMessage("Your re-trigger batch request is successfully logged!");
		//retriggerEngine.process();
		return new ForwardResolution(MAIN);
	}

	public List<EaiLog> getEaiList() {
		return eaiList;
	}

	public void setEaiList(List<EaiLog> eaiList) {
		this.eaiList = eaiList;
	}

	public SearchCriteria getSearchCriteria() {
		return searchCriteria;
	}

	public void setSearchCriteria(SearchCriteria searchCriteria) {
		this.searchCriteria = searchCriteria;
	}

	public String getParamListTA() {
		return paramListTA;
	}

	public void setParamListTA(String paramListTA) {
		this.paramListTA = paramListTA;
	}

	public int getTotalRecord() {
		return totalRecord;
	}

	public void setTotalRecord(int totalRecord) {
		this.totalRecord = totalRecord;
	}

	public List<String> getEventNameList() {
		return eventNameList;
	}

	public void setEventNameList(List<String> eventNameList) {
		this.eventNameList = eventNameList;
	}

	public List<String> getParamList() {
		return paramList;
	}

	public void setParamList(List<String> paramList) {
		this.paramList = paramList;
	}

}
