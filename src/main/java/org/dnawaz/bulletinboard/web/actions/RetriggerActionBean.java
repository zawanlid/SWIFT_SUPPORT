package org.dnawaz.bulletinboard.web.actions;

import java.util.ArrayList;
import java.util.Arrays;
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
import org.dnawaz.util.StringUtils;

@SessionScope
public class RetriggerActionBean extends AbstractActionBean{
	
	@SpringBean
	private transient BulletinService bulletinService;
	private static final Log log = LogFactory.getLog("RetriggerActionBean");
	private static final long serialVersionUID = 1761705363265894883L;
	private static final String Main = "/WEB-INF/jsp/common/Retrigger.jsp";
	
	private List<EaiLog> eaiList;	
	private SearchCriteria searchCriteria;
	private String param1;
	private String param2;
	private String param3;
	
	
	@DefaultHandler
	public ForwardResolution viewMain(){
		return new ForwardResolution(Main);
	}
	
	public ForwardResolution getList() {
		
		
		
		List<String> params = new ArrayList<String>();
		if (StringUtils.isNotEmpty(param1))
			params.add(param1);
		if (StringUtils.isNotEmpty(param2))
			params.add(param2);
		if (StringUtils.isNotEmpty(param3))
			params.add(param3);
		searchCriteria.setAdditionalParams(params);
		try{
			setEaiList(bulletinService.getErrorList(searchCriteria));
		} catch (Exception e) {
			e.printStackTrace();
		}
		log.debug(">>>>>>>>>>>>>Eai List:" + eaiList.size());
		log.debug("Date From:"+searchCriteria.getAuditDateFrom());
		log.debug("Date To:"+searchCriteria.getAuditDateTo());
		log.debug("System:"+searchCriteria.getSource());
		log.debug("TT Lists:"+searchCriteria.getTroubleTickets());
		log.debug("Additional Param:"+searchCriteria.getAdditionalParams());
		log.debug("Save:"+searchCriteria.getSaveParam());
		searchCriteria.setSaveParam(null);
		setSearchCriteria(searchCriteria);
		return new ForwardResolution(Main);
	}
	
	public ForwardResolution searchList(){
//		log.debug("========================:"+searchCriteria.getAuditDateFrom());
//		log.debug("========================:"+searchCriteria.getAuditDateTo());
//		log.debug("========================:"+searchCriteria.getSource());
//		List<String> params = new ArrayList<String>();
//		params.add(param1);
//		params.add(param2);
//		params.add(param3);
//		searchCriteria.setAdditionalParams(params);
//		log.debug("=-=-=-=-=-=-=-=-=-=-=-=-==--=-=:"+searchCriteria.getAdditionalParams());
		log.debug("-------------------:"+searchCriteria.getTroubleTickets());
		setEaiList(bulletinService.searchList(searchCriteria));
		log.debug(">>>>>>>>>>>>>Eai Search List:" + eaiList.size());
		return new ForwardResolution(Main);
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

	public String getParam1() {
		return param1;
	}

	public void setParam1(String param1) {
		this.param1 = param1;
	}

	public String getParam2() {
		return param2;
	}

	public void setParam2(String param2) {
		this.param2 = param2;
	}

	public String getParam3() {
		return param3;
	}

	public void setParam3(String param3) {
		this.param3 = param3;
	}	
	

}
