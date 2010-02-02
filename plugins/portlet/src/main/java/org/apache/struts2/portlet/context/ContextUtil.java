package org.apache.struts2.portlet.context;

import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletContext;
import javax.portlet.PortletMode;
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
	
	Map<PortletMode,String> getModeNamespaceMap();

	boolean isEvent();

	boolean isRender();

	PortletContext getPortletContext();

}
