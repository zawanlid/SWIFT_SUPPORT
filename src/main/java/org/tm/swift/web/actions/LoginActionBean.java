package org.tm.swift.web.actions;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.SessionScope;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.tm.swift.constant.Constant;

/**
 * 
 * @author DilNawaz
 *
 */
@SessionScope
public class LoginActionBean extends AbstractActionBean {


	private static final Log log = LogFactory.getLog("LoginActionBean");
	private static final long serialVersionUID = 1761705363265894883L;	
	private static final String MAIN = "/actions/Retrigger.action";

	private String login = null;
	private String password = null;

	@DefaultHandler
	public ForwardResolution login() {
		log.debug("Process Login");
		if (Constant.LOGIN.equalsIgnoreCase(getLogin())
				&& Constant.PASSWORD.equals(getPassword())){
			context.getRequest().setAttribute(Constant.LOGIN, getLogin());
			return new ForwardResolution(MAIN);
		}
		setLogin(null);
		setPassword(null);
		context.getRequest().setAttribute(Constant.LOGIN, getLogin());
		return new ForwardResolution("/WEB-INF/jsp/common/Login.jsp");
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}


}
