package org.dnawaz.bulletinboard.domain;

import java.io.Serializable;
import java.util.Date;

import net.sourceforge.stripes.validation.Validate;

/**
 * 
 * @author DilNawaz
 * 
 */
public class BulletinReply implements Serializable {

	private static final long serialVersionUID = 8751282105532159742L;

	private String id;
	private String name;
	private String body;
	private String bulletinId;
	private Date createdOn;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	@Validate(required = true)
	public void setName(String name) {
		this.name = name;
	}

	public String getBody() {
		return body;
	}

	@Validate(required = true)
	public void setBody(String body) {
		this.body = body;
	}

	public String getBulletinId() {
		return bulletinId;
	}

	public void setBulletinId(String bulletinId) {
		this.bulletinId = bulletinId;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

}
