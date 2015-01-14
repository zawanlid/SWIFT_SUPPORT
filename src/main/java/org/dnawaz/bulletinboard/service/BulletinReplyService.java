package org.dnawaz.bulletinboard.service;

import java.util.List;

import org.dnawaz.bulletinboard.domain.BulletinReply;
import org.dnawaz.bulletinboard.domain.Sequence;
import org.dnawaz.bulletinboard.persistence.BulletinReplyMapper;
import org.dnawaz.bulletinboard.persistence.SequenceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BulletinReplyService {

	@Autowired
	private BulletinReplyMapper bulletinReplyMapper;

	@Autowired
	private SequenceMapper sequenceMapper;
	
	public List<BulletinReply> getBulletinReplyList(String id) {
		return bulletinReplyMapper.getBulletinReplyList(id);
	}

	public void createBulletinReply(BulletinReply bulletinReply) {
		bulletinReply.setId(getNextId("BULLETIN_REPLY_ID") + "");
		bulletinReplyMapper.createBulletinReply(bulletinReply);

	}
	
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
}
