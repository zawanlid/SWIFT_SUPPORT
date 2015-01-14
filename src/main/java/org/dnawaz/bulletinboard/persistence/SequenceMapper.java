package org.dnawaz.bulletinboard.persistence;

import org.dnawaz.bulletinboard.domain.Sequence;

/**
 * 
 * @author DilNawaz
 * 
 */
public interface SequenceMapper {

	Sequence getSequence(Sequence sequence);

	void updateSequence(Sequence sequence);
}
