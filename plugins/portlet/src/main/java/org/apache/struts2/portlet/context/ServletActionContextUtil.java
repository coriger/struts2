package org.apache.struts2.portlet.context;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.opensymphony.xwork2.ActionContext;

public interface ServletActionContextUtil {

    HttpServletResponse getResponse();
    
    ServletContext getServletContext();

    HttpServletRequest getRequest();

    ActionContext getContext();

}
