package org.dnawaz.bulletinboard.web.actions;

import java.util.Calendar;
import java.util.List;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.SessionScope;
import net.sourceforge.stripes.integration.spring.SpringBean;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dnawaz.bulletinboard.domain.Bulletin;
import org.dnawaz.bulletinboard.domain.BulletinReply;
import org.dnawaz.bulletinboard.service.BulletinService;

/**
 * 
 * @author DilNawaz
 * 
 */
@SessionScope
public class BulletinActionBean extends AbstractActionBean {

	private static final Log log = LogFactory.getLog("BulletinActionBean");
	private static final long serialVersionUID = 5849523372175050635L;
	private static final String MAIN = "/WEB-INF/jsp/bulletin/BulletinList.jsp";
	private static final String VIEW_BULLETIN = "/WEB-INF/jsp/bulletin/ViewBulletin.jsp";
	private static final String CREATE_BULLETIN = "/WEB-INF/jsp/bulletin/CreateBulletin.jsp";
	private static final String UPDATE_BULLETIN = "/WEB-INF/jsp/bulletin/EditBulletin.jsp";

	/*
	 * @SpringBean annotation, that is an Stripes annotation that tells Stripes
	 * to look for that bean in Spring and inject it into this ActionBean.
	 */
	@SpringBean
	private transient BulletinService bulletinService;

	// unique identifier which we will pass from view.
	private String id;
	private String actionType;
	private boolean incrementFlag;
	private List<Bulletin> bulletinlist;
	private Bulletin bulletin;
	private List<BulletinReply> bulletinReplyList;
	private BulletinReply bulletinReply;

	/**
	 * This is default method for this ActionBean.
	 * 
	 * @return ForwardResolution
	 */
	@DefaultHandler
	public ForwardResolution viewMain() {
		clear();
		setBulletinlist(bulletinService.getBulletinList());
		log.debug(">>>>>>>>>>>>>bulletin List:" + bulletinlist.size());
		return new ForwardResolution(MAIN);
	}

	/**
	 * process read bulletin request from view.
	 * 
	 * @return ForwardResolution
	 */
	public ForwardResolution viewBulletin() {

		log.debug(">>>>> viewBulletin process start >>>>  ID : " + id
				+ " Increment Flag:" + incrementFlag);
		if (incrementFlag) {
			bulletinService.incrementReadCount(id);
			incrementFlag = false;
		}
		setBulletin(bulletinService.getBulletin(id));
		setBulletinReplyList(bulletinService.getBulletinReplyList(id));
		if (bulletinReplyList != null && bulletinReplyList.size() == 0) {
			setBulletinReplyList(null);
		}
		log.debug(">>>>> viewBulletin process completed >>>>  ID : "
				+ this.bulletin.getId());

		return new ForwardResolution(VIEW_BULLETIN);
	}

	/**
	 * open bulletin update screen to edit bulletin.
	 * 
	 * @return ForwardResolution
	 */
	public ForwardResolution updateBulletinView() {

		log.debug(">>>>>>>>>>>>>>>>>>>> update bulletin view >>>>>>>>>>>>>>  ");
		return new ForwardResolution(UPDATE_BULLETIN);
	}

	/**
	 * validate user before edit or delete.
	 * 
	 * @return ForwardResolution
	 */
	public ForwardResolution validateUser() {

		ForwardResolution resolution = null;
		log.debug(">>>>>>>>>>>>>>>>>>>> validate User process (action type) >>>>>>>>>>>>>>  "
				+ actionType);
		bulletin.setId(this.id);
		Bulletin validUser = bulletinService.validateUser(bulletin);
		if (validUser == null || validUser.getUserId() == null) {
			setMessage("Invalid User ID or Password!");
			return viewBulletin();
		}
		if (actionType.equals("1")) {
			resolution = new ForwardResolution(UPDATE_BULLETIN);
		} else {
			resolution = deleteBulletin();
		}
		return resolution;
	}

	/**
	 * update bulletin and redirect to view bulletin screen.
	 * 
	 * @return ForwardResolution
	 */
	public ForwardResolution updateBulletin() {

		log.debug(">>>>>>>>>>>>>>>>>>>> update bulletin process >>>>>>>>>>>>>>  ");
		bulletin.setId(this.id);
		bulletinService.updateBulletin(bulletin);
		return viewBulletin();
	}

	/**
	 * open create bulletin screen.
	 * 
	 * @return ForwardResolution
	 */
	public ForwardResolution createBulletinView() {

		log.debug(">>>>>>>>>>>>>>>>>>>> create bulletin View >>>>>>>>>>>>>>  ");
		return new ForwardResolution(CREATE_BULLETIN);
	}

	/**
	 * Process create bulletin request and redirect to bulletin list screen.
	 * 
	 * @return
	 */
	public ForwardResolution createBulletin() {

		log.debug(">>>>>>>>>>>>>>>>>>>> create bulletin process >>>>>>>>>>>>>>  ");
		bulletinService.createBulletin(bulletin);
		setId(bulletin.getId());
		setIncrementFlag(false);
		return viewBulletin();
	}

	/**
	 * Process delete bulletin request and redirect to bulletin list screen.
	 * 
	 * @return
	 */
	public ForwardResolution deleteBulletin() {
		log.debug(">>>>>>>>>>>>>>>>>>>> delete bulletin process >>>>>>>>>>>>>>  ");
		bulletinService.deleteBulletin(id);
		setBulletinlist(bulletinService.getBulletinList());
		return viewMain();
	}

	/**
	 * create bulletin reply.
	 * 
	 * @return ForwardResolution
	 */
	public ForwardResolution createBulletinReply() {

		log.debug(">>>>>> Create Bulletin Reply Process >>>>");

		bulletinReply.setBulletinId(id);
		bulletinReply.setCreatedOn(Calendar.getInstance().getTime());
		bulletinService.createBulletinReply(bulletinReply);
		clear();
		setBulletin(bulletinService.getBulletin(id));
		setBulletinReplyList(bulletinService.getBulletinReplyList(id));
		if (bulletinReplyList != null && bulletinReplyList.size() == 0) {
			setBulletinReplyList(null);
		}
		log.debug(">>>>> Create Bulletin Reply Process Completed >>> Bulletin Reply Id: "
				+ this.bulletinReply.getId());

		return new ForwardResolution(VIEW_BULLETIN);
	}

	/**
	 * clear class instance variable.
	 */
	public void clear() {
		log.debug(">>>>>>>>>>>>>>>> ////////////// Clear Cache ///////////// >>>>>>>>>>>>>>>>>>>");
		bulletin = null;
		bulletinlist = null;
		bulletinReplyList = null;

	}

	/*
	 * Getter Setter Block Start
	 */
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getActionType() {
		return actionType;
	}

	public boolean isIncrementFlag() {
		return incrementFlag;
	}

	public void setIncrementFlag(boolean incrementFlag) {
		this.incrementFlag = incrementFlag;
	}

	public void setActionType(String actionType) {
		this.actionType = actionType;
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

	public Bulletin getBulletin() {
		return bulletin;
	}

	public void setBulletin(Bulletin bulletin) {
		this.bulletin = bulletin;
	}

	public List<Bulletin> getBulletinlist() {
		return bulletinlist;
	}

	public void setBulletinlist(List<Bulletin> bulletinlist) {
		this.bulletinlist = bulletinlist;
	}

	/*
	 * Getter Setter Block End Here
	 */
}
