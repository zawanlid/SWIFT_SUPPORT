package org.dnawaz.bulletinboard.persistence;

import java.util.List;

import org.dnawaz.bulletinboard.domain.Bulletin;

/**
 * 
 * @author DilNawaz
 * 
 */
public interface BulletinMapper {

	List<Bulletin> getBulletinList();

	Bulletin getBulletin(String id);

	Object incrementReadCount(String id);

	void deleteBulletin(String id);

	void updateBulletin(Bulletin bulletin);

	void createBulletin(Bulletin bulletin);

	Bulletin validateUser(Bulletin bulletin);
}
