package org.apache.struts2.portlet.context;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

public interface ContextUtil {
	
	PortletConfig getPortletConfig();
	
	RenderRequest getRenderRequest();
	
	RenderResponse getRenderResponse();
	
	ActionRequest getActionRequest();
	
	ActionResponse getActionResponse();
	
	String getPortletNamespace();
	
	PortletRequest getRequest();
	
	PortletResponse getResponse();

}
