package org.dnawaz.bulletinboard.web.actions;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dnawaz.bulletinboard.domain.EaiLog;
import org.dnawaz.bulletinboard.domain.SearchCriteria;
import org.dnawaz.bulletinboard.service.BulletinService;
import org.dnawaz.util.StringUtils;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.SessionScope;
import net.sourceforge.stripes.integration.spring.SpringBean;

@SessionScope
public class MonitorActionBean extends AbstractActionBean{

	@SpringBean
	private transient BulletinService bulletinService;
	private static final Log log = LogFactory.getLog("MonitorActionBean");
	private static final long serialVersionUID = 1761705363265894883L;
	private static final String Main = "/WEB-INF/jsp/common/Monitor.jsp";
	
	private List<EaiLog> eaiList;	
	private SearchCriteria searchCriteria;
	private int totalRecord;
	
	
	@DefaultHandler
	public ForwardResolution viewMain(){
		return new ForwardResolution(Main);
	}
	
	public ForwardResolution getList() {
		
		try{
			setEaiList(bulletinService.getErrorList(searchCriteria));

			setTotalRecord(eaiList.size());

		} catch (Exception e) {
			e.printStackTrace();
		}
		log.debug(">>>>>>>>>>>>>Eai List:" + eaiList.size());

		return new ForwardResolution(Main);
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
	
}
