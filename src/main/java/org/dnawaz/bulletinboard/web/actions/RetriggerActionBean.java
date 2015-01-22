package org.dnawaz.bulletinboard.web.actions;

import java.util.ArrayList;
import java.util.List;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.SessionScope;
import net.sourceforge.stripes.integration.spring.SpringBean;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dnawaz.bulletinboard.domain.EaiLog;
import org.dnawaz.bulletinboard.domain.SearchCriteria;
import org.dnawaz.bulletinboard.service.BulletinService;
import org.dnawaz.constant.Constant;
import org.dnawaz.util.StringUtils;

@SessionScope
public class RetriggerActionBean extends AbstractActionBean {

	@SpringBean
	private transient BulletinService bulletinService;
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
		setEventNameList(bulletinService
				.getEventNameList(Constant.EAI_RESPONSE_ERROR));
		setParamList(bulletinService
				.getEAIResponseParamList(Constant.EAI_RESPONSE_ERROR));

		return new ForwardResolution(MAIN);
	}

	public ForwardResolution getList() {

		if (StringUtils.isNotEmpty(getParamListTA())) {
			String troubleTicketList[] = getParamListTA().split("\\|");
			List<String> list = new ArrayList<String>();
			for (String param : troubleTicketList) {
				list.add(param);
			}
			searchCriteria.setAdditionalParams(list);
		}
		try {
			setEaiList(bulletinService.getErrorList(searchCriteria));

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
		bulletinService.retriggerErrorList(searchCriteria, getEaiList());
		setEaiList(null);
		setTotalRecord(0);
		setMessage("Your re-trigger batch request is successfully logged!");
		return new ForwardResolution(MAIN);
	}

	public ForwardResolution searchList() {
		log.debug("-------------------:" + searchCriteria.getTroubleTickets());
		setEaiList(bulletinService.searchList(searchCriteria));
		log.debug(">>>>>>>>>>>>>Eai Search List:" + eaiList.size());
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
