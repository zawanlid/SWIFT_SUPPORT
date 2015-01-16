package org.dnawaz.bulletinboard.service;

import java.util.Calendar;
import java.util.List;

import org.apache.log4j.Logger;
import org.dnawaz.bulletinboard.dao.RetriggerDao;
import org.dnawaz.bulletinboard.domain.Bulletin;
import org.dnawaz.bulletinboard.domain.BulletinReply;
import org.dnawaz.bulletinboard.domain.EaiLog;
import org.dnawaz.bulletinboard.domain.SearchCriteria;
import org.dnawaz.bulletinboard.domain.Sequence;
import org.dnawaz.bulletinboard.persistence.BulletinMapper;
import org.dnawaz.bulletinboard.persistence.BulletinReplyMapper;
import org.dnawaz.bulletinboard.persistence.RetriggerMapper;
import org.dnawaz.bulletinboard.persistence.SequenceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * @author DilNawaz
 * 
 */
@Service
public class BulletinService {
	
	private Logger log = Logger.getLogger(BulletinService.class.getName());

	@Autowired
	private BulletinMapper bulletinMapper;

	@Autowired
	private BulletinReplyMapper bulletinReplyMapper;
	
	@Autowired
	private RetriggerMapper retriggerMapper;

	@Autowired
	private SequenceMapper sequenceMapper;
	
	@Autowired
	private RetriggerDao retriggerDao;

	public List<EaiLog> getList(){
		return retriggerMapper.getList();
	}
	
	public List<EaiLog> searchList(SearchCriteria searchCriteria){
		return retriggerMapper.searchList(searchCriteria);
	}
	
	public List<Bulletin> getBulletinList() {
		return bulletinMapper.getBulletinList();
	}

	public Bulletin getBulletin(String id) {
		return bulletinMapper.getBulletin(id);
	}

	public void incrementReadCount(String id) {
		bulletinMapper.incrementReadCount(id);

	}

	public List<EaiLog> getErrorList(SearchCriteria searchCriteria) {
		List<EaiLog> list = retriggerDao.getErrorList(searchCriteria);
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> " +list.size());
		return list;
	}
	
	public void getbyId(SearchCriteria searchCriteria) {
		EaiLog eailog = retriggerDao.findById(23266857);
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> " +eailog.getEaiId());
	}
	
	@Transactional
	public void deleteBulletin(String id) {
		bulletinReplyMapper.deleteBulletinReply(id);
		bulletinMapper.deleteBulletin(id);

	}

	public List<BulletinReply> getBulletinReplyList(String id) {
		return bulletinReplyMapper.getBulletinReplyList(id);
	}

	public void createBulletinReply(BulletinReply bulletinReply) {
		bulletinReply.setId(getNextId("BULLETIN_REPLY_ID") + "");
		bulletinReplyMapper.createBulletinReply(bulletinReply);

	}

	/**
	 * get sequence id from DB.
	 * @param name
	 * @return
	 */
	public int getNextId(String name) {
		Sequence sequence = new Sequence(name, -1);
		sequence = (Sequence) sequenceMapper.getSequence(sequence);
		if (sequence == null) {
			throw new RuntimeException(
					"Error: A null sequence was returned from the database (could not get next "
							+ name + " sequence).");
		}
		Sequence parameterObject = new Sequence(name, sequence.getNextId() + 1);
		sequenceMapper.updateSequence(parameterObject);
		return sequence.getNextId();
	}

	public void updateBulletin(Bulletin bulletin) {
		bulletinMapper.updateBulletin(bulletin);

	}

	public void createBulletin(Bulletin bulletin) {

		bulletin.setId(getNextId("BULLETIN_ID") + "");
		bulletin.setCreatedOn(Calendar.getInstance().getTime());
		bulletin.setReadCount(0);
		bulletinMapper.createBulletin(bulletin);

	}

	public Bulletin validateUser(Bulletin bulletin) {
		return bulletinMapper.validateUser(bulletin);

	}

	public void retriggerErrorList(SearchCriteria searchCriteria,
			List<EaiLog> eaiList) {

		retriggerDao.retriggerErrorList(searchCriteria, eaiList);
		
	}

	public List<EaiLog> getBatchDetails(SearchCriteria searchCriteria) {
		// TODO Auto-generated method stub
		return null;
	}

}