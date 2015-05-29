package com.uswop.pms.api;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.uswop.pms.api.base.BaseController;
import com.uswop.pms.commons.SecurityTools;
import com.uswop.pms.commons.SystemCached;
import com.uswop.pms.commons.SystemConfig;
import com.uswop.pms.commons.UswopException;


@Controller
@RequestMapping("/api/token")
public class TokenAPI extends BaseController {
		
	@Autowired
	private SystemConfig systemConfig;
	
	private Logger logger = Logger.getLogger(TokenAPI.class);
	
	@RequestMapping(value="getToken",method=RequestMethod.POST)
	@ResponseBody
	public String getToken(HttpServletRequest request,HttpServletResponse response,@RequestBody String jsonStr) {		
		
		JSONObject jsonObj=null;
		JSONObject respJson = new JSONObject();						
				
		if(jsonStr == null||jsonStr.isEmpty()) {
			respJson.put("errCode", 40006);
			respJson.put("errMsg", "The request parameter is required.");
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			logger.error("Failed to get API token, the request parameter is empty.");
			return JSON.toJSONString(respJson);
		}
				
		try{
			jsonObj= (JSONObject)JSON.parse(jsonStr);
			String appId=jsonObj.getString("appId");
			String appSecret=jsonObj.getString("appSecret");
			if(systemConfig.getAppId().equals(appId)){
				if(systemConfig.getApiKey().equals(appSecret)){
					JSONObject dataObj=new JSONObject();
					SystemCached.Api_Token_CreatedTime=System.currentTimeMillis();
					SystemCached.Api_Token=SecurityTools.SHA1(systemConfig.getAppId()+SystemCached.Api_Token_CreatedTime+systemConfig.getAppPublicKey());
					
					dataObj.put("accessToken", SystemCached.Api_Token);
					dataObj.put("expiresIn", systemConfig.getTokenExpiresIn());
					respJson.put("errCode", 0);
					respJson.put("errMsg", "OK");
					respJson.put("data", dataObj);
					logger.info("Get the API token successfully.");
					return JSON.toJSONString(respJson);	
				}
				else{
					respJson.put("errCode", 40003);
					respJson.put("errMsg", "The parameter appSecret is error.");
					response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				}
				
				return JSON.toJSONString(respJson);				
			}
			else{
				respJson.put("errCode", 40002);
				respJson.put("errMsg", "The parameter appId is error.");
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			}
			logger.error("Failed to get API token, appId or appSecret is error.");															
			return JSON.toJSONString(respJson);
		}
		catch(UswopException e){
			respJson.put("errCode", -1);
			respJson.put("errMsg", e.getMessage());
			logger.error("Failed to get API token, the exception information is : "+e.getMessage());
			return JSON.toJSONString(respJson);
		}		
	}
		
	@RequestMapping(value="getToken",method=RequestMethod.GET)
	@ResponseBody
	public String getToken(HttpServletRequest request,HttpServletResponse response) {						
		JSONObject respJson = new JSONObject();										
						
		try{			
			String appId=request.getParameter("appId");
			String appSecret=request.getParameter("appSecret");
			if(systemConfig.getAppId().equals(appId)){
				if(systemConfig.getApiKey().equalsIgnoreCase(appSecret)){
					JSONObject dataObj=new JSONObject();
					SystemCached.Api_Token_CreatedTime=System.currentTimeMillis();
					SystemCached.Api_Token=SecurityTools.SHA1(systemConfig.getAppId()+SystemCached.Api_Token_CreatedTime+systemConfig.getAppPublicKey());
					
					dataObj.put("accessToken", SystemCached.Api_Token);
					dataObj.put("expiresIn", systemConfig.getTokenExpiresIn());
					respJson.put("errCode", 0);
					respJson.put("errMsg", "OK");
					respJson.put("data", dataObj);
					logger.info("Get the API token successfully.");
					return JSON.toJSONString(respJson);	
				}
				else{
					respJson.put("errCode", 40003);
					respJson.put("errMsg", "The parameter appSecret is error.");
					response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				}
				
				return JSON.toJSONString(respJson);				
			}
			else{
				respJson.put("errCode", 40002);
				respJson.put("errMsg", "The parameter appId is error.");
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			}
			logger.error("Failed to get API token, appId or appSecret is error.");															
			return JSON.toJSONString(respJson);
		}
		catch(UswopException e){
			respJson.put("errCode", -1);
			respJson.put("errMsg", e.getMessage());
			logger.error("Failed to get API token, the exception information is : "+e.getMessage());
			return JSON.toJSONString(respJson);
		}		
	}
			
	
}
