package org.dnawaz.bulletinboard.persistence;

import java.util.List;

import org.dnawaz.bulletinboard.domain.Bulletin;
import org.dnawaz.bulletinboard.domain.BulletinReply;

/**
 * 
 * @author DilNawaz
 * 
 */
public interface BulletinReplyMapper {

	List<BulletinReply> getBulletinReplyList(String id);

	Bulletin getBulletinReply(String id);

	void createBulletinReply(BulletinReply bulletinReply);

	void deleteBulletinReply(String bulletinId);

}
