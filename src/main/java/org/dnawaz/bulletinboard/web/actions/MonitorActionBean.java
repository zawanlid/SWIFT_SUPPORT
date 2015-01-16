package org.dnawaz.bulletinboard.web.actions;

import java.util.List;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.SessionScope;
import net.sourceforge.stripes.integration.spring.SpringBean;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dnawaz.bulletinboard.domain.Batch;
import org.dnawaz.bulletinboard.domain.EaiLog;
import org.dnawaz.bulletinboard.domain.SearchCriteria;
import org.dnawaz.bulletinboard.service.MonitorService;

@SessionScope
public class MonitorActionBean extends AbstractActionBean{

	@SpringBean
	private transient MonitorService monitorService;
	
	private static final Log log = LogFactory.getLog("MonitorActionBean");
	private static final long serialVersionUID = 1761705363265894883L;
	private static final String MAIN = "/WEB-INF/jsp/common/Monitor.jsp";
	private static final String VIEW_RETRIGGER = "/WEB-INF/jsp/common/Retrigger.jsp";
	
	private List<EaiLog> eaiList;	
	private SearchCriteria searchCriteria;
	private Batch batch;
	private List<String> batchList;
	private int totalRecord;
	
	
	@DefaultHandler
	public ForwardResolution viewMain(){
		setBatchList(monitorService.getDistinctBatch());
		return new ForwardResolution(MAIN);
	}
	
	public ForwardResolution getList() {
		
		try{
			setEaiList(monitorService.getBatchDetails(searchCriteria));			
			setBatch(monitorService.getBatch(searchCriteria));
			setTotalRecord(eaiList.size());

		} catch (Exception e) {
			e.printStackTrace();
		}
		log.debug(">>>>>>>>>>>>> Batch Eai List:" + eaiList.size());

		return new ForwardResolution(MAIN);
	}

	public ForwardResolution viewRetrigger(){
		return new ForwardResolution(VIEW_RETRIGGER);
	}
	
	public List<EaiLog> getEaiList() {
		return eaiList;
	}

	public void setEaiList(List<EaiLog> eaiList) {
		this.eaiList = eaiList;
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

	public List<String> getBatchList() {
		return batchList;
	}

	public void setBatchList(List<String> batchList) {
		this.batchList = batchList;
	}

	public SearchCriteria getSearchCriteria() {
		return searchCriteria;
	}

	public void setSearchCriteria(SearchCriteria searchCriteria) {
		this.searchCriteria = searchCriteria;
	}
	
	
	
}
