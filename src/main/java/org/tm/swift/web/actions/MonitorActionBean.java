package org.tm.swift.web.actions;

import java.util.List;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.SessionScope;
import net.sourceforge.stripes.integration.spring.SpringBean;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.tm.swift.domain.Batch;
import org.tm.swift.domain.Monitor;
import org.tm.swift.domain.SearchCriteria;
import org.tm.swift.service.MonitorService;

/**
 * 
 * @author DilNawaz
 * 
 */
@SessionScope
public class MonitorActionBean extends AbstractActionBean {

	@SpringBean
	private transient MonitorService monitorService;

	private static final Log log = LogFactory.getLog("MonitorActionBean");
	private static final long serialVersionUID = 1761705363265894883L;
	private static final String MAIN = "/WEB-INF/jsp/common/Monitor.jsp";
	private static final String VIEW_RETRIGGER = "/WEB-INF/jsp/common/Retrigger.jsp";

	private List<Monitor> monitorList;
	private SearchCriteria searchCriteria;
	private Batch batch;
	private List<Batch> batchList;
	private int totalRecord;

	@DefaultHandler
	public ForwardResolution viewMain() {
		// filter Login In
		if (isLoginRequired())
			return new ForwardResolution(LOGIN);
		
		setBatchList(monitorService.getDistinctBatch());
		return new ForwardResolution(MAIN);
	}

	public ForwardResolution getList() {

		// filter Login In
		if (isLoginRequired())
			return new ForwardResolution(LOGIN);

		try {
			setMonitorList(monitorService.getBatchDetails(searchCriteria));
			setBatch(monitorService.getBatch(searchCriteria));
			setTotalRecord(getMonitorList().size());
			log.debug(">>>>>>>>>>>>> Batch Eai List:" + getMonitorList().size());

		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ForwardResolution(MAIN);
	}

	public ForwardResolution viewRetrigger() {
		return new ForwardResolution(VIEW_RETRIGGER);
	}

	public List<Monitor> getMonitorList() {
		return monitorList;
	}

	public void setMonitorList(List<Monitor> monitorList) {
		this.monitorList = monitorList;
	}

	public int getTotalRecord() {
		return totalRecord;
	}

	public void setTotalRecord(int totalRecord) {
		this.totalRecord = totalRecord;
	}

	public Batch getBatch() {
		return batch;
	}

	public void setBatch(Batch batch) {
		this.batch = batch;
	}

	public List<Batch> getBatchList() {
		return batchList;
	}

	public void setBatchList(List<Batch> batchList) {
		this.batchList = batchList;
	}

	public SearchCriteria getSearchCriteria() {
		return searchCriteria;
	}

	public void setSearchCriteria(SearchCriteria searchCriteria) {
		this.searchCriteria = searchCriteria;
	}

}
