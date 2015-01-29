package org.tm.swift.web.actions;

import java.io.Serializable;

import net.sourceforge.stripes.action.ActionBean;
import net.sourceforge.stripes.action.ActionBeanContext;
import net.sourceforge.stripes.action.SimpleMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.tm.swift.constant.Constant;
import org.tm.swift.util.StringUtils;

/**
 * 
 * @author DilNawaz
 * 
 */
abstract class AbstractActionBean implements ActionBean, Serializable {

	private static final long serialVersionUID = -1767714708233127983L;

	protected static final String ERROR = "/WEB-INF/jsp/common/Error.jsp";
	
	protected static final String LOGIN = "/actions/Login.action";
	
	private static final Log log = LogFactory.getLog("AbstractActionBean");

	protected transient ActionBeanContext context;

	protected void setMessage(String value) {
		context.getMessages().add(new SimpleMessage(value));
	}

	public ActionBeanContext getContext() {
		return context;
	}

	public void setContext(ActionBeanContext context) {
		this.context = context;
	}

	protected boolean isLoginRequired () {
		log.debug("Logged In User: " + context.getRequest().getAttribute(Constant.LOGIN));
		if (StringUtils.isEmpty((String)context.getRequest().getAttribute(Constant.LOGIN))){			
			return true;
		}
		return false;
	}
}
