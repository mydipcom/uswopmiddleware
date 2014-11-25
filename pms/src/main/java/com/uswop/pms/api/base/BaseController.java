/**   
* @Title: BaseController.java 
* @Package com.uswop.action 
*
* @Description: 积分管理系统
* 
* @date Sep 10, 2014 3:27:05 PM
* @version V1.0   
*/ 
package com.uswop.pms.api.base;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.support.RequestContext;

import com.uswop.pms.commons.SystemConfig;


/** 
 * <p>Spring控制器的基类</p>
 * <此类实现了一些控制器处理类通用的方法，实际的业务控制器可以根据需要继承此类>
 * @ClassName: BaseController 
 * @author Phills Li 
 *  
 */ 
public class BaseController {
	
	protected final String ERROR_MSG_KEY="errorMsg";		
		
	
	/**
	 * <p>Description:根据资源code获取资源文件中对应的消息</p>
	 * @Title: getMessage 
	 * @param request
	 * @param code
	 * @param args
	 * @return
	 * @throws
	 */
	public String getMessage(HttpServletRequest request,String code,Object[] args){
//		ApplicationContext ctx =WebApplicationContextUtils.getWebApplicationContext(request.getSession().getServletContext());		
//		MessageSource ms=(MessageSource)ctx.getBean("messageSource");
		RequestContext requestContext = new RequestContext(request);
		if(args==null){
			return requestContext.getMessage(code);
		}
		return requestContext.getMessage(code, args);
	}
	
	/**
	 * <p>Description:根据资源code获取资源文件中对应的消息</p>
	 * @Title: getMessage 
	 * @param request
	 * @param code
	 * @param arg
	 * @return
	 * @throws
	 */
	public String getMessage(HttpServletRequest request,String code,Object arg){				
		return getMessage(request,code, new Object[]{arg});
	}
	
	/**
	 * <p>Description:根据资源code获取资源文件中对应的消息</p>
	 * @Title: getMessage 
	 * @param request
	 * @param code
	 * @return
	 * @throws
	 */
	public String getMessage(HttpServletRequest request,String code){				
		return getMessage(request,code, null);
	}
	
	public String getSystemConfigApiKey(HttpServletRequest request){
		ApplicationContext ctx =WebApplicationContextUtils.getWebApplicationContext(request.getSession().getServletContext());
		SystemConfig systemConfig =(SystemConfig)ctx.getBean("systemConfig");
		return systemConfig.getApiKey();
	}
	
	
}
