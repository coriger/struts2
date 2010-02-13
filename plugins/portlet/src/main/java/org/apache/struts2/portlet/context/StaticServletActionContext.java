package org.apache.struts2.portlet.context;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.opensymphony.xwork2.ActionContext;

public class StaticServletActionContext implements ServletActionContextUtil {

    public ActionContext getContext() {
        return org.apache.struts2.ServletActionContext.getContext();
    }

    public HttpServletRequest getRequest() {
        return org.apache.struts2.ServletActionContext.getRequest();
    }

    public HttpServletResponse getResponse() {
        return org.apache.struts2.ServletActionContext.getResponse();
    }

    public ServletContext getServletContext() {
        return org.apache.struts2.ServletActionContext.getServletContext();
    }

}
