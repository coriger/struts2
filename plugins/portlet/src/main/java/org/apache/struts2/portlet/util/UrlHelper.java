package org.apache.struts2.portlet.util;

import java.util.Map;

public interface UrlHelper {

	String buildUrl(String action, String namespace, String method, Map<String,Object> params,
			String type, String mode, String state);

	String buildUrl(String action, String namespace, String method, Map<String,Object> params,
			String scheme, String type, String portletMode, String windowState,
			boolean includeContext, boolean encodeResult);

	String buildResourceUrl(String value, Map<String, Object> params);

	Map<String,String[]> ensureParamsAreStringArrays(Map<String, Object> params);

}
