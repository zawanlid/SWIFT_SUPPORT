package org.dnawaz.bulletinboard.web.actions;

import java.util.Calendar;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dnawaz.bulletinboard.domain.BulletinReply;
import org.dnawaz.bulletinboard.service.BulletinReplyService;

import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.SessionScope;
import net.sourceforge.stripes.integration.spring.SpringBean;

@SessionScope
public class BulletinReplyActionBean extends AbstractActionBean{

	private static final Log log = LogFactory.getLog("BulletinReplyActionBean");
	private static final long serialVersionUID = 8544404598360895789L;
	private static final String VIEW_BULLETIN = "/WEB-INF/jsp/bulletin/ViewBulletin.jsp";

	@SpringBean
	private transient BulletinReplyService bulletinReplyService;
	
	private String id;
	private List<BulletinReply> bulletinReplyList;
	private BulletinReply bulletinReply;
	
	public ForwardResolution createBulletinReply() {

		log.debug(">>>>>> Create Bulletin Reply Process >>>>");

		bulletinReply.setBulletinId(id);
		bulletinReply.setCreatedOn(Calendar.getInstance().getTime());
		bulletinReplyService.createBulletinReply(bulletinReply);
		clear();
		//setBulletin(bulletinService.getBulletin(id));
		setBulletinReplyList(bulletinReplyService.getBulletinReplyList(id));
		if (bulletinReplyList != null && bulletinReplyList.size() == 0) {
			setBulletinReplyList(null);
		}
		log.debug(">>>>> Create Bulletin Reply Process Completed >>> Bulletin Reply Id: "
				+ this.bulletinReply.getId());

		return new ForwardResolution(VIEW_BULLETIN);
	}
	
	public void clear() {
		log.debug(">>>>>>>>>>>>>>>> ////////////// Clear Cache ///////////// >>>>>>>>>>>>>>>>>>>");
		bulletinReplyList = null;

	}
	
	public BulletinReply getBulletinReply() {
		return bulletinReply;
	}

	public void setBulletinReply(BulletinReply bulletinReply) {
		this.bulletinReply = bulletinReply;
	}

	public List<BulletinReply> getBulletinReplyList() {
		return bulletinReplyList;
	}

	public void setBulletinReplyList(List<BulletinReply> bulletinReplyList) {
		this.bulletinReplyList = bulletinReplyList;
	}
}
