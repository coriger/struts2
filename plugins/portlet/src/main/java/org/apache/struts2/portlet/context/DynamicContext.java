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

import org.apache.struts2.StrutsStatics;
import org.apache.struts2.dispatcher.mapper.ActionMapping;
import org.apache.struts2.portlet.PortletActionConstants;

import com.opensymphony.xwork2.ActionContext;

public class DynamicContext implements ContextUtil , PortletActionConstants {

	/**
	 * Get the PortletConfig of the portlet that is executing.
	 * 
	 * @return The PortletConfig of the executing portlet.
	 */
	public PortletConfig getPortletConfig() {
		return (PortletConfig) getContext().get(PORTLET_CONFIG);
	}

	/**
	 * Get the RenderRequest. Can only be invoked in the render phase.
	 * 
	 * @return The current RenderRequest.
	 * @throws IllegalStateException
	 *             If the method is invoked in the wrong phase.
	 */
	public RenderRequest getRenderRequest() {
		if (!isRender()) {
			throw new IllegalStateException(
					"RenderRequest cannot be obtained in event phase");
		}
		return (RenderRequest) getContext().get(REQUEST);
	}

	/**
	 * Get the RenderResponse. Can only be invoked in the render phase.
	 * 
	 * @return The current RenderResponse.
	 * @throws IllegalStateException
	 *             If the method is invoked in the wrong phase.
	 */
	public RenderResponse getRenderResponse() {
		if (!isRender()) {
			throw new IllegalStateException(
					"RenderResponse cannot be obtained in event phase");
		}
		return (RenderResponse) getContext().get(RESPONSE);
	}

	/**
	 * Get the ActionRequest. Can only be invoked in the event phase.
	 * 
	 * @return The current ActionRequest.
	 * @throws IllegalStateException
	 *             If the method is invoked in the wrong phase.
	 */
	 public ActionRequest getActionRequest() {
		if (!isEvent()) {
			throw new IllegalStateException(
					"ActionRequest cannot be obtained in render phase");
		}
		return (ActionRequest) getContext().get(REQUEST);
	}

	/**
	 * Get the ActionRequest. Can only be invoked in the event phase.
	 * 
	 * @return The current ActionRequest.
	 * @throws IllegalStateException
	 *             If the method is invoked in the wrong phase.
	 */
	 public ActionResponse getActionResponse() {
		if (!isEvent()) {
			throw new IllegalStateException(
					"ActionResponse cannot be obtained in render phase");
		}
		return (ActionResponse) getContext().get(RESPONSE);
	}

	/**
	 * Get the action namespace of the portlet. Used to organize actions for
	 * multiple portlets in the same portlet application.
	 * 
	 * @return The portlet namespace as defined in <code>portlet.xml</code> and
	 *         <code>struts.xml</code>
	 */
	 public String getPortletNamespace() {
		return (String) getContext().get(PORTLET_NAMESPACE);
	}

	/**
	 * Get the current PortletRequest.
	 * 
	 * @return The current PortletRequest.
	 */
	 public PortletRequest getRequest() {
		return (PortletRequest) getContext().get(REQUEST);
	}

	/**
	 * Convenience setter for the portlet request.
	 * 
	 * @param request
	 */
	 public void setRequest(PortletRequest request) {
		getContext().put(REQUEST, request);
	}

	/**
	 * Get the current PortletResponse
	 * 
	 * @return The current PortletResponse.
	 */
	 public PortletResponse getResponse() {
		return (PortletResponse) getContext().get(RESPONSE);
	}

	/**
	 * Convenience setter for the portlet response.
	 * 
	 * @param response
	 */
	 public void setResponse(PortletResponse response) {
		getContext().put(RESPONSE, response);
	}

	/**
	 * Get the phase that the portlet is executing in.
	 * 
	 * @return {@link PortletActionConstants#RENDER_PHASE} in render phase, and
	 *         {@link PortletActionConstants#EVENT_PHASE} in the event phase.
	 */
	 public Integer getPhase() {
		return (Integer) getContext().get(PHASE);
	}

	/**
	 * @return <code>true</code> if the Portlet is executing in render phase.
	 */
	 public boolean isRender() {
		return PortletActionConstants.RENDER_PHASE.equals(getPhase());
	}

	/**
	 * @return <code>true</code> if the Portlet is executing in the event phase.
	 */
	 public boolean isEvent() {
		return PortletActionConstants.EVENT_PHASE.equals(getPhase());
	}

	/**
	 * @return The current ActionContext.
	 */
	private static ActionContext getContext() {
		return ActionContext.getContext();
	}

	/**
	 * Check to see if the current request is a portlet request.
	 * 
	 * @return <code>true</code> if the current request is a portlet request.
	 */
	 public boolean isPortletRequest() {
		return getRequest() != null;
	}

	/**
	 * Get the default action mapping for the current mode.
	 * 
	 * @return The default action mapping for the current portlet mode.
	 */
	 public ActionMapping getDefaultActionForMode() {
		return (ActionMapping) getContext().get(DEFAULT_ACTION_FOR_MODE);
	}

	/**
	 * Get the namespace to mode mappings.
	 * 
	 * @return The map of the namespaces for each mode.
	 */
	 @SuppressWarnings("unchecked")
	public Map<PortletMode,String> getModeNamespaceMap() {
		return (Map<PortletMode,String>) getContext().get(MODE_NAMESPACE_MAP);
	}

	/**
	 * Get the portlet context.
	 * 
	 * @return The portlet context.
	 */
	 public PortletContext getPortletContext() {
		return (PortletContext) getContext().get(
				StrutsStatics.STRUTS_PORTLET_CONTEXT);
	}

	/**
	 * Convenience setter for the portlet context.
	 * 
	 * @param context
	 */
	 public void setPortletContext(PortletContext context) {
		getContext().put(StrutsStatics.STRUTS_PORTLET_CONTEXT, context);
	}

	/**
	 * Gets the action mapping for this context
	 * 
	 * @return The action mapping
	 */
	 public ActionMapping getActionMapping() {
		return (ActionMapping) getContext().get(ACTION_MAPPING);
	}

}
