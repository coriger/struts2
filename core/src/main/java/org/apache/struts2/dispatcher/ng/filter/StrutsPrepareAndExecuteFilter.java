/*
 * $Id: DefaultActionSupport.java 651946 2008-04-27 13:41:38Z apetrelli $
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.struts2.dispatcher.ng.filter;

import org.apache.struts2.StrutsStatics;
import org.apache.struts2.dispatcher.Dispatcher;
import org.apache.struts2.dispatcher.mapper.ActionMapping;
import org.apache.struts2.dispatcher.ng.ExecuteOperations;
import org.apache.struts2.dispatcher.ng.InitOperations;
import org.apache.struts2.dispatcher.ng.PrepareOperations;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Struts2入口  接收请求
 * 通过过滤器的形式来拦截请求
 * Handles both the preparation and execution phases of the Struts dispatching process.  This filter is better to use
 * when you don't have another filter that needs access to action context information, such as Sitemesh.
 */
public class StrutsPrepareAndExecuteFilter implements StrutsStatics, Filter {
	
	// 预处理操作类   设置请求对象编码	响应对象语言环境	创建Action上下文环境	查找请求映射信息
    protected PrepareOperations prepare;
    // 业务逻辑处理操作类	执行真实业务逻辑
    protected ExecuteOperations execute;
    // 排除在外的请求格式（不拦截）
	protected List<Pattern> excludedPatterns = null;

    public void init(FilterConfig filterConfig) throws ServletException {
        InitOperations init = new InitOperations();
        try {
        	// 包装类  包装filterConfig 提供一些方法，内部还有由filterConfig提供服务
            FilterHostConfig config = new FilterHostConfig(filterConfig);
            init.initLogging(config);
            
            // Dispatcher初始化
            Dispatcher dispatcher = init.initDispatcher(config);
            
            init.initStaticContentLoader(config, dispatcher);

            // 构造预处理操作类对象
            prepare = new PrepareOperations(filterConfig.getServletContext(), dispatcher);
            // 构造业务逻辑处理类对象
            execute = new ExecuteOperations(filterConfig.getServletContext(), dispatcher);
			this.excludedPatterns = init.buildExcludedPatternsList(dispatcher);

            postInit(dispatcher, filterConfig);
        } finally {
        	// 初始化清理
            init.cleanup();
        }
    }

    /**
     * Callback for post initialization
     */
    protected void postInit(Dispatcher dispatcher, FilterConfig filterConfig) {
    }

    /**
     * 过滤请求 每次被struts2 Filter拦截的请求都会经过这里
     */
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        try {
        	// 设置请求编码格式和响应语言环境
            prepare.setEncodingAndLocale(request, response);
            
            // 创建ActionContext 上下文环境
            prepare.createActionContext(request, response);
            
            // 分配Dispatcher给每个线程 通过ThreadLocal保证线程安全
            prepare.assignDispatcherToThread();
			if ( excludedPatterns != null && prepare.isUrlExcluded(request, excludedPatterns)) {
				// 不匹配拦截规则 则struts不处理 交由Web容器处理
				chain.doFilter(request, response);
			} else {
				// 把和容器相关的HttpServeltRequest包装成Struts容器中的StrutsRequestWrapper类  和Web容器解耦合
				request = prepare.wrapRequest(request);
				
				// 查找请求Action的映射对象
				ActionMapping mapping = prepare.findActionMapping(request, response, true);
				
				if (mapping == null) {
					// 没找到匹配映射 则判断是否是静态资源请求
					boolean handled = execute.executeStaticResourceRequest(request, response);
					if (!handled) {
						// 也不是静态资源 则交由Web容器处理
						chain.doFilter(request, response);
					}
				} else {
					// 找到匹配映射 执行业务逻辑
					execute.executeAction(request, response, mapping);
				}
			}
        } finally {
        	//　请求结束后的清理操作
            prepare.cleanupRequest(request);
        }
    }

    public void destroy() {
        prepare.cleanupDispatcher();
    }
}
